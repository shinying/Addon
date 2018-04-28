package team.addon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import kotlinx.android.synthetic.main.activity_draw.*
import android.graphics.Bitmap
import android.os.Parcelable
import android.widget.ImageView
import android.widget.RelativeLayout
import devdon.com.painter.PaintBoard
import android.graphics.Bitmap.CompressFormat
import android.graphics.Color
import android.graphics.Paint
import android.opengl.Visibility
import android.util.LruCache
import android.view.View
import java.io.FileNotFoundException
import java.io.FileOutputStream

const val POSTIT_CNT = 5

class DrawActivity : AppCompatActivity() {

    private var content = false

    private var step = 0

    private lateinit var paintBoard: PaintBoard

    private lateinit var canvasCache : LruCache<Int, Bitmap>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)



        // set the bitmap to be the view
        /**val parent = findViewById<View>(R.id.signImageParent) as RelativeLayout
        val myDrawView = PaintBoard(this)
        parent.addView(myDrawView)

        parent.isDrawingCacheEnabled = true
        val b = parent.drawingCache

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(getFileName())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        b.compress(CompressFormat.PNG, 95, fos)*/

        // build posts-it for swipeView
        val drawPostIt = DrawPostIt(swipeView_d)

        swipeView_d.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(25)
                        .setRelativeScale(0.02f))

        for(i in 1..POSTIT_CNT) {
            swipeView_d.addView(drawPostIt)
        }

        swipeView_d.addItemRemoveListener {
            when (it) {
                4 -> swipeView_d.addView(drawPostIt)
            }
        }

        // handle paintBoard
        paintBoard = canvas
        paintBoard.setPaint()


        // handle memory cache
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8

        canvasCache = LruCache(cacheSize)


        // handle clicking
        brush.setOnClickListener {
            tool_bar.visibility = View.GONE
            draw_bar.visibility = View.VISIBLE
            canvas.visibility = View.VISIBLE
            swipeView_d.disableTouchSwipe()
            content = true
        }

        done.setOnClickListener {
            drawPostIt.frame.setImageBitmap(canvas.bitmap)
            step += 1
            draw_bar.visibility = View.GONE
            tool_bar.visibility = View.VISIBLE
            canvas.visibility = View.GONE
            swipeView_d.enableTouchSwipe()

            drawPostIt.frame.setImageBitmap(canvas.drawingCache)
        }
    }


    private fun addBitmapToCache(step: Int, bitmap: Bitmap) {
        if (getBitmapFromCache(step) == null) {
            canvasCache.put(step, bitmap)
        }
    }

    private fun getBitmapFromCache(step: Int): Bitmap? {
        return canvasCache.get(step)
    }

    private fun loadBitmap(step: Int, drawPostIt: DrawPostIt) {

        val bitmap = getBitmapFromCache(step)
        if (bitmap != null) {
            drawPostIt.frame.setImageBitmap(bitmap)
        } else {
            drawPostIt.frame.setImageResource(R.drawable.post_it_yellow)
        }
    }



    /**private fun checkWritable():Boolean {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
            return false
        } else {
            return true
        }
    }


    private val saveClickHandler = View.OnClickListener {
        view -> if(checkWritable()){
            try {
                val fileName = (System.currentTimeMillis() / 1000).toString() + ".jpg"
                val file = File(Environment.getExternalStorageDirectory(), fileName)
                val stream = FileOutputStream(file)
                paintBoard.saveBitmap(stream)
                stream.close()

                val intent = Intent()
                intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()))
                sendBroadcast(intent)

                Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show()

            } catch(e:Exception) {
                println(e)
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show()

            }
        }

    }*/

}
