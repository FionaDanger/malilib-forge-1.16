package fi.dy.masa.malilib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import fi.dy.masa.malilib.event.RenderEventHandler;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractParentElement
{
    @Inject(method = "renderTooltip(Lnet/minecraft/item/ItemStack;II)V", at = @At("RETURN"))
    private void onRenderTooltip(ItemStack stack, int x, int y, CallbackInfo ci)
    {
        ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderTooltipLast(stack, x, y);
    }
}
