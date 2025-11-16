package net.caffeinemc.mods.sodium.client.render.backend;

public enum RendererBackend {
    DEFAULT("Default"),
    MDI("MDI");

    private final String name;

    RendererBackend(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
