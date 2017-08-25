package com.elytradev.ville.worldgen;

import net.minecraft.world.gen.structure.MapGenVillage;

public class EmptyGen extends MapGenVillage {

    // No, you can never spawn. Ever. Stop. Stop it now.
    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        return false;
    }
}
