package net.vincent.rulemaster.datagen.datapack.worldgen.structures;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.vincent.rulemaster.RuleMaster;

import java.util.List;

public class ModStructureSets {

    public static final ResourceKey<StructureSet> CRADLE_OF_LIFE_SET = registerKey("cradle_of_life");

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structureLookup = context.lookup(Registries.STRUCTURE);

        StructureSet set = new StructureSet(
                List.of(
                        new StructureSet.StructureSelectionEntry(
                                structureLookup.getOrThrow(ModStructures.CRADLE_OF_LIFE),
                                1
                        )
                ),
                new RandomSpreadStructurePlacement(
                        512,
                        256,
                        RandomSpreadType.LINEAR,
                        72208002
                )
        );

        context.register(CRADLE_OF_LIFE_SET, set);
    }

    public static ResourceKey<StructureSet> registerKey(String name) {
        if(name.endsWith("_set")){
            return ResourceKey.create(Registries.STRUCTURE_SET,
                    Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name.replace("_set","")));
        }
        return ResourceKey.create(Registries.STRUCTURE_SET,
                Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
    }
}
