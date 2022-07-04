import inverted_index.jsonList
import net.liftweb.json.{JArray, JNull, JString, compactRender}

import scala.collection.mutable.ListBuffer

object join extends Problem {
  override var dataPath: String = "src/main/data/records.json"
  override var resultFileName: String = "src/main/solutions/join.json"

  override def mapper(line: String): Unit = {
    val list = JsonHandler.parseToList(line)
    val key = list(1)

    mr.emit_intermediate(key, line)

  }

  override def reducer(key: String, list_of_values: ListBuffer[String]): Unit = {
    val orderID = list_of_values.head
    val line_items = list_of_values.drop(1)
    for (l <- line_items) {
      mr.emit(orderID, ListBuffer[String](l))
    }
  }


  override def outPutMaker(list: ListBuffer[(String, ListBuffer[String])]): List[String] = {
    val result = ListBuffer(list.toSeq.sortBy(_._1): _*)
    //map list to json
    jsonList = result.map {
      r =>
        compactRender(JArray(List(JString((r._1.replace('[', ' ').replace(']', ' ') + " ," + r._2(0).replace('[', ' ').replace(']', ' ')))))).replace('\\', ' ')
    }.toList
    return jsonList
  }
}
