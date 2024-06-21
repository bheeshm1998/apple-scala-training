import com.example.user.User

object ProtobufSerializationExample {
  def main(args: Array[String]): Unit = {
    // Create a new User instance
    val user = User(
      id = 1,
      name = "John Doe",
      email = "john.doe@example.com",
      age = 30
    )

    // Serialize the User instance to a byte array
    val serializedUser: Array[Byte] = user.toByteArray

    // Print the serialized byte array
    println(s"Serialized User: ${serializedUser.mkString(",")}")
  }
}
