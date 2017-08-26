/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2017:
 *      Ethan Brooks (CalmBit),
 *      and contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of
 *  this software and associated documentation files (the "Software"), to deal in
 *  the Software without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do
 *  so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

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

    /*  Q: Why two methods?
     *
     *  TL;DR: Because flatworld generation doesn't adhere to any of the rules, and is just a dick.
     *
     *  It uses MapGenVillage instead of a call to the event bus, like ChunkGeneratorOverworld uses, so
     *  instead we're left doing this hacky shit. We hook into WorldEvent. CreateSpawnPosition for world creation,
     *  and WorldEvent.Load for world loading, so that both times the flatworld generation doesn't get its way.
     */
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

    // Meanwhile, look how crazy simple Overworld village nuking is:
    @SubscribeEvent
    public void nukeVillages(InitMapGenEvent event) {
        if(event.getType() == InitMapGenEvent.EventType.VILLAGE) {
            event.setNewGen(new EmptyGen());
        }
    }
}
