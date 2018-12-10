package db;

import java.util.ArrayList;

public interface IDataBase {
	public void connect();
	public void initDatabase();
	public Object getData(int userId);
	public void setDataItem(int userId,Object object, int hour);
	public boolean checkIdIsActive(int idUser);
	public void removeUserData(int userId);
	public ArrayList<Integer> getIdWithTime(int time);
}
