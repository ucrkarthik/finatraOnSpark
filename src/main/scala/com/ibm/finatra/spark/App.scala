package com.ibm.finatra.spark

import com.twitter.finatra._
import com.twitter.util.Future
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object App extends FinatraServer {

  val jarFlag = flag("jars", "target/finatraOnSpark-1.0.0.jar", "jars to distribute")

  val master = "local[4]"
  println("master: " + master)

  val conf = new SparkConf()
    .setMaster(master)
    .setAppName("Finatra OnSpark")
    .set("spark.ui.port", "4049")

  //Begin main script
  val sc = new SparkContext(conf)

  //Adding JAR dependency for all tasks to be executed on this SparkContext.
  val jarList = jarFlag().split(":").foreach(jar => {
    println("adding jar: " + jar);
    if (jar.endsWith("jar"))
      sc.addJar(jar)
    else
      sc.addFile(jar, true)
  })

  //Register the Graph Service to Finatra
  register(new GraphApp(sc))

  class GraphApp(sc: SparkContext) extends Controller {
    val INPUT_FILE = "input.file"


    get("/wordCount") { request =>
      val errorList = scala.collection.mutable.ArrayBuffer[String]()

      val inputFile = request.params.get(INPUT_FILE)
      if (inputFile.isEmpty) errorList += INPUT_FILE

      if (errorList.size > 0) {
        Future(render.json(Map(
          "success" -> false,
          "message" -> {
            "the following parameters were not defined: " + errorList.mkString("; ")
          })))
      }
      else {
        val textFile = sc.textFile(inputFile.get)

        //Simple Word Count
        val counts: RDD[(String, Int)] = textFile.flatMap(line => line.split(" "))
          .map(word => (word, 1))
          .reduceByKey(_ + _)

        //Remove empty key, sort by value and create a new map
        val retValue = counts.filter{case (x,y) => !x.isEmpty}.sortBy(_._2, false).map{case (x, y) => Map("text" -> x, "size" -> y)}

        // Collect the values from the partition and take the top 100 results and convert it to a JSON response.
        Future(render.json(retValue.collect().take(100)))
      }
    }

  }
}


