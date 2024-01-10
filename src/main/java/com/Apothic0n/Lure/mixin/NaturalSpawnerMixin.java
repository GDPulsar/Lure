package com.Apothic0n.Lure.mixin;

import com.Apothic0n.Lure.core.events.CommonForgeEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.mojang.text2speech.Narrator.LOGGER;
import static net.minecraft.world.level.NaturalSpawner.isSpawnPositionOk;

@Mixin(value = NaturalSpawner.class, priority = 69420)
public abstract class NaturalSpawnerMixin {

    @Shadow
    private static BlockPos getTopNonCollidingPos(LevelReader p_47066_, EntityType<?> p_47067_, int p_47068_, int p_47069_) {
        return null;
    }

    @Shadow
    protected static boolean isRightDistanceToPlayerAndSpawnPoint(ServerLevel p_47025_, ChunkAccess p_47026_, BlockPos.MutableBlockPos p_47027_, double p_47028_) {
        return false;
    }

    @Shadow
    protected static Optional<MobSpawnSettings.SpawnerData> getRandomSpawnMobAt(ServerLevel p_220430_, StructureManager p_220431_, ChunkGenerator p_220432_, MobCategory p_220433_, RandomSource p_220434_, BlockPos p_220435_) {
        return null;
    }

    @Shadow
    protected static boolean isValidSpawnPostitionForType(ServerLevel p_220422_, MobCategory p_220423_, StructureManager p_220424_, ChunkGenerator p_220425_, MobSpawnSettings.SpawnerData p_220426_, BlockPos.MutableBlockPos p_220427_, double p_220428_) {
        return false;
    }

    @Shadow
    @Nullable
    protected static Mob getMobForSpawn(ServerLevel p_46989_, EntityType<?> p_46990_) {
        return null;
    }

    @Shadow
    protected static boolean isValidPositionForMob(ServerLevel p_46992_, Mob p_46993_, double p_46994_) {
        return false;
    }

    /**
     * @author Apothicon
     * @reason Prevents vanilla mobs from generating.
     */
    @Overwrite
    public static void spawnMobsForChunkGeneration(ServerLevelAccessor p_220451_, Holder<Biome> p_220452_, ChunkPos p_220453_, RandomSource p_220454_) {
        MobSpawnSettings mobspawnsettings = p_220452_.value().getMobSettings();
        WeightedRandomList<MobSpawnSettings.SpawnerData> weightedrandomlist = mobspawnsettings.getMobs(MobCategory.CREATURE);
        if (!weightedrandomlist.isEmpty()) {
            int i = p_220453_.getMinBlockX();
            int j = p_220453_.getMinBlockZ();

            while(p_220454_.nextFloat() < mobspawnsettings.getCreatureProbability()) {
                Optional<MobSpawnSettings.SpawnerData> optional = weightedrandomlist.getRandom(p_220454_);
                if (!optional.isEmpty()) {
                    MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = optional.get();
                    int k = mobspawnsettings$spawnerdata.minCount + p_220454_.nextInt(1 + mobspawnsettings$spawnerdata.maxCount - mobspawnsettings$spawnerdata.minCount);
                    SpawnGroupData spawngroupdata = null;
                    int l = i + p_220454_.nextInt(16);
                    int i1 = j + p_220454_.nextInt(16);
                    int j1 = l;
                    int k1 = i1;

                    for(int l1 = 0; l1 < k; ++l1) {
                        boolean flag = false;

                        for(int i2 = 0; !flag && i2 < 4; ++i2) {
                            BlockPos blockpos = getTopNonCollidingPos(p_220451_, mobspawnsettings$spawnerdata.type, l, i1);
                            if (mobspawnsettings$spawnerdata.type.canSummon() && isSpawnPositionOk(SpawnPlacements.getPlacementType(mobspawnsettings$spawnerdata.type), p_220451_, blockpos, mobspawnsettings$spawnerdata.type)) {
                                float f = mobspawnsettings$spawnerdata.type.getWidth();
                                double d0 = Mth.clamp((double)l, (double)i + (double)f, (double)i + 16.0D - (double)f);
                                double d1 = Mth.clamp((double)i1, (double)j + (double)f, (double)j + 16.0D - (double)f);
                                if (!p_220451_.noCollision(mobspawnsettings$spawnerdata.type.getAABB(d0, (double)blockpos.getY(), d1)) || !SpawnPlacements.checkSpawnRules(mobspawnsettings$spawnerdata.type, p_220451_, MobSpawnType.CHUNK_GENERATION, BlockPos.containing(d0, (double)blockpos.getY(), d1), p_220451_.getRandom())) {
                                    continue;
                                }

                                Entity entity;
                                try {
                                    entity = mobspawnsettings$spawnerdata.type.create(p_220451_.getLevel());
                                } catch (Exception exception) {
                                    LOGGER.warn("Failed to create mob", (Throwable)exception);
                                    continue;
                                }

                                if (entity == null) {
                                    continue;
                                }

                                entity.moveTo(d0, (double)blockpos.getY(), d1, p_220454_.nextFloat() * 360.0F, 0.0F);
                                if (entity instanceof Mob) {
                                    Mob mob = (Mob)entity;
                                    if ((net.minecraftforge.event.ForgeEventFactory.checkSpawnPosition(mob, p_220451_, MobSpawnType.CHUNK_GENERATION)) && !CommonForgeEvents.affectedMobs.contains(entity.getType())) {
                                        spawngroupdata = mob.finalizeSpawn(p_220451_, p_220451_.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.CHUNK_GENERATION, spawngroupdata, (CompoundTag)null);
                                        p_220451_.addFreshEntityWithPassengers(mob);
                                        flag = true;
                                    }
                                }
                            }

                            l += p_220454_.nextInt(5) - p_220454_.nextInt(5);

                            for(i1 += p_220454_.nextInt(5) - p_220454_.nextInt(5); l < i || l >= i + 16 || i1 < j || i1 >= j + 16; i1 = k1 + p_220454_.nextInt(5) - p_220454_.nextInt(5)) {
                                l = j1 + p_220454_.nextInt(5) - p_220454_.nextInt(5);
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * @author Apothicon
     * @reason Prevents vanilla mobs from spawning.
     */
    @Overwrite
    public static void spawnCategoryForPosition(MobCategory p_47039_, ServerLevel p_47040_, ChunkAccess p_47041_, BlockPos p_47042_, NaturalSpawner.SpawnPredicate p_47043_, NaturalSpawner.AfterSpawnCallback p_47044_) {
        StructureManager structuremanager = p_47040_.structureManager();
        ChunkGenerator chunkgenerator = p_47040_.getChunkSource().getGenerator();
        int i = p_47042_.getY();
        BlockState blockstate = p_47041_.getBlockState(p_47042_);
        if (!blockstate.isRedstoneConductor(p_47041_, p_47042_)) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            int j = 0;

            for(int k = 0; k < 3; ++k) {
                int l = p_47042_.getX();
                int i1 = p_47042_.getZ();
                int j1 = 6;
                MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = null;
                SpawnGroupData spawngroupdata = null;
                int k1 = Mth.ceil(p_47040_.random.nextFloat() * 4.0F);
                int l1 = 0;

                for(int i2 = 0; i2 < k1; ++i2) {
                    l += p_47040_.random.nextInt(6) - p_47040_.random.nextInt(6);
                    i1 += p_47040_.random.nextInt(6) - p_47040_.random.nextInt(6);
                    blockpos$mutableblockpos.set(l, i, i1);
                    double d0 = (double)l + 0.5D;
                    double d1 = (double)i1 + 0.5D;
                    Player player = p_47040_.getNearestPlayer(d0, (double)i, d1, -1.0D, false);
                    if (player != null) {
                        double d2 = player.distanceToSqr(d0, (double)i, d1);
                        if (isRightDistanceToPlayerAndSpawnPoint(p_47040_, p_47041_, blockpos$mutableblockpos, d2)) {
                            if (mobspawnsettings$spawnerdata == null) {
                                Optional<MobSpawnSettings.SpawnerData> optional = getRandomSpawnMobAt(p_47040_, structuremanager, chunkgenerator, p_47039_, p_47040_.random, blockpos$mutableblockpos);
                                if (optional.isEmpty()) {
                                    break;
                                }

                                mobspawnsettings$spawnerdata = optional.get();
                                k1 = mobspawnsettings$spawnerdata.minCount + p_47040_.random.nextInt(1 + mobspawnsettings$spawnerdata.maxCount - mobspawnsettings$spawnerdata.minCount);
                            }

                            if (isValidSpawnPostitionForType(p_47040_, p_47039_, structuremanager, chunkgenerator, mobspawnsettings$spawnerdata, blockpos$mutableblockpos, d2) && p_47043_.test(mobspawnsettings$spawnerdata.type, blockpos$mutableblockpos, p_47041_)) {
                                Mob mob = getMobForSpawn(p_47040_, mobspawnsettings$spawnerdata.type);
                                if (mob == null || CommonForgeEvents.affectedMobs.contains(mob.getType())) {
                                    return;
                                }

                                mob.moveTo(d0, (double)i, d1, p_47040_.random.nextFloat() * 360.0F, 0.0F);
                                if (isValidPositionForMob(p_47040_, mob, d2)) {
                                    spawngroupdata = mob.finalizeSpawn(p_47040_, p_47040_.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.NATURAL, spawngroupdata, (CompoundTag)null);
                                    ++j;
                                    ++l1;
                                    p_47040_.addFreshEntityWithPassengers(mob);
                                    p_47044_.run(mob, p_47041_);
                                    if (j >= net.minecraftforge.event.ForgeEventFactory.getMaxSpawnPackSize(mob)) {
                                        return;
                                    }

                                    if (mob.isMaxGroupSizeReached(l1)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}