package game;


/**
 * Created by Matthew on 21/10/2016.
 * to simulate day time
 * a ticker zzzz
 */
public class Ticker {


    public final static int MORNING = 21600;
    public final static int NOON = 43200;
    public final static int NIGHT = 64800;
    public final static int A_DAY = 86400;
    public final static int interval = 3600;
    private int seconds = MORNING;
    private long last = System.currentTimeMillis();

    public Ticker() {

    }

    // time in seconds of a day.
    public int getTime() {
        return seconds;
    }

    /**
     *  set the time
     * @param seconds seconds
     */
    public void setTime(int seconds) {
        this.seconds = seconds;
    }

    /**
     *  call to update ticker
     */
    public void update() {
        if (Config.timePass) {
            long curr = System.currentTimeMillis();
            float secondsPassed = (curr - last) / 1000f;
            last = curr;
            this.seconds += secondsPassed * (A_DAY) / Config.seconds_per_day;
            this.seconds = this.seconds % A_DAY;
        }
    }
}
