package org.example

import scala.collection.mutable.ListBuffer

trait Problem {

  var dataPath:String
  var resultFileName:String
  var jsonList:List[String]=List()

  def execute(resultName:String,dataList:Array[String]):List[String]
  def main(args:Array[String]):Unit={
    var result =execute(resultFileName,FileHandler.getData(dataPath))


    //save to file
    FileHandler.writeFile(resultFileName, result.toSeq)
  }
}