package com.Apothic0n.Lure.core;

import com.Apothic0n.Lure.Lure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class LureSavedData extends SavedData {
    private Set<ChunkPos> set = new HashSet<>(Set.of());
    public static LureSavedData lureSavedData(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(LureSavedData::load, LureSavedData::new, Lure.MODID + "_alreadySpawned");
    }

    public static boolean contains(ServerLevel level, ChunkPos chunkPos) {
        return lureSavedData(level).set.contains(chunkPos);
    }

    public static void add(ServerLevel level, ChunkPos chunkPos) {
        LureSavedData data = lureSavedData(level);
        data.set.add(chunkPos);
        data.setDirty();
    }

    public static LureSavedData load(CompoundTag tag) {
        LureSavedData newData = new LureSavedData();
        ListTag data = tag.getList(Lure.MODID + "_alreadySpawned", Tag.TAG_INT_ARRAY);
        for (Tag array : data) {
            if (array instanceof IntArrayTag) {
                int[] ints = ((IntArrayTag) array).getAsIntArray();
                newData.set.add(new ChunkPos(ints[0], ints[1]));
            }
        }
        return newData;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        final ListTag tagList = new ListTag();
        for (ChunkPos chunkPos : set) {
            tagList.add(new IntArrayTag(List.of(chunkPos.x, chunkPos.z)));
        }
        tag.put(Lure.MODID + "_alreadySpawned", tagList);

        return tag;
    }
}
