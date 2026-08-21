package net.vincent.rulemaster.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.item.custom.BloodPiercerItem;
import net.vincent.rulemaster.item.custom.FleshBlockTestItem;
import net.vincent.rulemaster.item.custom.LocatorEyeItem;
import net.vincent.rulemaster.item.custom.written_books.LivoGuideBookItem;
import net.vincent.rulemaster.tags.ModTags;

import java.util.function.Consumer;

public class ModItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(RuleMaster.MOD_ID);

    public static DeferredItem<Item> BLOOD_CRYSTAL = ITEMS.registerSimpleItem("blood_crystal");

    public static DeferredItem<Item> BLOOD_PIERCER = ITEMS.registerItem("blood_piercer",
            BloodPiercerItem::new);

    public static DeferredItem<Item> BLOOD_CRYSTAL_STAFF = ITEMS.registerItem("blood_crystal_staff",
            Item::new);
    // replacement with a custom item object needed in the future

    public static DeferredItem<Item> FLESH_BLOCK_TEST_ITEM = ITEMS.registerItem("flesh_block_test_item",
            FleshBlockTestItem::new);

    public static DeferredItem<Item> LIVO_GUIDE_BOOK = ITEMS.registerItem("livo_guide_book",
            LivoGuideBookItem::new);

    public static DeferredItem<Item> EYE_OF_BIRTH = ITEMS.registerItem("eye_of_birth",
            properties -> new LocatorEyeItem(properties, ModTags.Structures.EYE_OF_BIRTH_LOCATED));

    public static DeferredItem<Item> KEY_OF_EMBRYO = ITEMS.registerItem("key_of_embryo",
            properties -> new Item(properties) {
                @Override
                public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                    builder.accept(Component.translatable("item.rulemaster.key_of_embryo.desc"));
                }
            });
    // TODO: add a texture for it

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
        // This is the registry, so all items should be present in the list of the registry
    }

    public static ResourceKey<Item> getRK(DeferredItem<Item> item) {
        return getRK(item.get());
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
