package com.Apothic0n.Lure.core.api;

import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public record CreatureSpawnParameters(EntityType entityType, List<Block> adjacentBlocks, int amountRequired) {
    public static class Deserializer implements JsonDeserializer<CreatureSpawnParameters> {
        @Override
        public CreatureSpawnParameters deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(GsonHelper.getAsString(json, "entityType")));
            List<Block> blocks = new ArrayList<>();
            JsonArray blocksJson = GsonHelper.getAsJsonArray(json, "blocks");
            for (int i = 0; i < blocksJson.size(); i++) {
                blocks.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(blocksJson.get(i).getAsString())));
            }
            return new CreatureSpawnParameters(entityType, blocks, GsonHelper.getAsInt(json, "amountRequired"));
        }
    }

    public static class Serializer implements JsonSerializer<CreatureSpawnParameters> {
        @Override
        public JsonElement serialize(CreatureSpawnParameters creatureSpawnParameters, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            json.addProperty("entityType", BuiltInRegistries.ENTITY_TYPE.getKey(creatureSpawnParameters.entityType()).toString());
            JsonArray blocksJson = new JsonArray();
            for (Block block : creatureSpawnParameters.adjacentBlocks()) {
                blocksJson.add(BuiltInRegistries.BLOCK.getKey(block).toString());
            }
            json.add("blocks", blocksJson);
            json.addProperty("amountRequired", creatureSpawnParameters.amountRequired());
            return json;
        }
    }
}