package bpsound.meettheteam.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import bpsound.meettheteam.items.MemberItem;

/**
 * Created by elegantuniv on 2016. 12. 10..
 */

public class MemberModel {

    private HashMap<Integer, ArrayList<MemberItem>> mHmMembers = new HashMap<>();

    private static MemberModel mInstance;
    public static MemberModel getInstance(){
        if(mInstance==null){
            mInstance = new MemberModel();
        }
        return mInstance;
    }

    public void initialize() {
        for(int i = 0; i< mHmMembers.size(); i++){
            ArrayList<MemberItem> alItem = mHmMembers.get(i);
            if(alItem!=null){
                alItem.clear();
            }
        }
        mHmMembers.clear();
    }

    public void setMemberItem(int key, MemberItem eItem){
        ArrayList<MemberItem> alItems;
        if(mHmMembers.get(key) == null){
            alItems = new ArrayList<>();
        } else {
            alItems = mHmMembers.get(key);
        }

        alItems.add(eItem);

        //shuffle
        Collections.shuffle(alItems);

        mHmMembers.put(key, alItems);
    }

    public ArrayList<MemberItem> getEventItems(int key){
        return this.mHmMembers.get(key);
    }

    public boolean hasEventItem(int posV, int posH) {
        ArrayList<MemberItem> alAds = this.mHmMembers.get(posV);
        if(alAds==null) return false;
        if(posH>=alAds.size()) return false;

        MemberItem item = alAds.get(posH);
        if(item==null){
            return false;
        } else {
            return true;
        }
    }
}
