//import java.util.ArrayList
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.graphics.Path
//import android.support.v4.content.ContextCompat
//import android.view.MotionEvent
//import android.view.View
//import android.view.View.OnTouchListener
//import team.addon.R
//
//class DrawView(context:Context):View(context), OnTouchListener {
//    private val mCanvas:Canvas
//    private val mPath:Path
//    private val mPaint:Paint
//    private val paths = ArrayList<Path>()
//    private val undonePaths = ArrayList<Path>()
//    private val im:Bitmap
//    private val mX:Float = 0.toFloat()
//    private val mY:Float = 0.toFloat()
//    init{
//        setFocusable(true)
//        setFocusableInTouchMode(true)
//        this.setOnTouchListener(this)
//
//        mPaint = Paint()
//        setPaint()
//
//        mCanvas = Canvas()
//        mPath = Path()
//        im = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher)
//    }
//
//    fun setPaint(){
//        mPaint.style = Paint.Style.STROKE
//        mPaint.isAntiAlias = true
//        mPaint.isDither = true
//        setPaintColor()
//        setPaintWidth()
//        setPaintCap()
//    }
//
//    fun setPaintColor(mColor: Int = R.color.paint_black) {
//        mPaint.color = ContextCompat.getColor(context, mColor)
//    }
//
//    fun setPaintWidth(width: Float = 10f) {
//        mPaint.strokeWidth = width
//    }
//
//    fun setPaintCap(cap: Paint.Cap = Paint.Cap.ROUND) {
//        mPaint.strokeCap = cap
//    }
//
//    protected fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//    }
//    protected fun onDraw(canvas:Canvas) {
//        //mPath = new Path();
//        //canvas.drawPath(mPath, mPaint);
//        for (p in paths)
//        {
//            canvas.drawPath(p, mPaint)
//        }
//        canvas.drawPath(mPath, mPaint)
//    }
//    private fun touch_start(x:Float, y:Float) {
//        undonePaths.clear()
//        mPath.reset()
//        mPath.moveTo(x, y)
//        mX = x
//        mY = y
//    }
//    private fun touch_move(x:Float, y:Float) {
//        val dx = Math.abs(x - mX)
//        val dy = Math.abs(y - mY)
//        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
//        {
//            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
//            mX = x
//            mY = y
//        }
//    }
//    private fun touch_up() {
//        mPath.lineTo(mX, mY)
//        // commit the path to our offscreen
//        mCanvas.drawPath(mPath, mPaint)
//        // kill this so we don't double draw
//        paths.add(mPath)
//        mPath = Path()
//    }
//    fun onClickUndo() {
//        if (paths.size() > 0)
//        {
//            undonePaths.add(paths.removeAt(paths.size() - 1))
//            invalidate()
//        }
//        else
//        {
//        }
//        //toast the user
//    }
//    fun onClickRedo() {
//        if (undonePaths.size() > 0)
//        {
//            paths.add(undonePaths.removeAt(undonePaths.size() - 1))
//            invalidate()
//        }
//        else
//        {
//        }
//        //toast the user
//    }
//    fun onTouch(arg0:View, event:MotionEvent):Boolean {
//        val x = event.getX()
//        val y = event.getY()
//        when (event.getAction()) {
//            MotionEvent.ACTION_DOWN -> {
//                touch_start(x, y)
//                invalidate()
//            }
//            MotionEvent.ACTION_MOVE -> {
//                touch_move(x, y)
//                invalidate()
//            }
//            MotionEvent.ACTION_UP -> {
//                touch_up()
//                invalidate()
//            }
//        }
//        return true
//    }
//    companion object {
//        private val TOUCH_TOLERANCE = 4f
//    }
//}