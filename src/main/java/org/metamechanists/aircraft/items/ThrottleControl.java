package org.metamechanists.aircraft.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.aircraft.vehicle.VehicleEntity;
import org.metamechanists.kinematiccore.api.storage.EntityStorage;


public class ThrottleControl extends SlimefunItem {
    private final int increment;

    public ThrottleControl(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int increment) {
        super(itemGroup, item, recipeType, recipe);
        this.increment = increment;
        addItemHandler(onItemUse());
    }

    private @NotNull ItemUseHandler onItemUse() {
        return event -> {
            Player player = event.getPlayer();
            Entity riding = player.getVehicle();
            if (riding == null) {
                return;
            }

            if (!(EntityStorage.kinematicEntity(riding.getUniqueId()) instanceof VehicleEntity vehicleEntity)) {
                return;
            }

            vehicleEntity.adjustThrottle(increment);
        };
    }
}
