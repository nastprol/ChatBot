package chatbot;

import db.IDataBase;

public class GameFactory implements IGameFactory {

	private BattleSea game;
	
    @Override
    public IGame create(IDataBase db, int id) {
    	
        game = db.checkId(id)?(BattleSea) db.getData(id): new BattleSea(id);
        return game;
    }

	@Override
	public IParser createParser() {
		return new Parser(game);
	}
}