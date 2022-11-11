package fr.iglee42.techresourcesdraconic;

import fr.iglee42.techresourcesdraconic.*;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Random;

@Mod(TechResourcesDraconic.MODID)
public class TechResourcesDraconic {

    public static final String MODID = "techresourcesdraconic";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab GROUP = new CreativeModeTab(MODID + ".evolved_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.NETHER_STAR);
        }
    };

    public TechResourcesDraconic() {
        InitContent.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }
    public static Vec3 offsetRandomly(Vec3 vec, Random r, float radius) {
        return new Vec3(vec.x + (r.nextFloat() - .5f) * 2 * radius, vec.y + (r.nextFloat() - .5f) * 2 * radius,
                vec.z + (r.nextFloat() - .5f) * 2 * radius);
    }
}
