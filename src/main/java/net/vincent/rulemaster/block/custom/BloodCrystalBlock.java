package net.vincent.rulemaster.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.tags.ModTags;

import java.util.List;

public class BloodCrystalBlock extends Block {
    public BloodCrystalBlock(Properties properties) {
        super(properties.strength(1.5f).requiresCorrectToolForDrops().randomTicks());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        RuleMaster.LOGGER.info("A Blood Crystal Block is running random ticks");
        if(random.nextInt(1000) == 67){
            // around one in 7 minutes
            AABB boundingBox = new AABB(pos).inflate(3);
            List<Entity> entities = level.getEntities(null, boundingBox);
            for(Entity entity : entities){
                if(!entity.is(ModTags.EntityTypes.LIVING_HUMANOID)){return;}
                RuleMaster.LOGGER.info("Entity " + entity.getName() + " is currently receiving the curse of the blood crystal");
                if(entity instanceof Player player) {
                    player.sendSystemMessage(Component.literal("You were given one stack of mark of crystal! You now have " + (player.getData(ModAttachments.MARK_OF_CRYSTAL) + 1) + " stacks!"));
                }
                entity.setData(ModAttachments.MARK_OF_CRYSTAL, entity.getData(ModAttachments.MARK_OF_CRYSTAL) + 1);
                if(entity.getData(ModAttachments.MARK_OF_CRYSTAL) == 3){
                    if(entity instanceof Player player){
                        player.sendSystemMessage(Component.literal("You have 3 stacks of mark of crystal!"));
                        player.sendSystemMessage(Component.literal("For now, we will clear your marks. Penality will come soon."));
                    }
                    entity.setData(ModAttachments.MARK_OF_CRYSTAL, 0);
                }
            }
        }
        super.randomTick(state, level, pos, random);
    }
}
