import org.apache.spark.sql.SparkSession

import java.util.Properties

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("SparkRDMSAndS3")
      .master("local[*]")
      .config("spark.hadoop.fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider")
      .getOrCreate()

    val hadoopConfig = spark.sparkContext.hadoopConfiguration

    hadoopConfig.set("fs.s3a.endpoint", "s3.amazonaws.com")
    hadoopConfig.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

    val jdbcUrl = "jdbc:mysql://34.100.169.199/spark"
    val tableName = "user"
    val properties = new Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "abhi@iit123")

    val df = spark.read.jdbc(jdbcUrl, tableName, properties)

    df.show()

    val filteredDf = df.filter("age> 25")

    filteredDf.show()

    val csvPath = "s3a://abhishek-iam-3akka/sample.csv"
    filteredDf.write.mode("overwrite").csv(csvPath)

    spark.stop()
  }
}
