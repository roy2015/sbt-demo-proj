package exercise.akka

import java.util.concurrent.TimeUnit

import akka.actor.Actor.Receive
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration.FiniteDuration

/**
  * Created by BG244210 on 25/08/2017.
  */
class Master extends akka.actor.Actor{
  //保存WorkerID和Work信息的map
  val idToWorker = new mutable.HashMap[String, WorkerInfo]
  //保存所有Worker信息的Set
  val workers = new mutable.HashSet[WorkerInfo]
  //Worker超时时间
  val WORKER_TIMEOUT = 10 * 1000
  //重新receive方法

  //导入隐式转换，用于启动定时器
  import context.dispatcher

  override def preStart: Unit = {
    //启动定时器，定时执行
    context.system.scheduler.schedule(scala.concurrent.duration.FiniteDuration(0, TimeUnit.MILLISECONDS),
      scala.concurrent.duration.FiniteDuration(WORKER_TIMEOUT, TimeUnit.MILLISECONDS) , self, CheckOfTimeOutWorker)

  }

  override def receive: Receive = {
    //Worker向Master发送的注册消息
    case RegisterWorker(id, workerHost, memory, cores) => {
      if(!idToWorker.contains(id)) {
        val worker = new WorkerInfo(id, workerHost, memory, cores)
        workers.add(worker)
        idToWorker(id) = worker
        sender ! RegisteredWorker("10.45.25.146")
      }
    }

    //Worker向Master发送的心跳消息
    case HeartBeat(workerId) => {
      val workerInfo = idToWorker(workerId)
      workerInfo.lastHeartbeat = System.currentTimeMillis()
    }

    //Master自己向自己发送的定期检查超时Worker的消息
    case CheckOfTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(w => currentTime - w.lastHeartbeat > WORKER_TIMEOUT).toArray
      for(worker <- toRemove){
        workers -= worker
        idToWorker.remove(worker.id)
      }
      println("worker size: " + workers.size)
    }
  }
}

object Master {
  //程序执行入口
  def main(args: Array[String]) {

    val host = "10.45.25.146"
    val port = 8888
    //创建ActorSystem的必要参数
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem是单例的，用来创建Actor
    val actorSystem = ActorSystem.create("MasterActorSystem", config)
    //启动Actor，Master会被实例化，生命周期方法会被调用
    actorSystem.actorOf(Props[Master], "Master")
  }
}

