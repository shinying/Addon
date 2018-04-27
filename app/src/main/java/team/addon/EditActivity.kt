package team.addon

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import devdon.com.painter.PaintBoard
import team.addon.R.layout.activity_edit
import kotlinx.android.synthetic.main.activity_edit.*
import android.R.attr.bitmap
import android.R.attr.bitmap
import android.graphics.Color
import android.graphics.Paint


class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupPaint()
        setupView()

        // send image to another activity
        val intent = Intent(this, DrawActivity::class.java)
        intent.putExtra("BitmapImage", bitmap)
        done.setOnClickListener{
            startActivity(intent)
        }


    }

    private lateinit var paintBoard: PaintBoard
    private fun setupView(){
        paintBoard = findViewById(R.id.layout_paint_board)
        //saveButton.setOnClickListener(saveClickHandler)

    }

    private fun setupPaint(){
        val paint = Paint()
        paint.setColor(Color.GREEN)
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
    }
}