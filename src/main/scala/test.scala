import net.liftweb.json.JsonParser

object test {
def main(args:Array[String]):Unit={
  for(line<-FileHandler.getData("src/main/data/books.json")){
    val record=JsonParser.parse(line)
val list=record.values.asInstanceOf[List[String]]
   println(list(1).split(" ").mkString("Array(", ", ", ")"))

  }
}
}
