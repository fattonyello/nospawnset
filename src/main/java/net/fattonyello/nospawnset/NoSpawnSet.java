package net.fattonyello.nospawnset;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.util.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NoSpawnSet.MOD_ID)
public class NoSpawnSet {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "nospawnset";
    private static final Logger LOGGER = LogUtils.getLogger();
    public NoSpawnSet() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onBedItemSetSpawn(PlayerSetSpawnEvent event) {

        BlockPos blockPos = event.getNewSpawn();
        BlockState blockState = event.getEntity().getCommandSenderWorld().getBlockState(blockPos);
        BedBlock bedBlock = (BedBlock) blockState.getBlock();

        ItemStack bedItemStack = bedBlock.asItem().getDefaultInstance();

        if (bedItemStack.getItem() instanceof BedItem) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {

        if (event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();

            player.experienceProgress = 0F;
            player.experienceLevel = 0;
            player.totalExperience = 0;

        }

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
