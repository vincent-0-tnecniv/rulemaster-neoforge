package net.vincent.rulemaster.datagen.villager.datapack;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {


    public static final ResourceKey<VillagerTrade> CLERIC_1_ROTTEN_FLESH_BLOOD_CRYSTAL = createKey("cleric/1/rotten_flesh_blood_crystal");
    public static final ResourceKey<VillagerTrade> CLERIC_2_EMERALD_BLOOD_CRYSTAL = createKey("cleric/2/emerald_blood_crystal");
    public static final ResourceKey<VillagerTrade> LIBRARIAN_3_EMERALD_BLOOD_CRYSTAL_LIVO_GUIDE_BOOK = createKey("librarian/3/emerald_blood_crystal_livo_guide_book");

    public static void bootstrap(BootstrapContext<VillagerTrade> context){

        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);

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
    }

    private static ResourceKey<VillagerTrade> createKey(String name){
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
    }

    protected static List<LootItemFunction> enchantedBook(BootstrapContext<VillagerTrade> context, ResourceKey<Enchantment> enchantment) {
        return VillagerTrades.enchantedBook(context.lookup(Registries.ITEM), HolderSet.direct(context.lookup(Registries.ENCHANTMENT).getOrThrow(enchantment)));
    }

    protected static List<LootItemFunction> enchantedBook(BootstrapContext<VillagerTrade> context, ResourceKey<Enchantment>... enchantments) {
        var enchantmentsList = context.lookup(Registries.ENCHANTMENT);
        List<Holder<Enchantment>> holder = new ArrayList<>();
        for(ResourceKey<Enchantment> enchantment : enchantments) {
            holder.add(enchantmentsList.getOrThrow(enchantment));
        }
        return VillagerTrades.enchantedBook(context.lookup(Registries.ITEM), HolderSet.direct(holder));
    }
}
