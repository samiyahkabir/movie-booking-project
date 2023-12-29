
import java.io.*;
import java.util.*;


//this is a custom object class that will contain elements of a customer's order 
//such as the # of each type of ticket reserved and the seats reserved
//orders will be stored in an arraylist which will be mapped to by a username within the HashMap
class Order{
    
    //number of seats per ticket type
    int adults;
    int seniors;
    int children;
    
    //which audi this order is from
    int auditorium;
    
    //contains the seats booked in this order
    ArrayList<Seat> selectedSeats = new ArrayList<>();
    
    
    //default constructor
    public Order()
    {
        adults = 0;
        seniors = 0;
        children = 0;
        selectedSeats = null;
    }
    
    
    //overloaded constructor
    public Order (int a, int s, int c, int audi, ArrayList<Seat> seats)
    {
        adults = a;
        seniors = s;
        children = c;
        auditorium = audi;
        selectedSeats = seats;
    }
    
    
    //prints out the contents of the order
    @Override
    public String toString() {
        
        //adds Auditorium number to string
        String a = "Auditorium " + auditorium + ", ";
        
        int i = 0;
        
        //displays each seat reserved in order
        for (Seat s: selectedSeats)
        {

            //adds each seat's row number + seat letter
            String b = (s.seatString());
            
            //adds a comma after each seat
            if (i != selectedSeats.size()-1)
            {
                b = b + ",";
            }
            
            i++;
            
            //updates string for every seat added
            a = a + b;
        }
        
        
        //final string with all information about order
        a = a + "\n" + adults + " adult, " + children + " child, " + seniors + " senior";
        
        return a;
    }
    
    
}
