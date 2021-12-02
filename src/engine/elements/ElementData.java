package engine.elements;

import engine.containers.Chunk;

public class ElementData {

    // Constraints
    public static final double SPEED_MAX = Chunk.WIDTH;
    public static final double STATIC_FRICTION = 0.95;


    // Element Information and Types

    // SOLID TYPES
    public static final String MATTER_SOLID = "solid";

    public static final String ELEMENT_SAND = "sand";
    public static final double ELEMENT_SAND_DENSITY = 1442;
    public static final double ELEMENT_SAND_FSS_SPREAD = 0.5;

    public static final String ELEMENT_SALT = "salt";
    public static final double ELEMENT_SALT_DENSITY = 1023.6;
    public static final double ELEMENT_SALT_FSS_SPREAD = 0.25;

    public static final String ELEMENT_COAL = "coal";
    public static final double ELEMENT_COAL_DENSITY = 1683;
    public static final double ELEMENT_COAL_FSS_SPREAD = 0.01;

    public static final String ELEMENT_WOOD = "wood";
    public static final double ELEMENT_WOOD_DENSITY = 800;

    // LIQUID TYPES
    public static final String MATTER_LIQUID = "liquid";

    public static final String ELEMENT_WATER = "water";
    public static final double ELEMENT_WATER_DENSITY = 998;
    public static final double ELEMENT_WATER_FSS_SPREAD = 0.5;

    // GAS TYPES
    public static final String MATTER_GAS = "gas";

    // Air
    public static final String ELEMENT_AIR = "air";
    public static final double ELEMENT_AIR_DENSITY = 1.2;

    // REACTION TYPES
    public static final String MATTER_REACTION = "reaction";

    // All types in an array
    public static final String[] ELEMENT_TYPES = {
            ELEMENT_AIR,
            ELEMENT_WOOD,
            ELEMENT_WATER,
            ELEMENT_SAND,
            ELEMENT_SALT,
            ELEMENT_COAL
    };
}
