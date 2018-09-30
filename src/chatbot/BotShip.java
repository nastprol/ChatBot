package chatbot;

public class BotShip extends Ship {
	
	private State state;
	private int scoreAlive;
	public final int IdNumber;
	public final int X;
	public final int Y;
	
	BotShip(int countDeck, int position, Orientation orientation, int number, int x, int y)
	{	
		super(countDeck, position);
		this.X = x;
		this.Y = y;
		this.IdNumber = number;
		this.scoreAlive = countDeck;
		this.state = State.alive;
		this.orientation = orientation;
	}
	
	public int Position()
	{
		return this.position;
	}
	
	public int CountDeck()
	{
		return this.length;
	}
	
	public Orientation Orientation()
	{
		return this.orientation;
	}
	
	public State State()
	{
		return this.state;
	}
	
	public int ScoreAlive()
	{
		return this.scoreAlive;
	}
	
	public void —hageState()
	{
		this.scoreAlive--;
		if (this.scoreAlive == 0)
			this.state = State.killed;
		else
			this.state = State.damaged;
	}
}
