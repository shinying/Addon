package team.addon

object EndPoints {
    private val URL_ROOT = "http://postwallapp.herokuapp.com"
    val joinWall = "$URL_ROOT/postdata.php"
    val buildWall = "$URL_ROOT/createwall.php"
    val stick = "$URL_ROOT/receive_pic.php"
}
