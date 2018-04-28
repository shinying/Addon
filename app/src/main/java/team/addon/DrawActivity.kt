package team.addon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
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
import android.os.Parcelable
import android.widget.ImageView
import android.widget.RelativeLayout
import devdon.com.painter.PaintBoard
import android.graphics.Bitmap.CompressFormat
import android.graphics.ImageFormat.JPEG
import android.opengl.Visibility
import android.support.v4.content.ContextCompat
import android.util.Base64
import android.util.LruCache
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import team.addon.R.id.image
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class DrawActivity : AppCompatActivity() {

    private var curPost = 0

    private var step = 0

    private lateinit var paintBoard: PaintBoard

    private lateinit var canvasCache : LruCache<Int, Bitmap>

    private lateinit var tempCanvas: Bitmap

    private var cacheBitmapKey = 1

    private var cacheBitmapDirtyKey = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        // build posts-it for swipeView
        val d1 = DrawPostIt(swipeView_d)
        val d2 = DrawPostIt(swipeView_d)
        val d3 = DrawPostIt(swipeView_d)
        val d4 = DrawPostIt(swipeView_d)
        val d5 = DrawPostIt(swipeView_d)

        val postPile = arrayOf(d1,d2,d3,d4,d5)

        swipeView_d.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(25)
                        .setRelativeScale(0.02f))

        swipeView_d.addView(d1)
        swipeView_d.addView(d2)
        swipeView_d.addView(d3)
        swipeView_d.addView(d4)
        swipeView_d.addView(d5)

        swipeView_d.addItemRemoveListener {
            when (it) {
                4 -> {
                    swipeView_d.addView(postPile[curPost])

                    curPost += 1
                    curPost %= 5

                    canvas.clearCanvas()
                    canvas.hasContent = false
                }
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

            if(canvas.hasContent){
                tempCanvas = getCanvasCache(canvas)
            }
        }

        done.setOnClickListener {

            postPile[curPost].frame.setImageBitmap(getCanvasCache(canvas))

            step += 1
            draw_bar.visibility = View.GONE
            tool_bar.visibility = View.VISIBLE
            canvas.visibility = View.GONE
            swipeView_d.enableTouchSwipe()

            canvas.hasContent = true
        }

        clear.setOnClickListener {
            if(canvas.hasContent){
                postPile[curPost].frame.setImageBitmap(tempCanvas)
                canvas.setBitmap(tempCanvas)
            }
            else {
                canvas.clearCanvas()
            }

            draw_bar.visibility = View.GONE
            tool_bar.visibility = View.VISIBLE
            canvas.visibility = View.GONE
            swipeView_d.enableTouchSwipe()
        }

        lateinit var test: Bitmap
        var encode: String
        encode = encodeToBase64(test, CompressFormat.JPEG, 100)
        //Log.e("bug", "bug")
        Log.e("encode", encode)

        val params = JSONObject()
        params.put("bitmap", encode)
        getresponse(EndPoints.joinWall, params)
    }




    private fun getCanvasCache(view: View): Bitmap {

        val bitmap = Bitmap.createBitmap(view.width, view.height,
        Bitmap.Config.RGB_565)
        bitmap.density = resources.displayMetrics.densityDpi

        val canvas = Canvas(bitmap)

        view.draw(canvas)
        canvas.setBitmap(null)

        return bitmap
    }

    private fun encodeToBase64(image: Bitmap, compressFormat: Bitmap.CompressFormat, quality: Int): String
    {
        val bitmap = image
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val result = stream.toByteArray()
        return result.toString()
        /**val byteArrayOS = ByteArrayOutputStream()
        image.compress(compressFormat, quality, byteArrayOS)
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)*/
    }

    private fun getresponse(url: String, params: JSONObject) {

        //creating volley string request
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, url, params,

                Response.Listener<JSONObject> { response ->

                    Log.e("response", response.toString())

                    //wallName = response.optString("wallPin", "none")

                    //val p2Welcome = String.format(getString(R.string.join_wall_name), wallName)

                    //post.setText(p2Welcome, getString(R.string.hint_member_name))
                },

                Response.ErrorListener { volleyError ->
                    Log.e("err", volleyError.toString())

                }) { }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(jsonObjectRequest)
    }
}



