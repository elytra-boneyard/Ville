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

package com.elytradev.ville.entity;


import com.elytradev.ville.Ville;
import com.elytradev.ville.generic.ItemHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class PigmanProfession extends IForgeRegistryEntry.Impl<PigmanProfession> {
    public static IForgeRegistry<PigmanProfession> PIGMAN_PROFESSION_REGISTRY;

    protected String name;
    protected Map<EntityEquipmentSlot, ItemStack> outfit;

    public PigmanProfession(String name, Map<EntityEquipmentSlot, ItemStack> outfit) {
        this.name = name;
        this.outfit = outfit;
    }

    public String getName() {
        return name;
    }

    public Map<EntityEquipmentSlot, ItemStack> getOutfit() {
        return outfit;
    }

    private static void registerPigmanProfession(String name, String registryName, Object[] outfit) {
        Map<EntityEquipmentSlot, ItemStack> outfitMap = new HashMap<>();
        for(int i =0;i < outfit.length;i+=2) {
            if(outfit.length == i+1)
                Ville.LOG.error("Outfit for " + name + " profession ended prematurely...");
            else {
                if (outfit[i] instanceof EntityEquipmentSlot && outfit[i + 1] instanceof ItemStack) {
                    outfitMap.put((EntityEquipmentSlot) outfit[i], (ItemStack) outfit[i + 1]);
                }
            }
        }

        PIGMAN_PROFESSION_REGISTRY.register(new PigmanProfession(name, outfitMap)
                .setRegistryName(new ResourceLocation(Ville.MOD_ID, registryName)));
    }

    public static void registerProfessions() {
        registerPigmanProfession("Butcher", "butcher", new Object[] {
                EntityEquipmentSlot.CHEST, ItemHelper.colorArmor(new ItemStack(Items.LEATHER_CHESTPLATE), 0xFFFFFF),
                EntityEquipmentSlot.LEGS, ItemHelper.colorArmor(new ItemStack(Items.LEATHER_LEGGINGS), 0xFFFFFF)
        });
    }
}
