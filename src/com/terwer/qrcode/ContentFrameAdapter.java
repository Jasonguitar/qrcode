package com.terwer.qrcode;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import com.terwer.qrcode.data.ContentStruct;
import com.terwer.qrcode.data.TitleStruct;

import java.util.List;

public class ContentFrameAdapter extends FragmentPagerAdapter {

    private List<TitleStruct> mTitleList;
    private List<ContentStruct> mContentList;

    public ContentFrameAdapter(FragmentManager fm) {
        super(fm);

    }

    public void setContentList(List<ContentStruct> list) {
        mContentList = list;
    }

    public void setTitleList(List<TitleStruct> list) {
        mTitleList = list;
    }

    /**
     *
     * @param pos
     * @return
     */
    @Override
    public Fragment getItem(int pos) {
        // TODO Auto-generated method stub
        if (mContentList == null) {
            return null;
        }
        Log.i("pos", new Integer(pos).toString());
        switch (pos) {
            case 0:
                return ContentFrameLeft.newInstance(mContentList.get(pos));
            //case 1:
            //    return ContentFrameCenter.newInstance(mContentList.get(pos));
            case 1:
                return ContentFrameRight.newInstance(mContentList.get(pos));
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mContentList != null) {
            return mContentList.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position).toString();
        }
        return super.getPageTitle(position);
    }


}
