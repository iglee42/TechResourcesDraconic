package fr.iglee42.techresourcesdraconic.utils;

import fr.iglee42.techresourcesdraconic.TechResourcesDraconic;
import fr.iglee42.techresourcesdraconic.customevents.ItemTickEvent;
import fr.iglee42.techresourcesdraconic.TechResourcesDraconic;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechResourcesDraconic.MODID)
public class CustomEventsHandler {
    @SubscribeEvent
    public static void onWorldTick(TickEvent.PlayerTickEvent event){
        event.player.getLevel().getEntities(event.player,event.player.getBoundingBox().inflate(50,50,50),e->e.getType() == EntityType.ITEM).forEach(e-> MinecraftForge.EVENT_BUS.post(new ItemTickEvent((ItemEntity) e)));
    }
}
