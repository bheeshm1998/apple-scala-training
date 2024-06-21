import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object WindowFunctions extends App {

  val spark = SparkSession.builder
    .appName("Window Functions Example")
    .master("local[*]")
    .getOrCreate()

  val data = Seq(
    ("John", "Sales", 1000),
    ("Mary", "Sales", 2000),
    ("David", "HR", 1500),
    ("Amy", "HR", 1200),
    ("Jake", "Sales", 2500),
    ("Paul", "HR", 1800)
  )

  val df = spark.createDataFrame(data).toDF("name", "department", "salary")

  // Define window specification
  val windowSpec = Window.partitionBy("department").orderBy("salary")

  // Rank function
  val rankDF = df.withColumn("rank", rank().over(windowSpec))
  rankDF.show()

  // Cumulative sum function
  val cumSumDF = df.withColumn("cumulative_sum", sum("salary").over(windowSpec))
  cumSumDF.show()

  // Row number function
  val rowNumberDF = df.withColumn("row_number", row_number().over(windowSpec))
  rowNumberDF.show()
}