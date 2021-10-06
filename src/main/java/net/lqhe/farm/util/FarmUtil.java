package net.lqhe.farm.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class FarmUtil {
    static MinecraftClient client = MinecraftClient.getInstance();
    static ClientPlayerInteractionManager manager = client.interactionManager;

    public static void areaReplace(){

        // double distance = manager.getReachDistance();
        double distance = 1.5D;

        for (double x = -distance; x <= distance; x++) {
            for (double z = -distance; z <= distance; z++) {
                Vec3d targetPos = client.player.getPos().add(x,1-0.9375,z);
                BlockHitResult result = new BlockHitResult(
                        targetPos,
                        Direction.UP,
                        new BlockPos(targetPos),
                        false
                );
                net.lqhe.farm.AutoFarm.registerTask(() -> singleReplace(result) );
            }
        }

    }

    public static void singleReplace(BlockHitResult result){
        BlockState state = client.world.getBlockState(new BlockPos(result.getPos()));
        Block target =state.getBlock();
        if (target instanceof CropBlock){
            if (state.get(((CropBlock) target).getAgeProperty()) == ((CropBlock) target).getMaxAge()) {
                manager.attackBlock(new BlockPos(result.getPos()), Direction.UP);
                manager.interactBlock(client.player,client.world, Hand.MAIN_HAND, result);
            }
        }
    }
}
