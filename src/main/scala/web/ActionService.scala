package web

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.{Config, ConfigFactory}
import domain.ActionObjectType._
import domain.ActionVerb._
import domain._
import spray.json._

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

trait Protocols extends DefaultJsonProtocol {
  implicit object VerbJsonFormat extends RootJsonFormat[Action] {
    def write(a: Action) = JsObject(
      "verb" -> JsString(a.verb),
      "objectType" -> JsString(a.objectType),
      "objectUri" -> JsString(a.objectUri))

    def read(value: JsValue) = {
      value.asJsObject.getFields("verb", "objectType", "objectUri") match {
        case Seq(JsString(verb), JsString(objectType), JsString(objectUri)) =>
          Action(None, verb, objectType, objectUri)
        case _ => throw new DeserializationException("Action expected")
      }
    }
  }
}

trait Service extends Protocols with DbConfiguration {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val actionsRepo = new ActionsRepository(dbConfig)
  actionsRepo.init()
  val logger: LoggingAdapter

  val routes = {
    logRequestResult("akka-http-microservice") {
      pathPrefix("action") {
        (get & path(Segment)) { id =>
          onComplete(actionsRepo.findById(id.toInt)) {
            case Success(Some(action)) => complete(action)
            case Success(None) => complete(StatusCodes.NotFound, s"No action found with id $id")
            case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred getting action with id $id")
          }
        } ~
        post {
          parameters("verb", "objectType", "objectUri") { (verb, objectType, objectUri) =>
            complete {
              actionsRepo.insert(Action(None, verb, objectType, objectUri))
            }
          }
        } ~
        (delete & path(Segment)) { id =>
          complete(s"Deleted action $id")
        }
      }
    }
  }
}

object ActionService extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}