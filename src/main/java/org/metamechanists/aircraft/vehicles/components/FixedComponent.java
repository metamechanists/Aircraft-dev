package org.metamechanists.aircraft.vehicles.components;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.metamechanists.aircraft.utils.Utils;
import org.metamechanists.aircraft.utils.models.ModelCuboid;
import org.metamechanists.aircraft.vehicles.VehicleSurface;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class FixedComponent implements VehicleComponent {
    private final String name;
    private final double density;
    private final double dragCoefficient;
    private final double liftCoefficient;
    private final Material material;
    private final Vector3f size;
    private final Vector3f location;
    private final Vector3d rotation;

    public FixedComponent(final String name,
                          final double density, final double dragCoefficient, final double liftCoefficient,
                          final Material material, final Vector3f size, final Vector3f location, final Vector3d rotation) {
        this.name = name;
        this.density = density;
        this.dragCoefficient = dragCoefficient;
        this.liftCoefficient = liftCoefficient;
        this.material = material;
        this.size = size;
        this.location = location;
        this.rotation = rotation;
    }

    private @NotNull VehicleSurface getSurface(final @NotNull Vector3d startingLocation, final double surfaceWidth, final double surfaceHeight) {
        final double area = surfaceWidth * surfaceHeight;
        final Vector3d relativeLocation = Utils.rotate(startingLocation, rotation);
        final Vector3d normal = new Vector3d(relativeLocation).normalize();
        return new VehicleSurface(dragCoefficient, liftCoefficient, area, normal, new Vector3d(location).add(relativeLocation));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<VehicleSurface> getSurfaces() {
        final Set<VehicleSurface> surfaces = new HashSet<>();

        surfaces.add(getSurface(new Vector3d(0, 0, size.z / 2), size.x, size.y));
        surfaces.add(getSurface(new Vector3d(0, 0, -size.z / 2), size.x, size.y));

        surfaces.add(getSurface(new Vector3d(0, size.y / 2, 0), size.x, size.z));
        surfaces.add(getSurface(new Vector3d(0, -size.y / 2, 0), size.x, size.z));

        surfaces.add(getSurface(new Vector3d(size.x / 2, 0, 0), size.y, size.z));
        surfaces.add(getSurface(new Vector3d(-size.x / 2, 0, 0), size.y, size.z));

        return surfaces;
    }

    @Override
    public ModelCuboid getCuboid() {
        return new ModelCuboid().material(material).size(size).location(location);
    }
}