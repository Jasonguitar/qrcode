package com.terwer.qrcode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.terwer.qrcode.function.QrcodeHelper;
import com.terwer.qrcode.util.Util;

/**
 * Created by Tangyouwei on 2014/5/22.
 */
public class GenerateCodeActivity extends Activity {
    //用于判断来源的变量
    private static String from = "left1";

    private EditText ed_text;
    private Button btn_gen_code;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.generate_code);


        ed_text = (EditText) findViewById(R.id.ed_text);
        btn_gen_code = (Button) findViewById(R.id.btn_gen_code);

//        //初始化扫描
//        Bundle bundle = getIntent().getExtras();
//        if (null != bundle) {
//            from = bundle.getString("From");
//        } else {
//            Util.showToast(GenerateCodeActivity.this, "来路非法！");
//        }
//
//        if (from.equalsIgnoreCase("left4")) {//文字编码
//
//
//        } else if (from.equalsIgnoreCase("left5")) {//语音编码
//            Util.showAlert(GenerateCodeActivity.this, "正准备录音。。。");
//
//        } else {
//            Util.showAlert(GenerateCodeActivity.this, "来路非法");
//        }


        //Util.showAlert(GenerateCodeActivity.this, "left4");
        btn_gen_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if (ed_text.getText().toString().equals("")){
                            Util.showToast(GenerateCodeActivity.this,"文字内容不能为空");
                            return;
                        }
                        QrcodeHelper.encodeBarcode(GenerateCodeActivity.this, "TEXT_TYPE", ed_text.getText().toString());
            }
        });



    }


}