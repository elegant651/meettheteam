package bpsound.meettheteam.viewpager;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyVerticalViewPager extends VerticalViewPager {

    public MyVerticalViewPager(Context context) { super(context); }

    public MyVerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

        return false;
    }

}