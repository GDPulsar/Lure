package com.Apothic0n.Lure.core.api;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public record MonsterSpawnParameters(EntityType entityType, List<TagKey<Biome>> validBiomeTags, List<ResourceKey<Biome>> validBiomes) {
}