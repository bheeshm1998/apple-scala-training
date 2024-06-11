import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.functions._

object CSVtoJSON {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("CSV to JSON Converter")
      .getOrCreate()

    val inputPath = "hdfs://0.0.0.0:9000/student.csv"
    val outputPath = "hdfs://0.0.0.0:9000/output/student.json"

    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(inputPath)

    val filteredDf = df.filter(col("Age") > 21)

    filteredDf.write
      .mode("overwrite")
      .json(outputPath)
    println("DONE DONE DONE DONE DONE DONE")

    val jdbcUrl = "jdbc:mysql://34.100.169.199/spark"
    val tableName = "student"
    val properties = new java.util.Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "abhi@iit123")
    properties.setProperty("driver", "com.mysql.cj.jdbc.Driver")
    println("writing to the database")
    filteredDf.write.mode("overwrite")
      .jdbc(jdbcUrl, tableName, properties)

    spark.stop()
  }
}