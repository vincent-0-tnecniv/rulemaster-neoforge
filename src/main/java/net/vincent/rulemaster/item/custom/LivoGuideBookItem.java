package net.vincent.rulemaster.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.WrittenBookContent;
import net.vincent.rulemaster.item.ModItems;

import java.util.List;

public class LivoGuideBookItem extends WrittenBookItem {

    private static final String BOOK_TITLE = "Livo's Bane";
    private static final String BOOK_AUTHOR = "The Defeated Adventurer";
    private static final WrittenBookContent CONTENT =
            new WrittenBookContent(Filterable.passThrough(BOOK_TITLE), BOOK_AUTHOR, 3, List.of(
                    Filterable.passThrough(
                            Component.literal(
                                    """
                                            §l§6RuleMaster BOSS: Livo§r
                                            """
                            ).append(
                                    createPageLinkWithoutHover("Lore\n", 2, true)
                            ).append(
                                    createPageLinkWithoutHover("How to find\n", 6, true)
                            )
                    ),
                    Filterable.passThrough(
                            Component.literal(
                                    "§lLORE\n"
                            ).append(
                                    Component.literal(
                                            """
                                            Scattered Blood Crystals are the source of all life for humanity and a few allied species. A select number of these beings carry a crystal within them, granting the sacred power to create new life — a power that ravages the body.
                                            """)
                            )
                    ),
                    Filterable.passThrough(
                            Component.literal(
                                    """
                                    To bear this burden for her children, the entity Livo was created. She gathered the collective energy of every Blood Crystal into herself, using the Totem of Reborn to forge humanity. As the crystals’ unstable power grew, she forged the Blood Crystal Staff to
                                    """
                            )
                    ),
                    Filterable.passThrough(
                            Component.literal(
                                    """
                                   contain it, but was ultimately consumed. Now, Livo unleashes that contained energy upon the world, targeting adventurers and crystal-bearers alike with devastating force.
                                   
                                   Generations of heroes have fallen before her, their efforts utterly futile.
                                   """)
                    ),
                    Filterable.passThrough(
                            Component.literal(
                                    """
                                   Her legend is now one of despair, and no new challengers dare rise to face the vessel of life’s own crushing power.
                                   """)
                    ),
                    Filterable.passThrough(
                            Component.literal(
                                    "§lHOW TO FIND\n"
                            ).append(
                                    Component.literal("""
                                            There are two ways to find Livo.
                                            
                                            1. Find Livo using an Eye of Birth
                                            2. Trade with Cartographers to get a Map of Life
                                            """
                                    )
                            )
                    )
            ), true);

    public LivoGuideBookItem(Properties properties) {
        super(properties.component(DataComponents.WRITTEN_BOOK_CONTENT, CONTENT));
    }

    public static boolean shouldUpdateBook(ItemStack stack) {
        return stack.get(DataComponents.WRITTEN_BOOK_CONTENT) == CONTENT;
    }

    public static ItemStack updatedBook(ItemStack stack) {
        stack.set(DataComponents.WRITTEN_BOOK_CONTENT, CONTENT);
        return stack;
    }

    public static ItemStack create() {
        ItemStack book = new ItemStack(ModItems.LIVO_GUIDE_BOOK.get());

        book.set(DataComponents.WRITTEN_BOOK_CONTENT, CONTENT);

        return book;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return itemStack.isEnchanted();
    }

    private static Component createPageLinkWithHover(String text, int page, ChatFormatting color, boolean underlined) {
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

    private static Component createPageLinkWithHover(String text, int page, boolean underlined) {
        return Component.literal(text)
                .setStyle(Style.EMPTY
                        .withUnderlined(underlined)
                        .withClickEvent(new ClickEvent.ChangePage(page))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Component.literal("§7Go to page " + page)
                        ))
                );
    }

    private static Component createPageLinkWithoutHover(String text, int page, ChatFormatting color, boolean underlined) {
        return Component.literal(text)
                .setStyle(Style.EMPTY
                        .withColor(color)
                        .withUnderlined(underlined)
                        .withClickEvent(new ClickEvent.ChangePage(page))
                );
    }

    private static Component createPageLinkWithoutHover(String text, int page, boolean underlined) {
        return Component.literal(text)
                .setStyle(Style.EMPTY
                        .withUnderlined(underlined)
                        .withClickEvent(new ClickEvent.ChangePage(page))
                );
    }


}
