package net.vincent.rulemaster.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.WrittenBookContent;
import net.vincent.rulemaster.item.ModItems;

import java.util.List;

public class LivoGuideBookItem extends WrittenBookItem {

    private static final String BOOK_TITLE = "Livo's Bane";
    private static final String BOOK_AUTHOR = "The Defeated Adventurer";

    public LivoGuideBookItem(Properties properties) {
        super(properties.component(DataComponents.WRITTEN_BOOK_CONTENT, createPages()));
    }

    public static WrittenBookContent createPages() {
        final Filterable<Component> P1 = Filterable.passThrough(
                Component.literal(
                        "String goes here"
                )
        );
        final Filterable<Component> P2 = Filterable.passThrough(
                Component.literal(
                        "String goes here"
                )
        );
        final Filterable<Component> P3 = Filterable.passThrough(
                Component.literal(
                        "String goes here"
                )
        );
        return new WrittenBookContent(Filterable.passThrough(BOOK_TITLE), BOOK_AUTHOR, 3, List.of(P1, P2, P3), true);
    }

    public static ItemStack create() {
        ItemStack book = new ItemStack(ModItems.LIVO_GUIDE_BOOK.get());

        book.set(DataComponents.WRITTEN_BOOK_CONTENT, createPages());

        return book;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return itemStack.isEnchanted();
    }
}
