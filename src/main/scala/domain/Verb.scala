package domain

object ActionVerb {
  sealed abstract class Verb(iri: String)

  case object MARK_AS_FAVORITE extends Verb("http://activitystrea.ms/schema/1.0/favorite")

  implicit def verbToString(verb: Verb) = verb.toString
  implicit def stringToVerb(verb: String) = verb match {
    case "MARK_AS_FAVORITE" => MARK_AS_FAVORITE
  }
}
