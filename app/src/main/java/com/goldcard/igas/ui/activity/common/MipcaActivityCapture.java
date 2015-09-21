package com.goldcard.igas.ui.activity.common;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;

import com.goldcard.igas.R;
import com.goldcard.igas.ui.activity.base.BaseFragmentActivity;
import com.goldcard.igas.ui.fragment.NavBarOneFragment;
import com.goldcard.igas.ui.model.NavBarOneModel;
import com.goldcard.igas.util.AnimUtils;
import com.goldcard.igas.util.DialogUtils;
import com.goldcard.igas.util.StringUtils;
import com.goldcard.igas.widget.zxing.camera.CameraManager;
import com.goldcard.igas.widget.zxing.decoding.CaptureActivityHandler;
import com.goldcard.igas.widget.zxing.decoding.InactivityTimer;
import com.goldcard.igas.widget.zxing.image.RGBLuminanceSource;
import com.goldcard.igas.widget.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 扫描二维码页面
 * @author liys
 * @since 2015-6-18
 * @version 1.0.0
 */
public class MipcaActivityCapture extends BaseFragmentActivity implements Callback ,NavBarOneFragment.NavBarOneFragmentCallBack {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
    private static final long VIBRATE_DURATION = 200L;
    private static final int PICK_IMG_FROM_ALBUM_REQ_CODE = 300;
    private String photoPath;
    private static final int PARSE_BARCODE_SUC = 100;
    private static final int PARSE_BARCODE_FAIL = 200;
    private Bitmap scanBitmap;

	@Override
	public void onResume() {
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

    /**
     * 处理二维码扫描的结果
     * @param result
     * @param barcode
     */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (StringUtils.isEmpty(resultString)) {
            DialogUtils.showToastOne(this, "", getResources().getString(R.string.scan_error), true);
		}else {
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", resultString);
			bundle.putParcelable("bitmap", barcode);
			resultIntent.putExtras(bundle);
			this.setResult(RESULT_OK, resultIntent);
		}
		MipcaActivityCapture.this.finish();
	}


    /**
     * 初始化摄像头
     * @param surfaceHolder
     */
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,characterSet);
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
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);
			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

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

    @Override
    protected void onUIViewInit() {

        // 1. 布局文件
        setContentView(R.layout.qrcode_capture_layout);

        // 2.设置导航
        NavBarOneModel navBarInfo = new NavBarOneModel();
        navBarInfo.setTitle(getResources().getString(R.string.scan_title));
        navBarInfo.setRightType(NavBarOneModel.type_txt);
        navBarInfo.setRightBtnTxt(getResources().getString(R.string.scan_album));
        NavBarOneFragment navBarOneFragment = NavBarOneFragment.newInstance(navBarInfo);
        navBarOneFragment.setCallBack(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_bar, navBarOneFragment).commit();

        // 3.findView
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

    }

    @Override
    protected void onListenerRegister() {

    }

    @Override
    protected void onDataInit() {
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    public void onLeftBtnClicked(NavBarOneFragment fragment, Button leftBtn) {
        finish();
        AnimUtils.outAnim();
    }

    @Override
    public void onRightBtnClicked(NavBarOneFragment fragment, Button rightBtn) {
        pickQRCodeFromAlbum();
    }

    /**
     * 从相册中选择图片
     */
    private void pickQRCodeFromAlbum(){
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, getResources().getString(R.string.scan_pick_img));
        this.startActivityForResult(wrapperIntent, PICK_IMG_FROM_ALBUM_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode ){
                case PICK_IMG_FROM_ALBUM_REQ_CODE:
                    handlePickImgSucc(data);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 处理从相册中选择图片成功
     * @param data
     */
    private void handlePickImgSucc(Intent data){
        Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
        if (cursor.moveToFirst()) {
            photoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
        DialogUtils.loading(this,getResources().getString(R.string.scan_scaning));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = scanningImage(photoPath);
                if (result != null) {
                    Message m = mHandler.obtainMessage();
                    m.what = PARSE_BARCODE_SUC;
                    m.obj = result.getText();
                    mHandler.sendMessage(m);
                } else {
                    Message m = mHandler.obtainMessage();
                    m.what = PARSE_BARCODE_FAIL;
                    m.obj = getResources().getString(R.string.scan_error);
                    mHandler.sendMessage(m);
                }
            }
        }).start();
    }

    /**
     * 扫描二维码图片的方法
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if(StringUtils.isEmpty(path)){
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0){
            sampleSize = 1;
        }
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *mHandler
     */
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogUtils.closeLoading();
            switch (msg.what) {
                case PARSE_BARCODE_SUC:
                    onResultHandler((String)msg.obj, scanBitmap);
                    break;
                case PARSE_BARCODE_FAIL:
                    DialogUtils.showToastOne(MipcaActivityCapture.this,"",(String)msg.obj,false);
                    break;

            }
        }
    };

    /**
     * 处理结果
     * @param resultString
     * @param bitmap
     */
    private void onResultHandler(String resultString, Bitmap bitmap){
        if(StringUtils.isEmpty(resultString)){
            DialogUtils.showToastOne(MipcaActivityCapture.this,"",getResources().getString(R.string.scan_error),false);
            return;
        }
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("result", resultString);
        bundle.putParcelable("bitmap", bitmap);
        resultIntent.putExtras(bundle);
        this.setResult(RESULT_OK, resultIntent);
        MipcaActivityCapture.this.finish();
    }


    @Override
    public void onTitleClicked(NavBarOneFragment fragment, TextView title) {

    }
}