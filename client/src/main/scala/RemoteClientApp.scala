package sample.shun.client

import java.io.File

import akka.actor.{ActorLogging, Props, ActorSystem, Actor}
import com.typesafe.config.ConfigFactory

object RemoteClientApp extends App {
  val config = ConfigFactory.load("application.conf")
  val system = ActorSystem("client-system", config)
  val localActor = system.actorOf(Props[ClientActor], "local")
}

class ClientActor extends Actor with ActorLogging {
  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    val remoteActor = context.actorSelection(RemoteClientApp.config.getString("remote-address"))
    log.info("送り先は {}", remoteActor)
    remoteActor ! "届いたらお返事ください。"
  }

  override def receive: Receive = {
    case msg: String =>
      log.info("{} から返事をもらったよ！！： {}", sender, msg)
  }
}

