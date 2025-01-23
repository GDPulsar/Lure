package com.Apothic0n.Lure.core.config;

import com.Apothic0n.Lure.Lure;
import com.Apothic0n.Lure.core.api.CreatureSpawnParameters;
import com.Apothic0n.Lure.core.api.MonsterSpawnParameters;
import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigHandler {
    static final Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(CreatureSpawnParameters.class, new CreatureSpawnParameters.Deserializer())
            .registerTypeAdapter(CreatureSpawnParameters.class, new CreatureSpawnParameters.Serializer())
            .registerTypeAdapter(MonsterSpawnParameters.class, new MonsterSpawnParameters.Deserializer())
            .registerTypeAdapter(MonsterSpawnParameters.class, new MonsterSpawnParameters.Serializer())
            .registerTypeAdapter(LureConfig.class, new Deserializer())
            .registerTypeAdapter(LureConfig.class, new Serializer()).create();
    private final static String configPath = FMLPaths.CONFIGDIR.get().toString() + "/";
    private final static String configName = Lure.MODID + ".json";
    private static LureConfig LURE_CONFIG;

    public static LureConfig create() throws IOException {
        File configFile = new File(configPath + configName);
        if (!configFile.exists()) {
            try (FileWriter fw = new FileWriter(configFile.getPath())) {
                gson.toJson(new LureConfig(), fw);
            }
        }

        LURE_CONFIG = load();
        return LURE_CONFIG;
    }

    public static LureConfig load() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configPath + configName));
        return gson.fromJson(bufferedReader, LureConfig.class);
    }

    public static LureConfig getConfig() {
        if (LURE_CONFIG == null) {
            try {
                LURE_CONFIG = ConfigHandler.create();
            } catch (IOException e) {
                Lure.LOGGER.error("Config was unable to be created!", e);
            }
        }
        return LURE_CONFIG;
    }

    public static class Deserializer implements JsonDeserializer<LureConfig> {
        @Override
        public LureConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            LureConfig config = new LureConfig();
            config.creatures.clear();
            config.monsters.clear();
            JsonObject creatures = GsonHelper.getAsJsonObject(jsonElement.getAsJsonObject(), "creatures");
            for (String key : creatures.keySet()) {
                Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(key));
                config.creatures.put(block, new ArrayList<>());
                JsonArray array = GsonHelper.getAsJsonArray(creatures, key);
                for (JsonElement element : array) {
                    config.creatures.get(block).add(gson.fromJson(element, CreatureSpawnParameters.class));
                }
            }
            JsonObject monsters = GsonHelper.getAsJsonObject(jsonElement.getAsJsonObject(), "monsters");
            for (String key : monsters.keySet()) {
                LureConfig.MoonPhase moonPhase = LureConfig.MoonPhase.valueOf(key);
                config.monsters.put(moonPhase, new ArrayList<>());
                JsonArray array = GsonHelper.getAsJsonArray(monsters, key);
                for (JsonElement element : array) {
                    config.monsters.get(moonPhase).add(gson.fromJson(element, MonsterSpawnParameters.class));
                }
            }
            return config;
        }
    }

    public static class Serializer implements JsonSerializer<LureConfig> {
        @Override
        public JsonElement serialize(LureConfig config, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            JsonObject creatures = new JsonObject();
            for (Map.Entry<Block, List<CreatureSpawnParameters>> spawn : config.creatures.entrySet()) {
                JsonArray array = new JsonArray();
                for (CreatureSpawnParameters parameters : spawn.getValue()) {
                    array.add(gson.toJsonTree(parameters));
                }
                creatures.add(BuiltInRegistries.BLOCK.getKey(spawn.getKey()).toString(), array);
            }
            json.add("creatures", creatures);
            JsonObject monsters = new JsonObject();
            for (Map.Entry<LureConfig.MoonPhase, List<MonsterSpawnParameters>> spawn : config.monsters.entrySet()) {
                JsonArray array = new JsonArray();
                for (MonsterSpawnParameters parameters : spawn.getValue()) {
                    array.add(gson.toJsonTree(parameters));
                }
                monsters.add(spawn.getKey().toString(), array);
            }
            json.add("monsters", monsters);
            return json;
        }
    }
}
