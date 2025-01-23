package com.Apothic0n.Lure.core.config;

import com.Apothic0n.Lure.core.api.CreatureSpawnParameters;
import com.Apothic0n.Lure.core.api.MonsterSpawnParameters;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LureConfig {
    public Map<Block, List<CreatureSpawnParameters>> creatures = new HashMap<>(Map.of(
            Blocks.MUD, List.of(
                    new CreatureSpawnParameters(EntityType.PIG, List.of(Blocks.CARROTS), 1),
                    new CreatureSpawnParameters(EntityType.FROG, List.of(Blocks.MANGROVE_ROOTS), 1)
            ),
            Blocks.GRASS_BLOCK, List.of(
                    new CreatureSpawnParameters(EntityType.SHEEP, List.of(Blocks.WHEAT), 1),
                    new CreatureSpawnParameters(EntityType.CHICKEN, List.of(Blocks.GRASS, Blocks.TALL_GRASS), 4),
                    new CreatureSpawnParameters(EntityType.RABBIT, List.of(Blocks.CARROTS), 1),
                    new CreatureSpawnParameters(EntityType.OCELOT, List.of(Blocks.JUNGLE_LEAVES), 2),
                    new CreatureSpawnParameters(EntityType.HORSE, List.of(Blocks.HAY_BLOCK), 1)
            ),
            Blocks.MYCELIUM, List.of(
                    new CreatureSpawnParameters(EntityType.MOOSHROOM, List.of(Blocks.WHEAT), 1)
            ),
            Blocks.PACKED_MUD, List.of(
                    new CreatureSpawnParameters(EntityType.COW, List.of(Blocks.WHEAT), 1),
                    new CreatureSpawnParameters(EntityType.DONKEY, List.of(Blocks.HAY_BLOCK), 1)
            ),
            Blocks.PODZOL, List.of(
                    new CreatureSpawnParameters(EntityType.FOX, List.of(Blocks.SWEET_BERRY_BUSH), 1),
                    new CreatureSpawnParameters(EntityType.PANDA, List.of(Blocks.BAMBOO), 1),
                    new CreatureSpawnParameters(EntityType.WOLF, List.of(Blocks.FERN), 1)
            ),
            Blocks.SNOW_BLOCK, List.of(
                    new CreatureSpawnParameters(EntityType.POLAR_BEAR, List.of(Blocks.AIR), 4),
                    new CreatureSpawnParameters(EntityType.RABBIT, List.of(Blocks.CARROTS), 1)
            ),
            Blocks.ICE, List.of(
                    new CreatureSpawnParameters(EntityType.POLAR_BEAR, List.of(Blocks.AIR), 4)
            ),
            Blocks.SAND, List.of(
                    new CreatureSpawnParameters(EntityType.RABBIT, List.of(Blocks.CARROTS), 1),
                    new CreatureSpawnParameters(EntityType.TURTLE, List.of(Blocks.SEAGRASS), 1)
            ),
            Blocks.JUNGLE_LEAVES, List.of(
                    new CreatureSpawnParameters(EntityType.PARROT, List.of(Blocks.AIR), 4)
            ),
            Blocks.STONE, List.of(
                    new CreatureSpawnParameters(EntityType.GOAT, List.of(Blocks.WHEAT), 1)
            )
    ));

    public Map<MoonPhase, List<MonsterSpawnParameters>> monsters = new HashMap<>(Map.of(
            MoonPhase.full_moon, List.of( //Full Moon
                    new MonsterSpawnParameters(EntityType.ZOMBIE, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIE_VILLAGER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.DROWNED, List.of(BiomeTags.IS_OVERWORLD), List.of()),
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
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            MoonPhase.waning_gibbous, List.of(
                    new MonsterSpawnParameters(EntityType.ZOMBIE, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIE_VILLAGER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.DROWNED, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.HUSK, List.of(), List.of(Biomes.DESERT)),
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            MoonPhase.last_quarter, List.of(
                    new MonsterSpawnParameters(EntityType.SPIDER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            MoonPhase.waning_crescent, List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(BiomeTags.IS_OVERWORLD), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.STRAY, List.of(), List.of(Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES, Biomes.FROZEN_RIVER)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            MoonPhase.new_moon, List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.CREEPER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            MoonPhase.waxing_crescent, List.of(
                    new MonsterSpawnParameters(EntityType.ZOMBIE, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIE_VILLAGER, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.DROWNED, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.HUSK, List.of(), List.of(Biomes.DESERT)),
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            MoonPhase.first_quarter, List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_OVERWORLD, BiomeTags.IS_NETHER, BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            ),
            MoonPhase.waxing_gibbous, List.of(
                    new MonsterSpawnParameters(EntityType.SKELETON, List.of(), List.of(Biomes.SOUL_SAND_VALLEY)),
                    new MonsterSpawnParameters(EntityType.WITCH, List.of(BiomeTags.IS_OVERWORLD), List.of()),
                    new MonsterSpawnParameters(EntityType.ENDERMAN, List.of(BiomeTags.IS_END), List.of()),
                    new MonsterSpawnParameters(EntityType.ZOMBIFIED_PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.PIGLIN, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.HOGLIN, List.of(), List.of(Biomes.CRIMSON_FOREST)),
                    new MonsterSpawnParameters(EntityType.GHAST, List.of(), List.of(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)),
                    new MonsterSpawnParameters(EntityType.SLIME, List.of(), List.of(Biomes.SWAMP))
            )
    ));

    public List<EntityType<?>> getAffectedCreatures() {
        List<EntityType<?>> types = new ArrayList<>();
        for (List<CreatureSpawnParameters> spawns : creatures.values()) {
            for (CreatureSpawnParameters parameters : spawns) {
                if (!types.contains(parameters.entityType())) types.add(parameters.entityType());
            }
        }
        return types;
    }

    public enum MoonPhase {
        full_moon,
        waning_gibbous,
        last_quarter,
        waning_crescent,
        new_moon,
        waxing_crescent,
        first_quarter,
        waxing_gibbous
    }
}
