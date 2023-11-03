package org.example

import net.liftweb.json.{JArray, JString, compactRender}
import org.apache.spark.sql.{SQLContext, SparkSession}

import scala.collection.mutable.ListBuffer

object P1 extends Problem {

  override var dataPath: String = "src/main/data/books.json"
  override var resultFileName: String = "src/main/solutions/inverted_index.json"

  override def execute(resultName: String, dataList: Array[String]): List[String] = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Problem1")
      .getOrCreate();
    val sc=spark.sparkContext

    val dataArray=FileHandler.getData(dataPath)
    var list=new ListBuffer[List[String]]
    for(line<-dataArray){
      val data=JsonHandler.parseToList(line)
      list+=data
    }
    val input=sc.parallelize(list).map{

      (item:List[String])=>(item.head,item(1))
    }
    val out= input.flatMap{
      case (path,text)=>
        text.trim.split(" ").map(word=>(word, path))
    }
      .map{
        case (word, path)=> ((word,path),1)
      }
      .reduceByKey{
        (count1,count2)=>count1+count2
      }
      .map{
        case ((word,path),n)=>(word,(path,n))
      }
      .groupByKey
      .collect().toList

    return  outPutMaker(out)


  }
  def outPutMaker(list: List[(String,Iterable[(String,Int)])]): List[String] = {
    val result = ListBuffer(list.toSeq.sortBy(_._1): _*)
    //map list to json
    val jsonList= result.map {
      r =>
        compactRender(JArray(List(JString(r._1), JArray(r._2.map(r2 => JString(r2._1)).toList))))
    }.toList
    return  jsonList
  }
}
