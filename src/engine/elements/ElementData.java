package engine.elements;

import engine.containers.Chunk;
import engine.math.XMath;

public class ElementData {

    // Constraints
    public static final double SPEED_MAX = Chunk.WIDTH;
    public static final double STATIC_FRICTION = 0.95;
    public static final double REST_PRESSURE = XMath.sciNum(1.013, 5);

    // Element Information and Types

    // SOLID TYPES
    public static final String MATTER_SOLID = "solid";

    public static final String SAND = "sand";
    public static final double SAND_DENSITY = 1442;
    public static final double SAND_FSS_SPREAD = 0.5;

    public static final String SALT = "salt";
    public static final double SALT_DENSITY = 1023.6;
    public static final double SALT_FSS_SPREAD = 0.25;

    public static final String COAL = "coal";
    public static final double COAL_DENSITY = 1683;
    public static final double COAL_FSS_SPREAD = 0.01;

    public static final String ICE = "ice";
    public static final double ICE_DENSITY = 917;
    public static final double ICE_FSS_SPREAD = 0.001;

    public static final String WOOD = "wood";
    public static final double WOOD_DENSITY = 800;

    // LIQUID TYPES
    public static final String MATTER_LIQUID = "liquid";

    // Water
    public static final String WATER = "water";
    public static final double WATER_DENSITY = 998;
    public static final double WATER_FSS_SPREAD = 0.5;
    public static final double WATER_FSS_RANGE = 10;
    public static final double WATER_HIGH_TEMPERATURE = 100;
    public static final double WATER_HIGH_TEMPERATURE_CHANCE = 0.5;
    public static final double WATER_LOW_TEMPERATURE = 0;
    public static final double WATER_LOW_TEMPERATURE_CHANCE = 0.5;

    // Acid
    public static final String ACID = "acid";
    public static final double ACID_DENSITY = 1826.7;
    public static final double ACID_FSS_SPREAD = 0.05;
    public static final double ACID_FSS_RANGE = 5;


    // GAS TYPES
    public static final String MATTER_GAS = "gas";

    // Air
    public static final String AIR = "air";
    public static final double AIR_DENSITY = 1.2;
    public static final double AIR_FSS_SPREAD = 0.75;
    public static final double AIR_FSS_RANGE = 20;

    // Steam
    public static final String STEAM = "steam";
    public static final double STEAM_DENSITY = 	0.58966;
    public static final double STEAM_FSS_SPREAD = 0.75;
    public static final double STEAM_FSS_RANGE = 15;

    // REACTION TYPES
    public static final String MATTER_REACTION = "reaction";

    // All types in an array
    public static final String[] TYPES = {
            AIR,
            WOOD,
            WATER,
            SAND,
            SALT,
            COAL,
            ACID,
            STEAM,
            ICE
    };
}
