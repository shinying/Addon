package team.addon
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.mindorks.placeholderview.annotations.View



class DrawBoard(context: Context, attrs: AttributeSet) : View {

    protected fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //paint a circle
        var paint = Paint()
        paint.setColor(Color.BLUE)
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeWidth(10)
        canvas.drawCircle(120, 80, 60, paint)

        //paint string
        paint = Paint()
        paint.setColor(Color.YELLOW)
        paint.setTextSize(20)
        canvas.drawText("My name is Linc!", 245, 140, paint)

        //draw line
        paint = Paint()
        paint.setColor(Color.BLACK)
        canvas.drawLine(245, 145, 500, 145, paint)
    }
}