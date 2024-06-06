import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object GroupingExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Grouping Example").master("local[*]").getOrCreate()

    val dataRDD = spark.sparkContext.parallelize(Seq("John 25", "Alice 30", "Bob 25", "Eve 30"))
    val groupedRDD = dataRDD.map(line => (line.split(" ")(1).toInt, line)).groupByKey().mapValues(_.toList)

    groupedRDD.collect().foreach(println)

    spark.stop()
  }
}
