package com.Apothic0n.Lure.core.events;

import com.Apothic0n.Lure.Lure;
import com.Apothic0n.Lure.core.api.CreatureSpawnParameters;
import com.Apothic0n.Lure.core.api.MonsterSpawnParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Lure.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {

    static List<Map<Block, List<CreatureSpawnParameters>>> creatures = List.of(
            Map.of(Blocks.MUD, List.of(
                    new CreatureSpawnParameters(EntityType.PIG, List.of(Blocks.CARROTS), 1),
                    new CreatureSpawnParameters(EntityType.FROG, List.of(Blocks.MANGROVE_ROOTS), 1)
            )),
            Map.of(Blocks.GRASS_BLOCK, List.of(
                    new CreatureSpawnParameters(EntityType.SHEEP, List.of(Blocks.WHEAT), 1),
                    new CreatureSpawnParameters(EntityType.CHICKEN, List.of(Blocks.GRASS, Blocks.TALL_GRASS), 4),
                    new CreatureSpawnParameters(EntityType.RABBIT, List.of(Blocks.CARROTS), 1),
                    new CreatureSpawnParameters(EntityType.OCELOT, List.of(Blocks.JUNGLE_LEAVES), 2),
                    new CreatureSpawnParameters(EntityType.HORSE, List.of(Blocks.HAY_BLOCK), 1)
            )),
            Map.of(Blocks.MYCELIUM, List.of(
                    new CreatureSpawnParameters(EntityType.MOOSHROOM, List.of(Blocks.WHEAT), 1)
            )),
            Map.of(Blocks.PACKED_MUD, List.of(
                    new CreatureSpawnParameters(EntityType.COW, List.of(Blocks.WHEAT), 1),
                    new CreatureSpawnParameters(EntityType.DONKEY, List.of(Blocks.HAY_BLOCK), 1)
            )),
            Map.of(Blocks.PODZOL, List.of(
                    new CreatureSpawnParameters(EntityType.FOX, List.of(Blocks.SWEET_BERRY_BUSH), 1),
                    new CreatureSpawnParameters(EntityType.PANDA, List.of(Blocks.BAMBOO), 1),
                    new CreatureSpawnParameters(EntityType.WOLF, List.of(Blocks.FERN), 1)
            )),
            Map.of(Blocks.SNOW_BLOCK, List.of(
                    new CreatureSpawnParameters(EntityType.POLAR_BEAR, List.of(Blocks.AIR), 4),
                    new CreatureSpawnParameters(EntityType.RABBIT, List.of(Blocks.CARROTS), 1)
            )),
            Map.of(Blocks.ICE, List.of(
                    new CreatureSpawnParameters(EntityType.POLAR_BEAR, List.of(Blocks.AIR), 4)
            )),
            Map.of(Blocks.SAND, List.of(
                    new CreatureSpawnParameters(EntityType.RABBIT, List.of(Blocks.CARROTS), 1)
            )),
            Map.of(Blocks.JUNGLE_LEAVES, List.of(
                    new CreatureSpawnParameters(EntityType.PARROT, List.of(Blocks.AIR), 4)
            )),
            Map.of(Blocks.STONE, List.of(
                    new CreatureSpawnParameters(EntityType.GOAT, List.of(Blocks.WHEAT), 4)
            ))
    );
    static List<List<MonsterSpawnParameters>> monsters = List.of(
            List.of( //Full Moon
                    new MonsterSpawnParameters(EntityType.ZOMBIE, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIE_VILLAGER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.HUSK, List.of(), List.of(Biomes.DESERT)),
                    new MonsterSpawnParameters(EntityType.SPIDER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(BiomeTags.IS_OVERWORLD), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.STRAY, List.of(), List.of(Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES, Biomes.FROZEN_RIVER)),
                    new MonsterSpawnParameters(EntityType.CREEPER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_OVERWORLD, BiomeTags.IS_NETHER, BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.WITCH, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            List.of(
                    new MonsterSpawnParameters(EntityType.ZOMBIE, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIE_VILLAGER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.HUSK, List.of(), List.of(Biomes.DESERT)),
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            List.of(
                    new MonsterSpawnParameters(EntityType.SPIDER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(BiomeTags.IS_OVERWORLD), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.STRAY, List.of(), List.of(Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES, Biomes.FROZEN_RIVER)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.CREEPER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            List.of(
                    new MonsterSpawnParameters(EntityType.ZOMBIE, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIE_VILLAGER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.HUSK, List.of(), List.of(Biomes.DESERT)),
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_OVERWORLD, BiomeTags.IS_NETHER, BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.WITCH, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            )
    );

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
                        if (attemptSpawn(level, new BlockPos(randomPos.getX(), y, randomPos.getZ()))) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static boolean attemptSpawn(Level level, BlockPos pos) {
        boolean mobSpawned = false;
        BlockState centerState = level.getBlockState(pos);
        BlockState aboveState = level.getBlockState(pos.above());
        if (!centerState.isSolid() && !aboveState.isSolid()) {
            List<Block> neighbors = List.of(
                    level.getBlockState(pos.north()).getBlock(),
                    level.getBlockState(pos.east()).getBlock(),
                    level.getBlockState(pos.south()).getBlock(),
                    level.getBlockState(pos.west()).getBlock()
            );
            BlockState belowState = level.getBlockState(pos.below());
            if (belowState.isSolid() || belowState.is(Blocks.LAVA)) {
                int light = level.getBrightness(LightLayer.SKY, pos.below()) + level.getBrightness(LightLayer.BLOCK, pos.below());
                if (light > 0 && level.dimensionType().natural()) {//creatures
                    CreatureSpawnParameters creatureSpawnParameters = new CreatureSpawnParameters(EntityType.BAT, List.of(Blocks.STRUCTURE_BLOCK), 4);
                    for (int i = 0; i < creatures.size(); i++) {
                        List<CreatureSpawnParameters> potentialCreatures = creatures.get(i).get(belowState.getBlock());
                        if (potentialCreatures != null) {
                            i = creatures.size();
                            creatureSpawnParameters = potentialCreatures.get((int) Math.round(Math.random() * (potentialCreatures.size()-1)));
                        }
                    }
                    if (matchingBlocks(creatureSpawnParameters.adjacentBlocks(), neighbors, creatureSpawnParameters.amountRequired()) >= creatureSpawnParameters.amountRequired()) {
                        creatureSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                        creatureSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                        mobSpawned = true;
                    }
                } else if (level.dimensionType().ultraWarm() && belowState.is(Blocks.LAVA)) {//nether creatures
                    CreatureSpawnParameters creatureSpawnParameters = new CreatureSpawnParameters(EntityType.STRIDER, List.of(Blocks.AIR), 4);
                    if (matchingBlocks(creatureSpawnParameters.adjacentBlocks(), neighbors, creatureSpawnParameters.amountRequired()) >= creatureSpawnParameters.amountRequired()) {
                        creatureSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                        mobSpawned = true;
                    }
                } else if (light == 0 && !belowState.is(Blocks.LAVA)) {//monsters
                    List<MonsterSpawnParameters> potentialMonsters = monsters.get(level.getMoonPhase());
                    MonsterSpawnParameters monsterSpawnParameters = potentialMonsters.get((int) Math.round(Math.random() * (potentialMonsters.size()-1)));
                    Holder<Biome> biome = level.getBiome(pos);
                    boolean suitableBiome = false;
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
                    if (suitableBiome == true && matchingBlocks(List.of(Blocks.AIR), neighbors, 4) >= 4) {
                        monsterSpawnParameters.entityType().spawn((ServerLevel) level, pos, MobSpawnType.NATURAL);
                        mobSpawned = true;
                    }
                }
            }
            if (centerState.is(Blocks.WATER)) { //axolotls, underground water creature, water creature, water ambient

            }
            //ambient
        }
        return mobSpawned;
    }

    public static int matchingBlocks(List<Block> adjacentBlocks, List<Block> neighbors, int maxMatches) {
        int matches = 0;
        for (int a = 0; a < adjacentBlocks.size(); a++) {
            for (int n = 0; n < neighbors.size(); n++) {
                if (adjacentBlocks.get(a).equals(neighbors.get(n))) {
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