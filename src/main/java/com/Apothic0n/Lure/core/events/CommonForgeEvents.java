package com.Apothic0n.Lure.core.events;

import com.Apothic0n.Lure.Lure;
import com.Apothic0n.Lure.core.LureSavedData;
import com.Apothic0n.Lure.core.api.CreatureSpawnParameters;
import com.Apothic0n.Lure.core.api.MonsterSpawnParameters;
import com.Apothic0n.Lure.core.config.ConfigHandler;
import com.Apothic0n.Lure.core.config.LureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Lure.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {
    static int spawnTick = 20;

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer()) {
            spawnTick--;
            if (spawnTick <= 0) {
                spawnTick = 20;
                Player player = event.player;
                Level level = player.level();
                BlockPos randomPos = new BlockPos((int) (((Math.random() * 128) + (-1 * (Math.random() * 128))) + player.getX()), 0, (int) (((Math.random() * 128) + (-1 * (Math.random() * 128))) + player.getZ()));
                randomPos = new BlockPos(randomPos.getX(), level.getHeight(Heightmap.Types.WORLD_SURFACE, randomPos.getX(), randomPos.getZ()), randomPos.getZ());
                if ((randomPos.getX() - player.getX()) > 24 || (randomPos.getZ() - player.getZ()) > 24 || (randomPos.getY() - player.getY()) > 24) {
                    for (int y = randomPos.getY(); y > level.getMinBuildHeight(); y--) {
                        if (attemptSpawn(level, new BlockPos(randomPos.getX(), y, randomPos.getZ()), false)) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static boolean attemptSpawn(Level level, BlockPos pos, boolean isExtra) {
        boolean mobSpawned = false;
        ResourceKey<Level> dimension = level.dimension();
        BlockState centerState = level.getBlockState(pos);
        BlockState aboveState = level.getBlockState(pos.above());
        if (pos.getY() < level.getMaxBuildHeight()-2 && !centerState.isSolid() && !aboveState.isSolid()) {
            List<Block> neighbors = List.of(
                    level.getBlockState(pos.north()).getBlock(),
                    level.getBlockState(pos.east()).getBlock(),
                    level.getBlockState(pos.south()).getBlock(),
                    level.getBlockState(pos.west()).getBlock()
            );
            BlockState belowState = level.getBlockState(pos.below());
            if ((belowState.isSolid() || belowState.is(Blocks.LAVA)) && !belowState.is(Blocks.BEDROCK)) {
                LureConfig config = ConfigHandler.getConfig();
                if (config == null) return false;
                boolean light = (level.getBrightness(LightLayer.BLOCK, pos) > 0 || (level.getBrightness(LightLayer.SKY, pos) > 0 && level.canSeeSky(pos) && level.isDay()));
                ChunkPos chunkPos = level.getChunkAt(pos).getPos();
                if (light == true && dimension.equals(Level.OVERWORLD) && !LureSavedData.contains((ServerLevel) level, chunkPos)) {//creatures
                    CreatureSpawnParameters creatureSpawnParameters = new CreatureSpawnParameters(EntityType.BAT, List.of(Blocks.STRUCTURE_BLOCK), 4);
                    if (config.creatures.containsKey(belowState.getBlock())) {
                        List<CreatureSpawnParameters> potentialCreatures = config.creatures.get(belowState.getBlock());
                        creatureSpawnParameters = potentialCreatures.get((int)Math.round(Math.random() * (potentialCreatures.size() - 1)));
                    }
                    if (!(!creatureSpawnParameters.entityType().equals(EntityType.TURTLE) && centerState.is(Blocks.WATER)) && matchingBlocks(creatureSpawnParameters.adjacentBlocks(), neighbors, creatureSpawnParameters.amountRequired()) >= creatureSpawnParameters.amountRequired()) {
                        LureSavedData.add((ServerLevel) level, chunkPos);
                        creatureSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                        creatureSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                        mobSpawned = true;
                    }
                } else if (dimension.equals(Level.NETHER) && belowState.is(Blocks.LAVA) && !LureSavedData.contains((ServerLevel) level, chunkPos)) {//nether creatures
                    CreatureSpawnParameters creatureSpawnParameters = new CreatureSpawnParameters(EntityType.STRIDER, List.of(Blocks.AIR), 4);
                    if (matchingBlocks(creatureSpawnParameters.adjacentBlocks(), neighbors, creatureSpawnParameters.amountRequired()) >= creatureSpawnParameters.amountRequired()) {
                        LureSavedData.add((ServerLevel) level, chunkPos);
                        creatureSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                        mobSpawned = true;
                    }
                }
                if (light == false && !belowState.is(Blocks.LAVA)) {//monsters
                    List<MonsterSpawnParameters> potentialMonsters = config.monsters.get(LureConfig.MoonPhase.values()[level.getMoonPhase()]);
                    int firstChoice = (int) Math.round(Math.random() * (potentialMonsters.size()-1));
                    MonsterSpawnParameters monsterSpawnParameters = potentialMonsters.get(firstChoice);
                    Holder<Biome> biome = level.getBiome(pos);
                    if (!biome.is(Biomes.MUSHROOM_FIELDS)) {
                        boolean suitableBiome = false;
                        for (int a = firstChoice; a < potentialMonsters.size(); a++) {
                            if (suitableBiome == false) {
                                monsterSpawnParameters = potentialMonsters.get(a);
                                for (int i = 0; i < monsterSpawnParameters.validBiomes().size(); i++) {
                                    if (biome.is(monsterSpawnParameters.validBiomes().get(i))) {
                                        suitableBiome = true;
                                        break;
                                    }
                                }
                                for (int i = 0; i < monsterSpawnParameters.validBiomeTags().size(); i++) {
                                    if (biome.is(monsterSpawnParameters.validBiomeTags().get(i))) {
                                        suitableBiome = true;
                                        break;
                                    }
                                }
                            }
                        }
                        boolean ghastSpawnable = true;
                        if (monsterSpawnParameters.entityType().equals(EntityType.GHAST)) {
                            ghastSpawnable = level.getBlockStatesIfLoaded(AABB.of(new BoundingBox(pos.north(2).east(2).getX(), pos.getY(), pos.north(2).east(2).getZ(), pos.south(2).west(2).getX(), pos.above(4).getY(), pos.south(2).west(2).getZ()))).allMatch(BlockBehaviour.BlockStateBase::isAir);
                        }
                        if (!(!monsterSpawnParameters.entityType().equals(EntityType.DROWNED) && centerState.is(Blocks.WATER)) && !(monsterSpawnParameters.entityType().equals(EntityType.DROWNED) && !centerState.is(Blocks.WATER)) &&
                                !(monsterSpawnParameters.entityType().equals(EntityType.DROWNED) && !aboveState.is(Blocks.WATER)) && ghastSpawnable == true && suitableBiome == true && matchingBlocks(List.of(Blocks.AIR), neighbors, 4) >= 4) {
                            if (isExtra == false) {
                                attemptSpawn(level, pos.north(), true);
                                attemptSpawn(level, pos.east(), true);
                                attemptSpawn(level, pos.south(), true);
                                attemptSpawn(level, pos.east(), true);
                                attemptSpawn(level, pos.north().east(), true);
                                attemptSpawn(level, pos.north().west(), true);
                                attemptSpawn(level, pos.south().east(), true);
                                attemptSpawn(level, pos.south().west(), true);

                            } else {
                                monsterSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                            }
                            mobSpawned = true;
                        }
                    }
                }
            }
        }
        return mobSpawned;
    }

    public static int matchingBlocks(List<Block> adjacentBlocks, List<Block> neighbors, int maxMatches) {
        int matches = 0;
        for (int a = 0; a < adjacentBlocks.size(); a++) {
            for (int n = 0; n < neighbors.size(); n++) {
                if (adjacentBlocks.get(a).equals(neighbors.get(n)) || !neighbors.get(n).defaultBlockState().isSolid() && adjacentBlocks.contains(Blocks.AIR)) {
                    matches++;
                    if (matches == maxMatches) {
                        return matches;
                    }
                }
            }
        }
        return matches;
    }
}