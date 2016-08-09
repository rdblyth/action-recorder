package domain

object ActionVerb {
  sealed abstract class Verb(iri: String)

  case object MARK_AS_FAVORITE extends Verb("http://activitystrea.ms/schema/1.0/favorite")
  case object START_FOLLOWING extends Verb("http://activitystrea.ms/schema/1.0/follow")
  case object MARK_AS_LIKED extends Verb("http://activitystrea.ms/schema/1.0/like")
  case object MAKE_FRIEND extends Verb("http://activitystrea.ms/schema/1.0/make-friend")
  case object JOIN extends Verb("http://activitystrea.ms/schema/1.0/join")
  case object PLAY extends Verb("http://activitystrea.ms/schema/1.0/play")
  case object POST extends Verb("http://activitystrea.ms/schema/1.0/post")
  case object SAVE extends Verb("http://activitystrea.ms/schema/1.0/save")
  case object SHARE extends Verb("http://activitystrea.ms/schema/1.0/share")
  case object TAG extends Verb("http://activitystrea.ms/schema/1.0/tag")
  case object UPDATE extends Verb("http://activitystrea.ms/schema/1.0/update")
  case object RSVP_YES extends Verb("http://activitystrea.ms/schema/1.0/rsvp-yes")
  case object RSVP_MAYBE extends Verb("http://activitystrea.ms/schema/1.0/rsvp-maybe")
  case object RSVP_NO extends Verb("http://activitystrea.ms/schema/1.0/rsvp-no")


  implicit def verbToString(verb: Verb) : String = verb.toString
  implicit def stringToVerb(verb: String) : Verb = verb match {
    case "MARK_AS_FAVORITE" => MARK_AS_FAVORITE
    case "START_FOLLOWING" => START_FOLLOWING
    case "MARK_AS_LIKED" => MARK_AS_LIKED
    case "MAKE_FRIEND" => MAKE_FRIEND
    case "JOIN" => JOIN
    case "PLAY" => PLAY
    case "POST" => POST
    case "SAVE" => SAVE
    case "SHARE" => SHARE
    case "TAG" => TAG
    case "UPDATE" => UPDATE
    case "RSVP_YES" => RSVP_YES
    case "RSVP_MAYBE" => RSVP_MAYBE
    case "RSVP_NO" => RSVP_NO
  }
}
