import net.liftweb.json.{JArray, JString, compactRender}

import scala.collection.mutable.ListBuffer

trait Problem {
  val mr=new MapReduce
  var dataPath:String
  var resultFileName:String
  var jsonList:List[String]=List()
  def mapper(line:String): Unit
  def reducer(key: String, list_of_values: ListBuffer[String]): Unit
  def outPutMaker(list: ListBuffer[(String,ListBuffer[String])]):List[String]
  def main(args:Array[String]):Unit={
    var result =mr.execute(resultFileName,FileHandler.getData(dataPath),mapper,reducer)
    jsonList=outPutMaker(result)

    //save to file
    FileHandler.writeFile(resultFileName, jsonList.toSeq)
  }
}