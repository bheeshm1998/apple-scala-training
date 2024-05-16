@main
def printMatrix(): Unit = {

  val rows = 3
  val cols = 3

  val seatMatrix = Array.ofDim[String](rows, cols);

  seatMatrix(0)(0) = "0"
  seatMatrix(0)(1) = "1"
  seatMatrix(0)(2) = "2"
  seatMatrix(1)(0) = "3"
  seatMatrix(1)(1) = "4"
  seatMatrix(1)(2) = "5"
  seatMatrix(2)(0) = "6"
  seatMatrix(2)(1) = "7"
  seatMatrix(2)(2) = "X"

  printMatrix(seatMatrix)

  var breakLoop: Boolean = false;
  while(!breakLoop){
    print("Enter the seat number to book: ")
    val inputString = scala.io.StdIn.readLine()
    try {
      val seatIndex = inputString.toInt
      assignSeat(seatMatrix, seatIndex, printMatrix)
      println("Booked!")
    } catch {
      case ex: Exception => println("Exiting the booking process");
      breakLoop = true;
    }
  }
}

def assignSeat(seats: Array[Array[String]], seatToAssign: Int, printSeatMatrix: Array[Array[String]] => Unit) = {
  println(s"Trying to book the seat: $seatToAssign")
  val rows = seats.length;
  val cols = seats(0).length;
  var breakLoop: Boolean = false;
  for(i <- 0 until rows){
    for(j <- 0 until cols){
      val seatRow = seatToAssign / cols;
      val seatCol = seatToAssign % cols;
      if(seatRow == i && seatCol == j && !breakLoop){
        println("Found an empty seat " + seatToAssign + " ")
        seats(i)(j) = "X";
        breakLoop = true;
      }
    }
  }
  printSeatMatrix(seats);
}

def printMatrix(matrix: Array[Array[String]]): Unit = {
  val rows = matrix.length;
  val cols = matrix(0).length;

  for (i <- 0 until rows) {
    for (j <- 0 until cols) {
      print(matrix(i)(j) + " ")
    }
    println()
  }
}
