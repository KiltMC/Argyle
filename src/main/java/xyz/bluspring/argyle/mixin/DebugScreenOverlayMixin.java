package xyz.bluspring.argyle.mixin;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.bluspring.argyle.Argyle;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {
    @Inject(at = @At("RETURN"), method = "getSystemInformation")
    public void kilt$appendModInfo(CallbackInfoReturnable<List<String>> cir) {
        var messages = cir.getReturnValue();

        messages.add("");
        messages.add("Argyle Loader v1.0.0");
        messages.add(Argyle.Companion.getLoader().getQuiltMods().size() + " Quilt mods loaded");
        messages.add("");
    }
}
