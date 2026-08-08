package net.vincent.rulemaster.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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

public class SetMarkOfCrystalCommand extends CommandHelper {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rulemaster")
                .then(Commands.literal("mark_of_crystal")
                        .then(Commands.literal("set")
                                .then(Commands.argument("target", EntityArgument.entity())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes(SetMarkOfCrystalCommand::setEntityMark)))
                        )));
    }

    private static int setEntityMark(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity target = EntityArgument.getEntity(context, "target");
        Player player = context.getSource().getPlayerOrException();
        if(player.level().isClientSide()) { return -1; }
        int userInput = IntegerArgumentType.getInteger(context, "amount");
        target.setData(ModAttachments.MARK_OF_CRYSTAL, userInput);
        context.getSource().sendSuccess(
                () -> Component.literal(target.getName().getString() + " has been set to have " + userInput + " stacks of Mark Of Crystal"), false);
        return 1;
    }
}
