import org.apache.spark.sql.SparkSession

object DataFramesExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DataFrames Example").master("local[*]").getOrCreate()

    val data = Seq((1, "John", 25), (2, "Alice", 30), (3, "Bob", 25))
    val df = spark.createDataFrame(data).toDF("id", "name", "age")

    df.show()

    spark.stop()
  }
}
