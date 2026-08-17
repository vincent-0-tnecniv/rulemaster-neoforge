package net.vincent.rulemaster.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.TriState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.vincent.rulemaster.block.interaction.ModBlockInteractions;
import net.vincent.rulemaster.util.BlockWithState;

public class FleshBlock extends BlockWithState {

    public static final IntegerProperty TOC = IntegerProperty.create("toc", 0, 35);

    public FleshBlock(Properties properties) {
        super(properties.strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn(Blocks::never).sound(SoundType.SLIME_BLOCK).lightLevel(_ -> 15));
        this.registerDefaultState(this.defaultBlockState().setValue(TOC, 0));
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return TriState.FALSE;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ModBlockInteractions.FleshBlock.useItemOnFleshBlockLike(itemStack, state, level, pos, player, hand, hitResult, super::useItemOn);
    }
    /*
    What is happening here?

    1. When Minecraft calls useItemOn(), it calls
       FleshBlockInteractions.useItemOnFleshBlockLike();
       - This includes super::useItemOn as a parameter, which is a METHOD OBJECT

    2. A new SuperCall<T> object is created for super::useItemOn to be stored in the
       SuperCall<T> object's originalUseItemOn() method
       - Because super::useItemOn returns an InteractionResult, T is of type
       InteractionResult and the object becomes SuperCall<InteractionResult>
       (because SuperCall<T>)
       - Afterwards, super::useItemOn is stored in the
         SuperCall<InteractionResult> object's originalUseItemOn() method

    3. When the logic in FleshBlockInteractions.useItemOnFleshBlockLike() runs
       and superCall.originalUseItemOn() is called, all parameters that call the
       FleshBlockInteraction.useItemOnFleshBlockLike() is passed into the
       superCall.originalUseItemOn() as parameters of THAT method (i.e. "superCall.originalUseItemOn()")

    4. When superCall.originalUseItemOn() returns an InteractionResult object, it gets
       returned to FleshBlock.useItemOn(), which THEN gets returned to
        BlockWithState.useItemOn()... until the original, non-inherited method is found and called
       Hence, the super class of the FleshBlock has its useItemOn() method called.
     */

    /*
    How to reuse?
    1. Create a new class for the super method/object (SuperSomeMethod<T> superObject) and rename
       the method (originalInheritedMethod()) with the original parameters inside
    2. Add a new method in ModBlockInteractions, with the original parameters and
       a new parameter with the super method/object AND the generic type the same as
       the value to be returned
       e.g. if T is float, then the superObject is of type SuperSomeMethod<Float>
    3. Whenever a super call is needed, return the new parameter's method
       In the above example, this would be superObject.originalInheritedMethod(), with
       original parameters inside
     */

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOC);
    }
}
