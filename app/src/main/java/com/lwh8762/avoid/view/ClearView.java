package com.lwh8762.avoid.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwh8762.avoid.R;

/**
 * Created by W on 2016-07-07.
 */
public class ClearView extends LinearLayout {
    private Context context;
    private int level = 0;
    private int width = 0;
    private int height = 0;
    private OnButtonClickListener onButtonClickListener;

    public ClearView(Context context,int level) {
        super(context);
        this.context = context;
        this.level = level;
        init();
    }

    public ClearView(Context context,AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
    }

    public ClearView(Context context,AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    private void init() {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = li.inflate(R.layout.dialog_clear,null);
        Button menuBtn = (Button) layout.findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onBack();
            }
        });
        Button regameBtn = (Button) layout.findViewById(R.id.regameBtn);
        regameBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onRestart();
            }
        });
        Button nextBtn = (Button) layout.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onNext();
            }
        });
        addView(layout);
    }



    public void setOnButtonClickListener(OnButtonClickListener listener) {
        onButtonClickListener = listener;
    }

    public interface OnButtonClickListener {
        public void onNext();
        public void onRestart();
        public void onBack();
    }
}
