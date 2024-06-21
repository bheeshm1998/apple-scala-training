
import com.example.user.User

object ProtobufDeserializationExample {
  def main(args: Array[String]): Unit = {
    // Serialized byte array (same as the one from the serialization example)
    val serializedUser: Array[Byte] = Array(8, 1, 18, 8, 74, 111, 104, 110, 32, 68, 111, 101, 26, 21, 106, 111, 104, 110, 46, 100, 111, 101, 64, 101, 120, 97, 109, 112, 108, 101, 46, 99, 111, 109, 32, 30)

    // Deserialize the byte array back to a User instance
    val deserializedUser: User = User.parseFrom(serializedUser)

    // Print the deserialized User instance
    println(s"Deserialized User: ${deserializedUser}")
  }
}
