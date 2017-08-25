package exercise.akka

/**
  * Created by BG244210 on 25/08/2017.
  */
class WorkerInfo (val id:String, val workerHost :String, val memory:Int, val cores:Int) {
  var lastHeartbeat:Long = 0
}

case object CheckOfTimeOutWorker
case class RegisterWorker(id:String, workerHost :String, memory:Int, cores:Int)
case class HeartBeat(id:String)
case object SendHeartBeat
case class RegisteredWorker(masterUrl:String)
