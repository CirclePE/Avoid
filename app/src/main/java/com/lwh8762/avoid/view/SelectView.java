package com.lwh8762.avoid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwh8762.avoid.GameInfo;
import com.lwh8762.avoid.R;

/**
 * Created by W on 2016-07-07.
 */
public class SelectView extends LinearLayout {

    private int btnSize = 0;
    private int btnMargin = 0;
    private Context context;
    private OnSelectedListener onSelectedListener;

    public SelectView(Context context,float lrPerimeter) {
        super(context);
        this.context = context;
    }

    public SelectView(Context context,AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
    }

    public SelectView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        this.context = context;
    }

    private void init() {
        setOrientation(VERTICAL);
        for (int line = 0;line < (GameInfo.maxLevel+9)/10;line ++) {
            LinearLayout l = new LinearLayout(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnSize);
            params.setMargins(0,10,0,10);
            l.setLayoutParams(params);
            l.setGravity(Gravity.LEFT);

            for (int level = line*10+1,c = 0; level <= GameInfo.maxLevel; level++,c ++) {
                TextView btn = getButton(level);
                if (level <= GameInfo.curLevel) {
                    btn.setTextColor(Color.argb(255, 255, 255, 255));
                } else {
                    btn.setTextColor(Color.argb(255, 120, 120, 120));
                }
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSelectedListener.onSelected((int) v.getTag());
                    }
                });
                l.addView(btn);
                if (c==9) {
                    break;
                }
            }
            addView(l);
        }
    }

    private TextView getButton(int level) {
        TextView btn = new TextView(context);
        LayoutParams params = new LayoutParams(btnSize,btnSize);
        params.setMargins(btnMargin, btnMargin,btnMargin,btnMargin);
        btn.setLayoutParams(params);
        btn.setGravity(Gravity.CENTER);
       /* try {
            btn.setTextColor(getResources().getColorStateList(R.color.selector_mainbtn, context.getTheme()));
        }catch (Error e) {
            btn.setTextColor(getResources().getColorStateList(R.color.selector_mainbtn));
        }*/
        btn.setTextColor(Color.WHITE);

        btn.setTextSize(25);
        btn.setTag(level);
        btn.setText(String.valueOf(level));
        return btn;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        btnSize = w/12;
        btnMargin = (w-btnSize*10)/20;
        init();
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        onSelectedListener = listener;
    }

    public interface OnSelectedListener {
        public void onSelected(int level);
    }
}
