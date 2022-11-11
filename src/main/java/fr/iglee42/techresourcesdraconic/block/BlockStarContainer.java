package fr.iglee42.techresourcesdraconic.block;

import fr.iglee42.techresourcesdraconic.InitContent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BlockStarContainer extends Block {

    public static final BooleanProperty HAS_STAR = BooleanProperty.create("star");

    public BlockStarContainer() {
        super(Properties.copy(Blocks.IRON_BLOCK));
        registerDefaultState(defaultBlockState().setValue(HAS_STAR,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(HAS_STAR);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (p_60506_.getMainHandItem().isEmpty()) return InteractionResult.FAIL;
        if (p_60506_.getMainHandItem().getItem() != InitContent.ENRICHED_STAR.get()) return InteractionResult.FAIL;
        if (!p_60503_.getValue(HAS_STAR)){
            BlockState newState = p_60503_;
            newState = newState.setValue(HAS_STAR,true);
            p_60504_.setBlockAndUpdate(p_60505_,newState);
            if (!p_60506_.isCreative()){
                p_60506_.getMainHandItem().setCount(p_60506_.getMainHandItem().getCount() - 1);
            }
        }
        return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
    }
}
