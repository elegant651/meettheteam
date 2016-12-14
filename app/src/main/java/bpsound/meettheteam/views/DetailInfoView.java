package bpsound.meettheteam.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bpsound.meettheteam.MainActivity;
import bpsound.meettheteam.R;
import bpsound.meettheteam.items.MemberItem;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by elegantuniv on 2016. 6. 8..
 */
public class DetailInfoView extends FrameLayout {

    private Context mContext;

    @InjectView(R.id.btnClose) Button mBtnClose;
    @InjectView(R.id.ivAvatar) ImageView mIvAvatar;
    @InjectView(R.id.tvTitle) TextView mTvTitle;
    @InjectView(R.id.tvName) TextView mTvName;
    @InjectView(R.id.tvBio) TextView mTvBio;

    private MemberItem mCurItem;

    public DetailInfoView(Context context){
        this(context, null, 0);
    }

    public DetailInfoView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public DetailInfoView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        mContext = context;
        init();
    }

    public void setCurrentItem(MemberItem evItem){
        this.mCurItem = evItem;
        setData();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View thisView = inflater.inflate(R.layout.layout_dinfo_dialog, null);
        ButterKnife.inject(this, thisView);
        this.addView(thisView);

        setListener();
    }

    private void setListener() {
        mBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).hideDinfoDialog();
            }
        });
    }

    private void setData(){
        if(mCurItem==null) return;

        Picasso.with(mContext).load(mCurItem.avatar).into(mIvAvatar);
        mTvTitle.setText(""+mCurItem.title);
        mTvName.setText(""+mCurItem.firstName +" "+mCurItem.lastName);
        mTvBio.setText(""+mCurItem.bio);
    }
}
