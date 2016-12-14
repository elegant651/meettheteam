package bpsound.meettheteam.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by elegantuniv on 2016. 6. 3..
 */
public class MyHorizontalViewPager extends HorizontalViewPager {

    public MyHorizontalViewPager(Context context) { super(context); }

    public MyHorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

        return false;
    }
}
