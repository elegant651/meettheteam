package bpsound.meettheteam;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import bpsound.meettheteam.adapters.VerticalPagerAdapter;
import bpsound.meettheteam.data.Constants;
import bpsound.meettheteam.items.MemberItem;
import bpsound.meettheteam.model.MemberModel;
import bpsound.meettheteam.viewpager.DoubleViewPager;
import bpsound.meettheteam.viewpager.DoubleViewPagerAdapter;
import bpsound.meettheteam.viewpager.MyHorizontalViewPager;
import bpsound.meettheteam.views.DetailInfoView;
import bpsound.meettheteam.views.NavView;

public class MainActivity extends AppCompatActivity {

    private DoubleViewPager mVpMain;
    private NavView mNavView;
    private Button mBtnDetail;
    private DetailInfoView mDInfoDlog;
    private FrameLayout mFlDim;

    private int mCurPosH = 0;
    private int mCurPosV = 0;
    private MemberItem mCurMemItem;
    private ArrayList<PagerAdapter> mAlVAdapters = new ArrayList<>();
    private DoubleViewPagerAdapter mDVAdapter;

    private int mCurNumContent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setListener();
        processGetMembers();
    }

    private void bindViews() {
        mVpMain = (DoubleViewPager)findViewById(R.id.vpMain);
        mNavView = (NavView)findViewById(R.id.navview);
        mNavView.setCurNav(Constants.NAV1);
        mFlDim = (FrameLayout)findViewById(R.id.flDim);
        mBtnDetail = (Button)findViewById(R.id.btnDetail);
        mDInfoDlog = (DetailInfoView)findViewById(R.id.dinfoDlog);
    }

    private void setListener() {
        mBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDinfoDialog();
            }
        });
    }

    private void processGetMembers() {
        MemberModel.getInstance().initialize();

        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
        Query queryRef = firebaseRef.child("members").orderByChild("id").limitToLast(45);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "d:" + dataSnapshot.getKey() + "/" + dataSnapshot.getValue());

                MemberItem item =
                        dataSnapshot.getValue(MemberItem.class);

                int typeId;
                if(mCurNumContent>=0 && mCurNumContent<6){
                    typeId = Constants.NAV1;
                } else if(mCurNumContent>=6 && mCurNumContent<12){
                    typeId = Constants.NAV2;
                } else {
                    typeId = Constants.NAV3;
                }

                MemberModel.getInstance().setMemberItem(typeId, item);

                loadPagerUI();
                setMemberType(0,0);
                moveVPViews(mCurPosV);
                mCurNumContent++;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
    }

    private void loadPagerUI() {
        mAlVAdapters = new ArrayList<PagerAdapter>();
        for (int i=0; i<Constants.HORIZONTAL_CHILDS; i++){
            VerticalPagerAdapter vpAdapter = new VerticalPagerAdapter(this, i, Constants.VERTICAL_CHILDS);
            mAlVAdapters.add(vpAdapter);
        }

        mDVAdapter = new DoubleViewPagerAdapter(getApplicationContext(), mAlVAdapters);
        mDVAdapter.setActivity(this);
        mVpMain.setAdapter(mDVAdapter);
        mVpMain.setOffscreenPageLimit(10);
        mDVAdapter.moveAllVPViews(0);

        /* horizontal paging */
        mVpMain.setOnPageChangeListener(new MyHorizontalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int posH = position;
                int posV = mDVAdapter.getPosV();

                if(!MemberModel.getInstance().hasEventItem(posV, posH)){
                    mVpMain.setCurrentItem(mCurPosH);
                } else {
                    mCurPosH = posH;
                    moveNavView(posV);
                    mDVAdapter.moveAllVPViews(posV);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void moveVPViews(int position) {
        mCurPosH = 0;
        mVpMain.setCurrentItem(mCurPosH);
        moveNavView(position);

        if(mDVAdapter!=null){
            mDVAdapter.moveAllVPViews(position);
        }
    }

    public void moveNavView(int position){
        mCurPosV = position;
        mNavView.setCurNav(position);

        checkBlankEvent(mCurPosH, position);
        setMemberType(mCurPosH, position);
    }

    private void checkBlankEvent(int posH, int posV) {
        ArrayList<MemberItem> curMemList = MemberModel.getInstance().getEventItems(posV);

        if(curMemList==null || posH >= curMemList.size()){
            mCurPosH = 0;
            mVpMain.setCurrentItem(mCurPosH);
        }
    }

    private void setMemberType(int posH, int posV){
        ArrayList<MemberItem> curMemList = MemberModel.getInstance().getEventItems(posV);

        if(curMemList==null || posH >= curMemList.size()) {
            return;
        }

        mCurMemItem = curMemList.get(posH);
    }

    public void showDinfoDialog() {
        mDInfoDlog.setCurrentItem(mCurMemItem);
        mDInfoDlog.setVisibility(View.VISIBLE);
        mFlDim.setVisibility(View.VISIBLE);
    }

    public void hideDinfoDialog() {
        mDInfoDlog.setVisibility(View.GONE);
        mFlDim.setVisibility(View.GONE);
    }
}
