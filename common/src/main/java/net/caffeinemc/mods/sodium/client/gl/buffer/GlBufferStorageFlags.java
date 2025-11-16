package net.caffeinemc.mods.sodium.client.gl.buffer;

import net.caffeinemc.mods.sodium.client.gl.util.EnumBit;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GL44C;

public enum GlBufferStorageFlags implements EnumBit {
    MAP_READ(GL30C.GL_MAP_READ_BIT),
    MAP_WRITE(GL30C.GL_MAP_WRITE_BIT),
    PERSISTENT(GL44C.GL_MAP_PERSISTENT_BIT),
    COHERENT(GL44C.GL_MAP_COHERENT_BIT),
    CLIENT_STORAGE(GL44C.GL_CLIENT_STORAGE_BIT),
    DYNAMIC_STORAGE(GL44C.GL_DYNAMIC_STORAGE_BIT);

    private final int bit;

    GlBufferStorageFlags(int bits) {
        this.bit = bits;
    }

    @Override
    public int getBits() {
        return this.bit;
    }
}
