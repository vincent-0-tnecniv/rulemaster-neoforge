package net.vincent.rulemaster.datagen.villager.datapack;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.vincent.rulemaster.RuleMaster;

import java.util.Optional;

public class ModTradeSets {

//    public static final ResourceKey<TradeSet> KAUPENGER_LEVEL_1 = create("kaupenger/level_1");
//    public static final ResourceKey<TradeSet> KAUPENGER_LEVEL_2 = create("kaupenger/level_2");

    public static void bootstrap(BootstrapContext<TradeSet> context) {
//        register(context, KAUPENGER_LEVEL_1, ModTags.Trades.KAUPENGER_LEVEL_1);
//        register(context, KAUPENGER_LEVEL_2, ModTags.Trades.KAUPENGER_LEVEL_2);
    }

    private static ResourceKey<TradeSet> create(final String id) {
        return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, id));
    }

    public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context,
                                                      final ResourceKey<TradeSet> key, final TagKey<VillagerTrade> tag) {
        return register(context, key, tag, ConstantValue.exactly(2f));
    }

    public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context, final ResourceKey<TradeSet> key,
                                                      final TagKey<VillagerTrade> tag, final NumberProvider numberProvider) {
        return context.register(key, new TradeSet(context.lookup(Registries.VILLAGER_TRADE).getOrThrow(tag),
                numberProvider, false, Optional.of(key.identifier().withPrefix("trade_set/"))));
    }

}
