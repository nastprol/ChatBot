package db;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import chatbot.BattleSea;

public class DataBase implements IDataBase {

	private Connection c;

	public void connect() {
		try {
			String dbUrl = System.getenv("DB_URL");
			c = DriverManager.getConnection(dbUrl, "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void tryConnect() {
		try {
			c.close();
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		} 
	}

	public void initDatabase() {
		try {
			
			String dbUrl = System.getenv("DB_URL");
			c = DriverManager.getConnection(dbUrl);
			if (c.isClosed())
				tryConnect();

			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS db2 (" + "user_id INT PRIMARY KEY NOT NULL, "
					+ "jsonString TEXT, hour INT)";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Object getData(int userId) {
		try {

			PreparedStatement stmt = c.prepareStatement("SELECT * FROM db2 WHERE user_id = ?;");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			String jsonString = rs.getString("jsonString");

			rs.close();
			stmt.close();
			Json<BattleSea> json = new Json<BattleSea>( BattleSea.class);
			return json.GetObject(jsonString);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	@SuppressWarnings("finally")
	public ArrayList<Integer> getIdWithTime(int time){
		ArrayList<Integer> result = null;
		try {
			PreparedStatement stmt = c.prepareStatement("SELECT * FROM db2 where hour = ?;");
			stmt.setInt(1, time);
			ResultSet rs = stmt.executeQuery();
			result = new ArrayList<Integer>();
			while(rs.next()) {
				result.add(rs.getInt("user_id"));
			}
			rs.close();
			stmt.close();
		}
		finally {
			return result;
		}
	}

	public void removeUserData(int userId) {
		delete(userId);
		setInactive(userId);
	}
	
	public void delete(int userId) {
		runSql(userId, "DELETE FROM db2 WHERE user_id = ?");
	}
	
	private void setInactive(int userId) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("HH");
	    	Date date = new Date();
	    	int hour =  Integer.parseInt(dateFormat.format(date));

			PreparedStatement stmt;
			stmt = c.prepareStatement("INSERT INTO db2(user_id, jsonString, hour) VALUES(?, ?, ?)");
			
			stmt.setInt(1, userId);
			stmt.setString(2, null);
			stmt.setInt(3, hour);
			

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void runSql(int userId, String command) {
		try {
			PreparedStatement stmt = c.prepareStatement(command);
			stmt.setInt(1, userId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void setDataItem(int userId,Object object) {
		try {
			delete(userId);
			
			Json json = new Json<BattleSea>( BattleSea.class);
			String jsonString = json.GetStringJson(object);
			
			DateFormat dateFormat = new SimpleDateFormat("HH");
	    	Date date = new Date();
	    	int hour =  Integer.parseInt(dateFormat.format(date));

			PreparedStatement stmt;
			stmt = c.prepareStatement("INSERT INTO db2(user_id, jsonString, hour) VALUES(?, ?, ?)");
			
			stmt.setInt(1, userId);
			stmt.setString(2, jsonString);
			stmt.setInt(3, hour);
			

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@SuppressWarnings("finally")
	public boolean checkIdIsActive(int idUser)
	{
		boolean isUserExists = false;
        try (PreparedStatement ps = c.prepareStatement("select 1 from db2 where user_id = ?")) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    isUserExists = getData(idUser) != null;
                }
            }
        }
        finally {
        	return isUserExists;
        }
	}
}
