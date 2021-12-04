package engine;

public class Debug {

    private static boolean DEBUGGING = true;
    private static String lastMSG = "";
    private static String lastFrom = "";
    private static int lastLine = -1;
    private static int lastCount = 0;

    public static void print(String msg, String from, int line){
        if(lastMSG.equals(msg) && lastFrom.equals(from) && lastLine == line){
            lastCount ++;
            if(lastCount % 10 == 0){
                System.out.print("0");
            }
        }else{
            System.out.println();
            System.out.print(from + " (" + line + "): " + msg + " | 1");
            lastLine = line;
            lastFrom = from;
            lastMSG = msg;
            lastCount = 1;
        }
    }

    public static boolean isDEBUGGING() {
        return DEBUGGING;
    }

    public static void setDEBUGGING(boolean DEBUGGING) {
        Debug.DEBUGGING = DEBUGGING;
    }
}
