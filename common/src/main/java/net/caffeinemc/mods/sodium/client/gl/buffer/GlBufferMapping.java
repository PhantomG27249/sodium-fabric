package net.caffeinemc.mods.sodium.client.gl.buffer;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class GlBufferMapping {
    private final GlBuffer buffer;
    private final ByteBuffer map;
    private final GlBufferTarget target;

    protected boolean disposed;

    public GlBufferMapping(GlBuffer buffer, ByteBuffer map, GlBufferTarget target) {
        this.buffer = buffer;
        this.map = map;
        this.target = target;
    }

    public void write(ByteBuffer data, int writeOffset) {
        MemoryUtil.memCopy(MemoryUtil.memAddress(data), MemoryUtil.memAddress(this.map, writeOffset), data.remaining());
    }

    public GlBuffer getBufferObject() {
        return this.buffer;
    }

    public GlBufferTarget getTarget() {
        return this.target;
    }

    public void dispose() {
        this.disposed = true;
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public ByteBuffer getMemoryBuffer() {
        return this.map;
    }
}
