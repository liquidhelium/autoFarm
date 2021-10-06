package net.lqhe.farm.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class FarmUtil {
    public static void areaReplace(){
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerInteractionManager manager = client.interactionManager;

        double distance = manager.getReachDistance();

        for (double x = -distance; x <= distance; x++) {
            for (double z = -distance; z <= distance; z++) {
                Vec3d targetPos = client.player.getPos().add(x,1-0.9375,z);
                BlockState state = client.world.getBlockState(new BlockPos(targetPos));
                Block target =state.getBlock();
                if (target instanceof CropBlock){
                    if (state.get(((CropBlock) target).getAgeProperty()) == ((CropBlock) target).getMaxAge()) {
                        manager.attackBlock(new BlockPos(targetPos), Direction.UP);
                        BlockHitResult hitResult = new BlockHitResult(
                                targetPos,
                                Direction.UP,
                                new BlockPos(targetPos),
                                false
                        );
                        manager.interactBlock(client.player,client.world, Hand.MAIN_HAND,hitResult);
                    }
                }
            }
        }

    }


}
