package dialog;

import java.util.Timer;

public class HourTimer implements ITimer {
    public void start() {
        OneHourJob hourJob = new OneHourJob();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(hourJob, 0, 5000 * 60 * 60); 
    }
}