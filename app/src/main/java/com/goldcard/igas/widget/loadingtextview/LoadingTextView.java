package com.goldcard.igas.widget.loadingtextview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.goldcard.igas.R;


/**
 * 
* @ClassName: LoadingTextView 
* @Description: TODO(加载中动画Textview) 
* @author Administrator 
* @date 2015-4-27 下午5:37:44 
*
 */
public class LoadingTextView extends TextView {

    /**构建3个圆点**/
    private JumpingSpan mDotOne;
    private JumpingSpan mDotTwo;
    private JumpingSpan mDotThree;

    private AnimatorSet mAnimatorSet = new AnimatorSet ();

    /**是否正在播放**/
    private boolean     isPlaying;
    /**是否自动播放**/
    private boolean     autoPlay;

    /**跳跃高度**/
    private int         jumpHeight;
    private int         period;

    public LoadingTextView(Context context) {
        this (context, null);
    }

    public LoadingTextView(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public LoadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        mDotOne = new JumpingSpan ();
        mDotTwo = new JumpingSpan ();
        mDotThree = new JumpingSpan ();

        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.LoadingTextView);
            period = typedArray.getInt (R.styleable.LoadingTextView_period, 1000);
            jumpHeight = typedArray.getInt (R.styleable.LoadingTextView_jumpHeight, (int) (getTextSize () / 4));
            autoPlay = typedArray.getBoolean (R.styleable.LoadingTextView_autoplay, false);
            typedArray.recycle ();
        } else {
            period = 1000;
            jumpHeight = (int) getTextSize () / 4;
            autoPlay = true;
        }

        SpannableString spannable = new SpannableString ("...");
        spannable.setSpan (mDotOne, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan (mDotTwo, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan (mDotThree, 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText (spannable, BufferType.SPANNABLE);

        // 构建属性动画
        ObjectAnimator dotOneJumpAnimator = createDotJumpAnimator (mDotOne, 0);
        dotOneJumpAnimator.addUpdateListener (new AnimatorUpdateListener () {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                invalidate ();
            }
        });
        mAnimatorSet.playTogether (dotOneJumpAnimator, createDotJumpAnimator (mDotTwo, period / 6), createDotJumpAnimator (mDotThree, period * 2 / 6));
        if (autoPlay) {
            showAndPlay ();
        } else {
            hideAndStop ();
        }
    }

    /**
     * 
    * @Title: isPlaying 
    * @Description: TODO(返回当前正在播放状态) 
    * @param @return    
    * @return boolean    
    * @throws
     */
    public boolean isPlaying(){
        return isPlaying;
    }

    /**
     * 
    * @Title: showAndPlay 
    * @Description: TODO(显示并播放动画) 
    * @param     
    * @return void    
    * @throws
     */
    public synchronized void showAndPlay(){
        if (getVisibility () != View.VISIBLE) {
            setVisibility (View.VISIBLE);
        }
        if (!isPlaying) {
            isPlaying = true;
            setAllAnimationsRepeatCount (ValueAnimator.INFINITE);
            mAnimatorSet.start ();
        }
    }

    /**
     * 
    * @Title: hideAndStop 
    * @Description: TODO(隐藏并取消动画播放) 
    * @param     
    * @return void    
    * @throws
     */
    public synchronized void hideAndStop(){
        if (getVisibility () != View.GONE) {
            setVisibility (View.GONE);
        }
        isPlaying = false;
        setAllAnimationsRepeatCount (0);
        mAnimatorSet.cancel ();
    }

    /**
     * 
    * @Title: setAllAnimationsRepeatCount 
    * @Description: TODO(设置动画重复次数) 
    * @param @param repeatCount    
    * @return void    
    * @throws
     */
    private void setAllAnimationsRepeatCount(int repeatCount){
        for ( Animator animator : mAnimatorSet.getChildAnimations () ) {
            if (animator instanceof ObjectAnimator) {
                ((ObjectAnimator) animator).setRepeatCount (repeatCount);
            }
        }
    }

    /**
     * 
    * @Title: createDotJumpAnimator 
    * @Description: TODO(创建圆点跳跃动画) 
    * @param @param jumpingSpan
    * @param @param delay
    * @param @return    
    * @return ObjectAnimator    
    * @throws
     */
    private ObjectAnimator createDotJumpAnimator(JumpingSpan jumpingSpan,long delay){
        ObjectAnimator jumpAnimator = ObjectAnimator.ofFloat (jumpingSpan, "translationY", 0, -jumpHeight);
        jumpAnimator.setEvaluator (new TypeEvaluator<Number> () {

            @Override
            public Number evaluate(float fraction,Number from,Number to){
                return Math.max (0, Math.sin (fraction * Math.PI * 2)) * (to.floatValue () - from.floatValue ());
            }
        });
        jumpAnimator.setDuration (period);
        jumpAnimator.setStartDelay (delay);
        jumpAnimator.setRepeatCount (ValueAnimator.INFINITE);
        jumpAnimator.setRepeatMode (ValueAnimator.RESTART);
        return jumpAnimator;
    }

}
