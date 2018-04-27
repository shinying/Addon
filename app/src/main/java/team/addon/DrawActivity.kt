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
import team.addon.R.id.swipeView2
import java.io.FileNotFoundException
import java.io.FileOutputStream


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
        swipeView2.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(5)
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingLeft(20)
                        .setRelativeScale(0.01f))
        val d1 = DrawPostIt(swipeView2)

        swipeView2.addView(d1)
        swipeView2.disableTouchSwipe()


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
