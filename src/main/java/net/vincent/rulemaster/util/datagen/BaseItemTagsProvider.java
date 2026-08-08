package net.vincent.rulemaster.util.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredItem;
import net.vincent.rulemaster.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BaseItemTagsProvider extends ItemTagsProvider {
    public BaseItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider, modId);
    }

    protected void addToTag(TagKey<Item> tag, Item item) {
        ResourceKey<Item> key = ModItems.getRK(item);
        tag(tag).add(key);
    }

    protected void addToTag(TagKey<Item> tag, DeferredItem<Item> item) {
        tag(tag).add(item.getKey());
    }

    protected void addToTag(TagKey<Item> tag, List<Item> items) {
        for(Item item : items) {
            addToTag(tag, item);
        }
    }

    protected void helmet(Item item, boolean trimmable){
        if(trimmable){
            addToTag(ItemTags.HEAD_ARMOR, item);
        } else{
            addToTag(ItemTags.HEAD_ARMOR_ENCHANTABLE, item);
        }
    }

    protected void helmet(DeferredItem<Item> item, boolean trimmable){
        helmet(item.get(), trimmable);
    }

    protected void chestplate(Item item, boolean trimmable){
        if(trimmable){
            addToTag(ItemTags.CHEST_ARMOR, item);
        } else{
            addToTag(ItemTags.CHEST_ARMOR_ENCHANTABLE, item);
        }
    }

    protected void chestplate(DeferredItem<Item> item, boolean trimmable){
        chestplate(item.get(), trimmable);
    }

    protected void leggings(Item item, boolean trimmable){
        if(trimmable){
            addToTag(ItemTags.LEG_ARMOR, item);
        } else{
            addToTag(ItemTags.LEG_ARMOR_ENCHANTABLE, item);
        }
    }

    protected void leggings(DeferredItem<Item> item, boolean trimmable){
        leggings(item.get(), trimmable);
    }

    protected void boots(Item item, boolean trimmable){
        if(trimmable){
            addToTag(ItemTags.FOOT_ARMOR, item);
        } else{
            addToTag(ItemTags.FOOT_ARMOR_ENCHANTABLE, item);
        }
    }

    protected void boots(DeferredItem<Item> item, boolean trimmable){
        boots(item.get(), trimmable);
    }

    protected void allArmor(Item helmetItem, Item chestplateItem, Item leggingsItem, Item bootsItem, boolean trimmable) {
        helmet(helmetItem, trimmable);
        chestplate(chestplateItem, trimmable);
        leggings(leggingsItem, trimmable);
        boots(bootsItem, trimmable);
    }

    protected void allArmor(DeferredItem<Item> helmetItem, DeferredItem<Item> chestplateItem, DeferredItem<Item> leggingsItem, DeferredItem<Item> bootsItem, boolean trimmable) {
        helmet(helmetItem, trimmable);
        chestplate(chestplateItem, trimmable);
        leggings(leggingsItem, trimmable);
        boots(bootsItem, trimmable);
    }

    protected void spear(DeferredItem<Item> item) {
        spear(item.get());
    }

    protected void spear(Item item){
        addToTag(ItemTags.SPEARS, item);
    }

    protected void spear(List<?> items) {
        for(Object obj : items) {
            if(obj instanceof Item item) {
                spear(item);
            } else {
                try{
                    DeferredItem<Item> deferredItem = (DeferredItem<Item>) obj;
                    spear(deferredItem.get());
                } catch(ClassCastException e) {
                    throw new ClassCastException("Cannot cast " + obj + " to DeferredItem<Item>");
                }

            }
        }
    }

    protected void pickaxe(DeferredItem<Item> item) {
        addToTag(ItemTags.PICKAXES, item);
    }

    protected void pickaxe(Item item){
        addToTag(ItemTags.PICKAXES, item);
    }

    protected void pickaxe(List<?> items) {
        for(Object obj : items) {
            if(obj instanceof Item item) {
                pickaxe(item);
            } else {
                try{
                    DeferredItem<Item> deferredItem = (DeferredItem<Item>) obj;
                    tag(ItemTags.PICKAXES).add(ModItems.getRK(deferredItem.get()));
                } catch(ClassCastException e) {
                    throw new ClassCastException("Cannot cast " + obj + " to DeferredItem<Item>");
                }
            }
        }
    }

    protected void sword(DeferredItem<Item> item) {
        addToTag(ItemTags.SWORDS, item);
    }

    protected void sword(Item item){
        addToTag(ItemTags.SWORDS, item);
    }

    protected void sword(List<?> items) {
        for(Object obj : items) {
            if(obj instanceof Item item) {
                sword(item);
            } else {
                try{
                    DeferredItem<Item> deferredItem = (DeferredItem<Item>) obj;
                    tag(ItemTags.SWORDS).add(ModItems.getRK(deferredItem.get()));
                } catch(ClassCastException e) {
                    throw new ClassCastException("Cannot cast " + obj + " to DeferredItem<Item>");
                }
            }
        }
    }

    protected void axe(DeferredItem<Item> item) {
        addToTag(ItemTags.AXES, item);
    }

    protected void axe(Item item){
        addToTag(ItemTags.AXES, item);
    }

    protected void axe(List<?> items) {
        for(Object obj : items) {
            if(obj instanceof Item item) {
                axe(item);
            } else {
                try{
                    DeferredItem<Item> deferredItem = (DeferredItem<Item>) obj;
                    tag(ItemTags.AXES).add(ModItems.getRK(deferredItem.get()));
                } catch(ClassCastException e) {
                    throw new ClassCastException("Cannot cast " + obj + " to DeferredItem<Item>");
                }
            }
        }
    }

    protected void shovel(DeferredItem<Item> item) {
        addToTag(ItemTags.SHOVELS, item);
    }

    protected void shovel(Item item){
        addToTag(ItemTags.SHOVELS, item);
    }

    protected void shovel(List<?> items) {
        for(Object obj : items) {
            if(obj instanceof Item item) {
                shovel(item);
            } else {
                try{
                    DeferredItem<Item> deferredItem = (DeferredItem<Item>) obj;
                    tag(ItemTags.SHOVELS).add(ModItems.getRK(deferredItem.get()));
                } catch(ClassCastException e) {
                    throw new ClassCastException("Cannot cast " + obj + " to DeferredItem<Item>");
                }
            }
        }
    }

    protected void hoe(DeferredItem<Item> item) {
        addToTag(ItemTags.HOES, item);
    }

    protected void hoe(Item item){
        addToTag(ItemTags.HOES, item);
    }

    protected void hoe(List<?> items) {
        for(Object obj : items) {
            if(obj instanceof Item item) {
                hoe(item);
            } else {
                try{
                    DeferredItem<Item> deferredItem = (DeferredItem<Item>) obj;
                    tag(ItemTags.HOES).add(ModItems.getRK(deferredItem.get()));
                } catch(ClassCastException e) {
                    throw new ClassCastException("Cannot cast " + obj + " to DeferredItem<Item>");
                }
            }
        }
    }

    protected void allTools(Item swordItem, Item pickaxeItem, Item axeItem,
                       Item shovelItem, Item hoeItem, Item spearItem){
        sword(swordItem);
        pickaxe(pickaxeItem);
        axe(axeItem);
        shovel(shovelItem);
        hoe(hoeItem);
        spear(spearItem);
    }

    protected void allTools(DeferredItem<Item> swordItem, DeferredItem<Item> pickaxeItem, DeferredItem<Item> axeItem,
                       DeferredItem<Item> shovelItem, DeferredItem<Item> hoeItem, DeferredItem<Item> spearItem){
        allTools(swordItem.get(), pickaxeItem.get(), axeItem.get(), shovelItem.get(), hoeItem.get(), spearItem.get());
    }
}
