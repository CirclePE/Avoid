package com.lwh8762.avoid;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.widget.TextView;

import com.lwh8762.avoid.unit.Attacker;
import com.lwh8762.avoid.unit.Block;
import com.lwh8762.avoid.unit.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by W on 2016-07-01.
 */
public class MyRenderer implements GLSurfaceView.Renderer {
    //private long lastTime = 0;
    //public int fps = 0;
    public int frame = 0;

    private GLSurfaceView glSurfaceView;
    private GameActivity gameActivity;

    private float blockSize = 0;
    private float widthLengh = 25;
    private float heightLength = 0;
    private float width = 0;
    private float height = 0;

    private Player player;
    private boolean dead = false;
    private boolean doubleJump = true;
    private boolean jumpable = true;
    private float playerSize = 1f;
    private float playerX = widthLengh/2-playerSize/2;
    private float playerY = 20f;
    private float playerXForce = 0;
    private float playerYForce = 0;
    private float playerXVel = 0;
    private float playerYVel = 0;

    private boolean enableGround = true;
    private int unitCount = 0;
    private HashMap<Integer,BlockData> block;
    private int attackerCount = 0;
    private HashMap<Integer,AttackerData> attacker;

    public MyRenderer(GameActivity ga,GLSurfaceView view) {
        gameActivity = ga;
        glSurfaceView = view;
        block = new HashMap<Integer,BlockData>();
        attacker = new HashMap<Integer,AttackerData>();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glOrthof(0, widthLengh, 0, heightLength, -1, 1);
        frame ++;
        if (dead) {
            gameover();
            return;
        }
        player = new Player(playerX,playerY,playerSize,playerSize);
        player.draw(gl);

        try {
            refreshPlayer(gl);
            setGround(gl);
        }catch (Exception e) {
            e.printStackTrace();
        }

        //long curTime = System.currentTimeMillis();
        //fps = (int) (1000/(curTime-lastTime));
        //lastTime = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        this.heightLength = this.height / this.width * widthLengh;
        this.blockSize = width/widthLengh;
        gl.glViewport(0, 0, width, height);
        //gl.glMatrixMode(GL10.GL_PROJECTION);
        //gl.glLoadIdentity();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }



    private void setGround(GL10 gl) {
        if(enableGround) {
            new Block(0, 1.95f, widthLengh, 0.05f).draw(gl);
        }
    }

    private void refreshPlayer(GL10 gl) throws Exception {
        if(playerY>(enableGround == true ? 2.0f : -10f)) {
            playerYForce -= 0.024f;
        }

        playerX += playerXForce + playerXVel;
        playerY += playerYForce + playerYVel;

        if(playerX<0) {
            playerX = 0;

        }else if(playerX>widthLengh-playerSize) {
            playerX = widthLengh-playerSize;
        }

        if(enableGround&&playerY<2.0f) {
            doubleJump = true;
            playerY = 2.0f;
        }

        Iterator biterator = new HashMap<>(block).keySet().iterator();

        while(biterator.hasNext()) {
            BlockData data = block.get(biterator.next());
            if(data.color==null)
                new Block(data.x,data.y,data.width,data.height).draw(gl);
            else
                new Block(data.x,data.y,data.width,data.height,data.color).draw(gl);
            if(data.canContact&&playerY>data.y&&playerX+playerSize>data.x&&playerX<data.x+data.width) {
                if(playerY<data.y+data.height&&playerYForce<=0) {
                    doubleJump = true;
                    playerY = data.y+data.height;
                    playerYForce = 0;
                    jumpable = true;
                }
            }
            data.x += data.xVel;
            data.y += data.yVel;
            data.run();
        }

        Iterator aiterator = new HashMap<>(attacker).keySet().iterator();

        while(aiterator.hasNext()) {
            try {
                Integer key = (Integer) aiterator.next();
                AttackerData data = attacker.get(key);

                if (data.color == null)
                    new Attacker(data.x, data.y, data.width, data.height).draw(gl);
                else
                    new Attacker(data.x, data.y, data.width, data.height, data.color).draw(gl);
                if (playerX + playerSize > data.x && playerX < data.x + data.width && playerY < data.y + data.height && playerY + playerSize > data.y) {
                    dead = true;
                }
                data.x += data.xVel;
                data.y += data.yVel;
                data.run();

                if (data.resistance) {

                    if (data.xVel > 0) {
                        data.xVel -= 0.0025f;
                        if (data.xVel < 0) {
                            data.xVel = 0;
                        }
                    } else if (data.xVel < 0) {
                        data.xVel += 0.0025f;
                        if (data.xVel > 0) {
                            data.xVel = 0;
                        }
                    }

                    if (data.yVel > 0) {
                        data.yVel -= 0.0025f;
                        if (data.yVel < 0) {
                            data.xVel = 0;
                        }
                    } else if (data.yVel < 0) {
                        data.yVel += 0.0025f;
                        if (data.yVel > 0) {
                            data.yVel = 0;
                        }
                    }
                }
                //attacker.remove(key);
                //attacker.put(key,data);
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        if(playerY<-playerSize-1) {
            dead = true;
        }
    }

    public void goLeft(boolean press) {
        if (press) {
            playerXForce = -0.22f;
        }else {
            playerXForce = 0;
        }
    }

    public void goRight(boolean press) {
        if (press) {
            playerXForce = 0.22f;
        }else {
            playerXForce = 0;
        }
    }

    public void jump() {
        if((enableGround&&playerY==2.0f)||jumpable) {
            playerYForce = 0.5f;
            jumpable = false;
        }else if(doubleJump) {
            doubleJump = false;
            playerYForce = 0.5f;
        }
    }

    public void gameover() {
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        gameActivity.gameover();
    }



    public void clean() {
        attacker.clear();
        block.clear();
        textClean();
    }

    public void attackerClean() {
        attacker.clear();
    }

    public void blockClean() {
        block.clear();
    }

    public void textClean() {
        gameActivity.textClean();
    }

    public float getMapHeight() {
        return heightLength;
    }

    public void sendException(String s) {
        gameActivity.sendException(s);
    }

    public void appendLog(String s) {
        gameActivity.appendLog(s);
    }

    public void setLog(String s) {
        gameActivity.setLog(s);
    }

    public int addText(String s,float x,float y,float size,int color) {
        return gameActivity.addText(s,(int) (x/widthLengh*width),(int) ((heightLength-y)/heightLength*height),(int) (blockSize*size),color);
    }

    public void setTextFormWidth(int textId,float width) {
        gameActivity.setTextFormWidth(textId,(int) (blockSize*width));
    }

    public void setTextFormHeight(int textId,float height) {
        gameActivity.setTextFormHeight(textId,(int) (blockSize*height));
    }

    public void setText(int textId,String s) {
        gameActivity.setText(textId,s);
    }

    public void setTextX(int textId,float x) {
        gameActivity.setTextX(textId,(int) (blockSize*x));
    }

    public void setTextY(int textId,float y) {
        gameActivity.setTextY(textId,(int) (height-blockSize*y));
    }

    public void setTextColor(int textId,int color) {
        gameActivity.setTextColor(textId,color);
    }

    public void removeText(int textId) {
        gameActivity.removeText(textId);
    }


    public float getPlayerX() {
        return playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public void setPlayerVelX(float value) {
        playerXVel = value;
    }

    public void setPlayerVelY(float value) {
        playerYVel = value;
    }

    public void setPlayerX(float x) {
        playerX = x;
    }

    public void setPlayerY(float y) {
        playerY = y;
    }

    public void clear() {
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        gameActivity.clear();
    }

    public void setEnableGround(boolean value) {
        enableGround = value;
    }

    public int setBlock(float x,float y,float width,float height,boolean contact) {
        block.put(unitCount, new BlockData(x, y, width, height, contact));
        unitCount ++;
        return (unitCount-1);
    }

    public int setBlock(float x,float y,float width,float height,float[] color,boolean contact) {
        block.put(unitCount,new BlockData(x,y,width,height,color,contact));
        unitCount ++;
        return (unitCount-1);
    }

    public void setBlockRunnable(int id,Runnable r) {
        block.get(id).setRunnable(r);
    }

    public void setBlockVelX(int blockId,float value) {
        BlockData data = block.get(blockId);
        data.xVel = value;
    }

    public void setBlockVelY(int blockId,float value) {
        BlockData data = block.get(blockId);
        data.yVel = value;
    }

    public void setBlockX(int blockId,float value) {
        BlockData data = block.get(blockId);
        data.x = value;
    }

    public void setBlockY(int blockId,float value) {
        BlockData data = block.get(blockId);
        data.y = value;
    }

    public void setBlockWidth(int blockId,float value) {
        BlockData data = block.get(blockId);
        data.width = value;
    }

    public void setBlockHeight(int blockId,float value) {
        BlockData data = block.get(blockId);
        data.height = value;
    }

    public float getBlockVelX(int blockId) {
        return block.get(blockId).xVel;
    }

    public float getBlockVelY(int blockId) {
        return block.get(blockId).yVel;
    }

    public float getBlockX(int blockId) {
        return block.get(blockId).x;
    }

    public float getBlockY(int blockId) {
        return block.get(blockId).y;
    }

    public float getBlockWidth(int blockId) {
        return block.get(blockId).width;
    }

    public float getBlockHeight(int blockId) {
        return block.get(blockId).height;
    }

    public void removeBlock(int blockId) {
        block.remove(blockId);
    }

    public int addAttacker(float x,float y,float width,float height) {
        attacker.put(attackerCount,new AttackerData(x,y,width,height,false));
        attackerCount ++;
        return (attackerCount-1);
    }

    public int addAttacker(float x,float y,float width,float height,float[] color) {
        attacker.put(attackerCount,new AttackerData(x,y,width,height,color,false));
        attackerCount ++;
        return (attackerCount-1);
    }

    public void setAttackerRunnable(int id,Runnable r) {
        attacker.get(id).setRunnable(r);
    }

    public void setAttackerVelX(int attackerId,float value) {
        AttackerData data = attacker.get(attackerId);
        data.xVel = value;
    }

    public void setAttackerVelY(int attackerId,float value) {
        AttackerData data = attacker.get(attackerId);
        data.yVel = value;
    }

    public void setAttackerX(int attackerId,float value) {
        AttackerData data = attacker.get(attackerId);
        data.x = value;
    }

    public void setAttackerY(int attackerId,float value) {
        AttackerData data = attacker.get(attackerId);
        data.y = value;
    }

    public void setAttackerWidth(int attackerId,float value) {
        AttackerData data = attacker.get(attackerId);
        data.width = value;
    }

    public void setAttackerHeight(int attackerId,float value) {
        AttackerData data = attacker.get(attackerId);
        data.height = value;
    }

    public float getAttackerVelX(int attackerId) {
        return attacker.get(attackerId).xVel;
    }

    public float getAttackerVelY(int attackerId) {
        return attacker.get(attackerId).yVel;
    }

    public float getAttackerX(int attackerId) {
        return attacker.get(attackerId).x;
    }

    public float getAttackerY(int attackerId) {
        return attacker.get(attackerId).y;
    }

    public float getAttackerWidth(int attackerId) {
        return attacker.get(attackerId).width;
    }

    public float getAttackerHeight(int attackerId) {
        return attacker.get(attackerId).height;
    }

    public void removeAttacker(int attackerId) {
        attacker.remove(attackerId);
    }

    public void setViewRotate(int degree) {
        glSurfaceView.setRotationY(degree);
    }

    class BlockData {
        public float x = 0;
        public float y = 0;
        public float width = 0;
        public float height = 0;
        public float[] color = null;
        public boolean canContact = true;
        public float xVel = 0;
        public float yVel = 0;
        public Runnable runnable = null;

        public BlockData(float x,float y,float width,float height,boolean contact) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.canContact = contact;
        }

        public BlockData(float x,float y,float width,float height,float[] color,boolean contact) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.canContact = contact;
        }

        public void setRunnable(Runnable r) {
            runnable = r;
        }

        public void run() {
            if(runnable!=null)
                runnable.run();
        }
    }

    class AttackerData {
        public float x = 0;
        public float y = 0;
        public float width = 0;
        public float height = 0;
        public float[] color = null;
        public float xVel = 0;
        public float yVel = 0;
        public boolean resistance = false;
        public Runnable runnable;

        public AttackerData(float x,float y,float width,float height,boolean resistance) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.resistance = resistance;
        }

        public AttackerData(float x,float y,float width,float height,float[] color,boolean resistance) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.resistance = resistance;
        }

        public void setRunnable(Runnable r) {
            runnable = r;
        }

        public void run() {
            if(runnable!=null)
                runnable.run();
        }
    }
}
