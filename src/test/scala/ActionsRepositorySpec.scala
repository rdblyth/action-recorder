import domain.ObjectType._
import domain.Verb._
import domain.{Action, ActionsRepository, DbConfiguration}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Await
import scala.concurrent.duration._

class ActionRepositorySpec extends FlatSpec with DbConfiguration with BeforeAndAfterEach with ScalaFutures with  Matchers {

  val timeout = 500 milliseconds
  val actionsRepo = new ActionsRepository(dbConfig)

 override def beforeEach = {
    Await.result(actionsRepo.init(), timeout)
  }

  override def afterEach = {
    Await.result(actionsRepo.drop(), timeout)
  }

  "Action" should "be inserted successfully" in {
    val action = Action(None, MARK_AS_FAVORITE, ARTICLE, "uri")
    actionsRepo.insert(action).futureValue should be(action.copy(id = Some(1)))
  }

  "findById" should "find a row" in {
    val action = actionsRepo.insert(Action(None, MARK_AS_FAVORITE, ARTICLE, "uri")).futureValue

    val maybeAction = actionsRepo.findById(1).futureValue
    maybeAction.isDefined should be(true)
    maybeAction.get should be(action)
  }
}
