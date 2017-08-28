package exercise.wordcount

import java.io.{File, InputStream}
import java.util.concurrent.TimeUnit

import scala.actors.{Actor, Future}
import scala.collection.mutable
import scala.io.Source

/**
  * Created by BG244210 on 24/08/2017.
  *
  *
  * actor wordcount
  *
  *
  */
class WorkCountWorker extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case WorkCountTask(filePath) => {

          val content =Source.fromInputStream(this.getClass.getResourceAsStream("/" + filePath)).mkString

//          val content = Source.fromFile(this.getClass.getResource("/" +filePath).getPath()).mkString
          val arryStr = content.split("\r\n")
          val map = arryStr.flatMap(_.split(" ")).map((_, 1)).groupBy(_._1).mapValues( x => x.foldLeft(0)((a,b) => a + b._2))
          sender ! new WorkCountResult( map )
        }
        case StopTask => {
          println("actor通信结束!!")
          exit()
        }
      }
    }
  }
}

object WorkCount {
  def main(args: Array[String]) {
    val strArry = Array( "123.txt", "456.txt" , "789.txt")
    var futureList = new mutable.HashSet[Future[Any]]
    var finResult = new mutable.ListBuffer[WorkCountResult]
    val wcwArry = new mutable.ListBuffer[WorkCountWorker]

    for (str <- strArry) {
      val wcw: WorkCountWorker = new WorkCountWorker
      wcwArry += wcw
      futureList+=(   wcw.start() !! new WorkCountTask(str) )
    }

    while ( futureList.size > 0 ) {
      val tmpList = futureList.filter( _.isSet)

      for ( tmp <- tmpList ) {
        val k = tmp.apply()
        finResult += k.asInstanceOf[WorkCountResult]
        futureList.remove(tmp)
      }
    }

    val finList = finResult.flatMap(_.result).groupBy(_._1).mapValues( x => x.foldLeft(0)((a,b) => a + b._2)).toList.sortBy(_._2)
    for ( tmp <- finList ) {
      println(tmp._1, tmp._2)
    }

    for ( tmp <- wcwArry ) {
      tmp ! StopTask
    }

    Thread.sleep(3000)

  }
}


case class  WorkCountTask(filePath : String)
case class  WorkCountResult(result : scala.collection.immutable.Map[String,Int])
case object StopTask

