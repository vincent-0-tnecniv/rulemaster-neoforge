package net.vincent.rulemaster.datagen.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.vincent.rulemaster.RuleMaster;

public class CradleOfLifeLoader {
    private static final Identifier CRADLE_OF_LIFE =
            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "structures/cradle_of_life.nbt");

    public static void placeStructure(ServerLevel level, BlockPos center) {
        StructureTemplateManager manager = level.getStructureManager();
        StructureTemplate template = manager.get(CRADLE_OF_LIFE).orElse(null);

        if(template == null) { return;}

        StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false);

        template.placeInWorld(level, center, center, settings, level.getRandom(), 3);
    }

    public static void placeStructureAtCorners(ServerLevel level, BlockPos center, int spacing) {
        BlockPos[] corners = {
                center.offset(-spacing, 0, -spacing),
                center.offset( spacing, 0, -spacing),
                center.offset(-spacing, 0,  spacing),
                center.offset( spacing, 0,  spacing)
        };

        for (BlockPos corner : corners) {
            placeStructure(level, corner);
        }
    }

    public static void placeStructureAtCornersWithRotation(ServerLevel level, BlockPos center, int spacing) {
        StructureTemplateManager manager = level.getStructureManager();
        StructureTemplate template = manager.get(CRADLE_OF_LIFE).orElse(null);

        if (template == null) return;

        StructurePlaceSettings[] settings = {
                new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                new StructurePlaceSettings().setRotation(Rotation.CLOCKWISE_90).setMirror(Mirror.NONE).setIgnoreEntities(false),
                new StructurePlaceSettings().setRotation(Rotation.CLOCKWISE_180).setMirror(Mirror.NONE).setIgnoreEntities(false),
                new StructurePlaceSettings().setRotation(Rotation.COUNTERCLOCKWISE_90).setMirror(Mirror.NONE).setIgnoreEntities(false)
        };

        BlockPos[] corners = {
                center.offset(-spacing, 0, -spacing),
                center.offset( spacing, 0, -spacing),
                center.offset(-spacing, 0,  spacing),
                center.offset( spacing, 0,  spacing)
        };

        for (int i = 0; i < 4; i++) {
            template.placeInWorld(
                    level,
                    corners[i],
                    corners[i],
                    settings[i],
                    level.getRandom(),
                    3
            );
        }
    }


}
