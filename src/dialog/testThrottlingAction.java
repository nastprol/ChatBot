package dialog;

import java.util.ArrayList;

public class testThrottlingAction implements IThrottlingAction{

	public ArrayList<Integer> list = new ArrayList<Integer>();
	
	public void Send(int id) {
		list.add(id);
	}

}
