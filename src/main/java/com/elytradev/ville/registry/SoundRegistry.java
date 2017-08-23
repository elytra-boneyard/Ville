package com.elytradev.ville.registry;


import com.elytradev.ville.Ville;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SoundRegistry {

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(createSoundEvent("entity.pigman.ambient"));
        event.getRegistry().register(createSoundEvent("entity.pigman.death"));
        event.getRegistry().register(createSoundEvent("entity.pigman.hurt"));
    }

    public static SoundEvent createSoundEvent(String name) {
        ResourceLocation location = new ResourceLocation(Ville.MOD_ID, name);
        return new SoundEvent(location).setRegistryName(location);
    }
}
