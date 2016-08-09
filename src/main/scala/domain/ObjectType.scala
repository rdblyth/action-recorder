package domain

object ObjectType {
  sealed abstract class ObjectType(typeIRI: String)

  case object ARTICLE extends ObjectType("http://activitystrea.ms/schema/1.0/article")
  case object AUDIO extends ObjectType("http://activitystrea.ms/schema/1.0/audio")
  case object BOOKMARK extends ObjectType("http://activitystrea.ms/schema/1.0/bookmark")
  case object COMMENT extends ObjectType("http://activitystrea.ms/schema/1.0/comment")
  case object EVENT extends ObjectType("http://activitystrea.ms/schema/1.0/event")
  case object FILE extends ObjectType("http://activitystrea.ms/schema/1.0/file")
  case object FOLDER extends ObjectType("http://activitystrea.ms/schema/1.0/folder")
  case object NOTE extends ObjectType("http://activitystrea.ms/schema/1.0/note")
  case object PERSON extends ObjectType ("http://activitystrea.ms/schema/1.0/person")
  case object PHOTO extends ObjectType("http://activitystrea.ms/schema/1.0/photo")
  case object PLACE extends ObjectType("http://activitystrea.ms/schema/1.0/place")
  case object STATUS extends ObjectType("htp://activitystrea.ms/schema/1.0/status")
  case object VIDEO extends ObjectType("http://activitystrea.ms/schema/1.0/video")

  implicit def objectTypeToString(objectType: ObjectType) : String = objectType.toString
  implicit def stringToObjectType(objectType: String) : ObjectType = objectType match {
    case "ARTICLE" => ARTICLE
    case "AUDIO" => AUDIO
    case "BOOKMARK" => BOOKMARK
    case "COMMENT" => COMMENT
    case "EVENT" => EVENT
    case "FILE" => FILE
    case "FOLDER" => FOLDER
    case "NOTE" => NOTE
    case "PERSON" => PERSON
    case "PHOTO" => PHOTO
    case "PLACE" => PLACE
    case "STATUS" => STATUS
    case "VIDEO" => VIDEO
  }
}
