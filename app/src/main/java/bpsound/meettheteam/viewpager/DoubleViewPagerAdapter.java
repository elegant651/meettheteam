package bpsound.meettheteam.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;

import bpsound.meettheteam.MainActivity;


public class DoubleViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private MainActivity mActivity;
    private ArrayList<PagerAdapter> mAdapters;
    private ArrayList<MyVerticalViewPager> mVVPAl = new ArrayList<>();
    private int mPosV = 0;

    public DoubleViewPagerAdapter(Context context, ArrayList<PagerAdapter> verticalAdapters){
        mContext = context;
        mAdapters = verticalAdapters;
    }

    public void setActivity(MainActivity activity){
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mAdapters.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final MyVerticalViewPager childVP = new MyVerticalViewPager(mContext);
        childVP.setOffscreenPageLimit(10);
        childVP.setOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                moveAllVPViews(position);

                mPosV = position;
                mActivity.moveNavView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        childVP.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        childVP.setAdapter(mAdapters.get(position));
        mVVPAl.add(position, childVP);
        container.addView(childVP);
        return childVP;
    }

    public void moveAllVPViews(int position) {
        for(int i=0; i<mVVPAl.size(); i++){
            MyVerticalViewPager vvP = mVVPAl.get(i);
            vvP.setCurrentItem(position);
        }
    }

    public int getPosV(){
        return this.mPosV;
    }

}
