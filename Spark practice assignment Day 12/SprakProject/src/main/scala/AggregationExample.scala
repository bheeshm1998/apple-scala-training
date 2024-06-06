import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object AggregationExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Aggregation Example").master("local[*]").getOrCreate()

    val dataRDD = spark.sparkContext.parallelize(Seq(1, 2, 3, 4, 5))

    val sum = dataRDD.reduce(_ + _)
    val count = dataRDD.count()
    val mean = sum.toDouble / count

    println(s"Sum: $sum")
    println(s"Count: $count")
    println(s"Mean: $mean")

    spark.stop()
  }
}
