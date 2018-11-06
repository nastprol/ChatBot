package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import chatbot.BattleSea;
import chatbot.BotMap;
import javassist.NotFoundException;

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
			try {
				Class.forName("org.sqlite.JDBC");
			} catch(ClassNotFoundException ex) {
				ex.printStackTrace();
				System.exit(1);
			}
			String dbUrl = System.getenv("DB_URL");
			c = DriverManager.getConnection(dbUrl);
			if (c.isClosed())
				tryConnect();

			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS db (" + "user_id INT PRIMARY KEY NOT NULL, "
					+ "jsonString TEXT)";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Object getData(int userId) {
		try {

			PreparedStatement stmt = c.prepareStatement("SELECT * FROM db WHERE user_id = ?;");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			String jsonString = rs.getString("jsonString");

			rs.close();
			stmt.close();
			Json json = new Json<BattleSea>( BattleSea.class);
			return json.GetObject(jsonString);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public void removeUserData(int userId) {
		runSql(userId, "DELETE FROM db WHERE user_id = ?");
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
			removeUserData(userId);
			
			Json json = new Json<BattleSea>( BattleSea.class);
			String jsonString = json.GetStringJson(object);

			PreparedStatement stmt;
			stmt = c.prepareStatement("INSERT INTO db(user_id, jsonString) VALUES(?, ?)");
			
			stmt.setInt(1, userId);
			stmt.setString(2, jsonString);

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Integer[] changeIntToInteger(int[] primitiveArray)
	{
		Integer[] objectArray = new Integer[primitiveArray.length];

		for(int ctr = 0; ctr < primitiveArray.length; ctr++) {
		    objectArray[ctr] = Integer.valueOf(primitiveArray[ctr]);
		}
		 return objectArray;
	}
	
	public boolean checkId(int idUser)
	{
		boolean isUserExists = false;
        try (PreparedStatement ps = c.prepareStatement("select 1 from db where user_id = ?")) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    isUserExists = true;
                }
            }
        }
        finally {
        	return isUserExists;
        }
	}
}
