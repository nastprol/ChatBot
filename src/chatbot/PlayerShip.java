package chatbot; 

public class PlayerShip extends Ship {
	public PlayerShip()
	{
		
	}
	
	public PlayerShip(int length, int position)
	{
		super(length, position);
	}
	
	public PlayerShip(int position,Orientation orientation,int length)
	{
		super(position, orientation, length);
	}
	
	protected void FirstUpdate(int position) {
		this.position = position;
		this.orientation = Orientation.unknown;
		this.length = 1;
	}
	
	protected void MoveShip(int position) {
		if (this.orientation == Orientation.unknown) {
			if (this.position % 10 == position % 10)
				this.orientation = Orientation.vertically;
			else
				this.orientation = Orientation.horizontally;
		}
		if (this.position > position)
			this.position = position;
		this.length++;
	}
}
