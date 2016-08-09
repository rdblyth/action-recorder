package domain

import domain.ActionObjectType._
import domain.ActionVerb._

case class Action(id: Option[Int], verb:Verb, objectType:ObjectType, objectUri:String)

trait ActionsTable { this: Db =>
  import dbConfig.driver.api._

  class Actions(tag: Tag) extends Table[Action](tag, "ACTIONS") {

    implicit val verbColumnType = MappedColumnType.base[Verb, String](verbToString, stringToVerb)
    implicit val objectTypeColumnType = MappedColumnType.base[ObjectType, String](objectTypeToString, stringToObjectType)

    // Columns
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    //def user = column[Option[String]]("USER")
    def verb = column[Verb]("VERB")
    def objectUri = column[String]("OBJECT_URI")
    def objectType = column[ObjectType]("OBJECT_TYPE")
    //def createDate = column[Date]("CREATE_DATE")

    // Select
    def * = (id.?, verb, objectType, objectUri) <> (Action.tupled, Action.unapply)
  }

  val actions = TableQuery[Actions]
}
