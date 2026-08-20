package net.vincent.rulemaster.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WrittenBookContent;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.util.misc.BaseWrittenBookItem;
import net.vincent.rulemaster.util.written_book_content.ModWrittenBookContents;

public class LivoGuideBookItem extends BaseWrittenBookItem {

    public static final WrittenBookContent CONTENT = ModWrittenBookContents.LivoGuideBook.CONTENT;

    public LivoGuideBookItem(Properties properties) {
        super(properties.component(DataComponents.WRITTEN_BOOK_CONTENT, CONTENT));
    }

    @Override
    protected WrittenBookContent writtenBookContent() {
        return CONTENT;
        // For some reason, a WrittenBookContent is needed to store the hidden content in the class
        // Using ModWrittenBookContents...... would not work here.
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
}
