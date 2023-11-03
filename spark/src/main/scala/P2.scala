package org.example

import net.liftweb.json.{JArray, JString, compactRender}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer

object P2 extends Problem {

  override var dataPath: String = "src/main/data/records.json"
  override var resultFileName: String = "src/main/solutions/join.json"

  override def execute(resultName: String, dataList: Array[String]): List[String] = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Problem2")
      .getOrCreate();
    val sc = spark.sparkContext

    val dataArray = FileHandler.getData(dataPath)
    var list = new ListBuffer[List[String]]
    for (line <- dataArray) {
      val data = JsonHandler.parseToList(line)
      list += data
    }
    val input = sc.parallelize(list).map {

      (item: List[String]) => (item(1), item)
    }
    val orders = input.filter(_._2(0).equals("order"))
    val line_items = input.filter(_._2(0).equals("line_item"))
    orders.cache()
    line_items.cache()
    val out = orders
      .join(line_items).collect().toList
    println(out(0))
      return  outPutMaker(out)
    //return List("")

  }

  def outPutMaker(list: List[(String, (List[String], List[String]))]): List[String] = {

    //map list to json
    val jsonList = list.map {
      r =>


        compactRender(
          JArray(List(JArray((r._2._1.map(r1 => JString(r1)))), JArray((r._2._2.map(r2 => JString(r2))))))
        )

    }
     jsonList
  }
}
