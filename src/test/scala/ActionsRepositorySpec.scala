import domain.ObjectTypes._
import domain.Verbs._
import domain.{Action, ActionsRepository, DbConfiguration}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Await
import scala.concurrent.duration._

class ActionsRepositorySpec extends FlatSpec with DbConfiguration with BeforeAndAfterEach with ScalaFutures with  Matchers {

  val timeout = 500 milliseconds
  val actionsRepo = new ActionsRepository(dbConfig)

 override def beforeEach = {
    Await.result(actionsRepo.init(), timeout)
  }

  override def afterEach = {
    Await.result(actionsRepo.drop(), timeout)
  }

  "Action" should "be insertable" in {
    val action = Action(None, MARK_AS_FAVORITE, ARTICLE, "uri")
    actionsRepo.insert(action).futureValue should be(action.copy(id = Some(1)))
  }

  it should "be retrievable by id" in {
    val action = actionsRepo.insert(Action(None, MARK_AS_FAVORITE, ARTICLE, "uri")).futureValue

    val maybeAction = actionsRepo.findById(1).futureValue
    maybeAction.isDefined should be(true)
    maybeAction.get should be(action)
  }

  it should "be deleteable by id" in {
    val action = actionsRepo.insert(Action(None, MARK_AS_FAVORITE, ARTICLE, "uri")).futureValue
    actionsRepo.delete(action.id.get).futureValue should be(true)
  }
}
