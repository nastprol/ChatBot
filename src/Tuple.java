package chatbot;

public class Tuple { 
    public final int x; 
    public final int y; 
    
    public Tuple(int x, int y) { 
        this.x = x; 
        this.y = y; 
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tuple)){
            return false;
        }

        Tuple other_ = (Tuple) other;
        
        return other_.x == this.x && other_.y == this.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int cons = 13;
        int result = 1;
        result = prime * result + x * cons;
        result = prime * result + y;
        return result;
    }
}
