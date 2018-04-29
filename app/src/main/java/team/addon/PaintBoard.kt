package devdon.com.painter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
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

    private lateinit var mBitmap: Bitmap

    private lateinit var mCanvas: Canvas

    private var mPaint: Paint

    private var startX:Float = 0f

    private var startY:Float = 0f

    var hasContent = false

    var bgColor = R.color.post_it_yellow

    init {

        // Bitmap
        val width = Resources.getSystem().displayMetrics.widthPixels
        val dfBtmp = Bitmap.createBitmap(width, 330 * resources.displayMetrics.density.toInt(), Bitmap.Config.RGB_565)
        setBitmap(dfBtmp)

        // Canvas
        clearCanvas()

        // Paint
        mPaint = Paint()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(mBitmap, 0f, 0f, mPaint)
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

                mCanvas.drawLine(startX, startY, stopX, stopY, mPaint)
                startX = event.x
                startY = event.y

                // call onDraw
                invalidate()
            }
        }
        return true
    }
    
    fun setBitmap(b: Bitmap) {
        mBitmap = b
        mCanvas = Canvas(b)

    }

    fun setPaint(){
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        setPaintColor()
        setPaintWidth()
        setPaintCap()
    }

    fun setPaintColor(mColor: Int = R.color.paint_black) {
        mPaint.color = ContextCompat.getColor(context, mColor)
    }

    fun setPaintWidth(width: Float = 10f) {
        mPaint.strokeWidth = width
    }

    fun setPaintCap(cap: Paint.Cap = Paint.Cap.ROUND) {
        mPaint.strokeCap = cap
    }

    fun clearCanvas() {
        mCanvas.drawColor(ContextCompat.getColor(context, bgColor))
    }

    fun saveBitmap(stream: OutputStream) {
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    }

    fun paintWidth(): Float {
        return mPaint.strokeWidth
    }
}