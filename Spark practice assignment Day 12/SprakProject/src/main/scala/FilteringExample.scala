import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object FilteringExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Filtering Example").master("local[*]").getOrCreate()

    val numbersRDD = spark.sparkContext.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    val evenNumbersRDD = numbersRDD.filter(_ % 2 == 0)

    evenNumbersRDD.collect().foreach(println)

    spark.stop()
  }
}
