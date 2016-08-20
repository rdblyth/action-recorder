package domain

import slick.backend.DatabaseConfig
import slick.dbio.DBIOAction
import slick.driver.JdbcProfile

class ActionsRepository(val dbConfig: DatabaseConfig[JdbcProfile])
  extends Db with ActionsTable {

  import dbConfig.driver.api._
  import scala.concurrent.ExecutionContext.Implicits.global

  def insert(action: Action) = db
    .run(actions returning actions.map(_.id) += action)
    .map(id => action.copy(id = Some(id)))

  def delete(id: Int) = db.run(filterById(id).delete) map { _ > 0 }

  def findById(id: Int) = db.run(filterById(id).result.headOption)

  def init() = db.run(DBIOAction.seq(actions.schema.create))
  def drop() = db.run(DBIOAction.seq(actions.schema.drop))

  private def filterById(id: Int) = actions.filter(_.id === id)
}
