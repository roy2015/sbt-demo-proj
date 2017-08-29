package exercise.other

import scala.io.Source

/**
  * Created by BG244210 on 29/08/2017.
  */
class RichFile {
  def read(path : String) : String =
    Source.fromInputStream(this.getClass.getResourceAsStream("/" + path)).mkString


}

object RichFile {
  implicit def File2RichFile (file : java.io.File) = new RichFile

  def main(args: Array[String]) {
    println( new RichFile().read("123.txt") )
  }
}
