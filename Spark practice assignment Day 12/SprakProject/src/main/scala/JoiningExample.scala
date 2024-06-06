import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object JoiningExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Joining Example").master("local[*]").getOrCreate()

    val studentsRDD = spark.sparkContext.parallelize(Seq((1, "John"), (2, "Alice"), (3, "Bob")))
    val scoresRDD = spark.sparkContext.parallelize(Seq((1, 90), (2, 80), (3, 70)))

    val joinedRDD = studentsRDD.join(scoresRDD)

    joinedRDD.collect().foreach(println)

    spark.stop()
  }
}
