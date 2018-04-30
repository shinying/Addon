package team.addon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView



class WallActivity : AppCompatActivity() {

    private var wallPin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webview = WebView(this)
        setContentView(webview)

        val extras = intent.extras
        if(extras != null) {
            wallPin = extras.getString("wallPin")
        }

        webview.settings.javaScriptEnabled = true

        webview.loadUrl(String.format(getString(R.string.wall_URL), wallPin))
    }
}
