package org.example

import net.liftweb.json.{JArray, JString, compactRender}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer

object P3 extends Problem {

  override var dataPath: String = "src/main/data/friends.json"
  override var resultFileName: String = "src/main/solutions/friends_count.json"

  override def execute(resultName: String, dataList: Array[String]): List[String] = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Problem3")
      .getOrCreate();
    val sc=spark.sparkContext

    val dataArray=FileHandler.getData(dataPath)
    var list=new ListBuffer[List[String]]
    for(line<-dataArray){
      val data=JsonHandler.parseToList(line)
      list+=data
    }
    val out=sc.parallelize(list).map{

      (item:List[String])=>(item.head,1)
    }
      .reduceByKey{
        (count1,count2)=>count1+count2
      }
      .groupByKey.collect().toList


    return  outPutMaker(out)



  }
  def outPutMaker(list: List[(String,Iterable[Int])]): List[String] = {
    val result = ListBuffer(list.toSeq.sortBy(_._1): _*)
    //map list to json
    val jsonList= result.map {
      r =>
        compactRender(JArray(List(JString(r._1),JString(String.valueOf(r._2.head)))))
    }.toList
    return  jsonList
  }
}
