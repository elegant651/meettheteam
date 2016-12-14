package bpsound.meettheteam.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashMap;

import bpsound.meettheteam.MainActivity;
import bpsound.meettheteam.R;
import bpsound.meettheteam.data.Constants;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by elegantuniv on 2016. 5. 22..
 */
public class NavView extends FrameLayout {

    private Context mContext;

    @InjectView(R.id.btnNav1) LinearLayout mBtnNav1;
    @InjectView(R.id.btnNav2) LinearLayout mBtnNav2;
    @InjectView(R.id.btnNav3) LinearLayout mBtnNav3;

    HashMap<Integer, LinearLayout> mHmBtns = new HashMap<>();

    public NavView(Context context){
        this(context, null, 0);
    }

    public NavView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public NavView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        mContext = context;
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View thisView = inflater.inflate(R.layout.layout_view_nav, null);
        ButterKnife.inject(this, thisView);
        this.addView(thisView);

        bindViews();
        setListener();
    }

    private void bindViews(){
        mHmBtns.put(Constants.NAV1, mBtnNav1);
        mHmBtns.put(Constants.NAV2, mBtnNav2);
        mHmBtns.put(Constants.NAV3, mBtnNav3);
    }

    private void setListener(){
        mBtnNav1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mContext).moveVPViews(Constants.NAV1);
                setCurNav(Constants.NAV1);
            }
        });

        mBtnNav2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mContext).moveVPViews(Constants.NAV2);
                setCurNav(Constants.NAV2);
            }
        });

        mBtnNav3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mContext).moveVPViews(Constants.NAV3);
                setCurNav(Constants.NAV3);
            }
        });
    }

    public void setCurNav(int curidx) {
        for(int i=0; i<mHmBtns.size(); i++){
            LinearLayout curnav = mHmBtns.get(i);
            curnav.setBackgroundColor(Color.parseColor("#4D000000"));
        }

        LinearLayout mBtnCurNav = mHmBtns.get(curidx);
        mBtnCurNav.setBackgroundColor(Color.parseColor("#F66513"));
    }
}
