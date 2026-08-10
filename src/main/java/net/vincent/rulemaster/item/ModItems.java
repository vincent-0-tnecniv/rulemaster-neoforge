package net.vincent.rulemaster.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.item.custom.BloodPiercerItem;
import net.vincent.rulemaster.item.custom.FleshBlockTestItem;
import net.vincent.rulemaster.item.custom.LivoGuideBookItem;
import net.vincent.rulemaster.item.custom.LocatorEyeItem;
import net.vincent.rulemaster.tags.ModTags;

public class ModItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(RuleMaster.MOD_ID);

    public static DeferredItem<Item> BLOOD_CRYSTAL = ITEMS.registerItem("blood_crystal",
            Item::new);

    public static DeferredItem<Item> BLOOD_PIERCER = ITEMS.registerItem("blood_piercer",
            BloodPiercerItem::new);

    public static DeferredItem<Item> BLOOD_CRYSTAL_STAFF = ITEMS.registerItem("blood_crystal_staff",
            Item::new);

    public static DeferredItem<Item> FLESH_BLOCK_TEST_ITEM = ITEMS.registerItem("flesh_block_test_item",
            FleshBlockTestItem::new);

    public static DeferredItem<Item> LIVO_GUIDE_BOOK = ITEMS.registerItem("livo_guide_book",
            LivoGuideBookItem::new);

    public static DeferredItem<Item> EYE_OF_BIRTH = ITEMS.registerItem("eye_of_birth",
            properties -> new LocatorEyeItem(properties, ModTags.Structures.EYE_OF_BIRTH_LOCATED, ParticleTypes.ANGRY_VILLAGER));

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }

    public static ResourceKey<Item> getRK(DeferredItem<Item> item) {
        return getRK(item.get());
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
