package com.lwh8762.avoid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lwh8762.avoid.view.ClearView;
import com.lwh8762.avoid.view.ControllerView;
import com.lwh8762.avoid.view.DeadView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrappedException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by W on 2016-07-01.
 */
public class GameActivity extends Activity {
    private int textId = 0;
    private HashMap<Integer,TextView> texts;
    private TextView fpsView;
    private TextView logView;
    private TextView exceptionView;

    private boolean debug = false;
    private int level = 0;
    private Display display;
    private float lrPerimeter = 0;
    private float rjPerimeter = 0;
    private GLSurfaceView glSurfaceView;
    private MyRenderer myRenderer;
    private RelativeLayout gameLayout;
    private ControllerView controllerView;
    public Context ctx;
    public Scriptable scope;
    public Function runFunc;

    private Timer timer;
    private MyThread myThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        texts = new HashMap<Integer, TextView>();

        debug = getIntent().getBooleanExtra("debug", false);
        level = getIntent().getIntExtra("level",1);

        mHandler = new Handler();
        myThread = new MyThread();

        Display display = getWindowManager().getDefaultDisplay();
        float width = display.getWidth();
        float height = display.getHeight();

        lrPerimeter = (int) (1.7f/10f*width);
        rjPerimeter = (int) (1.7f/10f*width)*2;

        glSurfaceView = new GLSurfaceView(this);
        myRenderer = new MyRenderer(this,glSurfaceView);
        glSurfaceView.setRenderer(myRenderer);
        ScriptManager.setGame(myRenderer);

        gameLayout = ((RelativeLayout) findViewById(R.id.gameLayout));

        fpsView = new TextView(this);
        RelativeLayout.LayoutParams fparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        fparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        fpsView.setTextColor(Color.WHITE);
        fpsView.setLayoutParams(fparams);

        logView = new TextView(this);
        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lparams.setMargins(0,50,0,0);
        logView.setTextColor(Color.WHITE);
        logView.setLayoutParams(lparams);

        exceptionView = new TextView(this);
        RelativeLayout.LayoutParams eparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        eparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        eparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        exceptionView.setText("");
        exceptionView.setTextSize(30);
        exceptionView.setGravity(Gravity.CENTER);
        exceptionView.setTextColor(Color.argb(120, 255, 0, 0));
        exceptionView.setLayoutParams(eparams);

        controllerView = new ControllerView(this,lrPerimeter);
        RelativeLayout.LayoutParams cparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        cparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        controllerView.setLayoutParams(cparams);
        controllerView.setOnControlListener(new ControllerView.OnControlListener() {
            @Override
            public void onLeft(boolean press) {
                myRenderer.goLeft(press);
            }

            @Override
            public void onRight(boolean press) {
                myRenderer.goRight(press);
            }

            @Override
            public void onJump() {
                myRenderer.jump();
            }
        });
        gameLayout.addView(glSurfaceView);
        gameLayout.addView(fpsView);
        gameLayout.addView(logView);
        gameLayout.addView(exceptionView);
        gameLayout.addView(controllerView);

        myThread.start();


        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fpsView.setText("FPS : " + myRenderer.frame);
                        myRenderer.frame = 0;
                    }
                });
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    private void onMapScript(int level) {
        try {
            String res = "";
            Method[] methods = Class.forName("com.lwh8762.avoid.ScriptManager").getMethods();
            for(Method m : methods) {
                if(m.getName().equals("equals")||m.getName().equals("getClass")||m.getName().equals("hashCode")||m.getName().equals("notify")||m.getName().equals("notifyAll")||m.getName().equals("wait")||m.getName().equals("toString")) continue;
                res += "var " + m.getName() + " = com.lwh8762.avoid.ScriptManager." + m.getName() + "; ";
            }
            res += "var sleep = java.lang.Thread.sleep; ";

            BufferedInputStream bis;
            if(debug) {
                bis = new BufferedInputStream(new FileInputStream("/sdcard/avoidmap.js"));
            }else {
                bis = new BufferedInputStream(getAssets().open("mapscript/avoidmap" + level + ".js"));
            }

            while(true) {
                byte[] buf = new byte[1024];
                int len = bis.read(buf);
                if(len==-1) {
                    bis.close();
                    break;
                }

                res += new String(buf,0,len);
            }
            //InputStreamReader isr = new InputStreamReader(getAssets().open("mapscript/avoidmap" + level + ".js"));

            try {
                ctx = Context.enter();
                ctx.setOptimizationLevel(-1);
                scope = ctx.initStandardObjects();
                ctx.evaluateString(scope, res, "map", 1, null);
                runFunc = (Function) scope.get("run",scope);
            }catch (EcmaError e) {
                sendException("#1\n" + e.toString());
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {

        }
    }
    public void sendException(final String s) {
        myThread.interrupt();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                exceptionView.setText(s);
                glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            }
        });
    }

    public void appendLog(final String s) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                logView.append(s);
            }
        });
    }

    public void setLog(final String s) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                logView.setText(s);
            }
        });
    }

    public void gameover() {
        myThread.interrupt();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DeadView deadView = new DeadView(GameActivity.this);
                deadView.setOnButtonClickListener(new DeadView.OnButtonClickListener() {

                    @Override
                    public void onRestart() {
                        if (debug) {
                            Intent intent = new Intent(GameActivity.this,GameActivity.class);
                            intent.putExtra("debug",true);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(GameActivity.this, GameActivity.class);
                            intent.putExtra("level", level);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onBack() {
                        Intent intent = new Intent(GameActivity.this, SelectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) ((float) (getWindowManager().getDefaultDisplay().getWidth()) * 0.75f), (int) ((float) (getWindowManager().getDefaultDisplay().getHeight()) * 0.75f));
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                gameLayout.addView(deadView, params);
                textClean();
            }
        });
    }

    public void clear() {
        if(level==GameInfo.curLevel) {
            GameInfo.setCurrentLevel(level+1);
            GameInfo.curLevel = level+1;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ClearView clearView = new ClearView(GameActivity.this, level);
                clearView.setOnButtonClickListener(new ClearView.OnButtonClickListener() {
                    @Override
                    public void onNext() {
                        Intent intent = new Intent(GameActivity.this,GameActivity.class);
                        intent.putExtra("level",level+1);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onRestart() {
                        Intent intent = new Intent(GameActivity.this,GameActivity.class);
                        intent.putExtra("level",level);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onBack() {
                        Intent intent = new Intent(GameActivity.this,SelectActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) ((float) (getWindowManager().getDefaultDisplay().getWidth()) * 0.75f), (int) ((float) (getWindowManager().getDefaultDisplay().getHeight()) * 0.75f));
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                gameLayout.addView(clearView, params);
            }
        });
    }

    public int addText(final String s, final int x, final int y, final int size,final int color) {
        textId ++;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //Log.e("x","c " + x);
                int id = textId;
                TextView t = new TextView(GameActivity.this);
                t.setGravity(Gravity.CENTER);
                t.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
                t.setTextColor(color);
                t.setTypeface(Typeface.MONOSPACE);
                //t.setBackgroundColor(Color.argb(100,255,255,255));
                t.setText(s);
                t.setX(x);
                t.setY(y);
                addContentView(t,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                texts.put(id,t);
            }
        });

        return textId;
    }

    public void setTextFormWidth(final int textId,final int width) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TextView t = texts.get(textId);
                t.setWidth(width);
            }
        });
    }

    public void setTextFormHeight(final int textId,final int height) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TextView t = texts.get(textId);
                t.setWidth(height);
            }
        });
    }

    public void setText(final int textId,final String s) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                texts.get(textId).setText(s);
            }
        });
    }

    public void setTextX(final int textId,final int x) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                texts.get(textId).setX(x);
            }
        });
    }

    public void setTextY(final int textId,final int y) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                texts.get(textId).setY(y);
            }
        });
    }


    public void setTextColor(int textId,int color) {
        texts.get(textId).setTextColor(color);
    }

    public void removeText(final int textId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    TextView t = texts.get(textId);
                    ((ViewManager) t.getParent()).removeView(t);
                    texts.remove(textId);
                }catch (NullPointerException e) {
                    sendException("#4\n" + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    public void textClean() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Iterator i = texts.keySet().iterator();
                    while (i.hasNext()) {
                        Integer k = (Integer) i.next();
                        TextView t = texts.get(k);
                        ((ViewManager) t.getParent()).removeView(t);
                    }
                    texts.clear();
                }catch (NullPointerException e) {
                    sendException("#5\n" + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        myThread.interrupt();
        timer.cancel();
        timer.purge();
        //startActivity(new Intent(this,SelectActivity.class));
        super.onDestroy();
    }

    class MyThread extends Thread {
        private int time = 0;
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(300);
                onMapScript(level);
                while (true) {
                    Thread.sleep(50);
                    try {
                        runFunc.call(ctx, scope, scope, new Object[]{});
                    }catch (WrappedException e) {
                        break;
                    }catch (EcmaError e) {
                        sendException("#2\n" + e.toString());
                        break;
                    }
                }
            }catch (InterruptedException e) {

            }catch (Exception e) {
                sendException("#3\n" + e.toString());
                e.printStackTrace();
            }
        }
    }
}
