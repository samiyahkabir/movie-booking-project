
public class Node {
    private Node Next;
    private Node down;
    private Seat Payload;

    // Default constructor
    public Node() {
        Next = null;
        down = null;
        Payload = new Seat(); // Initialize the Payload with the default constructor of Seat
    }

    // Overloaded constructor
    public Node(Node Next, Node down, Seat Payload) {
        this.Next = Next;
        this.down = down;
        this.Payload = Payload;
    }

    //mutators
    public void setNext(Node Next) {
        this.Next = Next;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public void setPayload(Seat Payload) {
        this.Payload = Payload;
    }

    //accessors
    public Node getNext() {
        return Next;
    }

    public Node getDown() {
        return down;
    }

    public Seat getPayload() {
        return Payload;
    }
    
    public char seatString(){
        return (Payload.getSeat());
    }

    // toString method
    @Override
    public String toString() {
        return Payload.toString(); //use Payload's toString method
    }
}

