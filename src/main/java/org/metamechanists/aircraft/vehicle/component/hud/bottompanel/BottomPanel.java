package org.metamechanists.aircraft.vehicle.component.hud.bottompanel;

import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.aircraft.vehicle.VehicleEntity;
import org.metamechanists.aircraft.vehicle.component.base.HudSection;
import org.metamechanists.kinematiccore.api.storage.StateReader;
import org.metamechanists.metalib.yaml.YamlTraverser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BottomPanel extends HudSection<BottomPanel.BottomPanelSchema> {
    public static class BottomPanelSchema extends HudSectionSchema {
        private final ThrottleBar.QuantityBarSchema throttleBarForegroundSchema;

        public BottomPanelSchema(@NotNull String id, @NotNull YamlTraverser traverser) {
            super(id, traverser, BottomPanel.class, Interaction.class);
            throttleBarForegroundSchema = new ThrottleBar.QuantityBarSchema(id, "throttleForeground", this, traverser);
        }

        @Override
        public void unregister() {
            super.unregister();
            throttleBarForegroundSchema.unregister();
        }
    }

    public BottomPanel(@NotNull BottomPanel.BottomPanelSchema schema, @NotNull VehicleEntity vehicleEntity) {
        super(schema, vehicleEntity);
    }

    public BottomPanel(@NotNull StateReader reader) {
        super(reader);
    }

    @Override
    protected List<UUID> buildComponents(@NotNull VehicleEntity vehicleEntity) {
        List<UUID> components = new ArrayList<>();
        components.add(new ThrottleBar(schema().throttleBarForegroundSchema, vehicleEntity).uuid());
        return components;
    }
}