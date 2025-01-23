package com.Apothic0n.Lure.core.api;

import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public record MonsterSpawnParameters(EntityType entityType, List<TagKey<Biome>> validBiomeTags, List<ResourceKey<Biome>> validBiomes) {
    public static class Deserializer implements JsonDeserializer<MonsterSpawnParameters> {
        @Override
        public MonsterSpawnParameters deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(GsonHelper.getAsString(json, "entityType")));
            List<TagKey<Biome>> validBiomeTags = new ArrayList<>();
            JsonArray validBiomeTagsJson = GsonHelper.getAsJsonArray(json, "validBiomeTags");
            for (int i = 0; i < validBiomeTagsJson.size(); i++) {
                validBiomeTags.add(TagKey.create(Registries.BIOME, ResourceLocation.tryParse(validBiomeTagsJson.get(i).getAsString())));
            }
            List<ResourceKey<Biome>> validBiomes = new ArrayList<>();
            JsonArray validBiomesJson = GsonHelper.getAsJsonArray(json, "validBiomes");
            for (int i = 0; i < validBiomesJson.size(); i++) {
                validBiomes.add(ResourceKey.create(Registries.BIOME, ResourceLocation.tryParse(validBiomesJson.get(i).getAsString())));
            }
            return new MonsterSpawnParameters(entityType, validBiomeTags, validBiomes);
        }
    }

    public static class Serializer implements JsonSerializer<MonsterSpawnParameters> {
        @Override
        public JsonElement serialize(MonsterSpawnParameters monsterSpawnParameters, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            json.addProperty("entityType", BuiltInRegistries.ENTITY_TYPE.getKey(monsterSpawnParameters.entityType()).toString());
            JsonArray validBiomeTagsJson = new JsonArray();
            for (TagKey<?> tagKey : monsterSpawnParameters.validBiomeTags()) {
                validBiomeTagsJson.add(tagKey.location().toString());
            }
            json.add("validBiomeTags", validBiomeTagsJson);
            JsonArray validBiomesJson = new JsonArray();
            for (ResourceKey<?> resourceKey : monsterSpawnParameters.validBiomes()) {
                validBiomesJson.add(resourceKey.location().toString());
            }
            json.add("validBiomes", validBiomesJson);
            return json;
        }
    }
}