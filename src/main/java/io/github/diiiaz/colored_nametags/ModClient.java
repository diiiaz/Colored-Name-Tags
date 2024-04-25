package io.github.diiiaz.colored_nametags;

import net.fabricmc.api.ClientModInitializer;


public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTagColorProvider.register();
    }

}
