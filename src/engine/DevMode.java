package engine;

public class DevMode {

    private static DevMode dev;
    private boolean isDev = true;

    private DevMode() {
    }

    public static DevMode D(){
        return (dev == null ? dev = new DevMode():dev);
    }

    public void setDev(){
        isDev = !isDev;
    }

    public void draw(){

        // FPS

    }

}
