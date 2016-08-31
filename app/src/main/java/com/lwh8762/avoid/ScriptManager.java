package com.lwh8762.avoid;

/**
 * Created by W on 2016-07-03.
 */
public class ScriptManager {

    private static MyRenderer myRenderer;

    public static void setGame(MyRenderer renderer) {
        myRenderer = renderer;
    }

    public static int getColor(int a,int r,int g,int b) {
        return android.graphics.Color.argb(a, r, g, b);
    }

    public static void appendLog(String s) {
        myRenderer.appendLog(s);
    }
    
    public static void setLog(String s) {
        myRenderer.setLog(s);
    }

    public static void sendException(String s) {
        myRenderer.sendException(s);
    }

    public static void clear() {
        myRenderer.clear();
    }

    public static void clean() {
        myRenderer.clean();
    }

    public static void attackerClean() {
        myRenderer.attackerClean();
    }

    public static void blockClean() {
        myRenderer.blockClean();
    }

    public static void textClean() {
        myRenderer.textClean();
    }

    public static float getMapHeight() {
        return myRenderer.getMapHeight();
    }

    public static int addText(String s,float x,float y,float size) {
        return myRenderer.addText(s,x,y,size,android.graphics.Color.WHITE);
    }

    public static int addText(String s,float x,float y,float size,int color) {
        return myRenderer.addText(s,x,y,size,color);
    }

    public static void setTextFormWidth(int textId,float width) {
        myRenderer.setTextFormWidth(textId, width);
    }

    public static void setTextFormHeight(int textId,float height) {
        myRenderer.setTextFormHeight(textId, height);
    }

    public static void setText(int textId,String s) {
        myRenderer.setText(textId,s);
    }

    public static void setTextX(int textId,float x) {
        myRenderer.setTextX(textId,x);
    }

    public static void setTextY(int textId,float y) {
        myRenderer.setTextY(textId,y);
    }

    public static void setTextColor(int textId,int color) {
        myRenderer.setTextColor(textId,color);
    }

    public static void removeText(int textId) {
        myRenderer.removeText(textId);
    }

    public static float getPlayerX() {
        return myRenderer.getPlayerX();
    }

    public static float getPlayerY() {
        return myRenderer.getPlayerY();
    }

    public static void setPlayerVelX(float value) {
        myRenderer.setPlayerVelX(value);
    }

    public static void setPlayerVelY(float value) {
        myRenderer.setPlayerVelY(value);
    }

    public static void setPlayerX(float x) {
        myRenderer.setPlayerX(x);
    }

    public static void setPlayerY(float y) {
        myRenderer.setPlayerY(y);
    }

    public static void setEnableGround(boolean val) {
        myRenderer.setEnableGround(val);
    }

    public static int setBlock(float x,float y,float width,float height) {
        return myRenderer.setBlock(x, y, width, height, true);
    }

    public static int setBlock(float x,float y,float width,float height,float[] color) {
        return myRenderer.setBlock(x, y, width, height, color, true);
    }

    public static int setBlock(float x,float y,float width,float height,float[] color,boolean contact) {
        return myRenderer.setBlock(x, y, width, height, color, contact);
    }

    public static void setBlockRunnable(int id,Runnable r) {
        myRenderer.setBlockRunnable(id,r);
    }

    public static void setBlockVelX(int blockId,float value) {
        myRenderer.setBlockVelX(blockId, value);
    }

    public static void setBlockVelY(int blockId,float value) {
        myRenderer.setBlockVelY(blockId, value);
    }

    public static void setBlockX(int blockId,float value) {
        myRenderer.setBlockX(blockId, value);
    }

    public static void setBlockY(int blockId,float value) {
        myRenderer.setBlockY(blockId, value);
    }

    public static void setBlockWidth(int blockId,float value) {
        myRenderer.setBlockWidth(blockId, value);
    }

    public static void setBlockHeight(int blockId,float value) {
        myRenderer.setBlockHeight(blockId, value);
    }

    public static float getBlockVelX(int blockId) {
        return myRenderer.getBlockVelX(blockId);
    }

    public static float getBlockVelY(int blockId) {
        return myRenderer.getBlockVelY(blockId);
    }

    public static float getBlockX(int blockId) {
        return myRenderer.getBlockX(blockId);
    }

    public static float getBlockY(int blockId) {
        return myRenderer.getBlockY(blockId);
    }

    public static float getBlockWidth(int blockId) {
        return myRenderer.getBlockWidth(blockId);
    }

    public static float getBlockHeight(int blockId) {
        return myRenderer.getBlockHeight(blockId);
    }

    public static void removeBlock(int blockId) {
        myRenderer.removeBlock(blockId);
    }


    public static int addAttacker(float x,float y,float width,float height) {
        return myRenderer.addAttacker(x, y, width, height);
    }

    public static int addAttacker(float x,float y,float width,float height,float[] color) {
        return myRenderer.addAttacker(x, y, width, height, color);
    }

    public static void setAttackerRunnable(int id,Runnable r) {
        myRenderer.setAttackerRunnable(id,r);
    }

    public static void setAttackerVelX(int attackerId,float value) {
        myRenderer.setAttackerVelX(attackerId, value);
    }

    public static void setAttackerVelY(int attackerId,float value) {
        myRenderer.setAttackerVelY(attackerId, value);
    }

    public static void setAttackerX(int attackerId,float value) {
        myRenderer.setAttackerX(attackerId, value);
    }

    public static void setAttackerY(int attackerId,float value) {
        myRenderer.setAttackerY(attackerId, value);
    }

    public static void setAttackerWidth(int attackerId,float value) {
        myRenderer.setAttackerWidth(attackerId, value);
    }

    public static void setAttackerHeight(int attackerId,float value) {
        myRenderer.setAttackerHeight(attackerId, value);
    }

    public static float getAttackerVelX(int attackerId) {
        return myRenderer.getAttackerVelX(attackerId);
    }

    public static float getAttackerVelY(int attackerId) {
        return myRenderer.getAttackerVelY(attackerId);
    }

    public static float getAttackerX(int attackerId) {
        return myRenderer.getAttackerX(attackerId);
    }

    public static float getAttackerY(int attackerId) {
        return myRenderer.getAttackerY(attackerId);
    }

    public static float getAttackerWidth(int attackerId) {
        return myRenderer.getAttackerWidth(attackerId);
    }

    public static float getAttackerHeight(int attackerId) {
        return myRenderer.getAttackerHeight(attackerId);
    }

    public static void removeAttacker(int attackerId) {
        myRenderer.removeAttacker(attackerId);
    }

    public static void setViewRotate(int degree) {
        myRenderer.setViewRotate(degree);
    }
}
