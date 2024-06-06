object ImplicitExample extends App {

  implicit def intToString(x: Int): String = x.toString

  val str: String = 123  // Implicitly converts Int to String
  println(str)  // Output: "123"

  implicit def oldToNew(old: OldClass): NewClass = new NewClass

  val old = new OldClass
  old.oldMethod()  // Calls the old method
  old.newMethod()  // Implicitly converts to NewClass and calls newMethod

  def greet(name: String)(implicit greeting: String): Unit = {
    println(s"$greeting, $name")
  }

  implicit val defaultGreeting: String = "Hello"

  greet("Alice")  // Output: "Hello, Alice"


//  implicit val defaultPrefix: String = "User Info"

  val user = User("Bob")
  printUserInfo(user)  // Output: "User Info: Bob"

  def printUserInfo(user: User)(implicit prefix: String): Unit = {
    println(s"$prefix: ${user.name}")
  }

}
class OldClass {
  def oldMethod(): Unit = println("Old method")
}

class NewClass {
  def newMethod(): Unit = println("New method")
}

case class User(name: String)








