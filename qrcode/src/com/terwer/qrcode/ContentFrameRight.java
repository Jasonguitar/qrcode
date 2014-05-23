package com.terwer.qrcode;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.terwer.qrcode.data.ContentStruct;

@SuppressLint("ValidFragment")
public class ContentFrameRight extends Fragment {

    private ContentStruct mStruct;

    public ContentFrameRight(ContentStruct struct){
        mStruct = struct;
    }

    @SuppressLint("ValidFragment")
    public static ContentFrameRight newInstance(ContentStruct object){
        return new ContentFrameRight(object);
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


        View view = inflater.inflate(R.layout.activitymain_apps, null);
//		TextView tView = (TextView) view.findViewById(R.id.textView);
//		tView.setText(mStruct.toString());

        return view;
    }

}
