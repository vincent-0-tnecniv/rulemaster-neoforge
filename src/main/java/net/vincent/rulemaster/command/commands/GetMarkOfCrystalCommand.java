package net.vincent.rulemaster.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.util.CommandHelper;

public class GetMarkOfCrystalCommand extends CommandHelper {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("rulemaster").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.literal("mark_of_crystal")
                        .then(Commands.literal("get")
                                .then(Commands.argument("target", EntityArgument.entity())
                                        .executes(GetMarkOfCrystalCommand::getEntityMark)
                                )
                                .executes(GetMarkOfCrystalCommand::getSelfMark)
                        )));
    }

    private static int getEntityMark(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity target = EntityArgument.getEntity(context, "target");
        Player player = context.getSource().getPlayerOrException();
        if(player.level().isClientSide()) { return -1; }
        int markOfCrystal = target.getData(ModAttachments.MARK_OF_CRYSTAL);
        context.getSource().sendSuccess(
                () -> Component.literal(target.getName().getString() + " has " + markOfCrystal + " stacks of Mark Of Crystal"), false);
        return 1;
    }



    private static int getSelfMark(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayerOrException();
        int markOfCrystal = player.getData(ModAttachments.MARK_OF_CRYSTAL);
        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString() + " has " + markOfCrystal + " stacks of Mark Of Crystal"), false);
        return 1;
    }
}
