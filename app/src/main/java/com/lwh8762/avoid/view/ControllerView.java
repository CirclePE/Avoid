package com.lwh8762.avoid.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwh8762.avoid.R;

/**
 * Created by W on 2016-07-02.
 */
public class ControllerView extends LinearLayout {
    private Context context;
    private OnControlListener onControlListener;
    private float lrPrimeter = 0;

    public ControllerView(Context context,float lrPerimeter) {
        super(context);
        this.context = context;
        this.lrPrimeter = lrPerimeter;
        init();
    }

    public ControllerView(Context context,AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        init();
    }

    public ControllerView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        this.context = context;
        init();
    }

    private void init() {
        Button move = new Button(context);
        move.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), R.mipmap.move)));
        LayoutParams mparams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3.4f);
        mparams.leftMargin = 10;
        mparams.rightMargin = 10;
        move.setLayoutParams(mparams);
        move.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    if(event.getX()<lrPrimeter) {
                        onControlListener.onLeft(true);
                    }else if (event.getX()>lrPrimeter) {
                        onControlListener.onRight(true);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX()<lrPrimeter) {
                        onControlListener.onLeft(false);
                    }else if (event.getX()>lrPrimeter) {
                        onControlListener.onRight(false);
                    }
                }
                return false;
            }
        });

        addView(move);

        Button jump = new Button(context);
        jump.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), R.mipmap.jump)));
        LayoutParams jparams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 6.5f);
        jparams.leftMargin = 10;
        jparams.rightMargin = 10;
        jump.setLayoutParams(jparams);
        jump.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onControlListener.onJump();
                }
                return false;
            }
        });
        addView(jump);
    }

    public void setOnControlListener(OnControlListener listener) {
        onControlListener = listener;
    }

    public interface OnControlListener {
        public void onLeft(boolean press);
        public void onRight(boolean press);
        public void onJump();
    }
}
