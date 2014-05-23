package com.terwer.qrcode;

import java.io.IOException;
import java.util.Vector;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Window;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import com.terwer.qrcode.camera.CameraManager;
import com.terwer.qrcode.decoding.CaptureActivityHandler;
import com.terwer.qrcode.decoding.InactivityTimer;
import com.terwer.qrcode.util.Util;
import com.terwer.qrcode.view.ViewfinderView;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;
import org.json.JSONObject;

public class CaptureActivity extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private TextView txtResult;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;


    //用于判断来源的变量
    private static String from = "left1";
    //条码类型
    private static CodeType codeType = CodeType.QR_CODE;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.capture);
        //初始化 CameraManager
        CameraManager.init(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        txtResult = (TextView) findViewById(R.id.txt_decode_result);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);


        //初始化扫描
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            codeType = (CodeType) bundle.get("CodeType");
            from = bundle.getString("From");
            Log.i("Msg", codeType.toString());
            Log.i("Msg1", from);
            //Util.showAlert(DecodeAcyivity.this, codeType.toString());
            //Util.showAlert(DecodeAcyivity.this, from);
        } else {
            Util.showToast(CaptureActivity.this, "来路非法！");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 处理解码事件
     *
     * @param obj
     * @param barcode
     */
    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();


        barcode = Bitmap.createScaledBitmap(barcode, 100, 116, true);

        viewfinderView.drawResultBitmap(barcode);
        playBeepSoundAndVibrate();

        /////////////////////////////////////////////////////////////////
        //处理解码逻辑
        Intent intent = new Intent();
        intent.setClass(CaptureActivity.this, DecodeActivity.class);
        intent.putExtra("CodeType", codeType);
        intent.putExtra("From", from);
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        intent.putExtra("Contents", json);
        startActivity(intent);
        /////////////////////////////////////////////////////////////////


        txtResult.setText("扫描结果如下：\r\n" +
                "条码类型：" + obj.getBarcodeFormat().toString() + "\r\n" +
                "内容：" + obj.getText());
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}