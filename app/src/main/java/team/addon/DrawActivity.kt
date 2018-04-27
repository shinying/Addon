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
import kotlinx.android.synthetic.main.activity_join.*
import android.graphics.Bitmap
import android.os.Parcelable
import android.widget.ImageView
import android.widget.RelativeLayout
import devdon.com.painter.PaintBoard
import android.graphics.Bitmap.CompressFormat
import java.io.FileNotFoundException
import java.io.FileOutputStream

const val POSTIT_CNT = 5

class DrawActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        // get the bitmap image from the edit activity
        /**val intent = intent
        val bitmap = intent.getParcelableExtra<Parcelable>("BitmapImage") as Bitmap*/

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

        // build the first three post-it
        swipeView_d.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(25)
                        .setRelativeScale(0.02f))
        val d1 = DrawPostIt(swipeView_d)

        for(i in 1..POSTIT_CNT) {
            swipeView_d.addView(d1)
        }

//        swipeView_d.disableTouchSwipe()
        swipeView_d.addItemRemoveListener {
            when (it) {
                4 -> swipeView_d.addView(d1)
            }
        }


        /**
         * create a view for drawing
         */
        brush.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }
//        swipeView2.lockViews()
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
