package net.vincent.rulemaster.datagen.structure.structures;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.tags.ModTags;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModStructures {

    public static final ResourceKey<Structure> CRADLE_OF_LIFE = registerKey("cradle_of_life");

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<StructureTemplatePool> poolLookup = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);

        HolderSet<Biome> biomes = biomeLookup.getOrThrow(ModTags.StructureBiomes.CRADLE_OF_LIFE_BIOMES);

        Holder.Reference<StructureTemplatePool> startPool = poolLookup.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL,
                createId("cradle_of_life")));

        JigsawStructure structure = new JigsawStructure(
                new Structure.StructureSettings(
                    biomes,
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_BOX
                ),
                startPool,
                Optional.empty(),
                2,
                ConstantHeight.of(VerticalAnchor.absolute(80)),
                false,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                new JigsawStructure.MaxDistance(80),
                List.of(),
                DimensionPadding.ZERO,
                LiquidSettings.IGNORE_WATERLOGGING
        );

        context.register(CRADLE_OF_LIFE, structure);
    }

    public static Identifier createId(String name) {
        return Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name);
    }

    public static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, createId(name));
    }
}
