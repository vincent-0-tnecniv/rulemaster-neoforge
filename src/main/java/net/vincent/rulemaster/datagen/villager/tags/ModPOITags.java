package net.vincent.rulemaster.datagen.villager.tags;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.vincent.rulemaster.RuleMaster;

import java.util.concurrent.CompletableFuture;

public class ModPOITags extends PoiTypeTagsProvider {

    public ModPOITags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RuleMaster.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
//        getOrCreateRawBuilder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
//                .add(convert(ModVillagers.KAUPEN_POI));
    }

    protected TagEntry convert(Holder<PoiType> profession) {
        return TagEntry.element(profession.unwrapKey().get().identifier());
    }
}
