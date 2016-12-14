package bpsound.meettheteam.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bpsound.meettheteam.items.MemberItem;
import bpsound.meettheteam.model.MemberModel;


/**
 * Created by elegantuniv on 2016. 5. 22..
 */
public class VerticalPagerAdapter extends PagerAdapter {
    private Context mContext;
    private int mParent;
    private int mChilds;


    public VerticalPagerAdapter(Context c, int parent, int childs){
        mContext = c;
        mParent = parent;
        mChilds = childs;
    }


    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mChilds;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ArrayList<MemberItem> curEvList = MemberModel.getInstance().getEventItems(position);

        if(curEvList==null || mParent >= curEvList.size()) {
            return null;
        }

        MemberItem curMemItem = curEvList.get(mParent);

        RelativeLayout linear = new RelativeLayout(mContext);

        linear.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        linear.setPadding(125, 10, 125, 10);
        linear.setGravity(Gravity.CENTER);
        linear.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        LinearLayout.LayoutParams llayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout llWrapper = new LinearLayout(mContext);
        llWrapper.setOrientation(LinearLayout.VERTICAL);
        llWrapper.setGravity(Gravity.CENTER);
        llWrapper.setLayoutParams(llayoutParam);

        RelativeLayout.LayoutParams rlayoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlayoutParams2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        ImageView ivAd = new ImageView(mContext);
        ivAd.setLayoutParams(rlayoutParams2);
        ivAd.setScaleType(ImageView.ScaleType.FIT_START);
        ivAd.setAdjustViewBounds(true);

        String url = curMemItem.avatar;
        if(url!=null){
            Picasso.with(mContext).load(url).into(ivAd);
            llWrapper.addView(ivAd);
        }

        String strName = curMemItem.firstName + " " + curMemItem.lastName;
        LinearLayout.LayoutParams llayoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tvName = new TextView(mContext);
        tvName.setLayoutParams(llayoutParams3);
        tvName.setText(strName);
        tvName.setTextColor(Color.BLACK);
        tvName.setTextSize(16);
        llWrapper.addView(tvName);

        String titleContent = curMemItem.title;
        TextView tvTitle = new TextView(mContext);
        tvTitle.setLayoutParams(llayoutParams3);
        tvTitle.setText(titleContent);
        tvTitle.setTextColor(Color.DKGRAY);
        tvTitle.setTextSize(14);
        llWrapper.addView(tvTitle);

        linear.addView(llWrapper);

        container.addView(linear);

        return linear;
    }

}
