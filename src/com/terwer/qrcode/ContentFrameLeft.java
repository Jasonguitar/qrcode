package com.terwer.qrcode;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.terwer.qrcode.data.ContentStruct;

@SuppressLint("ValidFragment")
public class ContentFrameLeft extends Fragment {

    private ContentStruct mStruct;

    //立即扫描
    private Button btn_scan_now;

    //通知
    private TextView txt_tool_notice;
    //条形码扫描
    private LinearLayout ly_line_read;
    //图片解码
    private  LinearLayout ly_pic_decode;
    //文字编码
    private LinearLayout ly_gen_text;
    //语音解码
    private  LinearLayout ly_gen_audio;

    public ContentFrameLeft(ContentStruct struct) {
        mStruct = struct;
    }

    @SuppressLint("ValidFragment")
    public static ContentFrameLeft newInstance(ContentStruct object) {
        return new ContentFrameLeft(object);
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


        View view = inflater.inflate(R.layout.activitymain_tools, null);

        //设置工具箱
        setupTools(view);

        return view;
    }


    /**
     * 设置工具箱
     *
     * @param view
     */
    private void setupTools(View view) {
        btn_scan_now = (Button) view.findViewById(R.id.btn_scan_now);
        txt_tool_notice = (TextView) view.findViewById(R.id.txt_tool_notice);
        ly_line_read = (LinearLayout) view.findViewById(R.id.ly_line_read);
        ly_pic_decode=(LinearLayout)view.findViewById(R.id.ly_pic_decode);
        ly_gen_text = (LinearLayout) view.findViewById(R.id.ly_gen_text);
        ly_gen_audio=(LinearLayout)view.findViewById(R.id.ly_gen_audio);

        //立即扫描
        btn_scan_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CaptureActivity.class);
                intent.putExtra("CodeType",CodeType.ALL_CODE);
                intent.putExtra("From","left1");
                startActivity(intent);
            }
        });

        //条码识别
        ly_line_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent();
                intent.setClass(getActivity(), CaptureActivity.class);
                intent.putExtra("CodeType",CodeType.BAR_CODE);
                intent.putExtra("From","left2");
                startActivity(intent);

            }
        });

        //二维解码
        ly_pic_decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CaptureActivity.class);
                intent.putExtra("CodeType",CodeType.QR_CODE);
                intent.putExtra("From","left3");
                startActivity(intent);

            }
        });



        //文字编码
        ly_gen_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), GenerateCodeActivity.class);
                intent.putExtra("From","left4");
                startActivity(intent);

            }
        });

        //语音编码
        ly_gen_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), GenerateCodeActivity.class);
                intent.putExtra("From","left5");
                startActivity(intent);

            }
        });



    }


}
