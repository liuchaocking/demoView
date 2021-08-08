package com.slax.zyw;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class kaiguanView extends View {
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    boolean iskg = false;
    ValueAnimator valueAnimator, valueAnimator1;
    public OnCheckedChangeListener onCheckedChangeListener;
    //IAPP版的自定义开关  改改你也能用  仿VIVO开关
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
    public kaiguanView(Context context, String pan1, double c, int width, int height, double shrinkCoe, int radius, int R, double tranCoe, int duration) {
        super(context);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.FILL);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.FILL);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setStyle(Paint.Style.FILL);
        paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint4.setStyle(Paint.Style.FILL);
        String[] split = pan1.split("\\|");
        if(split.length==5){
            this.pa1Color_1 = Color.parseColor(split[0]);//关闭 小球颜色
            this.pa1Color_2 = Color.parseColor(split[1]);//开启 小球颜色
            paint2.setColor(Color.parseColor(split[2]));//关闭 开关背景颜色
            paint3.setColor(Color.parseColor(split[3]));//开启 小球外边颜色
            paint4.setColor(Color.parseColor(split[4]));//开启  背景颜色
            this.color=this.pa1Color_1;
        }
        paint4.setAlpha(0);
        this.radius = radius;
        C = c;//小球外圈圆的大小系数
        this.width=width;//开关的宽度
        this.height = height;//开关的高度
        this.shrinkCoe = shrinkCoe;//开关的缩放系数
        this.RADIUS = radius;//关闭 小球的大小半径
        this.BigRshrinkCoe = (float) (R - radius) / 80;//开启 小球的大小半径
        this.tranCoe = tranCoe;//小球平移的距离倍数
        this.duration=duration;//动画时长
    }
    public kaiguanView(Context context, String pan1, String a) {
        super(context);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.FILL);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.FILL);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setStyle(Paint.Style.FILL);
        paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint4.setStyle(Paint.Style.FILL);
        String[] split = pan1.split("\\|");
        if(split.length==5){
            this.pa1Color_1 = Color.parseColor(split[0]);//关闭 小球颜色
            this.pa1Color_2 = Color.parseColor(split[1]);//开启 小球颜色
            paint2.setColor(Color.parseColor(split[2]));//关闭 开关背景颜色
            paint3.setColor(Color.parseColor(split[3]));//开启 小球外边颜色
            paint4.setColor(Color.parseColor(split[4]));//开启  背景颜色
            this.color=this.pa1Color_1;
        }
        paint4.setAlpha(0);
        String[] split1 = a.split("");
        if(split1.length==8){
            this.width=Integer.parseInt(split1[0]);//开关的宽度
            this.height = Integer.parseInt(split1[1]);//开关的高度
            this.radius = Integer.parseInt(split1[2]);
            this.RADIUS = (int) radius;//关闭 小球的大小半径
            this.BigRshrinkCoe =  (Integer.parseInt(split1[3]) - radius) / 80;//开启 小球的大小半径
            C = Double.parseDouble(split1[4]);//小球外圈圆的大小系数
            this.shrinkCoe = Double.parseDouble(split1[5]);//开关的缩放系数
            this.tranCoe=Double.parseDouble(split1[6]);//小球平移的距离倍数
            this.duration=Integer.parseInt(split1[7]);//动画时长
        }


    }
    public kaiguanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public kaiguanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    double C = (float) 0.125;
    int pa1Color_1 = Color.RED;//关 小球颜色
    int pa1Color_2 = Color.WHITE;//开 小球颜色
    int RADIUS = 15;
    float radius = 15;
    int off = 0;
    int color;
    int width = 60;
    int height = 30;
    int value;
    double shrinkCoe = 0.1;
    float BigRshrinkCoe = 0.1875f;
    double tranCoe = 0.9;
    int duration=300;
    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
    public void openORshut(boolean a){
             dh(!a);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = getWidth() / 2 - width;
        int top = getHeight() / 2 - height;
        int right = getWidth() / 2 + width;
        int bottom = getHeight() / 2 + height;
        canvas.drawRoundRect(left, (float) (top + value * shrinkCoe), right, (float) (bottom - value * shrinkCoe), 30, 30, paint2);
        canvas.drawRoundRect(left, (float) (top + value * shrinkCoe), right-width/2, (float) (bottom - value * shrinkCoe), 30, 30, paint4);
        int cy = getHeight() / 2;
        paint1.setColor(color);
        canvas.drawCircle(left + RADIUS + 15 + off, cy, (float) (radius + value * C), paint3);
        canvas.drawCircle(left + RADIUS + 15 + off, cy, radius, paint1);

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int left = getWidth() / 2 - width-10;
        int top = getHeight() / 2 - height-10;
        int right = getWidth() / 2 + width+10;
        int bottom = getHeight() / 2 + height+10;
        if(event.getAction() == MotionEvent.ACTION_DOWN&&x>left&&x<right&&y>top&&y<bottom){
            if (valueAnimator != null && valueAnimator.isRunning())  return true;
            if (valueAnimator1 != null && valueAnimator1.isRunning())  return true;
            dh(iskg);
        }


        return true;
    }

    public void dh(boolean a){
        if (a) {
            if(onCheckedChangeListener!=null)   onCheckedChangeListener.onCheckedChangeListener(false);
            ObjectAnimator animator = ObjectAnimator.ofArgb(this, "color", pa1Color_2, pa1Color_1);
            animator.setDuration(300);
            animator.start();
            valueAnimator = ValueAnimator.ofInt(80, 0);
            valueAnimator.setDuration(duration);
            valueAnimator.addUpdateListener(animation -> {
                value = (int) animation.getAnimatedValue();
                off = (int) (value * tranCoe);
                paint4.setAlpha(value*3);
                radius =  value * BigRshrinkCoe + RADIUS;
                if (value == 0) iskg = false;
                invalidate();
            });
            valueAnimator.start();
        } else {
            if(onCheckedChangeListener!=null)   onCheckedChangeListener.onCheckedChangeListener(true);
            ObjectAnimator animator = ObjectAnimator.ofArgb(this, "color", pa1Color_1, pa1Color_2);
            animator.setDuration(duration);
            animator.start();
            valueAnimator1 = ValueAnimator.ofInt(0, 80);
            valueAnimator1.setDuration(300);
            valueAnimator1.addUpdateListener(animation -> {
                value = (int) animation.getAnimatedValue();
                off = (int) (value * tranCoe);
                radius =  (value * BigRshrinkCoe) + RADIUS;
                paint4.setAlpha(value*3);
                if (value == 80) iskg = true;
                invalidate();
            });
            valueAnimator1.start();
        }

    }
}



