import java.sql.{Connection, ResultSet, Statement}
//Task2
//-----
//
//From the employee table a 4 member
//  from sales need to be sent on assignment
//  to client
//
//Create a function that gives
//all the combinations that are possible (Note: Not
//permutations)
//Use Backtracking concept to come up with the algorithm

// Fetch all the records from the employee table which has the profession Sales
// Make a list out of them
// Generate all combinations of 4 from them (the four sales people)
case class EmployeeAbstractInfo(name: String, city: String, salary: Double){
  override def toString: String = name
}

object Task2 extends App {
  var listOfEmployees: List[EmployeeAbstractInfo] = List();
  val connection: Connection = setupDdatabaseConnection("jdbc:mysql://hadoop-server.mysql.database.azure.com:3306/abhishek", "sqladmin", "Password@12345");
  println("Data base connected successfully");
  val deptId = getDepartmentId(connection, "Sales");

  listOfEmployees = getEmployeesOfADepartment(connection, deptId);
  printCombinationsOfTheEmployees(listOfEmployees);
}

def getEmployeesOfADepartment(connection: Connection, deptId: Int): List[EmployeeAbstractInfo] = {
  val statement: Statement = connection.createStatement();

  val getSalesEmployeesSQL =
    s"""SELECT * FROM EMPLOYEE WHERE DEPARTMENT_ID = $deptId""".stripMargin
  val resultSet: ResultSet = statement.executeQuery(getSalesEmployeesSQL);
  var employeesList: List[EmployeeAbstractInfo] = List();
  while (resultSet.next()) {
    val empName: String = resultSet.getString("name")
    val empCity: String = resultSet.getString("city")
    val salary: Double = resultSet.getDouble("salary")
    employeesList = employeesList :+ EmployeeAbstractInfo(empName, empCity, salary);
  }
  employeesList;

}

def printCombinationsOfTheEmployees(employees: List[EmployeeAbstractInfo]): Unit = {
  var allCombos: List[List[EmployeeAbstractInfo]] = List();
  var currentCombo: List[EmployeeAbstractInfo] = List();
  allCombos = helper(employees, currentCombo, 4, 0, allCombos);
  println("Printing combinations of employees")
  println(employees.length)
  println(allCombos.length)
//  allCombos.foreach(comb => println(comb));
}

def helper(listOfEmployees: List[EmployeeAbstractInfo], currentCombination: List[EmployeeAbstractInfo], reqSize: Int, startIndex: Int, allCombinations: List[List[EmployeeAbstractInfo]]): List[List[EmployeeAbstractInfo]] = {
  var updatedCombinations = allCombinations;
  var currentCombo = currentCombination;
  if(currentCombination.length == reqSize){
    updatedCombinations = updatedCombinations :+ currentCombination
//    println(updatedCombinations)
    println(currentCombination)
  }
  for(i <- startIndex until listOfEmployees.length){
    currentCombo = currentCombo :+ listOfEmployees(i);
    updatedCombinations = helper(listOfEmployees, currentCombo, reqSize, i + 1, allCombinations);
    currentCombo = currentCombo.dropRight(1)
  }
  updatedCombinations
}

