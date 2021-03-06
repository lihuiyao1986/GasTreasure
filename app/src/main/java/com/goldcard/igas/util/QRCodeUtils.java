package com.goldcard.igas.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;

import com.alibaba.fastjson.JSONObject;
import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.http.NameValueBean;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理二维码对应的工具类
 * @author liys
 * @since 2015-6-18
 * @version 1.0.0
 */
public class QRCodeUtils {


    /**
     * 生成二维码对应的Bitmap
     * @param params
     * @param widthPix
     * @param heightPix
     * @param logoBm
     * @param filePath
     * @param fileName
     * @return
     */
    public static final boolean createQRImage(List<NameValueBean>params, int widthPix, int heightPix, Bitmap logoBm, String filePath,String fileName){
        JSONObject jsonObject = new JSONObject();
        for(NameValueBean item :params){
            jsonObject.put(StringUtils.trimNull(item.getName()),StringUtils.trimNull(item.getValue()));
        }
        String content =  jsonObject.toJSONString();
        return createQRImage(content,widthPix,heightPix,logoBm,filePath,fileName);
    }


    /**
     * 生成二维码Bitmap
     * @param content   内容
     * @param widthPix  图片宽度
     * @param heightPix 图片高度
     * @param logoBm    二维码中心的Logo图标（可以为null）
     * @param filePath  用于存储二维码图片的文件路径
     * @return 生成二维码及保存文件是否成功
     */
    public static final boolean createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm, String filePath,String fileName) {
        boolean result = false;
        if (StringUtils.isEmpty(content)) {
            return result;
        }
        //配置参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //容错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置空白边距的宽度
        //hints.put(EncodeHintType.MARGIN, 2); //default is 4
        BufferedOutputStream resultImage = null;
        FileOutputStream fos = null;
        try {
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            File fileDir = new File(filePath);
            if(!fileDir.exists()){ fileDir.mkdirs();}

            File file = new File(fileDir,fileName);

            fos = new FileOutputStream(file);
            resultImage = new BufferedOutputStream(fos);

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            result = bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100,resultImage);
            if(result){
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                AppContext.getInstance().sendBroadcast(intent);
            }
            return result;
        } catch (WriterException  e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(fos !=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(resultImage!=null){
                try {
                    resultImage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }
}
