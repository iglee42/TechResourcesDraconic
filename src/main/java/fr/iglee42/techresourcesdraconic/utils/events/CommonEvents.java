package fr.iglee42.techresourcesdraconic.utils.events;

import fr.iglee42.techresourcesdraconic.InitContent;
import fr.iglee42.techresourcesdraconic.block.BlockStarContainer;
import fr.iglee42.techresourcesdraconic.customevents.ItemTickEvent;
import fr.iglee42.techresourcesdraconic.TechResourcesDraconic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber(modid = TechResourcesDraconic.MODID)
public class CommonEvents {


    @SubscribeEvent
    public static void onEntityTick(ItemTickEvent event){
        if (event.getEntityItem().getItem().getItem() != Items.NETHER_STAR) return;
        boolean hasBeaconUnder = false;
        int entityX = Mth.floor(event.getEntityItem().getX());
        int entityZ = Mth.floor(event.getEntityItem().getZ());
        int localWorldHeight = event.getEntityItem().level.getHeight(Heightmap.Types.WORLD_SURFACE, entityX, entityZ);
        BlockPos.MutableBlockPos testPos =
                new BlockPos.MutableBlockPos(entityX, Mth.floor(event.getEntityItem().getY()), entityZ);
        while (testPos.getY() > -64) {
            testPos.move(Direction.DOWN);
            BlockState state = event.getEntityItem().level.getBlockState(testPos);
            if (state.getLightBlock(event.getEntityItem().level, testPos) >= 15 && state.getBlock() != Blocks.BEDROCK)
                break;
            if (state.getBlock() == Blocks.BEACON) {
                BlockEntity te = event.getEntityItem().level.getBlockEntity(testPos);

                if (!(te instanceof BeaconBlockEntity))
                    break;

                BeaconBlockEntity bte = (BeaconBlockEntity) te;

                if (!bte.getBeamSections().isEmpty())
                    hasBeaconUnder = true;

                break;
            }
        }
        if (hasBeaconUnder){
            ItemStack stack = new ItemStack(InitContent.ENRICHED_STAR.get());
            stack.setCount(event.getEntityItem().getItem().getCount());
            event.getEntityItem().getPersistentData().putBoolean("JustCreated", true);
            event.getEntityItem().setItem(stack);
        }

    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event){
        if (event.getEntity().getType() != EntityType.ENDER_DRAGON) return;
        EnderDragon dragon = (EnderDragon) event.getEntity();
        if (dragon.level.dimension() == Level.END){
            Level lvl = dragon.level;
            AtomicBoolean hasAContainer = new AtomicBoolean(false);
            List<BlockPos> possiblePos = List.of(new BlockPos(3,0,0),new BlockPos(-3,0,0),new BlockPos(0,0,3),new BlockPos(0,0,-3));
            Map<BlockPos,BlockState> validContainers = new HashMap<>();
            possiblePos.forEach(pos -> {
                for (int i = 70; i >= 50 ; i--){
                    if (lvl.getBlockState(pos.atY(i)).getBlock() == InitContent.STAR_CONTAINER.get() && lvl.getBlockState(pos.atY(i - 1)).getBlock() == Blocks.BEDROCK){
                        BlockPos containerPos = pos.atY(i);
                        BlockState containerState =lvl.getBlockState(containerPos);
                        if (!containerState.getValue(BlockStarContainer.HAS_STAR)) continue;
                        validContainers.put(containerPos,containerState);
                        hasAContainer.set(true);
                    }
                }
            });
            final int[] runCount = {0};
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (hasAContainer.get() && runCount[0] < 6){
                        List<LightningBolt> bolts = new ArrayList<>();
                        LightningBolt bolt1 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt1.setPos(3,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(3,0,3)).getY() + 2,3);
                        bolts.add(bolt1);
                        LightningBolt bolt2 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt2.setPos(-3,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(-3,0,3)).getY() + 2,3);
                        bolts.add(bolt2);
                        LightningBolt bolt3 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt3.setPos(3,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(3,0,-3)).getY() + 2,-3);
                        bolts.add(bolt3);
                        LightningBolt bolt4 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt4.setPos(-3,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(-3,0,-3)).getY() + 2,-3);
                        bolts.add(bolt4);
                        bolts.forEach(lvl::addFreshEntity);
                        List<LightningBolt> bolts2 = new ArrayList<>();
                        LightningBolt bolt5 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt5.setPos(3,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(3,0,0)).getY() + 2,0);
                        bolts2.add(bolt5);
                        LightningBolt bolt6 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt6.setPos(-3,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(-3,0,0)).getY() + 2,0);
                        bolts2.add(bolt6);
                        LightningBolt bolt7 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt7.setPos(0,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(0,0,-3)).getY() + 2,3);
                        bolts2.add(bolt7);
                        LightningBolt bolt8 = EntityType.LIGHTNING_BOLT.create(lvl);
                        bolt8.setPos(0,lvl.getHeightmapPos(Heightmap.Types.WORLD_SURFACE,new BlockPos(0,0,-3)).getY() + 2,-3);
                        bolts2.add(bolt8);
                        bolts2.forEach(lvl::addFreshEntity);
                    }

                    if (runCount[0] == 6){
                        validContainers.forEach((containerPos,containerState) -> {
                            lvl.setBlockAndUpdate(containerPos,containerState.setValue(BlockStarContainer.HAS_STAR,false));
                            Block.popResource(lvl,containerPos.offset(0,1,0),new ItemStack(InitContent.ENRICHED_DRAGON_STAR.get()));
                        });
                        cancel();
                    }
                    runCount[0]++;
                }
            }, 1000L,1000L);
            /*if (containerPos == null) return;
            BlockState containerState =lvl.getBlockState(containerPos);
            if (!containerState.getValue(BlockStarContainer.HAS_STAR)) return;
            lvl.setBlockAndUpdate(containerPos,containerState.setValue(BlockStarContainer.HAS_STAR,false));
            Block.popResource(lvl,containerPos.offset(0,1,0),new ItemStack(InitContent.ENRICHED_DRAGON_STAR.get()));*/
        }
    }



}
