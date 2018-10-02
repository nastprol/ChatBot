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
	
	public void FirstUpdate(int position) {
		this.position = position;
		this.orientation = Orientation.unknown;
		this.length = 1;
	}
	
	public void MoveShip(int position) {
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
