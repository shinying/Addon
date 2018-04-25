package devdon.com.painter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import team.addon.R
import java.io.OutputStream


/**
 * Created by slamdon on 2017/12/13.
 * invoked by ShihYun from the website on 2018/04/25
 */


class PaintBoard(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var paint: Paint
    private var bitmap: Bitmap
    private var mCanvas: Canvas

    private var startX:Float = 0f
    private var startY:Float = 0f

    init {
        // bitmap
        val width = Resources.getSystem().displayMetrics.widthPixels
        bitmap = Bitmap.createBitmap(width, 330 * resources.displayMetrics.density.toInt(), Bitmap.Config.RGB_565)

        // Canvas
        mCanvas = Canvas(bitmap)
        mCanvas.drawColor(resources.getColor(R.color.main))

        // Paint
        paint = Paint()
        paint.setColor(Color.BLACK)
        paint.setStrokeWidth(10f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(bitmap, 0f, 0f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val stopX = event.x
                val stopY = event.y

                mCanvas.drawLine(startX, startY, stopX, stopY, paint)
                startX = event.x
                startY = event.y

                // call onDraw
                invalidate()
            }
        }

        return true
    }

    fun saveBitmap(stream: OutputStream) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

    }
}