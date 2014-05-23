package com.terwer.qrcode.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @project terwer
 * @package com.xinvalue.util
 * @filename filename
 * @description 描述
 * @author: Terwer
 * @updatetime: 13-12-15 上午12:45
 * @site http://www.xinvalue.com
 * @mail cbgtyw@gmail.com
 * To change this template use File | Settings | File Templates.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


public class Util {
    public static void showToast(final Activity activity, final String content) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(activity, content,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public static void showAlert(final Activity activity, final String content) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new AlertDialog.Builder(activity).setMessage(content)
                        .create();
                dialog.show();
            }
        });
    }

    public static void showConfirm(final Activity activity,final String title, final String content) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new AlertDialog.Builder(activity).setTitle(title).setMessage(content).setPositiveButton("确定", null)
                        .create();
                dialog.show();
            }
        });
    }


    /**
     * 弹出对话框，带回调
     * @param activity
     * @param title
     * @param l
     * @param content
     */
    public static void showConfirm(final Activity activity,final String title,final  DialogInterface.OnClickListener l, final String content) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new AlertDialog.Builder(activity).setTitle(title).setMessage(content).setPositiveButton("确定", l)
                        .create();
                dialog.show();
            }
        });
    }

    public static void setTextViewContent(final Activity activity,
                                          final TextView textView, final String content) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (textView != null) {
                    textView.setText(content);
                }
            }
        });
    }

    public static   String getVistorIp(Context context) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        int ipAddress = wifiInfo.getIpAddress();

        String ip = intToIp(ipAddress);

        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 24) & 0xFF);
    }

    /**
     * 根据url获取Bitmap
     * @param src
     * @return
     */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    public static int calculateBitmapSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

    /**
     * 检测网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 将Birmap保存为图片
     * @param bitmap
     * @throws IOException
     */
    public  static  String saveBitmap(Bitmap bitmap) throws IOException
    {
        String filepath="/sdcard/terwer/"+new Date().getTime()+".png";
        File file = new File(filepath);
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 70, out))
            {
                out.flush();
                out.close();
            }
            return filepath;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();return "";
        }
        catch (IOException e)
        {
            e.printStackTrace();return "";
        }
    }
}