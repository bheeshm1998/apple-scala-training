import org.apache.spark.sql.SparkSession

object Main {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("S3 Example with Spark")
      .master("local[*]")
      .config("spark.hadoop.fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider")
      .getOrCreate()

    val hadoopConfig = spark.sparkContext.hadoopConfiguration
//    hadoopConfig.set("fs.s3a.access.key", "")
//    hadoopConfig.set("fs.s3a.secret.key", "")
    hadoopConfig.set("fs.s3a.endpoint", "s3.amazonaws.com")
    hadoopConfig.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

    val s3Data = spark.read.json("s3a://abhishek-iam-3akka/sample.json")

    val filteredData = s3Data.filter("age > 25")
    println("Showing processed data")

    println("Showing filtered data")
    // show filtered data
    filteredData.show()

    val jdbcUrl = "jdbc:mysql://34.100.169.199/spark"
    val tableName = "user"
    val properties = new java.util.Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "abhi@iit123")
    println("writing to the database")
    filteredData.write.mode("overwrite")
      .jdbc(jdbcUrl, tableName, properties)
  }
}
