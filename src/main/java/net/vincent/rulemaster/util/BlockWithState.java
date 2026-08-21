package net.vincent.rulemaster.util;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockWithState extends Block {
    public BlockWithState(Properties properties) {
        super(properties);
    }

    public static <T extends Enum<T> & StringRepresentable> BlockState cycleInBetween(BlockState state, EnumProperty<T> property) {
        List<T> values = new ArrayList<>(property.getPossibleValues());
        T current = state.getValue(property);
        int currentIndex = values.indexOf(current);
        int nextIndex = (currentIndex + 1) % values.size();
        return state.setValue(property, values.get(nextIndex));
    }

    public static BlockState cycleInBetween(BlockState state, IntegerProperty property) {
        List<Integer> values = new ArrayList<>(property.getPossibleValues());
        int current = state.getValue(property);
        int next = (current + 1)  % values.size();
        return state.setValue(property, values.get(next));
    }

    public static <T extends Enum<T> & StringRepresentable> BlockState cycleUntil(BlockState state, EnumProperty<T> property, T end) {
        List<T> values = new ArrayList<>(property.getPossibleValues());
        T current = state.getValue(property);
        int currentIndex = values.indexOf(current);
        int nextIndex = currentIndex + 1;
        int limit = Math.min(values.size(), values.indexOf(end));
        if(hasNotExceededLimit(nextIndex, limit)){
            return state.setValue(property, values.get(nextIndex));
        }
        return state;
    }

    public static BlockState cycleUntil(BlockState state, IntegerProperty property, int end) {
        List<Integer> values = new ArrayList<>(property.getPossibleValues());
        int current = state.getValue(property);
        int currentIndex = values.indexOf(current);
        int nextIndex = currentIndex + 1;
        int limit = Math.min(values.size(), values.indexOf(end));
        if(hasNotExceededLimit(nextIndex, limit)){
            return state.setValue(property, values.get(nextIndex));
        }
        return state;
    }

    private static boolean hasNotExceededLimit(int index, int limit){
        return index <= limit;
    }

    @Override
    protected abstract void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder);
}
