
import java.util.*;
import java.io.*;

public class Auditorium {
    
    // head pointer
     Node first;
     int ROWS;
     int SEATS;
     
     

     
     //retrives the number of rows within the auditorium
     public void numRows(String fileName)
     {
         try {
            File file = new File(fileName);
            Scanner scan = new Scanner(file);

            int rows = 0; 
            int seats = 0;

            // Read lines from the file
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                seats = line.length();

                rows++; // Increment rows count
            }

            scan.close();
            ROWS = rows;
            
            //return rows; 
        
        //FILE CHECK: if the file entered by user is not found
         }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            //return 0; 
        }
     }
     
     
     
    //retrives the number of seats within the auditorium
     public void numSeats(String fileName)
     {
         try {
            File file = new File(fileName);
            Scanner scan = new Scanner(file);

            int rows = 0; 
            int seats = 0;

            // Read lines from the file
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                seats = line.length();

                rows++; 
            }

            scan.close();
            
            SEATS = seats;
            //return seats; 
        
        //FILE CHECK
         }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            //return 0; 
        }
     }

     
     
     
    
    
    //creates linked list of linked lists to hold auditorium contents
    public Node createAuditorium(String fileName) {
    Node prevRowHead = null; // keep track of the previous row's head
    Node firstRowHead = null; // store the head of the first row
    
    
        numRows(fileName);
        numSeats(fileName);

    try {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        int rows = 0; 

        //read lines from the file
        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            //create new linked list for the current row
            Node rowHead = null;

            //populate inked list for the current row
            for (int i = 0; i < line.length(); i++) {
                char ticket = line.charAt(i);
                char seat = (char) (i + 65);
                rowHead = insert(rowHead, rows, seat, ticket);
            }

            //connect down pointer to the previous row's head
            if (prevRowHead != null) {
                prevRowHead.setDown(rowHead);
            } else {
                //store head of first row
                firstRowHead = rowHead;
            }

            //update previous row's head
            prevRowHead = rowHead;

            rows++; 
        }

        scan.close();

        first = firstRowHead;
        return firstRowHead; //return head of first row
        
    } catch (FileNotFoundException e) {
        
        System.out.println("An error occurred.");
        e.printStackTrace();
        return null; 
    }
}

    
    
    
    //insets node with seat info into auditorium
    Node insert(Node head, int row, char seat, char tic) {
        Node newNode = new Node();
        Seat s = new Seat(0, seat, tic);
        newNode.setPayload(s);
        newNode.setNext(null);

        //return new node as head if list is empty
        if (head == null) {
            return newNode; 
        }

        Node current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newNode);
        
        //returns head with new node attatched to list
        return head;
    }
    

    
    
    
    //prints auditorium to console
    public void printAuditorium() {
        Node head = first;
        int seatCount = SEATS;
        //prints out seat chars A-Z
        System.out.print("  ");
        for (int i = 0; i < seatCount; i++) {
            System.out.print((char) ('A' + i));
        }
        System.out.println();
    
        
        Node current = head;
        int rowNumber = 1;
    
    
        //traverse through auditorium
        while (current != null) {
            
            System.out.print(rowNumber + " ");
            Node ptr = current;
    
            //traverse through row
            while (ptr != null) {
                if (!ptr.toString().equals(".")) {
                    System.out.print('#');
                } else {
                    //prints out ticket type of seat
                    System.out.print(ptr.toString());
                }
                ptr = ptr.getNext();
            }
            
            System.out.println();
            //moves down to next row
            current = current.getDown();
            rowNumber++;
        }
}

    
    
    
    //checks availability of seat(s) selected by user
    boolean checkAvailability(int row, char seat, int tickets) {
        
        Node head = first;
        row -=1;
        Node ptr = head;
        boolean available = true;
        int seatPos = seat - 'A';
        
        //traverses to row selected by user
        for (int i = 0; i < row; i++)
        {
            ptr = ptr.getDown();
        }
        
        //traverses to seat selected by user
        for (int i = 0; i < seatPos; i++)
        {
            ptr = ptr.getNext();
        }
        
        
        //check seat(s) selected to see if they are empty
        for (int i = 0; i < tickets; i++) {
            if (!ptr.toString().equals(".")) 
            {
                available = false;
                break;
            }
            

            ptr = ptr.getNext();
        }
        
        
        
        return available;
        

    }

    
    //reserves seats selected/offered to user
    ArrayList<Seat> reserveSeats(int row, char seat, int adults, int children, int seniors) {
        Node head = first;
    Node ptr = head;
    int seatPos = seat - 'A';
    ArrayList<Seat> seatsSelected = new ArrayList<>();

    //move to the specified row
    for (int i = 1; i < row; i++) {
        if (ptr.getDown() == null) {
            // if specified row doesn't exist
            System.out.println("Row " + row + " does not exist.");
            return null;
        }
        ptr = ptr.getDown();
    }

    //move to the specified seat
    for (int i = 0; i < seatPos; i++) {
        if (ptr.getNext() == null) {
            //if specified seat doesn't exist
            System.out.println("Seat " + seat + " does not exist in row " + row + ".");
            return null;
        }
        ptr = ptr.getNext();
    }

    //reserve seats for adults, children, and seniors
    for (int i = 0; i < adults; i++) {
        ptr.getPayload().setTicType('A');
        char x = (ptr.toString()).charAt(0);
        Seat s = new Seat(row, ptr.seatString(), x);
        seatsSelected.add(s);
        
        ptr = ptr.getNext();
        
   
        
        
    }

    for (int i = 0; i < children; i++) {
        ptr.getPayload().setTicType('C');
        char x = (ptr.toString()).charAt(0);
        Seat s = new Seat(row, ptr.seatString(), x);
        seatsSelected.add(s);
        ptr = ptr.getNext();
    }

    for (int i = 0; i < seniors; i++) {
        ptr.getPayload().setTicType('S');
        char x = (ptr.toString()).charAt(0);
        Seat s = new Seat(row, ptr.seatString(), x);
        seatsSelected.add(s);
        ptr = ptr.getNext();
    }
    
    return seatsSelected;
}



char unReserve(int row, char seat) {
        Node head = first;
    Node ptr = head;
    int seatPos = seat - 'A';
    ArrayList<Seat> seatsSelected = new ArrayList<>();

    //move to the specified row
    for (int i = 1; i < row; i++) {
        if (ptr.getDown() == null) {
            // if specified row doesn't exist
            System.out.println("Row " + row + " does not exist.");
            //return null;
        }
        ptr = ptr.getDown();
    }

    //move to the specified seat
    for (int i = 0; i < seatPos; i++) {
        if (ptr.getNext() == null) {
            //if specified seat doesn't exist
            System.out.println("Seat " + seat + " does not exist in row " + row + ".");
            //return null;
        }
        ptr = ptr.getNext();
    }
    
    
    
    char oldTicType = ptr.getPayload().getTicType();
    ptr.getPayload().setTicType('.');


    return oldTicType;
}


    //displays final report to console and prints auditorium status to A1.txt file
    void displayReport(String file) {
        Node head = first;
        int lineCount = ROWS;
        int seatCount = SEATS;
        
        
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(file));
    
            int totalSeats = lineCount * seatCount;
            int totalTickets = 0;
            int totalAdults = 0;
            int totalChildren = 0;
            int totalSeniors = 0;
    
            Node currentRow = head;
            
            //traverses through auditorium
            for (int i = 0; i < lineCount; i++) {
                Node ptr = currentRow; // Initialize ptr for the current row
    
    
                //traverses through row and adds to total for each ticket type and total ticket count
                while (ptr != null) {
                    fileOut.print(ptr.toString());
                    
                    
                    if (ptr.toString().equals("A"))
                    {
                        totalAdults++;
                        totalTickets++;
                    }
                    
                    else if (ptr.toString().equals("C"))
                    {
                        totalChildren++;
                        totalTickets++;
                    }
                    
                    else if (ptr.toString().equals("S"))
                    {
                        totalSeniors++;
                        totalTickets++;
                    }
                    
                    ptr = ptr.getNext();
                }
                fileOut.println();
    
                //check if row exists below current row
                if (currentRow != null) {
                    currentRow = currentRow.getDown();
                }
            }
    
            //calculate total sales based on ticket prices
            double totalSales = totalAdults * 10.0 + totalChildren * 5.0 + totalSeniors * 7.5;
    

    
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }



//MERGED

int[] bestAvailable(int tickets, int seatCount, int numRows) {
    Node head = first;
    boolean valid;
    int bestRow = -1;
    int bestStart = -1;

    if (seatCount % 2 == 1)
    {
            double minDistFromCenter = Math.pow(Math.pow(seatCount / 2.0, 2.0) + Math.pow((numRows - 1) / 2.0, 2.0), 0.5);
    
        Node current = head;
    
        for (int row = 0; row < numRows; row++) {
            Node ptr = current;
    
            for (int i = 0; i <= seatCount - tickets; i++) {
                if (ptr.toString().equals(".")) {
                    int count = 1;
                    int j = i + 1;
                    Node ptr2 = ptr;
    
                    do {
                        ptr2 = ptr2.getNext();
    
                        if (ptr2 != null && ptr2.toString().equals(".")) {
                            valid = true;
                        } else {
                            valid = false;
                            break;
                        }
    
                        count++;
                        j += 1;
                    } while (count < tickets);
    
                    if (valid || tickets == 1) {
                        double curDistFromCenter = Math.pow(Math.pow(Math.abs(seatCount / 2.0 - (i + tickets / 2.0)), 2.0) +
                                Math.pow(Math.abs((numRows - 1) / 2.0 - row), 2.0), 0.5);
    
                        if (curDistFromCenter < minDistFromCenter && valid) {
                            minDistFromCenter = curDistFromCenter;
                            bestRow = row + 1;
                            bestStart = i;
                        } else if (curDistFromCenter == minDistFromCenter && valid) {
                            if (Math.abs(row - (numRows - 1) / 2.0) < Math.abs(bestRow - (numRows - 1) / 2.0)) {
                                minDistFromCenter = curDistFromCenter;
                                bestRow = row + 1;
                                bestStart = i;
                            } else if (Math.abs(row - (numRows - 1) / 2.0) == Math.abs(bestRow - (numRows - 1) / 2.0)) {
                                if (row + 1 < bestRow) {
                                    minDistFromCenter = curDistFromCenter;
                                    bestRow = row + 1;
                                    bestStart = i;
                                }
                            }
                        }
                    }
                }
    
                ptr = ptr.getNext();
            }
    
            if (current.getDown() == null) {
                break;
            }
    
            current = current.getDown();
        }
    
        if (bestRow == -1 || bestStart == -1) {
            return new int[]{-1, -1};
        } else {
            return new int[]{bestRow, bestStart};
        }
    }
    
    else if (seatCount % 2 == 0)
    {
        
        double center = (seatCount+1) / 2.0; //calculates the center seat 
    double centerRow = (numRows+1)/2.0; //calculates the center row

    double distance = Math.pow( Math.pow(seatCount/2.0,2.0) + Math.pow(numRows/2.0,2.0) , 0.5); // Initialize the minimum distance from the center

    Node current = head;

    // Loop through rows
    for (int row = 0; row < numRows; row++) {
        Node ptr = current;

        // To find starting seat position with the best available seats (closest to center)
        for (int i = 0; i <= seatCount - tickets; i++) {
            if (ptr.toString().equals(".")) {
                int count = 1;
                int j = i + 1;

                // To find the # of empty seats needed that are next to each other
                Node ptr2 = ptr;

                do {
                    ptr2 = ptr2.getNext();

                    if (ptr2 != null && ptr2.toString().equals(".")) {
                        valid = true;
                    } else {
                        valid = false;
                        break;
                    }

                    count++;
                    j += 1;
                } while (count < tickets);

                if (valid || tickets == 1) {
                    // Find the closest consecutive seats by comparing distances

                    
                    //if current seating is closer than current closest seating
                    if ((Math.sqrt(Math.abs((Math.pow(((double)(row+1) - centerRow),2.0) + Math.pow(((double)(i+1) - center),2.0))))) < distance) {
                        distance = (Math.sqrt(Math.abs((Math.pow(((double)(row+1) - centerRow),2.0) + Math.pow(((double)(i+1) - center),2.0)))));
                        

                        bestRow = row + 1; 
                        bestStart = i; 


                    //if the current seating has the same distance
                    } else if ((Math.sqrt(Math.abs((Math.pow(((double)row - centerRow),2.0) + Math.pow(((double)i - center),2.0))))) == distance) {


                        //assigns bestRow to row with distance from center row
                        if ((row - centerRow) < (bestRow - centerRow)){
                            bestRow = row + 1;
                            bestStart = i;
                        }
                        
                        
                        //assigns bestRow to row with lesser value
                        else if ((row - centerRow) == (bestRow - centerRow))
                        {
                            if (row < bestRow)
                            {
                                bestRow = row + 1;
                                bestStart = i;
                            }
                        }
                    }

                }
            }

            //moves onto next seat
            ptr = ptr.getNext();
        }

        //reached end of auditorium
        if (current.getDown() == null) {
            break; 
        }
        
        //moves onto next row
        current = current.getDown();
    }


    //if no available seats found
    if (bestRow == -1 || bestStart == -1) {
        return new int[]{-1, -1}; 
    } else {
        //return both row and seat position
        return new int[]{bestRow, bestStart}; 
    }
        
        
        
        
    }
    
    
    //if no available seats found
    if (bestRow == -1 || bestStart == -1) {
        return new int[]{-1, -1}; 
    } else {
        //return both row and seat position
        return new int[]{bestRow, bestStart}; 
    }
    
}

















    
    
    
}





