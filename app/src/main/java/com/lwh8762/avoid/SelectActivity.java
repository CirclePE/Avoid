package com.lwh8762.avoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lwh8762.avoid.view.SelectView;

/**
 * Created by W on 2016-07-07.
 */
public class SelectActivity extends Activity {
    private SelectView selectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        GameInfo.curLevel = GameInfo.getCurrentLevel();
        selectView  = (SelectView) findViewById(R.id.selectView);
        selectView.setOnSelectedListener(new SelectView.OnSelectedListener() {
            @Override
            public void onSelected(int level) {
                if(level<=GameInfo.curLevel) {
                    Intent i = new Intent(SelectActivity.this, GameActivity.class);
                    i.putExtra("level", level);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
