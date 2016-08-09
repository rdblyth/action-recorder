package domain

object ActionObjectType {
  sealed abstract class ObjectType(typeIRI: String)

  case object ARTICLE extends ObjectType("http://activitystrea.ms/schema/1.0/article")

  implicit def objectTypeToString(objectType: ObjectType) : String = objectType.toString
  implicit def stringToObjectType(objectType: String) : ObjectType = objectType match {
    case "ARTICLE" => ARTICLE
  }
}
