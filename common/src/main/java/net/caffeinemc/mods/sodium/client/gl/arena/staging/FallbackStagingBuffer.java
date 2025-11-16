package net.caffeinemc.mods.sodium.client.gl.arena.staging;

import net.caffeinemc.mods.sodium.client.gl.buffer.GlBuffer;
import net.caffeinemc.mods.sodium.client.gl.buffer.GlBufferUsage;
import net.caffeinemc.mods.sodium.client.gl.buffer.GlMutableBuffer;
import net.caffeinemc.mods.sodium.client.gl.device.CommandList;
import net.caffeinemc.mods.sodium.client.gl.buffer.GlBufferTarget;
import net.caffeinemc.mods.sodium.client.gl.device.GLRenderDevice;

import java.nio.ByteBuffer;

public class FallbackStagingBuffer implements StagingBuffer {
    private final GlMutableBuffer fallbackBufferObject;

    public FallbackStagingBuffer(CommandList commandList) {
        this.fallbackBufferObject = commandList.createMutableBuffer();
        commandList.allocateStorage(this.fallbackBufferObject, GlBufferTarget.COPY_WRITE_BUFFER, 0L, GlBufferUsage.STREAM_COPY);
        commandList.flush();
    }

    @Override
    public void enqueueCopy(CommandList commandList, ByteBuffer data, GlBuffer dst, long writeOffset) {
        commandList.uploadData(this.fallbackBufferObject, data, GlBufferUsage.STREAM_COPY);
        commandList.copyBufferSubData(this.fallbackBufferObject, dst, 0, writeOffset, data.remaining());
    }

    @Override
    public void flush(CommandList commandList) {
        commandList.allocateStorage(this.fallbackBufferObject, GlBufferTarget.COPY_WRITE_BUFFER, 0L, GlBufferUsage.STREAM_COPY);
    }

    @Override
    public void delete(CommandList commandList) {
        commandList.deleteBuffer(this.fallbackBufferObject);
    }

    @Override
    public void flip() {

    }

    @Override
    public String toString() {
        return "Fallback";
    }

    @Override
    public long getUploadSizeLimit(long frameDuration) {
        return Long.MAX_VALUE; // No limit for fallback buffer since time-liming takes care of it
    }
}
