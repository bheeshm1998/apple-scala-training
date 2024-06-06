import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset

case class Person(id: Int, name: String, age: Int)

object DataSetsExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DataSets Example").master("local[*]").getOrCreate()

    import spark.implicits._
    val data = Seq(Person(1, "John", 25), Person(2, "Alice", 30), Person(3, "Bob", 25))
    val ds: Dataset[Person] = spark.createDataset(data)

    ds.show()

    spark.stop()
  }
}
