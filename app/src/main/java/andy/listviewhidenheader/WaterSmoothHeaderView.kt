package andy.listviewhidenheader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.graphics.RectF
import android.graphics.PointF
import android.R.attr.y
import android.R.attr.x
import android.view.MotionEvent
import android.R.attr.centerY
import android.R.attr.centerX







/**
 * Created by andyli on 2017/9/15.
 */
class WaterSmoothHeaderView : FrameLayout {

    private var mPaint: Paint = Paint()
    private var mPath: Path = Path()
    private var centerX: Int = 0
    var centerY: Int = 0
    private var start: PointF = PointF()
    var end: PointF =  PointF()
    var control: PointF =  PointF(-1f,-1f)
    var smoothLength = 0;
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
            smoothLength = it.getDimensionPixelSize(R.styleable.WaterSmoothHeaderView_waterSmoothLength, 0)
        }
        a?.recycle();
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        start.x = (0).toFloat()
        start.y = (h - smoothLength).toFloat();
        end.x = (w).toFloat()
        end.y = (h - smoothLength).toFloat();
        centerX = ((start.x + end.x)/2).toInt();
        centerY = h - smoothLength
        control.x = centerX.toFloat()
        if(bottom - top > smoothLength){
            control.y = (centerY + smoothLength).toFloat()
        } else {
            control.y = (centerY + bottom - top).toFloat()
        }
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.setColor(smoothBgColor)
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 8f
        mPath.reset()
        mPath.moveTo(start.x, start.y)
        mPath.quadTo(control.x, control.y, end.x, end.y)
        mPath.lineTo(end.x, 0f)
        mPath.lineTo(0f,0f)
        mPath.lineTo(0f, start.y)
        canvas.drawPath(mPath, mPaint)
    }

}