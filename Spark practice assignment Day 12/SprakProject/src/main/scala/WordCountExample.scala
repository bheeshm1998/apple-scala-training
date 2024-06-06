import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object WordCountExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Word Count").master("local[*]").getOrCreate()

    val text = spark.sparkContext.textFile("/Users/abhishekanand/IdeaProjects/SprakProject/src/main/scala/sample-file.txt")
    val wordCount = text.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)

    wordCount.collect().foreach(println)

    spark.stop()
  }
}
