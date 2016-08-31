package com.lwh8762.avoid.view;

import android.content.Context;
import android.graphics.Color;
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
public class DeadView extends LinearLayout {
    private Context context;
    private int width = 0;
    private int height = 0;
    private OnButtonClickListener onButtonClickListener;

    public DeadView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DeadView(Context context,AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
    }

    public DeadView(Context context,AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    private void init() {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = li.inflate(R.layout.dialog_dead,null);
        Button regameBtn = (Button) layout.findViewById(R.id.regameBtn);
        regameBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onRestart();
            }
        });
        Button menuBtn = (Button) layout.findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onBack();
            }
        });
        addView(layout);
    }



    public void setOnButtonClickListener(OnButtonClickListener listener) {
        onButtonClickListener = listener;
    }

    public interface OnButtonClickListener {
        public void onRestart();
        public void onBack();
    }
}
