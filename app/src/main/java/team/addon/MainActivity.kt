package team.addon

import android.app.PendingIntent.getActivity
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Point

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        welcome_postIt.height = size.x - 16

        dot1.imageAlpha = 192
        
    }
}
