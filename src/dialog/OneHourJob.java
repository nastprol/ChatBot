package dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

class OneHourJob extends TimerTask {

    @Override
    public void run() {
    	DateFormat dateFormat = new SimpleDateFormat("HH");
    	Date date = new Date();
    	var hour =  Integer.parseInt(dateFormat.format(date));
    	//System.out.println(hour);
    	//TODO вызвать менеджера
    }
}
