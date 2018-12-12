package dialog;

import java.util.ArrayList;

import db.IDataBase;

public class TestDataBase implements IDataBase {

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDatabase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getData(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataItem(int userId, Object object, int hour) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkIdIsActive(int idUser) {
		return true;
	}

	@Override
	public void removeUserData(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Integer> getIdWithTime(int time) {
		var array = new ArrayList<Integer>();
		array.add(1);
		array.add(2);
		array.add(3);
		return array;
	}

}
