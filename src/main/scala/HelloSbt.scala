import org.apache.commons.lang3.StringUtils

/**
  * Created by BG244210 on 22/08/2017.
  */
object HelloSbt {
  def main(args: Array[String]) {
    println("hello sbt!")

    val s = StringUtils.leftPad("90", 5,'0')
    println(s)
  }
}
