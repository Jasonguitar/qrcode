package com.terwer.qrcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;
import com.terwer.qrcode.function.QrcodeHelper;

/**
 * Created by Tangyouwei on 2014/5/24.
 */
public class GenerateCodeResultActivity extends Activity {

    private ImageView img_qrcode;//显示二维码图片
    /**
     * 头像图片
     */
    private Bitmap portrait;

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
            //正常二维码
            //QrcodeHelper.createQRImage(encode_text, img_qrcode);

            // 用于显示带头像的二维码的view
            // 初始化头像
            portrait = QrcodeHelper.initProtrait(GenerateCodeResultActivity.this, "small.jpg");
            // 建立二维码
            Bitmap qr = QrcodeHelper.createQRImage(encode_text);
            QrcodeHelper.createQRCodeBitmapWithPortrait(qr, portrait);
            img_qrcode.setImageBitmap(qr);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }
}