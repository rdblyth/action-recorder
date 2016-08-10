package domain

import domain.ObjectType._
import domain.Verb._
import java.util.Date

case class Action(id: Option[Int], verb: Verb, objectType: ObjectType, objectUri: String, createDate: Date = new Date())

trait ActionsTable { this: Db =>
  import dbConfig.driver.api._

  class Actions(tag: Tag) extends Table[Action](tag, "ACTIONS") {

    // Columns
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    //def user = column[Option[String]]("USER")
    def verb = column[Verb]("VERB")
    def objectUri = column[String]("OBJECT_URI")
    def objectType = column[ObjectType]("OBJECT_TYPE")
    def createDate = column[java.util.Date]("CREATE_DATE")

    // Select
    def * = (id.?, verb, objectType, objectUri, createDate) <> (Action.tupled, Action.unapply)

    implicit val verbMapper = MappedColumnType.base[Verb, String](verbToString, stringToVerb)
    implicit val objectTypeMapper = MappedColumnType.base[ObjectType, String](objectTypeToString, stringToObjectType)

    implicit val javaUtilDateMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp] (
        d => new java.sql.Timestamp(d.getTime),
        t => new java.util.Date(t.getTime))
  }

  val actions = TableQuery[Actions]
}
