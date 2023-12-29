
class Seat {
    
    private int row; //number
    private char seat; //letter ie. A-Z
    private char ticType; //adult, child, or senior


    //default constructor
    public Seat()
    {
        row = 0;
        seat = ' ';
        ticType = ' ';
    }
    
    //overloaded constructor
    public Seat(int r, char s, char t)
    {
        row = r;
        seat = s;
        ticType = t; 
    }
    
    
     //mutators
    public void setRow (int r)
    {
        row = r;
    }
    

    public void setSeat (char s)
    {
        seat = s;
    }
    

    public void setTicType (char t)
    {
        ticType = t;
    }
    
    
    //accessors
    public int getRow ()
    {
        return row;
    }
    

    public char getSeat ()
    {
        return seat;
    }
    


    public char getTicType ()
    {
        return ticType;
    }
    
    public String seatString(){
        return ("" + row + seat);
    }
    
    

    //to string method overrided to return ticket type
    @Override
    public String toString() {
        return Character.toString(ticType);
    }
    
   
    
    
}
