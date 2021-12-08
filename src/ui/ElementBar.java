package ui;

import engine.Graphics;
import engine.Renderable;
import engine.elements.ElementData;
import engine.math.V2D;

import java.util.ArrayList;

public class ElementBar implements Renderable {

    public static final int PADDING = 10;
    private ArrayList<SpawnButton> spawnButtons = new ArrayList<>();
    private SpawnButton activeSpawnButton = null;

    public ElementBar() {
        int y = Graphics.G().HEIGHT - 2 * SpawnButton.HEIGHT - PADDING;
        int x = 0;
        V2D location = new V2D(x, y);
        for(String type: ElementData.TYPES){
            spawnButtons.add(new SpawnButton(location, type));

            location = location.addX(PADDING + SpawnButton.WIDTH);
            if(location.X + SpawnButton.WIDTH > Graphics.G().WIDTH){
                location = new V2D(0, y + SpawnButton.WIDTH + PADDING);
            }
        }
    }

    public boolean clickOn(V2D point){
        boolean success = false;
        for(SpawnButton b:spawnButtons){
            if(b.isWithin(point)) {
                b.setActive(true);
                activeSpawnButton = b;
                success = true;
            }
        }
        if(success){
            for(SpawnButton b:spawnButtons){
                if(b != activeSpawnButton) b.setActive(false);
            }
        }

        return success;
    }

    @Override
    public void render() {
        for(SpawnButton b: spawnButtons){
            b.render();
        }
    }

    public ArrayList<SpawnButton> getSpawnButtons() {
        return spawnButtons;
    }

    public SpawnButton getActiveSpawnButton() {
        return activeSpawnButton;
    }

    public void setActiveSpawnButton(SpawnButton activeSpawnButton) {
        this.activeSpawnButton = activeSpawnButton;
    }
}
