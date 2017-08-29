package exercise.other

/**
  * Created by BG244210 on 29/08/2017.
  */
object FrenchPunctuation {
  implicit val delimeter = Delimeter ("(", ")")

  def quoto(content : String) (implicit delimeter: Delimeter ) = {
    delimeter.left + content + delimeter.right
  }
}
case class Delimeter (left : String, right : String)
