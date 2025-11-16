package net.caffeinemc.mods.sodium.client.render.chunk;

import net.caffeinemc.mods.sodium.client.gl.device.RenderDevice;
import net.caffeinemc.mods.sodium.client.render.backend.RendererBackend;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkMeshFormats;

public class ChunkRenderers {
    public static ChunkRenderer create(RendererBackend backend) {
        return switch (backend) {
            case DEFAULT -> new DefaultChunkRenderer(RenderDevice.INSTANCE, ChunkMeshFormats.COMPACT);
            case MDI -> new MdiChunkRenderer(RenderDevice.INSTANCE, ChunkMeshFormats.COMPACT);
        };
    }
}
