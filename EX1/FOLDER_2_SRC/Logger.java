import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by giladi on 30/10/2017.
 */
public class Logger {

    private Level filterLevel;
    private String logPath;

    public Logger(Level level, String logPath){
        this.filterLevel = level;
        this.logPath = logPath;
    }

    public enum Level {
        SILENT(-1, "SILENT"), DEBUG(1,"DEBUG"), INFO(2,"INFO "), WARNING(3,"WARN "), ERROR(4,"ERROR");
        private int value;
        private String tag_name;

        Level(int value, String tag_name) {
            this.value = value;
            this.tag_name = tag_name;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            return this.tag_name;
        }
    }

    // For conveniently accessing these levels directly from static Logger instead of Logger.Level
    public static final Level SILENT = Level.SILENT; // will turning off the prints, will be used only to write to log file
    public static final Level DEBUG = Level.DEBUG;
    public static final Level INFO = Level.INFO;
    public static final Level WARNING = Level.WARNING;
    public static final Level ERROR = Level.ERROR;


    public void log(Level level, String message) {

        String out = "[" + level + "]" + " "+message;
        if (level.getValue() >= filterLevel.getValue()) {
            if(filterLevel.getValue() > 0)
                System.out.println(out);
            try{
                PrintWriter outWriter = new PrintWriter(logPath);
                outWriter.println(out);
                outWriter.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
