package net.vincent.rulemaster.datagen.villager.datapack;

import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.functions.*;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.tags.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {


    public static final ResourceKey<VillagerTrade> CLERIC_1_ROTTEN_FLESH_BLOOD_CRYSTAL = createKey("cleric/1/rotten_flesh_blood_crystal");
    public static final ResourceKey<VillagerTrade> CLERIC_2_EMERALD_BLOOD_CRYSTAL = createKey("cleric/2/emerald_blood_crystal");
    public static final ResourceKey<VillagerTrade> LIBRARIAN_3_EMERALD_BLOOD_CRYSTAL_LIVO_GUIDE_BOOK = createKey("librarian/3/emerald_blood_crystal_livo_guide_book");
    public static final ResourceKey<VillagerTrade> CARTOGRAPHER_1_BLOOD_CRYSTAL_AND_COMPASS_CRADLE_OF_LIFE_MAP = createKey("cartographer/2/blood_crystal_and_compass_cradle_of_life_map");
    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_AND_BLOOD_CRYSTAL_CRADLE_OF_LIFE_MAP = createKey("wandering_trader/emerald_cradle_of_life_map");

    public static void bootstrap(BootstrapContext<VillagerTrade> context){

        HolderGetter<Item> items = context.lookup(Registries.ITEM);
//        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);

        context.register(CLERIC_1_ROTTEN_FLESH_BLOOD_CRYSTAL, new VillagerTrade(
                new TradeCost(Items.ROTTEN_FLESH, 20),
                new ItemStackTemplate(ModItems.BLOOD_CRYSTAL, 1),
                12, 2, 0.05f, Optional.empty(), List.of()
        ));
        context.register(CLERIC_2_EMERALD_BLOOD_CRYSTAL, new VillagerTrade(
                new TradeCost(Items.EMERALD, 2),
                new ItemStackTemplate(ModItems.BLOOD_CRYSTAL, 4),
                12, 1, 0.05f, Optional.empty(), List.of()
        ));
        context.register(LIBRARIAN_3_EMERALD_BLOOD_CRYSTAL_LIVO_GUIDE_BOOK, new VillagerTrade(
                new TradeCost(Items.EMERALD, 40), Optional.of(new TradeCost(ModItems.BLOOD_CRYSTAL, 4)),
                new ItemStackTemplate(ModItems.LIVO_GUIDE_BOOK.get(), 1),
                1, 6, 0.05f, Optional.empty(), List.of()
        ));
//        context.register(LIBRARIAN_1_AZURITE_ENCHANTED, new VillagerTrade(
//                new TradeCost(ModItems.AZURITE, 32),
//                new ItemStackTemplate(Items.ENCHANTED_BOOK),
//                12, 6, 0.05f, Optional.empty(),
//                enchantedBook(context, Enchantments.MENDING)
//        ));
        context.register(CARTOGRAPHER_1_BLOOD_CRYSTAL_AND_COMPASS_CRADLE_OF_LIFE_MAP,
                createExplorerMapTrades(ModItems.BLOOD_CRYSTAL.get(), 8, Items.COMPASS, 1, items, ModTags.Structures.EYE_OF_BIRTH_LOCATED, MapDecorationTypes.RED_BANNER, "cradle_of_life"));

        context.register(WANDERING_TRADER_EMERALD_AND_BLOOD_CRYSTAL_CRADLE_OF_LIFE_MAP,
                createExplorerMapTrades(Items.EMERALD, 8, ModItems.BLOOD_CRYSTAL.get(), 8, items, ModTags.Structures.EYE_OF_BIRTH_LOCATED, MapDecorationTypes.RED_BANNER, "cradle_of_life"));
    }

    private static ResourceKey<VillagerTrade> createKey(String name){
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
    }

    private static VillagerTrade createExplorerMapTrades(Item item1, int item1Count, Item item2, int item2Count, HolderGetter<Item> items, TagKey<Structure> structureTagKey, Holder<MapDecorationType> mapDecorationType, String translationKey) {
        return new VillagerTrade(new TradeCost(item1, item1Count), Optional.of(new TradeCost(item2, item2Count)), new ItemStackTemplate(Items.MAP), 12, 5, 0.2F, Optional.empty(), List.of(ExplorationMapFunction.makeExplorationMap().setDestination(structureTagKey).setMapDecoration(mapDecorationType).setSearchRadius(100).setSkipKnownStructures(true).build(), SetNameFunction.setName(Component.translatable("filled_map." + translationKey), SetNameFunction.Target.ITEM_NAME).build(), FilteredFunction.filtered((new ItemPredicate.Builder()).of(items, Items.FILLED_MAP).withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build()).build()).onFail(Optional.of(DiscardItem.discardItem().build())).build()));
    }

    private static VillagerTrade createBasicExplorerMapTrades(HolderGetter<Item> items, TagKey<Structure> structureTagKey, Holder<MapDecorationType> mapDecorationType, String translationKey) {
        return createExplorerMapTrades(Items.EMERALD, 8, Items.COMPASS, 1, items, structureTagKey, mapDecorationType, translationKey);
    }

    protected static List<LootItemFunction> enchantedBook(BootstrapContext<VillagerTrade> context, ResourceKey<Enchantment> enchantment) {
        return VillagerTrades.enchantedBook(context.lookup(Registries.ITEM), HolderSet.direct(context.lookup(Registries.ENCHANTMENT).getOrThrow(enchantment)));
    }

    @SafeVarargs
    protected static List<LootItemFunction> enchantedBook(BootstrapContext<VillagerTrade> context, ResourceKey<Enchantment>... enchantments) {
        var enchantmentsList = context.lookup(Registries.ENCHANTMENT);
        List<Holder<Enchantment>> holder = new ArrayList<>();
        for(ResourceKey<Enchantment> enchantment : enchantments) {
            holder.add(enchantmentsList.getOrThrow(enchantment));
        }
        return VillagerTrades.enchantedBook(context.lookup(Registries.ITEM), HolderSet.direct(holder));
    }
}
