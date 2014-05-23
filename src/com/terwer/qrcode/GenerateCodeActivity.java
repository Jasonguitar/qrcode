package com.terwer.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.google.zxing.WriterException;
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

        btn_gen_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_text.getText().toString().equals("")) {
                    Util.showToast(GenerateCodeActivity.this, "文字内容不能为空");
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.setClass(GenerateCodeActivity.this, GenerateCodeResultActivity.class);
                    intent.putExtra("CodeText", ed_text.getText().toString());
                    startActivity(intent);
                }
            }
        });


    }


}