package sample.shun.remote

import java.io.File

import akka.actor.{ActorLogging, Props, ActorSystem, Actor}
import com.typesafe.config.ConfigFactory

object RemoteServerApp extends App {
  val config = ConfigFactory.load("application.conf")
  val system = ActorSystem("remote-system", config)
  val remote = system.actorOf(Props[RemoteActor], "remote")
  system.log.info(remote.toString())
  system.log.info("サーバ待機中・・・")
}

class RemoteActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg: String =>
      log.info("{} から、メッセージ「 {} 」を受け取りました！", sender, msg)
      sender ! "ありがとね！！"
  }
}
