package com.lwh8762.avoid.unit;

import com.lwh8762.avoid.ShapeBase;

import javax.microedition.khronos.opengles.GL10;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;

public class Player extends ShapeBase
{
    float[] vertices;
    byte[] indices;
    float[] colors;
    FloatBuffer mVertexBuffer;
    ByteBuffer mIndexBuffer;
    FloatBuffer mColorBuffer;

    public Player(float x,float y,float width,float height)
    {
        vertices = new float[]{
                x      ,y+height,0,
                x+width,y+height,0,
                x+width,y       ,0,
                x      ,y       ,0,
        };
        indices = new byte[]{
                0, 3, 2,
                0, 1, 2,
                1, 3, 2
        };
        colors = new float[]{
                1.0f,  1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,  1.0f,
        };

        mVertexBuffer = getFloatBufferFromFloatArray(vertices);
        mIndexBuffer = getByteBufferFromByteArray(indices);
        mColorBuffer = getFloatBufferFromFloatArray(colors);
    }

    @Override
    public void draw(GL10 gl)
    {
        gl.glFrontFace(GL10.GL_CW);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,mVertexBuffer);
        gl.glColorPointer(4,GL10.GL_FLOAT,0,mColorBuffer);
        gl.glDrawElements(GL10.GL_LINE_LOOP,indices.length,GL10.GL_UNSIGNED_BYTE,mIndexBuffer);
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}

