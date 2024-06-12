import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object nested_json extends App{
  val spark=SparkSession.builder().master("local[*]").appName("nested_json").getOrCreate()
  val employee_df = spark.read
    .format("jdbc")
    .option("url", "jdbc:mysql://34.100.169.199:3306/spark")
    .option("dbtable", "employee")
    .option("user", "root")
    .option("password", "abhi@iit123")
    .load()

  val department_df=spark.read
    .format("jdbc")
    .option("url", "jdbc:mysql://34.100.169.199:3306/spark")
    .option("dbtable", "department")
    .option("user", "root")
    .option("password", "abhi@iit123")
    .load()


  val joined_df=department_df.join(employee_df,department_df("id")===employee_df("department_id"),"inner")
  val output_df=joined_df.groupBy(department_df("id"),department_df("name")).agg(collect_list(employee_df("name")).as("Employee names"))

  output_df.show()
  output_df.write.mode("overwrite").format("json").save("/Users/abhishekanand/Documents/Day16Task/result.json")
}