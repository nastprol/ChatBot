package dialog;

import java.util.ArrayList;

import db.IDataBase;

public class Manager implements IManager {
	private IDataBase db;
	private IThrottlingClass throttling;
	
	public Manager(IDataBase db, IThrottlingClass th){
		this.throttling = th;
		this.db = db;
	}
	
	@Override
	public void initialDialog(int time) {
		ArrayList<Integer> listId = this.db.getIdWithTime(time);
		if (listId != null && !listId.isEmpty()) {
			this.throttling.Throttling(listId);
		}
	}
}
