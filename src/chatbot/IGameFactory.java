package chatbot;

import db.IDataBase;

public interface IGameFactory {
	
	IParser createParser();
	IGame create(IDataBase db, int id);
}

