package web

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.{Config, ConfigFactory}
import domain.ObjectType.stringToObjectType
import domain.Verb.stringToVerb
import domain._
import logging.AuditLogger
import spray.json._

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

trait Protocols extends DefaultJsonProtocol {
  implicit object VerbJsonFormat extends RootJsonFormat[Verbs.EnumVal] {
    def write(v: Verbs.EnumVal) = JsString(v)
    def read(json: JsValue) : Verbs.EnumVal = stringToVerb(json.compactPrint)
  }

  implicit object ObjectTypeJsonFormat extends RootJsonFormat[ObjectTypes.EnumVal] {
    def write(o: ObjectTypes.EnumVal) = JsString(o)
    def read(json: JsValue) : ObjectTypes.EnumVal = stringToObjectType(json.compactPrint)
  }

  implicit object DateJsonFormat extends RootJsonFormat[Date] {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    def write(d: Date) = JsString(dateFormat.format(d))
    def read(json: JsValue) : Date = dateFormat.parse(json.compactPrint)
  }

  implicit val actionJsonFormat = jsonFormat5(Action)
}

trait Service extends Protocols with DbConfiguration with AuditLogger {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val actionsRepo = new ActionsRepository(dbConfig)
  actionsRepo.init()
  val logger: LoggingAdapter

  val routes = {
    logRequest(auditRequest) {
      pathPrefix("action") {
        (get & path(Segment)) { id =>
          getAction(id)
        } ~
        post {
          parameters("verb", "objectType", "objectUri") { (verb, objectType, objectUri) =>
            createAction(verb, objectType, objectUri)
          }
        } ~
        (delete & path(Segment)) { id =>
          deleteAction(id)
        }
      }
    }
  }

  private def getAction(id: String) = {
    onComplete(actionsRepo.findById(id.toInt)) {
      case Success(Some(action)) => complete(action)
      case Success(None) => complete(StatusCodes.NotFound, s"No action found with id $id")
      case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred getting action with id $id")
    }
  }

  private def createAction(verb: String, objectType: String, objectUri: String) = {
    complete {
      actionsRepo.insert(Action(None, verb, objectType, objectUri))
    }
  }

  private def deleteAction(id: String) = {
    onComplete(actionsRepo.delete(id.toInt)) {
      case Success(true) => complete(StatusCodes.OK, s"Action with id $id was deleted ")
      case Success(false) => complete(StatusCodes.NotFound, s"No action found with id $id")
      case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred deleting action with id $id")
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
