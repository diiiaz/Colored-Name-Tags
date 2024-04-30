package io.github.diiiaz.colored_nametags;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Mod implements ModInitializer {
	public static final String ID = "colored-nametags";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		Map<Item, CauldronBehavior> map = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();
		map.put(Items.NAME_TAG, CauldronBehavior.CLEAN_DYEABLE_ITEM);
	}
}