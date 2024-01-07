package com.Apothic0n.Lure.core.api;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record CreatureSpawnParameters(EntityType entityType, List<Block> adjacentBlocks, int amountRequired) {
}