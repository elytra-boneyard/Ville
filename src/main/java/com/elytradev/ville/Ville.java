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

package com.elytradev.ville;

import com.elytradev.ville.block.BlockBase;
import com.elytradev.ville.entity.EntityPigman;
import com.elytradev.ville.entity.PigmanProfession;
import com.elytradev.ville.generic.VilleCreativeTab;
import com.elytradev.ville.item.ItemBase;
import com.elytradev.ville.proxy.CommonProxy;
import com.elytradev.ville.worldgen.VillageNuker;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Ville.MOD_ID,
        name = Ville.NAME,
        version = Ville.VERSION)
@Mod.EventBusSubscriber
public final class Ville {

    public static final String MOD_ID = "ville";
    public static final String NAME = "Ville";
    public static final String VERSION = "1.12-0.1.0";

    public static final Logger LOG = LogManager.getLogger(Ville.NAME);

    @SidedProxy(
            clientSide = "com.elytradev.ville.proxy.ClientProxy",
            serverSide = "com.elytradev.ville.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static final CreativeTabs CREATIVE_TAB = new VilleCreativeTab(Ville.MOD_ID);

    @Mod.Instance
    public static Ville INSTANCE;

    public Ville() {
        MinecraftForge.TERRAIN_GEN_BUS.register(new VillageNuker());
        MinecraftForge.EVENT_BUS.register(new VillageNuker());
    }


    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PROXY.init();
        PigmanProfession.registerProfessions();
    }



    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // TODO: Register WorldGen here
        EntityRegistry.registerModEntity(new ResourceLocation(Ville.MOD_ID, "pigman"), EntityPigman.class, "pigman", 0, this, 128, 3, true);
        EntityRegistry.registerEgg(new ResourceLocation(Ville.MOD_ID, "pigman"), 16758736, 14505605);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public void onRegisterPigmanProfessions(RegistryEvent.Register<PigmanProfession> event) {

    }

    private static void registerBlock(IForgeRegistry<Block> registry, Block block) {
        registry.register(block);
    }

    private static void registerItem(IForgeRegistry<Item> registry, Item item) {
        registry.register(item);

        if (item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        } else if (item instanceof ItemBlock &&
                ((ItemBlock) item).getBlock() instanceof BlockBase) {
            ((BlockBase) ((ItemBlock) item).getBlock()).registerItemModel(((ItemBlock) item));
        } else {
            final ResourceLocation loc = Item.REGISTRY.getNameForObject(item);
            PROXY.registerItemRenderer(item, 0, loc.getResourcePath());
        }
    }

}
