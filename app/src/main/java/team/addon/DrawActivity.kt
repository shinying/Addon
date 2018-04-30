package team.addon

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject
import team.addon.R.id.*
import java.io.ByteArrayOutputStream

class DrawActivity : AppCompatActivity() {

    private var wallPin = ""

    private var name = ""

    private var wallName = ""

    private var curPost = 0

    private var step = 0

    private lateinit var curPaintColor: ImageButton

    private lateinit var tempCanvas: Bitmap

    private var erasing = false

    private var colorBfErase = 0

    private var widthBfErase = 0F

    private var sent = false

    private var firstDraw = true

    private lateinit var postIt2Stick: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        val extras = intent.extras
        if(extras != null) {
            wallPin = extras.getString("wallPin")
            wallName = extras.getString("wallName")
            name = extras.getString("name")
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(String.format(getString(R.string.show_wall_pin), wallName, wallPin))

        builder.setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
        })
        val dialog = builder.create()

        wall_name.text = wallName

        // region handle swipeView

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
                    if(!sent && canvas.hasContent) {
                        postIt2Stick = getCanvasCache(canvas)
                        sendPostIt()
                        sent = true
                    }

                    swipeView_d.addView(postPile[curPost])

                    curPost += 1
                    curPost %= 5

                    canvas.clearCanvas()
                    canvas.hasContent = false
                    sent = false
                }
            }
        }

        // endregion

        // handle paintBoard
        canvas.setPaint()

        // region handle choosing color
        // set paint color
        curPaintColor = paint1

        val colorDot = arrayOf(paint1, paint2, paint3, paint4, paint5, paint6, paint7, paint8)
        val colorSet = arrayOf(R.color.paint_black, R.color.paint_white, R.color.paint_gray, R.color.paint_red, R.color.paint_brown, R.color.paint_green, R.color.paint_blue, R.color.paint_purple)

        val smallPadding = toPx(12F)
        val bigPadding = toPx(5F)

        for(i in 0..7) {
            colorDot[i].setOnClickListener {

                if (curPaintColor != colorDot[i]) {
                    canvas.setPaintColor(colorSet[i])

                    // make small the last using color
                    curPaintColor.setPadding(smallPadding, smallPadding, smallPadding, smallPadding )
                    curPaintColor.animate().scaleXBy(-0.3F).scaleYBy(-0.3F)

                    // make big the chosen color
                    colorDot[i].setPadding(bigPadding, bigPadding, bigPadding, bigPadding)
                    colorDot[i].animate().scaleXBy(0.3F).scaleYBy(0.3F)
                    curPaintColor = colorDot[i]
                }
            }
        }
        // endregion

        // region handle clicking event
        wall_name.setOnClickListener {
            dialog.show()
        }

        wall.setOnClickListener {
            val intent = Intent(this, WallActivity::class.java)
            intent.putExtra("wallPin", wallPin)
            startActivity(intent)

//            val uri = Uri.parse(String.format(getString(R.string.wall_URL), wallPin))
//            val intent = Intent(Intent.ACTION_VIEW,uri)
//            startActivity(intent)
        }

        brush.setOnClickListener {

            // set up UI
            tool_bar.visibility = View.GONE
            draw_bar.visibility = View.VISIBLE
            canvas.visibility = View.VISIBLE
            paint_palette.visibility = View.VISIBLE
            swipeView_d.disableTouchSwipe()

            // capture current post-it to canvas
            if(canvas.hasContent){
                tempCanvas = getCanvasCache(canvas)
            }
            if(firstDraw){
                paint1.animate().scaleXBy(0.3F).scaleYBy(0.3F)
                paint1.setPadding(bigPadding, bigPadding, bigPadding, bigPadding)
                firstDraw = false
            }
        }


        done.setOnClickListener {

            // capture current canvas to post_it
            postPile[curPost].frame.setImageBitmap(getCanvasCache(canvas))

            // set up UI
            step += 1
            draw_bar.visibility = View.GONE
            tool_bar.visibility = View.VISIBLE
            canvas.visibility = View.GONE
            paint_palette.visibility = View.GONE
            swipeView_d.enableTouchSwipe()
            canvas.hasContent = true

            // initialize eraser
            if (erasing) {
                eraser.setImageDrawable(resources.getDrawable(R.drawable.ic_eraser, theme))
                canvas.setPaintColor(colorBfErase)
                canvas.setPaintWidth(widthBfErase)
                erasing = false
            }
        }


        clear.setOnClickListener {

            // capture current post-it to canvas
            if(canvas.hasContent){
                postPile[curPost].frame.setImageBitmap(tempCanvas)
                canvas.setBitmap(tempCanvas)
            }
            else {
                canvas.clearCanvas()
            }

            // set up UI
            draw_bar.visibility = View.GONE
            tool_bar.visibility = View.VISIBLE
            canvas.visibility = View.GONE
            paint_palette.visibility = View.GONE
            swipeView_d.enableTouchSwipe()

            // initialize eraser
            if (erasing) {
                eraser.setImageDrawable(resources.getDrawable(R.drawable.ic_eraser, theme))
                canvas.setPaintColor(colorBfErase)
                canvas.setPaintWidth(widthBfErase)
                erasing = false
            }
        }


        eraser.setOnClickListener {

            // back to drawing
            if(erasing) {
                eraser.setImageDrawable(resources.getDrawable(R.drawable.ic_eraser, theme))
                paint_palette.visibility = View.VISIBLE
                canvas.setPaintColor(colorBfErase)
                canvas.setPaintWidth(widthBfErase)
                erasing = false
            }

            // choose the color of background and start to erase
            else {
                colorBfErase = when (curPaintColor) {
                    paint2 -> colorSet[1]
                    paint3 -> colorSet[2]
                    paint4 -> colorSet[3]
                    paint5 -> colorSet[4]
                    paint6 -> colorSet[5]
                    paint7 -> colorSet[6]
                    paint8 -> colorSet[7]
                    else -> colorSet[0]
                }
                widthBfErase = canvas.paintWidth()
                canvas.setPaintColor(canvas.bgColor)
                canvas.setPaintWidth(80F)
                paint_palette.visibility = View.GONE
                eraser.setImageDrawable(resources.getDrawable(R.drawable.ic_eraser_on, theme))
                erasing = true
            }
        }

        // endregion

    }


    // take a screen shot of a view
    private fun getCanvasCache(view: View): Bitmap {

        val bitmap = Bitmap.createBitmap(view.width, view.height,
        Bitmap.Config.RGB_565)
        bitmap.density = resources.displayMetrics.densityDpi

        val canvas = Canvas(bitmap)

        view.draw(canvas)
        canvas.setBitmap(null)

        return bitmap
    }


    private fun sendPostIt() {

        val base64 = bitmapToBase64(postIt2Stick)
        val passToWall = JSONObject()
        passToWall.put("wallPin", wallPin)
        passToWall.put("name", name)
        passToWall.put("postIt", base64)

        //creating volley string request
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, EndPoints.stick, passToWall,

                Response.Listener<JSONObject> { response ->

                    Log.e("response", response.toString())
                },

                Response.ErrorListener { volleyError ->
                    Log.e("err", volleyError.toString())

                }) {}

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(jsonObjectRequest)
    }


    // turn dp to pixel
    private fun toPx(dp: Float): Int {
        val metrics = applicationContext.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
    }

    private fun bitmapToBase64(image: Bitmap):String {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val bytes = stream.toByteArray()

        return Base64.encodeToString(bytes, DEFAULT_BUFFER_SIZE)
    }
}



