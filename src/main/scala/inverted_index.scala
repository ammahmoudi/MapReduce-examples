import net.liftweb.json.{JArray, JString, compactRender}

import scala.collection.mutable.ListBuffer
object inverted_index extends Problem {
  override var dataPath: String = "src/main/data/books.json"
  override var resultFileName: String = "src/main/solutions/inverted_index.json"
  override def mapper(line:String): Unit = {
    val list = JsonHandler.parseToList(line)
    val key=list(0)
    val value=list(1)
    val words=value.split(" ")
    for(w<-words){
      mr.emit_intermediate(w,key)
    }
  }

  override def reducer(key: String, list_of_values: ListBuffer[String]): Unit = {
     val list_of_values_distinct=list_of_values.distinct
    mr.emit(key,list_of_values_distinct)
  }



  override def outPutMaker(list: ListBuffer[(String, ListBuffer[String])]): List[String] = {
    val result = ListBuffer(list.toSeq.sortBy(_._1): _*)
    //map list to json
    jsonList= result.map {
      r =>
        compactRender(JArray(List(JString(r._1), JArray(r._2.map(r2 => JString(r2)).toList))))
    }.toList
    return  jsonList
  }
}
