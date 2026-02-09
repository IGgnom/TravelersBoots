package net.iggnom.travelersboots.compat;

import net.neoforged.fml.loading.LoadingModList;

import java.util.function.Supplier;

public enum Mods {
    CURIOS;

    private final boolean isLoaded;

    Mods() {
        String id = name().toLowerCase();
        isLoaded = LoadingModList.get().getModFileById(id) != null;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void executeIfInstalled(Supplier<Runnable> toExecute) {
        if (isLoaded()) {
            toExecute.get().run();
        }
    }
}
