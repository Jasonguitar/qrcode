package com.terwer.qrcode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.zxing.WriterException;
import com.terwer.qrcode.function.QrcodeHelper;

/**
 * Created by Tangyouwei on 2014/5/24.
 */
public class GenerateCodeResultActivity extends Activity {

    private ImageView img_qrcode;//显示二维码图片


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gencode_result);

        img_qrcode = (ImageView) findViewById(R.id.img_qrcode);

        //扫描信息展示
        String encode_text = "";
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            encode_text = bundle.getString("CodeText");
        }

        try {
            QrcodeHelper.createQRImage(encode_text, img_qrcode);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }
}