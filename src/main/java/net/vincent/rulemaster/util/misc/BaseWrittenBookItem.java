package net.vincent.rulemaster.util.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.WrittenBookContent;
import net.vincent.rulemaster.item.ModItems;
import org.jetbrains.annotations.NotNull;

public abstract class BaseWrittenBookItem extends WrittenBookItem {

    public static WrittenBookContent CONTENT;

    public BaseWrittenBookItem(Properties properties) {
        super(properties.stacksTo(1));
        CONTENT = writtenBookContent();
    }

    public static void update(Player player) {
        Item book = ModItems.LIVO_GUIDE_BOOK.get();
        for(ItemStack item : player.getInventory()){
            if(item.getItem() == book){
                updateBook(item);
            }
        }
    }

    public static boolean shouldUpdateBook(ItemStack stack) {
        return stack.get(DataComponents.WRITTEN_BOOK_CONTENT) == CONTENT;
    }

    private static void updateBook(ItemStack stack) {
        stack.set(DataComponents.WRITTEN_BOOK_CONTENT, CONTENT);
    }

    public static Component createPageLinkWithHover(String text, int page, ChatFormatting color, boolean underlined) {
        return Component.literal(text)
                .setStyle(Style.EMPTY
                        .withColor(color)
                        .withUnderlined(underlined)
                        .withClickEvent(new ClickEvent.ChangePage(page))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Component.literal("§7Go to page " + page)
                        ))
                );
    }

    public static Component createPageLinkWithHover(String text, int page, boolean underlined) {
        return Component.literal(text)
                .setStyle(Style.EMPTY
                        .withUnderlined(underlined)
                        .withClickEvent(new ClickEvent.ChangePage(page))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Component.literal("§7Go to page " + page)
                        ))
                );
    }

    public static Component createPageLinkWithoutHover(String text, int page, ChatFormatting color, boolean underlined) {
        return Component.literal(text)
                .setStyle(Style.EMPTY
                        .withColor(color)
                        .withUnderlined(underlined)
                        .withClickEvent(new ClickEvent.ChangePage(page))
                );
    }

    public static Component createPageLinkWithoutHover(String text, int page, boolean underlined) {
        return Component.literal(text)
                .setStyle(Style.EMPTY
                        .withUnderlined(underlined)
                        .withClickEvent(new ClickEvent.ChangePage(page))
                );
    }

    protected @NotNull
    abstract WrittenBookContent writtenBookContent();
}
