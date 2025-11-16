package net.caffeinemc.mods.sodium.client.gl.device;

import net.caffeinemc.mods.sodium.client.gl.buffer.*;
import net.caffeinemc.mods.sodium.client.gl.util.EnumBitField;
import org.lwjgl.opengl.GL44C;

import java.nio.ByteBuffer;

public class MultiDrawIndirectBuffer {
    public static final int COMMAND_STRIDE = 20; // 5 integers * 4 bytes each

    private final GlImmutableBuffer buffer;
    private final GlBufferMapping mapping;
    private final int capacity;

    public int size;

    public MultiDrawIndirectBuffer(int initialCapacity, CommandList commandList) {
        this.capacity = initialCapacity;

        EnumBitField<GlBufferStorageFlags> storageFlags = EnumBitField.of(
                GlBufferStorageFlags.MAP_WRITE,
                GlBufferStorageFlags.PERSISTENT,
                GlBufferStorageFlags.COHERENT);

        long sizeBytes = (long) this.capacity * COMMAND_STRIDE;
        this.buffer = commandList.createImmutableBuffer(GlBufferTarget.DRAW_INDIRECT_BUFFER, sizeBytes, storageFlags);

        EnumBitField<GlBufferMapFlags> mapFlags = EnumBitField.of(
                GlBufferMapFlags.WRITE,
                GlBufferMapFlags.PERSISTENT);

        this.mapping = commandList.mapBuffer(this.buffer, GlBufferTarget.DRAW_INDIRECT_BUFFER, 0, sizeBytes, mapFlags);
    }

    public void addCommand(int count, int firstIndex, int baseVertex) {
        int offset = this.size * COMMAND_STRIDE;
        ByteBuffer byteBuffer = this.mapping.getMemoryBuffer();
        byteBuffer.putInt(offset, count);
        byteBuffer.putInt(offset + 4, 1); // instanceCount is always 1
        byteBuffer.putInt(offset + 8, firstIndex);
        byteBuffer.putInt(offset + 12, baseVertex);
        byteBuffer.putInt(offset + 16, 0); // baseInstance is always 0
        this.size++;
    }

    public GlImmutableBuffer getBuffer() {
        return this.buffer;
    }

    public void clear() {
        this.size = 0;
    }

    public void delete(CommandList commandList) {
        if (this.mapping != null) {
            commandList.unmap(this.mapping);
        }
        if (this.buffer != null) {
            commandList.deleteBuffer(this.buffer);
        }
    }

    public boolean isEmpty() {
        return this.size <= 0;
    }
}
