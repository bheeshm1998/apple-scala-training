object UnderscroreOperators extends App{
  var x: Int = _
  println(x)


  val numbers = List(1, 2, 3, 4, 5)


  val doubled = numbers.map(_ * 2)
  println(doubled)


  val someNumber = 42

  someNumber match {
    case 0 => println("Zero")
    case _ => println("Not zero")
  }

  val ignoreSecondParam = (a: Int, _: Int) => a
  println(ignoreSecondParam(1, 2))

  def add(a: Int, b: Int): Int = a + b

  val addFive = add(5, _: Int)
  println(addFive(3))

  import scala.collection.immutable.{Map, _}

  val partialFunction: PartialFunction[Int, String] = {
    case 1 => "One"
    case 2 => "Two"
    case _ => "Other"
  }

  println(partialFunction(2))
  println(partialFunction(3))

  val pairs = List((1, "one"), (2, "two"), (3, "three"))

  for ((num, _) <- pairs) {
    println(num)
  }

  def multiply(a: Int, b: Int): Int = a * b

  val multiplyByTwo = multiply(2, _: Int)
  println(multiplyByTwo(3))


}
