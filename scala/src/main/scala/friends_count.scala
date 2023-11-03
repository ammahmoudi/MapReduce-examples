import net.liftweb.json.{JArray, JString, compactRender}

import scala.collection.mutable.ListBuffer

object friends_count extends Problem {
  override var dataPath: String = "src/main/data/friends.json"
  override var resultFileName: String = "src/main/solutions/friends_count.json"

  override def mapper(line: String): Unit = {
    val list = JsonHandler.parseToList(line)
    val key = list(0)

    mr.emit_intermediate(key, String.valueOf(1))

  }

  override def reducer(key: String, list_of_values: ListBuffer[String]): Unit = {
 val num_friends=list_of_values.size
    mr.emit(key,ListBuffer(String.valueOf(num_friends)))
  }


  override def outPutMaker(list: ListBuffer[(String, ListBuffer[String])]): List[String] ={
    val result = ListBuffer(list.toSeq.sortBy(_._1): _*)
    //map list to json
    jsonList= result.map {
      r =>
        compactRender(JArray(List(JString((r._1+" ,"+r._2(0))))))
    }.toList
    return  jsonList
  }
}
