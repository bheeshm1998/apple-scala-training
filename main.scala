import scala.io.Source

@main
def main(): Unit = {

  val filePath = "/Users/abhishekanand/Documents/Scala Training/Day4Task/src/main/scala/data.csv"

  val bufferedSource = Source.fromFile(filePath)
  var listOfEmployees: List[Employee] = List();
  var index: Int = 0;
  for (line <- bufferedSource.getLines ) {
    if (index > 0) {
      val Array(serialNo, name, city, salary, department) = line.split(",").map(_.trim)
      val emp: Employee = Employee(serialNo.toInt, name, city, salary.toDouble, department);
      listOfEmployees = listOfEmployees :+ emp;
    }
    index += 1;
  }

  val salesEmpWithSalaryMoreThan5000 = listOfEmployees.filter(emp => emp.salary >= 50000 && emp.depat.equalsIgnoreCase("sales"))
  println("\nEmployees with Department sales and salary more than 5000")
  salesEmpWithSalaryMoreThan5000.foreach(println)
  val employeeNamesInCapsStartingWithJ = listOfEmployees.map(emp => emp.name.toUpperCase).filter(item => item.startsWith("J"));
  println("\nEmployee names in capital letters starting with J")
  employeeNamesInCapsStartingWithJ.foreach(println)

  val groupedEmployees = listOfEmployees.groupBy(_.depat)

  println("\nDepartment based stats of the employees")
  val departmentStats = groupedEmployees.view.mapValues { employees =>
    val totalEmpoyees = employees.length
    val totalSalary = employees.map(_.salary).sum
    val averageSalary = totalSalary / employees.length.toDouble
    (totalEmpoyees, totalSalary, averageSalary)
  }

  departmentStats.foreach { case (department, (totalEmployees, totalSalary, averageSalary)) =>
    println(s"Department: $department, Total Employees: $totalEmployees, Total Salary: $totalSalary, Average Salary: $averageSalary")
  }
  bufferedSource.close()
}

case class Employee(sno: Int, name: String, city: String, salary: Double, depat: String)