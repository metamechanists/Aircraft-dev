package org.metamechanists.aircraft.slimefun;

import io.github.bakedlibs.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.metamechanists.aircraft.Aircraft;
import org.metamechanists.aircraft.utils.Keys;
import org.metamechanists.aircraft.vehicle.VehicleEntitySchema;
import org.metamechanists.kinematiccore.api.entity.KinematicEntitySchema;

import java.util.HashSet;
import java.util.Set;


public final class Items {
    private static final ItemGroup AIRCRAFT_GROUP = new ItemGroup(Keys.AIRCRAFT,
            new CustomItemStack(Material.COMPASS, "&aAircraft"));

    private static final SlimefunItemStack THROTTLE_UP_STACK = new SlimefunItemStack(
                    "AIRCRAFT_THROTTLE_UP",
                    new CustomItemStack(Material.LIME_DYE, ChatColor.WHITE + "Throttle up"));
    private static final AircraftControl THROTTLE_UP = new AircraftControl(
            AIRCRAFT_GROUP,
            THROTTLE_UP_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "THROTTLE_UP");

    private static final SlimefunItemStack THROTTLE_DOWN_STACK = new SlimefunItemStack(
            "AIRCRAFT_THROTTLE_DOWN",
            new CustomItemStack(Material.RED_DYE, ChatColor.WHITE + "Throttle down"));
    private static final AircraftControl THROTTLE_DOWN = new AircraftControl(
            AIRCRAFT_GROUP,
            THROTTLE_DOWN_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "THROTTLE_DOWN");

    private static final SlimefunItemStack STEER_LEFT_STACK = new SlimefunItemStack(
                        "AIRCRAFT_STEER_LEFT",
                        new CustomItemStack(Material.MUSIC_DISC_STAL, ChatColor.WHITE + "Steer left"));
    private static final AircraftControl STEER_LEFT = new AircraftControl(
            AIRCRAFT_GROUP,
            STEER_LEFT_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "STEER_LEFT");

    private static final SlimefunItemStack STEER_RIGHT_STACK = new SlimefunItemStack(
                        "AIRCRAFT_STEER_RIGHT",
                        new CustomItemStack(Material.MUSIC_DISC_STAL, ChatColor.WHITE + "Steer right"));
    private static final AircraftControl STEER_RIGHT = new AircraftControl(
            AIRCRAFT_GROUP,
            STEER_RIGHT_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "STEER_RIGHT");

    private static final SlimefunItemStack STRAFE_LEFT_STACK  = new SlimefunItemStack(
            "AIRCRAFT_STRAFE_LEFT",
            new CustomItemStack(Material.ECHO_SHARD, ChatColor.WHITE + "Strafe left"));
    private static final AircraftControl STRAFE_LEFT = new AircraftControl(
            AIRCRAFT_GROUP,
            STRAFE_LEFT_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "STRAFE_LEFT");

    private static final SlimefunItemStack STRAFE_RIGHT_STACK = new SlimefunItemStack(
            "AIRCRAFT_STRAFE_RIGHT",
            new CustomItemStack(Material.AMETHYST_SHARD, ChatColor.WHITE + "Strafe right"));
    private static final AircraftControl STRAFE_RIGHT = new AircraftControl(
            AIRCRAFT_GROUP,
            STRAFE_RIGHT_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "STRAFE_RIGHT");

    private static final SlimefunItemStack STRAFE_UP_STACK  = new SlimefunItemStack(
            "AIRCRAFT_STRAFE_UP",
            new CustomItemStack(Material.LIME_CANDLE, ChatColor.WHITE + "Strafe up"));
    private static final AircraftControl STRAFE_UP = new AircraftControl(
            AIRCRAFT_GROUP,
            STRAFE_UP_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "STRAFE_UP");

    private static final SlimefunItemStack STRAFE_DOWN_STACK = new SlimefunItemStack(
            "AIRCRAFT_STRAFE_DOWN",
            new CustomItemStack(Material.RED_CANDLE, ChatColor.WHITE + "Strafe down"));
    private static final AircraftControl STRAFE_DOWN = new AircraftControl(
            AIRCRAFT_GROUP,
            STRAFE_DOWN_STACK,
            RecipeType.NULL,
            new ItemStack[]{},
            "STRAFE_DOWN");

    private static final SlimefunItemStack CRUDE_AIRCRAFT_STACK = new SlimefunItemStack(
            "AIRCRAFT_CRUDE_AIRCRAFT",
            new CustomItemStack(Material.FEATHER, ChatColor.WHITE + "Crude Aircraft"));

    private static final SlimefunItemStack CRUDE_AIRSHIP_STACK = new SlimefunItemStack(
            "AIRCRAFT_CRUDE_AIRSHIP",
            new CustomItemStack(Material.FEATHER, ChatColor.WHITE + "Crude Airship"));

    private static @NotNull VehicleItem crudeAircraft(VehicleEntitySchema schema) {
        return new VehicleItem(
                schema,
                AIRCRAFT_GROUP,
                CRUDE_AIRCRAFT_STACK,
                RecipeType.NULL,
                new ItemStack[]{});
    }

    private static @NotNull VehicleItem crudeAirship(VehicleEntitySchema schema) {
        return new VehicleItem(
                schema,
                AIRCRAFT_GROUP,
                CRUDE_AIRSHIP_STACK,
                RecipeType.NULL,
                new ItemStack[]{});
    }

    private static final Set<String> loadedAircraft = new HashSet<>();

    private Items() {}

    private static @Nullable VehicleEntitySchema loadVehicle(@NotNull String id) {
        try {
            VehicleEntitySchema schema = new VehicleEntitySchema(id);
            loadedAircraft.add(schema.id());
            return schema;
        } catch (RuntimeException e) {
            Aircraft.getInstance().getLogger().severe("Failed to load aircraft " + id);
            e.printStackTrace();
            return null;
        }
    }

    public static void init() {
        JavaPlugin plugin = Aircraft.getInstance();
        SlimefunAddon addon = (SlimefunAddon) plugin;

        THROTTLE_UP.register(addon);
        THROTTLE_DOWN.register(addon);
        STEER_LEFT.register(addon);
        STEER_RIGHT.register(addon);
        STRAFE_LEFT.register(addon);
        STRAFE_RIGHT.register(addon);
        STRAFE_UP.register(addon);
        STRAFE_DOWN.register(addon);

        VehicleEntitySchema crudeAircraftSchema = loadVehicle("crude_aircraft");
        if (crudeAircraftSchema != null) {
            crudeAircraft(crudeAircraftSchema).register(addon);
        }

        VehicleEntitySchema crudeAirshipSchema = loadVehicle("crude_airship");
        if (crudeAirshipSchema != null) {
            crudeAirship(crudeAirshipSchema).register(addon);
        }
    }

    public static void reload() {
        for (String id : KinematicEntitySchema.registeredSchemasByAddon(Aircraft.getInstance())) {
            KinematicEntitySchema schema = KinematicEntitySchema.get(id);
            if (schema != null) {
                schema.unregister();
            }
        }

        for (String id : loadedAircraft) {
            // hacky but works
            new VehicleEntitySchema(id.replaceAll("aircraft:", ""));
        }
    }
}