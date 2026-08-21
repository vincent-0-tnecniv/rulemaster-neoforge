package net.vincent.rulemaster.datagen.datapack.worldgen.structures;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.vincent.rulemaster.RuleMaster;

import java.util.List;

public class ModStructureTemplatePools {

    public static final ResourceKey<StructureTemplatePool> CRADLE_OF_LIFE_POOL = registerKey("cradle_of_life");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> poolLookup = context.lookup(Registries.TEMPLATE_POOL);
//        HolderGetter<StructureProcessorList> processorLookup = context.lookup(Registries.PROCESSOR_LIST);

        var emptyPool = poolLookup.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL,
                Identifier.withDefaultNamespace("empty")));

        StructureTemplatePool pool = new StructureTemplatePool(
                emptyPool,
                List.of(
                        new Pair<>(
                                StructurePoolElement.single(
                                        String.valueOf(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "cradle_of_life"))
                                ).apply(StructureTemplatePool.Projection.RIGID),
                                1
                        )
                )
        );

        context.register(CRADLE_OF_LIFE_POOL, pool);
    }

    public static ResourceKey<StructureTemplatePool> registerKey(String name) {
        if(name.endsWith("_pool")) {
            return ResourceKey.create(Registries.TEMPLATE_POOL,
                    Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name.replace("_pool", "")));
        }
        return ResourceKey.create(Registries.TEMPLATE_POOL,
                Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
    }
}
