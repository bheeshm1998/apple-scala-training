import java.sql.{Connection, DriverManager, ResultSet, Statement}
import scala.io.Source

@main
def main(): Unit = {

  val filePath = "/Users/abhishekanand/Documents/Scala Training/Day5Task/src/main/scala/data.csv"

  val bufferedSource = Source.fromFile(filePath)
  var listOfEmployees: List[Employee] = List();
  var index: Int = 0;
  for (line <- bufferedSource.getLines ) {
    if (index > 0) {
      val Array(id, name, city, salary, department) = line.split(",").map(_.trim)
      val emp: Employee = Employee(id.toInt, name, city, salary.toDouble, department);
      listOfEmployees = listOfEmployees :+ emp;
    }
    index += 1;
  }

  var departmentsList: List[Department] = List()
  for(emp <- listOfEmployees){
    departmentsList = departmentsList :+ Department(emp.dept)
  }
  departmentsList = departmentsList.distinct
  println("The unique departments are " + departmentsList)

  val connection: Connection = setupDdatabaseConnection("jdbc:mysql://hadoop-server.mysql.database.azure.com:3306/abhishek", "sqladmin", "Password@12345");
  println("Persisting all the departments")
  persistDepartmentsToDB(connection, departmentsList);
  println("Persisting the list of employees")
  persistEmployeesToDB(connection, listOfEmployees);

printDepartmentAndEmployees(connection);
  println("---Closing the Database connection---")
  connection.close()

  bufferedSource.close()
}

case class Employee(sno: Int, name: String, city: String, salary: Double, dept: String)
case class Department(name: String)


def printOrganizationChart(department: Department) = {

}

def persistDepartmentsToDB(connection: Connection, listOfDepartments: List[Department]) = {
  try {
    val statement: Statement = connection.createStatement()

    // Create a table
    val createTableSQL =
      """
        |CREATE TABLE IF NOT EXISTS department (
        |  id INT AUTO_INCREMENT PRIMARY KEY,
        |  name VARCHAR(100))
      """.stripMargin

    statement.execute(createTableSQL)
    println("Table created successfully.")

    for(dept <- listOfDepartments) {
      val insertSQL =
        s"""
          |INSERT INTO department (name)
          |VALUES ('${dept.name}')
          """.stripMargin

      println("the insert sql command is "+ insertSQL);
      statement.executeUpdate(insertSQL)
    }

    println("Data inserted successfully into the department table.")
  } catch {
    case e: Exception => println("Some error occurred while inserting data to the department table" + e.printStackTrace());
  } finally {
    // Close Statement and Connection
  }
}

def persistEmployeesToDB(connection: Connection, employeesList: List[Employee]) = {

  try {
    val createTableSQL =
      """CREATE TABLE employee (
        |    id INT AUTO_INCREMENT PRIMARY KEY,
        |    name VARCHAR(100) NOT NULL,
        |    city VARCHAR(100) NOT NULL,
        |    salary DOUBLE NOT NULL,
        |    department_id INT ,
        |    FOREIGN KEY (department_id) REFERENCES department(id)
        |);""".stripMargin
    val statement: Statement = connection.createStatement()
    statement.execute(createTableSQL)

    for(emp <- employeesList){
      val deptId: Int = getDepartmentId(connection, emp.dept);
      val insertEmpSQl =
        s"""INSERT INTO employee (name, city, salary, department_id ) VALUES ('${emp.name}', '${emp.city}', '${emp.salary}', ${deptId})""".stripMargin
      statement.executeUpdate(insertEmpSQl)
    }

  } catch {
    case e: Exception => println("Exception occured in persisting data to the employees table " + e.getMessage);
  } finally {

  }
}

def setupDdatabaseConnection(url: String, username: String, password: String): Connection = {
  Class.forName("com.mysql.cj.jdbc.Driver")

  val url = "jdbc:mysql://hadoop-server.mysql.database.azure.com:3306/abhishek"
  val username = "sqladmin"
  val password = "Password@12345"
  val connection: Connection = DriverManager.getConnection(url, username, password)
  println("DB connection successful")
  connection
}

def getDepartmentId(connection: Connection, deptName: String): Int = {
  val statement: Statement = connection.createStatement();
  val query = s"SELECT id FROM department where name = '${deptName}'"
  val resultSet: ResultSet = statement.executeQuery(query)
  if(resultSet.next()){
    resultSet.getInt("id");
  } else{
    0;
  }
}

def printDepartmentAndEmployees(connection: Connection) = {
  try {
    val statement: Statement = connection.createStatement();
    val getAllDepartmentsSQL = "SELECT * from department";
    val resultSet: ResultSet = statement.executeQuery(getAllDepartmentsSQL);
    while(resultSet.next()){
      val deptId = resultSet.getInt("id");
      val deptName = resultSet.getString("name");
      val getEmployeesOfADepartment = s"SELECT * from employee where department_id=${deptId}"
      val empStatement: Statement = connection.createStatement();
      val empResultSet: ResultSet = empStatement.executeQuery(getEmployeesOfADepartment);
      println(deptName)
      while(empResultSet.next()){
        val empName: String = empResultSet.getString("name")
        val empCity: String = empResultSet.getString("city")
        val salary: Double = empResultSet.getDouble("salary")
        println(s"     ${empName} --- ${empCity} --- ${salary}")
      }
    }
  } catch {
      case e: Exception => println("some exception occurred while printing " + e.getMessage)
  } finally {
    println("DONE printing, in the finally block")
  }


}
