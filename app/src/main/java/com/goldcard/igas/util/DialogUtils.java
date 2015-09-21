package com.goldcard.igas.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.widget.Toast;

import com.goldcard.igas.R;
import com.goldcard.igas.interfaces.AlertDialogueCallBack;
import com.goldcard.igas.interfaces.TwoBtnAlertDialogCallbackAdapter;
import com.goldcard.igas.widget.LoadingOneDialog;
import com.goldcard.igas.widget.LoadingProgressDialog;
import com.goldcard.igas.widget.ToastOne;
import com.goldcard.igas.widget.ToastTwo;
import com.goldcard.igas.widget.TwoBtnAlertDialog;

/**
 *
 * @Description:窗口对应的工具类
 * @author liys
 * @since  2015-5-20
 * @version 1.0.0
 */
public class DialogUtils {

    /** 进度对话框 **/
    private static LoadingProgressDialog progressDialog = null;

    private static LoadingOneDialog oneDialog = null;

    private static TwoBtnAlertDialog alertDialogue = null;


    /**
     * 加载对话框
     * @param message
     */
    public final static void loading(Context context,String message){
        closeLoading();
        oneDialog = LoadingOneDialog.makeLoading(context,message,false,new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        oneDialog.show();
    }

    /**
     * 关闭对话框
     */
    public final static void closeLoading(){
        if (oneDialog != null) {
            oneDialog.dismiss ();
            oneDialog = null;
        }
    }

    /**
     * 显示进度对话框
     */
    public static void showProgressDialog(){
        showProgressDialog (false, null);
    }

    /**
     *
     * @Description:显示进度对话框
     * @Author: 李焱生
     * @Since: 2015-5-20
     * @param tipInfo
     */
    public static void showProgressDialog(String tipInfo){
        showProgressDialog (false, StringUtils.trimNull (tipInfo, "正在加载，请稍后..."));
    }

    /**
     * @Description:显示进度对话框
     * @Author: 李焱生
     * @Since: 2015-5-20
     * @param cancelable
     * @param msg
     */
    public static void showProgressDialog(boolean cancelable,String msg)
    {
        closeProgressDialogue();
        progressDialog = new LoadingProgressDialog (AppUtils.getActivity (),msg,R.anim.loading_progress_anim);
        progressDialog.setCancelable (cancelable);
        progressDialog.setOnCancelListener (new OnCancelListener () {
            @Override
            public void onCancel(DialogInterface dialog){}
        });
        try {
            progressDialog.show ();
        } catch (Exception e) {}
    }


    /**
     * @Description:关闭进度对话框
     * @Author: 李焱生
     * @Since: 2015-5-20
     */
    public static void closeProgressDialogue()
    {
        if (progressDialog != null && progressDialog.isShowing ()) {
            try {
                progressDialog.dismiss ();
            } catch (Exception e) {}
            progressDialog = null;
        }
    }


    /**
     *@Description: 显示toast对话框
     *@Author: 李焱生
     *@Since: 2015-5-20
     *@param context
     *@param msg
     */
    public static final void showToast(Context context,String msg){
        Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();
    }

    /**
     * 显示ToastOne
     * @param context
     * @param title
     * @param msg
     * @param playSound
     */
    public static final void showToastOne(Context context,String title,String msg,boolean playSound){
        ToastOne.makeText(context,title,msg,Toast.LENGTH_SHORT,playSound).show();
    }

    /**
     * 显示ToastTwo
     * @param context
     * @param msg
     * @param playSound
     */
    public static final void showToastTwo(Context context,String msg,boolean playSound){
        ToastTwo.makeText(context, msg, Toast.LENGTH_SHORT,playSound).show();
    }


    /**
     * @Description 显示弹出的alert框
     * @param msg
     * @param context
     * @param leftBtnMsg
     * @param rightBtnMsg
     * @param windowType
     * @param callback
     */
    public static void showAlertDialog(String msg,Context context,String leftBtnMsg,String rightBtnMsg,int windowType,final AlertDialogueCallBack callback){

        closeAlertDialog ();

        alertDialogue = new TwoBtnAlertDialog(context,new TwoBtnAlertDialogCallbackAdapter() {

            @Override
            public void onSureBtnClicked(TwoBtnAlertDialog dialog){
                if (callback != null) {
                    callback.doCallBack ();
                }
            }

            @Override
            public void onCancelBtnClicked(TwoBtnAlertDialog dialog){

                if(callback!=null)
                {
                    callback.onCancalBtnClicked ();
                }
            }
        },windowType);
        alertDialogue.setMessage (msg);
        alertDialogue.getDialog ().setCanceledOnTouchOutside (false);
        if(!StringUtils.isEmpty (rightBtnMsg)){
            alertDialogue.setPositiveButton (StringUtils.trimNull (rightBtnMsg));
        }
        if(!StringUtils.isEmpty (leftBtnMsg)){
            alertDialogue.setNegativeButton (StringUtils.trimNull (leftBtnMsg));
        }
    }


    /**
     * 提示对话框
     *
     * @param @param msg
     * @author liys
     * @date 2015-1-29
     */
    public static void showAlertDialog(String msg,Context context,String leftBtnMsg,String rightBtnMsg,final AlertDialogueCallBack callback){
        closeAlertDialog ();
        alertDialogue = new TwoBtnAlertDialog (context,new TwoBtnAlertDialogCallbackAdapter () {
            @Override
            public void onSureBtnClicked(TwoBtnAlertDialog dialog){
                if (callback != null) {
                    callback.doCallBack ();
                }
            }

            @Override
            public void onCancelBtnClicked(TwoBtnAlertDialog dialog){
                if(callback!=null)
                {
                    callback.onCancalBtnClicked ();
                }
            }
        });
        alertDialogue.setMessage (msg);
        alertDialogue.getDialog ().setCanceledOnTouchOutside (false);
        if(!StringUtils.isEmpty (rightBtnMsg))
        {
            alertDialogue.setPositiveButton (StringUtils.trimNull (rightBtnMsg));
        }
        if(!StringUtils.isEmpty (leftBtnMsg))
        {
            alertDialogue.setNegativeButton (StringUtils.trimNull (leftBtnMsg));
        }
    }


    /**
     *
     *@Description: 关闭弹出框
     *@Author: 李焱生
     *@Since: 2015年4月14日上午11:22:31
     */
    public static void closeAlertDialog()
    {
        if (alertDialogue != null) {
            try {
                alertDialogue.dismiss ();
            } catch (Exception e) {}
            alertDialogue = null;
        }
    }


}
