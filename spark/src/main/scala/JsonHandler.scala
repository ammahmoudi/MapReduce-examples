package org.example

import net.liftweb.json.JsonParser


object JsonHandler {

  def parseToList(json:String):List[String]={
  val record=JsonParser.parse(json)
  record.values.asInstanceOf[List[String]]
}
}
