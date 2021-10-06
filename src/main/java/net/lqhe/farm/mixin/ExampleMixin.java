package net.lqhe.farm.mixin;

import net.lqhe.farm.util.FarmUtil;
import net.minecraft.block.CropBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ExampleMixin {
	@Shadow
	@Nullable
	public HitResult crosshairTarget;

	@Shadow
	@Nullable
	public ClientWorld world;

	@Inject(at = @At("HEAD"), cancellable = true, method = "doAttack")
	public void onAttack(CallbackInfo ci) {
		if (this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHitResult = (BlockHitResult) this.crosshairTarget;
			if (world.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof CropBlock){
				ci.cancel();
				FarmUtil.areaReplace();
			}
		}
	}
	@Inject(method = "doItemUse", at = @At(value = "HEAD"))
	public void onItemUse(CallbackInfo ci){
		if (this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
			FarmUtil.singleReplace((BlockHitResult) this.crosshairTarget);
		}
	}
}
