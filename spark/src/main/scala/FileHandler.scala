package org.example

import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source
object FileHandler {
  def getData(fileName:String):Array[String]={
    val bufferedSource =Source.fromFile(fileName)
    val lines=bufferedSource.getLines.toArray
    bufferedSource.close()
    lines
  }
  def writeFile(filename: String, s: String): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(s)
    bw.close()
  }
  def writeFile(filename: String, lines: Seq[String]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    for (line <- lines) {
      bw.write(line+"\n")
    }
    bw.close()
  }

}
