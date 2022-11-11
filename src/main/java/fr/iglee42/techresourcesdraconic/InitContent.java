package fr.iglee42.techresourcesdraconic;

import static fr.iglee42.techresourcesdraconic.TechResourcesDraconic.GROUP;
import static fr.iglee42.techresourcesdraconic.TechResourcesDraconic.MODID;

import fr.iglee42.techresourcesdraconic.block.BlockStarContainer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class InitContent {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,MODID);

    //ITEMS
    public static final RegistryObject<Item> ENRICHED_STAR = createItem("enriched_star", ()-> new Item(new Item.Properties().tab(GROUP)){
        @Override
        public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
            if (entity.getPersistentData().contains("JustCreated")){
                if (entity.level.isClientSide){
                    Vec3 ppos = TechResourcesDraconic.offsetRandomly(entity.position(), entity.level.random, .5f);
                    entity.level.addParticle(ParticleTypes.END_ROD, ppos.x, entity.getY(), ppos.z, 0, -.1f, 0);
                    entity.level.addParticle(ParticleTypes.FLASH,entity.getX(),entity.getY(),entity.getZ(),0,0,0);
                }
                entity.getPersistentData().remove("JustCreated");
            }
            return false;
        }
    });
    public static final RegistryObject<Item> ENRICHED_DRAGON_STAR = createItem("enriched_dragon_star", ()-> new Item(new Item.Properties().tab(GROUP)));


    //BLOCKS
    public static final RegistryObject<Block> STAR_CONTAINER = createBlockWithoutItem("star_container", BlockStarContainer::new);




    /**METHODS*/
    public static RegistryObject<Block> createBlock(String name, Supplier<? extends Block> supplier)
    {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(GROUP)));
        return block;
    }
    public static RegistryObject<Block> createBlockWithoutItem(String name, Supplier<? extends Block> supplier)
    {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        return block;
    }
    public static RegistryObject<Item> createItem(String name, Supplier<? extends Item> supplier)
    {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        return item;
    }

    public static void register(IEventBus bus){
        ITEMS.register(bus);
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}
