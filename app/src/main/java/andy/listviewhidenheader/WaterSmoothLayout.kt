package andy.listviewhidenheader

import android.content.Context
import android.os.Bundle
import android.support.v4.view.NestedScrollingParent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * Created by andyli on 2017/9/16.
 */
class WaterSmoothLayout :LinearLayout, NestedScrollingParent{

    var recyclerView_Scrolly = 0;
    var header_Scrolly = 0;
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return target is RecyclerView
    }

    override fun onStopNestedScroll(child: View?) {

    }

    override fun onNestedScroll(target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {

    }

    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        val header: WaterSmoothHeaderView = getChildAt(0) as WaterSmoothHeaderView;
        val recycler: RecyclerView = getChildAt(1) as RecyclerView;
        val isNestedScroll = (recycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() == 0
        if(isNestedScroll && recyclerView_Scrolly - header.height  < 0) {
            if (dy > 0) {
                //往上滑 +
                if (header_Scrolly < header.height) {
                    scrollBy(0, dy);
                    header.smooth(header_Scrolly, dy)
                    if (consumed != null) {
                        consumed[0] = 0;
                        consumed[1] = dy;
                    }
                    resetheight(recycler)
                    header_Scrolly += dy
                    if (header_Scrolly > header.height) {
                        header_Scrolly = header.height;
                    }
                }
            } else if (dy < 0) {
                //往下滑 -
                if (header_Scrolly > 0) {

                    if (header_Scrolly > 0) {
                        scrollBy(0, dy);
                        header.smooth(header_Scrolly, dy)
                        if (consumed != null) {
                            consumed[0] = 0;
                            consumed[1] = dy;
                        }
                        resetheight(recycler)
                        header_Scrolly += dy
                        if (header_Scrolly < 0) {
                            header_Scrolly = 0;
                        }
                    }
                }
            }
        }
        recyclerView_Scrolly += dy
        if (recyclerView_Scrolly < 0) {
            recyclerView_Scrolly = 0;
        } else if(recyclerView_Scrolly > recycler.computeVerticalScrollRange() + header.height){
            recyclerView_Scrolly = recycler.computeVerticalScrollRange()+ header.height;
        }
    }

     fun resetheight(view:View){
         val point:IntArray = kotlin.IntArray(2)
         view.getLocationOnScreen(point);
         val point2:IntArray = kotlin.IntArray(2)
         getLocationOnScreen(point2);
         view.layoutParams.height = height - (point[1]-point2[1])+1
         view.requestLayout()
    }

    override fun onNestedFling(target: android.view.View?, velocityX: kotlin.Float, velocityY: kotlin.Float, consumed: kotlin.Boolean): kotlin.Boolean {
        return false
    }

    override fun onNestedScrollAccepted(child: View?, target: View?, axes: Int) {
    }

    override fun onNestedPreFling(target: View?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedPrePerformAccessibilityAction(target: View?, action: Int, args: Bundle?): Boolean {
        return false
    }


}
