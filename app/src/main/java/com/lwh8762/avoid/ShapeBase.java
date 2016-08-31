package com.lwh8762.avoid;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

abstract public class ShapeBase
{
    abstract public void draw(GL10 gl);

    public FloatBuffer getFloatBufferFromFloatArray(float[] array)
    {
        ByteBuffer tempBuffer = ByteBuffer.allocateDirect(array.length*4);
        tempBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = tempBuffer.asFloatBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }
    public ShortBuffer getShortBufferFromShortArray(short[] array)
    {
        ByteBuffer ibb = ByteBuffer.allocateDirect(array.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        ShortBuffer buffer = ibb.asShortBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    public ByteBuffer getByteBufferFromByteArray(byte[] array)
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(array.length);
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }
}

