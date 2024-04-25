package io.github.diiiaz.colored_nametags;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Items;

public class ItemTagColorProvider {

    private static final int DEFAULT_NAME_TAG_ITEM_COLOR = 15124364;

    public static void register() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex > 0) {
                return -1;
            } else {
                DyeableItem dyeableItem = ((DyeableItem) ((Object) stack.getItem()));
                if (dyeableItem.hasColor(stack)) {
                    return ((DyeableItem) ((Object) stack.getItem())).getColor(stack);
                }
                return DEFAULT_NAME_TAG_ITEM_COLOR;
            }
        }, Items.NAME_TAG);
    }

}
