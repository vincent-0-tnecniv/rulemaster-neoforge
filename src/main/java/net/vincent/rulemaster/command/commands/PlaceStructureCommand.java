package net.vincent.rulemaster.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.vincent.rulemaster.RuleMaster;

public class PlaceStructureCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("place")
                        .then(Commands.literal("structure")
                                .then(Commands.literal("corners")
                                        .executes(context -> {
                                            Player player = context.getSource().getPlayerOrException();
                                            ServerLevel level = (ServerLevel) player.level();
                                            BlockPos center = player.blockPosition();

                                            StructureTemplateManager manager = level.getStructureManager();
                                            StructureTemplate[] templates = {null, null, null, null};
                                            for(int i = 0; i < 4; i++){
                                                templates[i] = manager.get(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "cradle_of_life_corner_" + (i + 1))).orElse(null);
                                                if (templates[i] == null) {
                                                    player.sendSystemMessage(Component.literal("§cStructure not found!"));
                                                    return 0;
                                                }
                                            }

                                            int spacing = 15; // Distance between corners
                                            BlockPos[] corners = {
                                                    center.offset(-spacing, 0,  spacing),
                                                    center.offset( spacing, 0,  spacing),
                                                    center.offset( spacing, 0, -spacing),
                                                    center.offset(-spacing, 0, -spacing)

                                            };



                                            StructurePlaceSettings settings = new StructurePlaceSettings()
                                                    .setIgnoreEntities(false);

                                            for (int i = 0; i < corners.length; i++) {
                                                templates[i].placeInWorld(
                                                        level,
                                                        corners[i],
                                                        corners[i],
                                                        settings,
                                                        level.getRandom(),
                                                        3
                                                );
                                            }

                                            player.sendSystemMessage(Component.literal("§aPlaced structure at all 4 corners!"));
                                            return 1;
                                        })
                                )
                        )
        );
    }
}
