package chatbot;

public class GameFactory implements IGameFactory {

    @Override
    public IGame create() {

        final Game game = new Game();
        return game;
    }
}