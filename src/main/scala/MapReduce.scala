import net.liftweb.json.JsonAST.RenderSettings.compact
import net.liftweb.json.JsonAST.{JValue, render}
import net.liftweb.json.{JArray, JField, JObject, JString, JsonDSL, JsonParser, compactRender, prettyRender}

import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer


class MapReduce {
  var intermediate: scala.collection.mutable.Map[String, ListBuffer[String]] = scala.collection.mutable.Map()
  var result = ListBuffer[(String, ListBuffer[String])]()


  def emit_intermediate(key: String, value: String): Unit = {
    if (!intermediate.contains(key)) intermediate.addOne(key, new ListBuffer[String])
    intermediate(key).append(value)
  }

  def emit(key: String, value: ListBuffer[String]): Unit = {
    result.append((key, value))
  }

  def execute(resultFileName: String, data: Array[String], mapper: (String) => Unit, reducer: (String, ListBuffer[String]) => Unit): ListBuffer[(String,ListBuffer[String])] = {
    for (line <- data) {

      mapper(line)
    }
    intermediate.foreach(record => reducer(record._1, record._2))
   return result

  }


}
