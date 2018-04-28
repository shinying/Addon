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
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import kotlinx.android.synthetic.main.activity_draw.*
import android.os.Parcelable
import devdon.com.painter.PaintBoard
import android.graphics.Bitmap.CompressFormat
import android.opengl.Visibility
import android.support.v4.content.ContextCompat
import android.util.LruCache
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_draw.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class DrawActivity : AppCompatActivity() {

    private var curPost = 0

    private var step = 0

    private val smallColor = 30 * resources.displayMetrics.density.toInt()

    private val bigColor = 50 * resources.displayMetrics.density.toInt()

    private lateinit var curPaintColor: Button

    private lateinit var paintBoard: PaintBoard

    private lateinit var tempCanvas: Bitmap

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


        // handle clicking
        brush.setOnClickListener {
            tool_bar.visibility = View.GONE
            draw_bar.visibility = View.VISIBLE
            canvas.visibility = View.VISIBLE
            paint_color.visibility = View.VISIBLE
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
            paint_color.visibility = View.GONE
            swipeView_d.enableTouchSwipe()
        }

        // set paint color
        curPaintColor = paint1

        paint1.setOnClickListener {
            canvas.setPaintColor(R.color.paint_black)
            changePaintColor(curPaintColor, paint1)
        }
        paint2.setOnClickListener {
            canvas.setPaintColor(R.color.paint_white)
            changePaintColor(curPaintColor, paint2)
        }
        paint3.setOnClickListener {
            canvas.setPaintColor(R.color.paint_gray)
            changePaintColor(curPaintColor, paint3)
        }
        paint4.setOnClickListener {
            canvas.setPaintColor(R.color.paint_red)
            changePaintColor(curPaintColor, paint4)
        }
        paint5.setOnClickListener {
            canvas.setPaintColor(R.color.paint_brown)
            changePaintColor(curPaintColor, paint5)
        }
        paint6.setOnClickListener {
            canvas.setPaintColor(R.color.paint_green)
            changePaintColor(curPaintColor, paint6)
        }
        paint7.setOnClickListener {
            canvas.setPaintColor(R.color.paint_blue)
            changePaintColor(curPaintColor, paint7)
        }
        paint8.setOnClickListener {
            canvas.setPaintColor(R.color.paint_purple)
            changePaintColor(curPaintColor, paint8)
        }
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

    private fun changePaintColor(lastColor: Button, newColor: Button) {

        lastColor.width = smallColor
        lastColor.height = smallColor

        curPaintColor = newColor

        newColor.width = bigColor
        newColor.width = bigColor
    }
}
