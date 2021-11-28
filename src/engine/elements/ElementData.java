package engine.elements;

public class ElementData {

    // Element Information and Types

    // SOLID TYPES
    public static final String MATTER_SOLID = "solid";

    // Sand
    public static final String ELEMENT_SAND = "sand";
    public static final double ELEMENT_SAND_DENSITY = 1442;
    public static final double ELEMENT_SAND_INERTIAL_RESISTANCE = 0.1;

    // LIQUID TYPES
    public static final String MATTER_LIQUID = "liquid";


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
            ELEMENT_SAND
    };
}
