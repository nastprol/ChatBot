package dialog;

import java.util.ArrayList;

import db.IDataBase;

public class Manager implements IManager {
	private IDataBase db;
	
	public Manager(IDataBase db){
		this.db = db;
	}
	
	@Override
	public void initialDialog(int time) {
		ArrayList<Integer> listId = this.db.getIdWithNeedTime(time);
		//TODO вызваю тротлинг и передаю этот лист
	}

}
