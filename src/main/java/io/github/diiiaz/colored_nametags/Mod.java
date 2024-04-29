package io.github.diiiaz.colored_nametags;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
	public static final String ID = "colored-nametags";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.NAME_TAG, CauldronBehavior.CLEAN_DYEABLE_ITEM);
//		ModRecipeSerializer.registerRecipes();
	}
}