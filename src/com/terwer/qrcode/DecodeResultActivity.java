package com.terwer.qrcode;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.terwer.qrcode.util.Util;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Created by Tangyouwei on 2014/5/22.
 */
public class DecodeResultActivity extends Activity {

    private TextView txt_decode_result;
    private Button btn_do;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.decode_result);

        txt_decode_result = (TextView) findViewById(R.id.txt_decode_result);

        //扫描信息展示
        String decode_result = "";
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            decode_result = bundle.getString("Contents");
         }

        Gson gson = new Gson();
        Result result=gson.fromJson(decode_result,Result.class);
        Util.showAlert(DecodeResultActivity.this,result.getText());
        ////////////////////////////////////////////////////////////





        return;

       // txt_decode_result.setText("扫描到的信息如下：\r\n" + decode_result.replace("Format", "编码格式").replace("QR_CODE", "二维码").replace("Contents", "内容").replace("Raw bytes:", "大小:").replace("bytes)", "字节)").replace("Orientation: null", "").replace("EC level: H", "").replace("EC level: null", "").replace("EC level: L",""));
       // txt_decode_result.setTextColor(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));


        //final String contents = getContents(decode_result);

       // final String contents = decode_result;
        //Util.showAlert(DecodeResultActivity.this,contents);
//        if (contents.contains("weixin.qq.com")) {
//            Util.showConfirm(DecodeResultActivity.this, "温馨提示", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Intent weixin = new Intent(Intent.ACTION_MAIN);
//                    weixin.addCategory(Intent.CATEGORY_LAUNCHER);
//                    ComponentName cn = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//                    weixin.setComponent(cn);
//                    weixin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(weixin);
//                }
//            }, "检测到微信二维码，是否打开微信？");
//
//        } else {
//            if (contents.startsWith("http://") || contents.startsWith("https://")) {
//                Util.showConfirm(DecodeResultActivity.this, "温馨提示", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(contents);
//                        intent.setData(content_url);
//                        startActivity(intent);
//                    }
//                }, "检测到网址，是否打开浏览器？");
//            }
//        }

    }

    /**
     * 根据返回内容获取有用的内容信息
     *
     * @param content
     * @return
     */
    private String getContents(String content) {
        return StringUtils.substringsBetween(content, "Contents:", "Raw")[0].trim();
    }
}