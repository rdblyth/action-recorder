package logging

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.directives.LoggingMagnet

trait AuditLogger {

  def auditRequest = LoggingMagnet(_ => printRequest _)

  def printRequest(request: HttpRequest) : Unit = println(request)
}