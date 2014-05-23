package com.terwer.qrcode;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.terwer.qrcode.data.ContentStruct;

@SuppressLint("ValidFragment")
public class ContentFrameCenter extends Fragment {

    private ContentStruct mStruct;

    public ContentFrameCenter(ContentStruct struct){
        mStruct = struct;
    }

    @SuppressLint("ValidFragment")
    public static ContentFrameCenter newInstance(ContentStruct object){
        return new ContentFrameCenter(object);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activitymain_personal_center, null);
//		TextView tView = (TextView) view.findViewById(R.id.textView);
//		tView.setText(mStruct.toString());

        return view;
    }

}
