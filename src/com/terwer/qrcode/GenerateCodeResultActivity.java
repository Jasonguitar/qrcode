package com.terwer.qrcode;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import com.google.zxing.WriterException;
import com.terwer.qrcode.function.QrcodeHelper;
import com.terwer.qrcode.util.Util;

import java.io.IOException;

/**
 * Created by Tangyouwei on 2014/5/24.
 */
public class GenerateCodeResultActivity extends Activity {

    private ImageView img_qrcode;//显示二维码图片
    private static Bitmap qrcode_bitmap;
    /**
     * 头像图片
     */
    private Bitmap portrait;
    private Button btn_save_qrimg;
    private Button btn_share_qrimg;
    private Button btn_go_tocap;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.generatecode_result);

        img_qrcode = (ImageView) findViewById(R.id.img_qrcode);
        btn_save_qrimg = (Button) findViewById(R.id.btn_save_qrimg);
        btn_share_qrimg = (Button) findViewById(R.id.btn_share_qrimg);
        btn_go_tocap = (Button) findViewById(R.id.btn_go_tocap);

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
            qrcode_bitmap = QrcodeHelper.createQRImage(encode_text);
            QrcodeHelper.createQRCodeBitmapWithPortrait(qrcode_bitmap, portrait);
            img_qrcode.setImageBitmap(qrcode_bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        //保存图片
        btn_save_qrimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String path = Util.saveBitmap(qrcode_bitmap);
                    Util.showToast(GenerateCodeResultActivity.this, "图片保存成功，图片保存在" + path);
//                    Util.showConfirm(GenerateCodeResultActivity.this, "温馨提示",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Intent intent = new Intent();//异常
//                                    Util.showAlert(GenerateCodeResultActivity.this,path);
//                                    Uri content_url = Uri.parse("file:/"+path);
//                                    intent.setData(content_url);
//                                    startActivity(intent);
//                                }
//                            }, "是否立即查看？"
//                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //分享
        btn_share_qrimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //扫一扫
        btn_go_tocap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(GenerateCodeResultActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });


    }
}