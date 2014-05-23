package com.terwer.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import com.terwer.qrcode.left.LineReadActivity;
import com.terwer.qrcode.data.ContentStruct;
import com.terwer.qrcode.data.TitleStruct;
import com.terwer.qrcode.util.Util;
import com.terwer.qrcode.widget.UnderlinePageIndicatorEx;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tangyouwei on 2014/5/22.
 */
public class DecodeActivity extends FragmentActivity {

    private ContentFrameAdapter mContentAdapter;
    private ViewPager mPager;
    private TabPageIndicator mTabPageIndicator;
    private UnderlinePageIndicatorEx mUnderlinePageIndicator;


    private int COUNT = 0;
    private List<TitleStruct> mTitleList;
    private List<ContentStruct> mContentList;


    //用于判断来源的变量
    private static String from = "left1";
    //条码类型
    private static CodeType codeType = CodeType.QR_CODE;
    //条码内容(json格式)
    private static String contents;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.decode);

        initData();
        setupViews();
        //初始化扫描
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            codeType = (CodeType) bundle.get("CodeType");
            from = bundle.getString("From");
            contents = bundle.getString("Contents");
            Log.i("Msg", codeType.toString());
            Log.i("Msg1", from);
            //Util.showAlert(DecodeAcyivity.this, codeType.toString());
            //Util.showAlert(DecodeAcyivity.this, from);
        } else {
            Util.showToast(DecodeActivity.this, "来路非法！");
        }


        //处理解码数据
        doDecode(codeType, from);
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

    /**
     * 解码后续数据处理
     *
     * @param codeType
     * @param from
     */
    private void doDecode(CodeType codeType, String from) {
        if (contents != null) {
            //Util.showAlert(DecodeAcyivity.this, from);
            Log.i("Msg2", from);

            if (from.equalsIgnoreCase("left1")) {//立即扫描
                //Util.showAlert(DecodeActivity.this, "立即扫描：" + result.toString());
                Intent intent1 = new Intent(DecodeActivity.this, DecodeResultActivity.class);
                intent1.putExtra("Contents", contents);
                startActivity(intent1);
            } else if (from.equalsIgnoreCase("left2")) {//条码扫描
                //Util.showAlert(DecodeActivity.this, "条码扫描：" + result.toString());
                Intent intent2 = new Intent(DecodeActivity.this, LineReadActivity.class);
                intent2.putExtra("Contents", contents);
                startActivity(intent2);
            } else if (from.equalsIgnoreCase("left3")) {//二维解码
                //Util.showAlert(DecodeActivity.this, "二维解码：" + result.toString());
                Intent intent2 = new Intent(DecodeActivity.this, DecodeResultActivity.class);
                intent2.putExtra("Contents", contents);
                startActivity(intent2);
            } else {
                Util.showAlert(DecodeActivity.this, "来路非法");
            }


        } else {
            Util.showToast(DecodeActivity.this, "您返回了主界面");
        }

    }
}