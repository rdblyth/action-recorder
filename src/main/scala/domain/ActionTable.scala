package domain

import domain.ObjectType.{objectTypeToString, stringToObjectType}
import domain.Verb.{verbToString, stringToVerb}

trait ActionsTable { this: Db =>
  import dbConfig.driver.api._

  class Actions(tag: Tag) extends Table[Action](tag, "ACTION") {

    // Columns
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    //def user = column[Option[String]]("USER")
    def verb = column[Verbs.EnumVal]("VERB")
    def objectUri = column[String]("OBJECT_URI")
    def objectType = column[ObjectTypes.EnumVal]("OBJECT_TYPE")
    def createDate = column[java.util.Date]("CREATE_DATE")

    // Select
    def * = (id.?, verb, objectType, objectUri, createDate) <> (Action.tupled, Action.unapply)

    implicit val verbMapper = MappedColumnType.base[Verbs.EnumVal, String](verbToString, stringToVerb)
    implicit val objectTypeMapper = MappedColumnType.base[ObjectTypes.EnumVal, String](objectTypeToString, stringToObjectType)

    implicit val javaUtilDateMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp] (
      d => new java.sql.Timestamp(d.getTime),
      t => new java.util.Date(t.getTime))
  }

  val actions = TableQuery[Actions]
}
