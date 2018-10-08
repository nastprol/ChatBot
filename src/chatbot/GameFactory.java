package chatbot;

public class GameFactory implements IGameFactory {

	private BattleSea game;
	
    @Override
    public IGame create() {

        game = new BattleSea();
        return game;
    }

	@Override
	public IParser createParser() {
		return new Parser(game);
	}
}