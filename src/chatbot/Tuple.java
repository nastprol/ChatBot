package chatbot;

public class Tuple {
	public final int X;
	public final int Y;
	
	public Tuple()
	{
		this.X = 0;
		this.Y = 0;
	}

	public Tuple(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	@Override
    public String toString() {
		String sY = String.valueOf(Y + 1);
		String sX = String.valueOf((char) (X + 96));
		return sX + " " + sY;
    }

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Tuple)) {
			return false;
		}

		Tuple other_ = (Tuple) other;

		return other_.X == this.X && other_.Y == this.Y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int cons = 13;
		int result = 1;
		result = prime * result + X * cons;
		result = prime * result + Y;
		return result;
	}
}
