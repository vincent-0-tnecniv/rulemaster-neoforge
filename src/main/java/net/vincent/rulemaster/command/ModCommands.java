package net.vincent.rulemaster.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.vincent.rulemaster.command.commands.GetMarkOfCrystalCommand;
import net.vincent.rulemaster.command.commands.SetMarkOfCrystalCommand;

public class ModCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        GetMarkOfCrystalCommand.register(dispatcher);
        SetMarkOfCrystalCommand.register(dispatcher);
    }
}
