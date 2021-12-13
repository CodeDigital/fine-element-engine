package engine.elements;

import engine.containers.Chunk;
import engine.elements.gases.Air;
import engine.elements.gases.Steam;
import engine.elements.liquids.Acid;
import engine.elements.liquids.Lava;
import engine.elements.liquids.Water;
import engine.elements.solids.*;
import engine.math.Chance;
import engine.math.XMath;

// TODO: Commenting
// TODO: Have a pass at redoing physics statistics (heat, temp changes, chances).
/**
 * The main information class for element information.
 */
public class ElementData {

    // Constraints
    public static final double SPEED_MAX = Chunk.WIDTH;
    public static final double STATIC_FRICTION = 0.95;
    public static final double REST_PRESSURE = XMath.sciNum(1.013, 5);
    public static final Chance DEFAULT_TEMPERATURE_CHANCE = new Chance(0.01);
    public static final double DEFAULT_CONDUCTIVITY_HEAT = 1;

    // Element Information and Types

    // SOLID TYPES
    public static final String MATTER_SOLID = "solid";

    public static final String SAND = "sand";
    public static final double SAND_DENSITY = 1442;
    public static final double SAND_FSS_SPREAD = 0.5;
    public static final double SAND_CONDUCTIVITY_HEAT = 2.05;

    public static final String SALT = "salt";
    public static final double SALT_DENSITY = 1023.6;
    public static final double SALT_FSS_SPREAD = 0.25;
    public static final double SALT_CONDUCTIVITY_HEAT = 0.8;

    public static final String COAL = "coal";
    public static final double COAL_DENSITY = 1683;
    public static final double COAL_FSS_SPREAD = 0.01;
    public static final double COAL_CONDUCTIVITY_HEAT = 0.23;

    public static final String ROCK = "rock";
    public static final double ROCK_DENSITY = 1683;
    public static final double ROCK_FSS_SPREAD = 0.001;
    public static final double ROCK_CONDUCTIVITY_HEAT = 1.8;

    public static final String ICE = "ice";
    public static final double ICE_DENSITY = 917;
    public static final double ICE_FSS_SPREAD = 0.001;
    public static final double ICE_CONDUCTIVITY_HEAT = 2.2;
    public static final double ICE_TEMPERATURE_HIGH = XMath.toKelvin(0);
    public static final String ICE_TEMPERATURE_HIGH_TYPE = ElementData.WATER;

    public static final String WOOD = "wood";
    public static final double WOOD_DENSITY = 800;

    public static final String BOILER = "boiler";
    public static final double BOILER_DENSITY = 1000;

    // LIQUID TYPES
    public static final String MATTER_LIQUID = "liquid";

    // Water
    public static final String WATER = "water";
    public static final double WATER_DENSITY = 998;
    public static final double WATER_FSS_SPREAD = 0.5;
    public static final double WATER_FSS_RANGE = 10;
    public static final double WATER_CONDUCTIVITY_HEAT = 0.6;
    public static final double WATER_TEMPERATURE_LOW = XMath.toKelvin(0);
    public static final String WATER_TEMPERATURE_LOW_TYPE = ICE;
    public static final double WATER_TEMPERATURE_HIGH = XMath.toKelvin(100);
    public static final String WATER_TEMPERATURE_HIGH_TYPE = ElementData.STEAM;

    // Lava
    public static final String LAVA = "lava";
    public static final double LAVA_DENSITY = 3100;
    public static final double LAVA_FSS_SPREAD = 0.01;
    public static final double LAVA_FSS_RANGE = 4;
    public static final double LAVA_CONDUCTIVITY_HEAT = 0.6;
    public static final double LAVA_TEMPERATURE_LOW = XMath.toKelvin(700);
    public static final String LAVA_TEMPERATURE_LOW_TYPE = ROCK;

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
    public static final double AIR_CONDUCTIVITY_HEAT = 2.3;


    // Steam
    public static final String STEAM = "steam";
    public static final double STEAM_DENSITY = 	0.58966;
    public static final double STEAM_FSS_SPREAD = 0.75;
    public static final double STEAM_FSS_RANGE = 15;
    public static final double STEAM_CONDUCTIVITY_HEAT = 0.04;
    public static final double STEAM_TEMPERATURE_LOW = XMath.toKelvin(100);
    public static final String STEAM_TEMPERATURE_LOW_TYPE = WATER;

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
            ICE,
            BOILER,
            ROCK,
            LAVA
    };

    public static Element spawn(String type){
        switch (type){
            case SAND: return new Sand();
            case AIR: return new Air();
            case SALT: return new Salt();
            case COAL: return new Coal();
            case WATER: return new Water();
            case WOOD: return new Wood();
            case ACID: return new Acid();
            case STEAM: return new Steam();
            case ICE: return new Ice();
            case BOILER: return new Boiler();
            case ROCK: return new Rock();
            case LAVA: return new Lava();
            default: return null;
        }
    }
}
