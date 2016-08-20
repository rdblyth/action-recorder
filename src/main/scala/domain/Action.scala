package domain

import java.util.Date

case class Action(id: Option[Int], verb: Verbs.EnumVal, objectType: ObjectTypes.EnumVal, objectUri: String, createDate: Date = new Date())

trait IRI {
  def iri: String
}

trait Verb extends Enum {
  type EnumVal <: Value with IRI
}

object Verb {
  def apply(iri: String): Option[Verbs.EnumVal] = Verbs.values.find(_.iri == iri)
  def withName(n: String): Option[Verbs.EnumVal] = Verbs.values.find(_.name == n)

  implicit def verbToString(verb: Verbs.EnumVal) : String = verb.toString
  implicit def stringToVerb(name: String) : Verbs.EnumVal = withName(name).get
}

object Verbs extends Verb {
  case class EnumVal private[Verbs] (name: String, iri: String) extends Value with IRI

  val MARK_AS_FAVORITE = EnumVal("MARK_AS_FAVORITE", "http://activitystrea.ms/schema/1.0/favorite")
  val START_FOLLOWING = EnumVal("START_FOLLOWING", "http://activitystrea.ms/schema/1.0/follow")
  val MARK_AS_LIKED = EnumVal("MARK_AS_LIKED", "http://activitystrea.ms/schema/1.0/like")
  val JOIN = EnumVal("JOIN", "http://activitystrea.ms/schema/1.0/join")
  val PLAY = EnumVal("PLAY", "http://activitystrea.ms/schema/1.0/play")
  val POST = EnumVal("POST", "http://activitystrea.ms/schema/1.0/post")
  val SAVE = EnumVal("SAVE", "http://activitystrea.ms/schema/1.0/save")
  val SHARE = EnumVal("SHARE", "http://activitystrea.ms/schema/1.0/share")
  val TAG = EnumVal("TAG", "http://activitystrea.ms/schema/1.0/tag")
  val UPDATE = EnumVal("UPDATE", "http://activitystrea.ms/schema/1.0/update")
  val RSVP_YES = EnumVal("RSVP_YES", "http://activitystrea.ms/schema/1.0/rsvp-yes")
  val RSVP_MAYBE = EnumVal("RSVP_MAYBE", "http://activitystrea.ms/schema/1.0/rsvp-maybe")
  val RSVP_NO = EnumVal("RSVP_NO", "http://activitystrea.ms/schema/1.0/rsvp-no")
}

trait ObjectType extends Enum {
  type EnumVal <: Value with IRI
}

object ObjectType {
  def apply(iri: String): Option[ObjectTypes.EnumVal] = ObjectTypes.values.find(_.iri == iri)
  def withName(n: String): Option[ObjectTypes.EnumVal] = ObjectTypes.values.find(_.name == n)

  implicit def objectTypeToString(objectType: ObjectTypes.EnumVal) : String = objectType.toString
  implicit def stringToObjectType(name: String) : ObjectTypes.EnumVal = withName(name).get
}
object ObjectTypes extends ObjectType {
  case class EnumVal private[ObjectTypes] (name: String, iri: String) extends Value with IRI

  val ARTICLE = EnumVal("ARTICLE", "http://activitystrea.ms/schema/1.0/article")
  val AUDIO = EnumVal("AUDIO", "http://activitystrea.ms/schema/1.0/audio")
  val BOOKMARK = EnumVal("BOOKMARK", "http://activitystrea.ms/schema/1.0/bookmark")
  val COMMENT = EnumVal("COMMENT", "http://activitystrea.ms/schema/1.0/comment")
  val EVENT = EnumVal("EVENT", "http://activitystrea.ms/schema/1.0/event")
  val FILE = EnumVal("FILE", "http://activitystrea.ms/schema/1.0/file")
  val FOLDER = EnumVal("FOLDER", "http://activitystrea.ms/schema/1.0/folder")
  val NOTE = EnumVal("NOTE", "http://activitystrea.ms/schema/1.0/note")
  val PERSON = EnumVal("PERSON", "http://activitystrea.ms/schema/1.0/person")
  val PHOTO = EnumVal("PHOTO", "http://activitystrea.ms/schema/1.0/photo")
  val PLACE = EnumVal("PLACE", "http://activitystrea.ms/schema/1.0/place")
  val STATUS = EnumVal("STATUS", "http://activitystrea.ms/schema/1.0/status")
  val VIDEO = EnumVal("VIDEO", "http://activitystrea.ms/schema/1.0/video")
}
