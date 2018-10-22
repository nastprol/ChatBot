package chatbot;

public class Ship {
	protected int length;
	protected int position;
	protected Orientation orientation;
	
	public Ship(int length, int position)
	{
		this.length = length;
		this.position = position;
	}
	
	public Ship(int position,Orientation orientation,int  length)
	{
		this.length = length;
		this.position = position;
		this.orientation = orientation;
	}
} 
