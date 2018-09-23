package chatbot;

public class Ship {
	
	private int countDeck;
	private int X;
	private int Y;
	private Direction direction;
	private State state;
	private int scoreAlive;
	private int number;
	
	Ship(int countDeck, int X, int Y, Direction direction, int number)
	{
		this.number = number;
		this.scoreAlive = countDeck;
		this.state = State.alive;
		this.countDeck = countDeck;
		this.X = X;
		this.Y = Y;
		this.direction = direction;
	}
	
	public int number()
	{
		return this.number;
	}
	
	public int X()
	{
		return this.X;
	}
	
	public int Y()
	{
		return this.Y;
	}
	
	public int countDeck()
	{
		return this.countDeck;
	}
	
	public Direction direction()
	{
		return this.direction;
	}
	
	public State state()
	{
		return this.state;
	}
	
	public void chageState()
	{
		this.scoreAlive--;
		if (this.scoreAlive == 0)
			this.state = State.dead;
		else
			this.state = State.injured;
	}
}
