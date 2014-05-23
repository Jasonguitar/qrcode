package com.terwer.qrcode;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import com.terwer.qrcode.data.ContentStruct;
import com.terwer.qrcode.data.TitleStruct;
import com.terwer.qrcode.widget.UnderlinePageIndicatorEx;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @project qrcode
 * @package com.terwer.qrcode
 * @filename filename
 * @description 描述
 * @author: Terwer
 * @updatetime: 14-5-13 上午3:04
 * @site http://www.xinvalue.com
 * @mail cbgtyw@gmail.com
 * To change this template use File | Settings | File Templates.
 */


public class MainActivity extends FragmentActivity {

    private ContentFrameAdapter mContentAdapter;
    private ViewPager mPager;
    private TabPageIndicator mTabPageIndicator;
    private UnderlinePageIndicatorEx mUnderlinePageIndicator;


    private int COUNT = 0;
    private List<TitleStruct> mTitleList;
    private List<ContentStruct> mContentList;




    @Override
    protected void onCreate(Bundle arg0) {

        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initData();
        setupViews();


    }



    private void setupViews() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mContentAdapter);

        mTabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        mTabPageIndicator.setViewPager(mPager);

        mUnderlinePageIndicator = (UnderlinePageIndicatorEx) findViewById(R.id.underline_indicator);
        mUnderlinePageIndicator.setViewPager(mPager);
        mUnderlinePageIndicator.setFades(false);

        mTabPageIndicator.setOnPageChangeListener(mUnderlinePageIndicator);
    }


    private void initData() {
        mContentList = new ArrayList<ContentStruct>();
        mTitleList = new ArrayList<TitleStruct>();

        String[] arrStrings = getResources().getStringArray(R.array.sections);
        COUNT = arrStrings.length;
        for (int i = 0; i < COUNT; i++) {
            ContentStruct object = new ContentStruct();
            object.daString = arrStrings[i];
            object.index = i;
            mContentList.add(object);

            TitleStruct object2 = new TitleStruct(arrStrings[i]);
            mTitleList.add(object2);
        }


        mContentAdapter = new ContentFrameAdapter(getSupportFragmentManager());
        mContentAdapter.setContentList(mContentList);
        mContentAdapter.setTitleList(mTitleList);

    }




}
