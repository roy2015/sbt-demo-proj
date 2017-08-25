package exercise.akka

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.util.Random

/**
  * Created by BG244210 on 25/08/2017.
  */
class Worker extends Actor{

  //Worker端持有Master端的引用（代理对象）
  var master: ActorSelection = null
  //生成一个UUID，作为Worker的标识
  val id = UUID.randomUUID().toString

  //构造方法执行完执行一次
  override def preStart(): Unit = {
    //Worker向MasterActorSystem发送建立连接请求
    master = context.system.actorSelection("akka.tcp://MasterActorSystem@10.45.25.146:8888/user/Master")
    //Worker向Master发送注册消息
    master ! RegisterWorker(id, "10.45.25.146", 10240, 8)
  }

  //该方法会被反复执行，用于接收消息，通过case class模式匹配接收消息
  override def receive: Receive = {
    //Master向Worker的反馈信息
    case RegisteredWorker(masterUrl) => {
      import context.dispatcher
      //启动定时任务，向Master发送心跳
      context.system.scheduler.schedule(
        scala.concurrent.duration.FiniteDuration(0, TimeUnit.MILLISECONDS),
        scala.concurrent.duration.FiniteDuration(5000, TimeUnit.MILLISECONDS),
        self, SendHeartBeat)

      println("work received connected msg , masterUrl", masterUrl)
    }

    case SendHeartBeat => {
      println("worker send heartbeat")
      master ! HeartBeat(id)
    }
  }
}

object Worker {
  def main(args: Array[String]) {
    val clientPort = Random.nextInt(10000);
    //创建WorkerActorSystem的必要参数
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.port = $clientPort
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("WorkerActorSystem", config)
    //启动Actor，Master会被实例化，生命周期方法会被调用
    actorSystem.actorOf(Props[Worker], "Worker")
  }
}

