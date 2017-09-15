package andy.listviewhidenheader;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by andyli on 2017/9/13.
 */

public class DependentBehavior extends  CoordinatorLayout.Behavior<View> {

	public DependentBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		return dependency instanceof ListView;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		if(dependency.getTop() < 20) {
			child.setAlpha((dependency.getTop()/20));
			if(dependency.getTop() == 10 ){
				child.setVisibility(View.GONE);
			}
			return true;
		} else {
			return false;
		}
	}
}
