package andy.listviewhidenheader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout
import android.graphics.PointF
import android.support.v4.view.NestedScrollingChild
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.support.v4.view.NestedScrollingChildHelper
import android.widget.ListView


/**
 * Created by andyli on 2017/9/15.
 */
class WaterSmoothHeaderView : FrameLayout {
    var mPaint: Paint = Paint()
    var mPath: Path = Path()
    var centerX =0f
    var centerY = 0f
    var start: PointF = PointF()
    var end: PointF =  PointF()
    var control: PointF =  PointF(-1f,-1f)
    var smoothLength = 0f
    var smoothBgColor = Color.RED;


    constructor(context: Context?) : super(context){
        init(context, null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(context, attrs)
    }


    private fun init(context: Context?, attrs: AttributeSet?) {
        setBackgroundColor(Color.TRANSPARENT)
        val a = context?.obtainStyledAttributes(attrs, R.styleable.WaterSmoothHeaderView, 0, 0);
        a?.let {
            smoothBgColor = it.getColor(R.styleable.WaterSmoothHeaderView_waterSmoothBackground, Color.RED)
            smoothLength = it.getDimension(R.styleable.WaterSmoothHeaderView_waterSmoothLength, 0f)
        }
        a?.recycle();
        mPaint.isAntiAlias = true
    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        update(this.smoothLength)

    }

    fun update(smoothLength:Float){
        start.x = (0).toFloat()
        start.y = (height - smoothLength)
        end.x = (width).toFloat()
        end.y = (height - smoothLength)
        centerX = (start.x + end.x)/2f
        centerY = height - smoothLength
        control.x = centerX
        control.y = (height + smoothLength)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.setColor(smoothBgColor)
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 2f
        mPath.reset()
        mPath.moveTo(start.x, start.y)
        mPath.quadTo(control.x, control.y, end.x, end.y)
        mPath.lineTo(end.x, 0f)
        mPath.lineTo(0f,0f)
        mPath.lineTo(0f, start.y)
        canvas.drawPath(mPath, mPaint)
    }

    open fun smooth(scrollY:Int, dy:Int) :Boolean{
        Log.d("test", "scrollY" + scrollY)
        val scale = scrollY/(height - smoothLength);
        if(scale <= 1) {
            val tmpSmooth: Float = (smoothLength * (1f - scale))
            update(tmpSmooth)
            invalidate()
            return true
        }
        return false
    }


}