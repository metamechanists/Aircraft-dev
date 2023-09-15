package org.metamechanists.aircraft.vehicles;

import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Vector3d;


public class VehicleSurface {
    private static final double AIR_DENSITY = 1.0;

    private final double liftCoefficient;
    private final double dragCoefficient;
    private final double area;
    private final Vector3d relativeNormal;
    private final Vector3d relativeLocation;

    public VehicleSurface(final double dragCoefficient, final double liftCoefficient, final double area, final Vector3d relativeNormal, final Vector3d relativeLocation) {
        this.dragCoefficient = dragCoefficient;
        this.liftCoefficient = liftCoefficient;
        this.area = area;
        this.relativeNormal = relativeNormal;
        this.relativeLocation = relativeLocation;
    }

    private double getRelativeArea(final @NotNull Vector3d normal, final @NotNull Vector3d airflowVelocity) {
        return new Vector3d(normal).angleCos(airflowVelocity) * area;
    }

    public SpatialForce getLiftForce(final @NotNull Quaterniond rotation, final @NotNull Vector3d velocity, final @NotNull Quaterniond angularVelocity) {
        final Vector3d location = new Vector3d(relativeLocation).rotate(rotation);
        final Vector3d normal = new Vector3d(relativeNormal).rotate(rotation);
        final Vector3d angularVelocityVector = angularVelocity.getEulerAnglesXYZ(new Vector3d()).mul(relativeLocation.length());
        final Vector3d airflowVelocity = new Vector3d(velocity).add(angularVelocityVector).mul(-1);

        // Check the airflow isn't coming *out* of the surface as opposed to going into it
        // Also check the airflow and normal are not in opposite directions - this causes NaN values
        if (normal.angle(airflowVelocity) < Math.PI / 2 || normal.angle(airflowVelocity) > (Math.PI - 0.001)) {
            return new SpatialForce(new Vector3d(), location);
        }

        // L = 0.5 * Cl * ρ * A * V^2,
        // L = lift force
        // Cl = coefficient of lift
        // ρ = air density
        // A = surface area facing airflow
        // V = aircraft velocity
        final double aircraftSpeed = velocity.length();
        final Vector3d perpendicularDirection = new Vector3d(normal).cross(airflowVelocity);
        final Vector3d liftDirection = new Vector3d(perpendicularDirection).cross(airflowVelocity).normalize();
        final Vector3d force = liftDirection.mul(
                Math.sin(2.0*normal.angle(airflowVelocity))
                        * 0.5
                        * liftCoefficient
                        * AIR_DENSITY
                        * getRelativeArea(normal, airflowVelocity)
                        * (aircraftSpeed * aircraftSpeed));
        return new SpatialForce(force, location);
    }

    public SpatialForce getDragForce(final @NotNull Quaterniond rotation, final @NotNull Vector3d velocity, final @NotNull Quaterniond angularVelocity) {
        final Vector3d location = new Vector3d(relativeLocation).rotate(rotation);
        final Vector3d normal = new Vector3d(relativeNormal).rotate(rotation);
        final Vector3d angularVelocityVector = angularVelocity.getEulerAnglesXYZ(new Vector3d()).mul(relativeLocation.length());
        final Vector3d airflowVelocity = new Vector3d(velocity).add(angularVelocityVector).mul(-1);

        // Check the airflow isn't coming *out* of the surface as opposed to going into it
        // Also check the airflow and normal are not in opposite directions - this causes NaN values
        if (normal.angle(airflowVelocity) < Math.PI / 2 || normal.angle(airflowVelocity) < 0.001) {
            return new SpatialForce(new Vector3d(), location);
        }

        // D = 0.5 * Cd * ρ * A * V^2, where
        // D = drag force
        // Cd = coefficient of drag
        // ρ = air density
        // A = surface area facing airflow
        // V = aircraft velocity
        final double aircraftSpeed = velocity.length();
        final Vector3d dragDirection = new Vector3d(airflowVelocity).mul(-1).normalize();
        final Vector3d force = dragDirection.mul(
                Math.sin(normal.angle(airflowVelocity))
                        * 0.5
                        * dragCoefficient
                        * AIR_DENSITY
                        * getRelativeArea(normal, airflowVelocity)
                        * (aircraftSpeed * aircraftSpeed));
        return new SpatialForce(force, location);
    }
}