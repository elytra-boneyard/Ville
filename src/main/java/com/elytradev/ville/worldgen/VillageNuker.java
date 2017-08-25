package com.elytradev.ville.worldgen;


import com.elytradev.concrete.reflect.accessor.Accessor;
import com.elytradev.concrete.reflect.accessor.Accessors;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class VillageNuker {

    @SubscribeEvent
    public void nukeVillages(InitMapGenEvent event) {
        if(event.getType() == InitMapGenEvent.EventType.VILLAGE) {
            event.setNewGen(new EmptyGen());
        }
    }

    @SubscribeEvent
    public void nukeFlatworldVillagesOnCreation(WorldEvent.CreateSpawnPosition event) {
        if(event.getWorld().getChunkProvider() instanceof ChunkProviderServer) {
            ChunkProviderServer serverProvider = (ChunkProviderServer)event.getWorld().getChunkProvider();
            if(serverProvider.chunkGenerator instanceof ChunkGeneratorFlat) {
                Accessor<Map<String, MapGenStructure>> generators = Accessors.findField(ChunkGeneratorFlat.class,
                        "structureGenerators", "field_82696_f");
                generators.get(serverProvider.chunkGenerator).remove("Village");
            }
        }
    }

    @SubscribeEvent
    public void nukeFlatworldVillagesOnLoad(WorldEvent.Load event) {
        if(event.getWorld().getChunkProvider() instanceof ChunkProviderServer) {
            ChunkProviderServer serverProvider = (ChunkProviderServer)event.getWorld().getChunkProvider();
            if(serverProvider.chunkGenerator instanceof ChunkGeneratorFlat) {
                Accessor<Map<String, MapGenStructure>> generators = Accessors.findField(ChunkGeneratorFlat.class,
                        "structureGenerators", "field_82696_f");
                generators.get(serverProvider.chunkGenerator).remove("Village");
            }
        }
    }
}
