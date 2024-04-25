package io.github.diiiaz.colored_nametags.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.Objects;

@Mixin(NameTagItem.class)
public class NameTagItemMixin extends Item implements DyeableItem {

    public NameTagItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!(entity instanceof PlayerEntity)) {
            if (!user.getWorld().isClient && entity.isAlive()) {

                entity.setCustomName(getNewName(stack, entity));

                if (entity instanceof MobEntity) {
                    ((MobEntity) entity).setPersistent();
                }
                stack.decrement(1);
            }
            cir.setReturnValue(ActionResult.success(user.getWorld().isClient));
            cir.cancel();
            return;
        }
        cir.setReturnValue(ActionResult.PASS);
        cir.cancel();
    }

    @Unique
    private static final Formatting DEFAULT_FORMATTING_COLOR = Formatting.WHITE;

    @Unique
    private MutableText getNewName(ItemStack stack, LivingEntity entity) {
        DyeableItem dyeableItem;
        Item item = stack.getItem();
        dyeableItem = (DyeableItem) ((Object) item);
        Style style = Style.EMPTY.withColor(DEFAULT_FORMATTING_COLOR);
        if (dyeableItem.hasColor(stack)) {
            int decimalColor = dyeableItem.getColor(stack);
            style = Style.EMPTY.withColor(decimalToRGB(decimalColor));
        }
        String nameText = stack.hasCustomName() ? stack.getName().getString() : entity.hasCustomName() ? Objects.requireNonNull(entity.getCustomName()).getString() : entity.getName().getString();
        return Text.literal(nameText).setStyle(style);
    }

    // we do it like that because the method getRGB() from the Color class return a rgb value with alpha,
    // and we don't want alpha set on the custom name, so it has to be homemade. (unless there is already a method for this conversion ouch)
    @Unique
    int decimalToRGB(int decimal) {
        Color color = new Color(decimal);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return composeRGB(red, green, blue);

    }

    @Unique
    int composeRGB(int red, int green, int blue) {
        red = MathHelper.clamp(red, 0, 255);
        green = MathHelper.clamp(green, 0, 255);
        blue = MathHelper.clamp(blue, 0, 255);
        return (red << 16) | (green << 8) | blue;
    }

}