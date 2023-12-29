

import java.io.*;
import java.util.*;





public class Main
{
    public static Auditorium a1 = new Auditorium();
    public static Auditorium a2 = new Auditorium();
    public static Auditorium a3 = new Auditorium();
    
    
    
    //will reArrange seats when a new seat is added to keep the order selected in order by row number then seat letter
    public static ArrayList<Seat> reArrange(ArrayList<Seat> seats){
        
        ArrayList<Seat> reArranged = new ArrayList<>(seats);

        // Sort the new list based on row and seat character
        reArranged.sort(Comparator.comparingInt(Seat::getRow).thenComparing(Seat::getSeat));
        
        return reArranged;
        
    }
    
    //admin choice- will print a report for each Auditorium status and the overall status for all auditoriums
    public static void printReport() {
        
        //creates an arraylist containing all audistorums
        ArrayList<Auditorium> audis = new ArrayList<>();
        
        audis.add(a1);
        audis.add(a2);
        audis.add(a3);
        
        
        int overallOpen = 0;
        int overallReserved = 0;
        int overallAdult = 0;
        int overallChilren = 0;
        int overallSenior = 0;
        double overallTotal = 0;
        
        
        int audiNum = 1;
        
        //for each Auditorium in the list
        for (Auditorium A: audis)
        {
            Node head = A.first;
            int lineCount = A.ROWS;
            int seatCount = A.SEATS;
            
            //prints out the Auditorium number
            System.out.print("Auditorium " + audiNum + "\t");
            
            

            //calculates the following info for each auditorium
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

                //check if row exists below current row
                if (currentRow != null) {
                    currentRow = currentRow.getDown();
                }
            }
    
            //calculate total sales based on ticket prices
            double totalSales = totalAdults * 10.0 + totalChildren * 5.0 + totalSeniors * 7.5;
    
            //display report for current auditorium to console
            System.out.print((totalSeats - totalTickets) + "\t"); //open seats
            System.out.print(totalTickets + "\t");
            System.out.print(totalAdults + "\t");
            System.out.print(totalChildren + "\t");
            System.out.print(totalSeniors + "\t");
            System.out.printf("$%.2f%n", totalSales);
            
            
            //adds totals for current auditorium to overall totals for all auditoriums
            overallOpen += (totalSeats - totalTickets);
            overallReserved += (totalTickets);
            overallAdult += (totalAdults);
            overallChilren += (totalChildren);
            overallSenior += (totalSeniors);
            overallTotal += (totalSales);
            
    
            //increases to auditorium number and moves onto to next auditorium
            audiNum++;
            
        }
        
        
        //prints out the totals of the following info across every auditorium
        System.out.print("Total\t");
        System.out.print((overallOpen)+ "\t");
        System.out.print(overallReserved + "\t");
        System.out.print(overallAdult + "\t");
        System.out.print(overallChilren + "\t");
        System.out.print(overallSenior + "\t");
        System.out.printf("$%.2f%n", overallTotal);
        System.out.println();
        

        
    }
    
    
    //writes the current status of each Auditorium to its respective file and exits the program
    public static void exitSystem()
    {
        a1.displayReport("A1Final.txt");
        a2.displayReport("A2Final.txt");
        a3.displayReport("A3Final.txt");

    }
    
        
        
        //deletes the order from the customer's orders
        public static void cancelOrder(int orderNum, String username, HashMap<String, ArrayList<Object>> users){
            
            //retrieves the customer's orders
            ArrayList<Object> list = (users.get(username));
            
            //retireves the order to be deleted/cancelled
            Order o = (Order)list.get(orderNum);
            
            //retireves the Auditorium of the order
            Auditorium A = displayAuditoriumChoice(o.auditorium);
            
            //retrieves the seats reseved witin order
            ArrayList<Seat> seats = o.selectedSeats;
            
            int removeIndex = 0;
            
            
            //unreserves the seats reserved in the order
            for (Seat s: seats)
            {

                //for each seat, retrieves the ticket type
                char removed = A.unReserve(s.getRow(), s.getSeat());
                
                
                //based on the ticket type, updates the totals for each type of ticket
                if (removed == 'A')
                {
                    o.adults--;
                }
                
                if (removed == 'C')
                {
                    o.children--;
                }
                
                if (removed == 'S')
                {
                    o.seniors--;
                }
                
                removeIndex++;
                
            }
            

            
            //removes the order from the list, deleting it from the cusotmer's orders
            list.remove(orderNum);
            
            
        }
        
        
        //deletes a ticket from a customer's order
        public static void deleteTickets(int row, char seat, int orderNum, String username, HashMap<String,ArrayList<Object>> users){
            
            //retrieves the customer's orders
            ArrayList<Object> list = (users.get(username));
            //retireves the order to have a ticket deleted/cancelled
            Order o = (Order)list.get(orderNum);
            //retireves the Auditorium of the order
            Auditorium A = displayAuditoriumChoice(o.auditorium);
            //retrieves the seats reseved witin order
            ArrayList<Seat> seats = o.selectedSeats;
            
            boolean rowMatches = false;
            boolean seatMatches = false;
            
            int removeIndex = 0;
            
            
            //for every seat reserved within this order
            for(Seat s: seats)
            {
                //checks if the ticket the user wants removed is found
                if (s.getRow() == row && s.getSeat() == seat)
                {
                    //the row of current seat matches the row selected by user
                    rowMatches = true;
                    //the row of current seat matches the seat letter selected by user
                    seatMatches = true;
                    break;
                }

                
                //tracks the index of the ticket to be removed
                removeIndex++;
                
            }
            
            
            //if the ticket the user wants removed is found
            if (rowMatches && seatMatches)
            {

                //deletes the ticket from the order's reserved tickets
                (o.selectedSeats).remove(removeIndex);
                
                
                //unreserves the seat from the deleted ticket and retrieves the ticket type
                char removed = A.unReserve(row, seat);
                
                
                //based on the ticket type, updates the totals for each type of ticket
                if (removed == 'A')
                {
                    o.adults--;
                }
                
                if (removed == 'C')
                {
                    o.children--;
                }
                
                if (removed == 'S')
                {
                    o.seniors--;
                }
                
                //if selectedSeats is empty, cancels/deletes the entire order
                if ((o.selectedSeats).isEmpty())
                {
                    list.remove(orderNum);
                }
                
                
                
            }
            
            //if the ticket entered by the user is not found
            else
            {
                System.out.println("Seat not located.");
            }

            
            
        }
    
    
    //adds tickets to a customer's order
        public static void addTickets(Scanner scan, int orderNum, String username, HashMap<String, ArrayList<Object>> users){
        
        
        //allows user to select an auditorium from the menu
		        
		        
		        
		        
		        int rowNumber;
		        char seatLetter;
		        int adults;
		        int seniors;
		        int children;
		        int tickets;
		        String[] seatsReserved;
		        
		        //retrieves the customer's orders
		        ArrayList<Object> list = (users.get(username));
		        //retireves the order to have a ticket added to it
    	        Order o = (Order)list.get(orderNum);
    	        
    	        //retireves the auditorium number of the order
    	        int audi = o.auditorium;

		        //retrieves the Auditorium of the order
		        Auditorium A = displayAuditoriumChoice(audi);


		        
		        //the following while statements retrieve all the information about the seat selection by user
		        while (true) 
		        {
                    System.out.print("Enter the row number: ");
                
                    try {
                        //retrieves the row number of the ticket to be added
                        rowNumber = scan.nextInt();
                
                
                        //checks if row input is within valid range
                        if (rowNumber >= 1 && rowNumber <= A.ROWS) {
                            
                            break;
                        } else {
                            //if number out of range, prompts user to enter valid row number
                            System.out.println("Enter a valid row number: ");
                        }
                        //exception handling to handle invalid input ie. a letter
                    } catch (InputMismatchException e) {
                        System.out.println("Enter a valid row number: ");
                        scan.nextLine(); 
                    }
                }
                
                while (true) 
                {
                    System.out.print("Enter the starting seat letter: ");
                
                    try {
                        //retrieves the seat letter of the ticket to be added
                        seatLetter = scan.next().charAt(0);
                
                        //checks if row input is within valid range
                        if (seatLetter >= 'A' && seatLetter <= 'Z') {
                            
                            break;
                        } else {
                            //if number out of range, prompts user to enter valid seat letter
                            System.out.println("Enter a valid starting seat letter: ");
                        }
                        //exception handling to handle invalid input ie. a letter
                    } catch (InputMismatchException e) {
                        System.out.println("Enter a valid starting seat letter: ");
                        scan.nextLine(); 
                    }
                }
                
                while (true) {
                System.out.print("Enter the number of adult tickets: ");
            
                try {
                    //retrieves the number of adult tickets to book
                    adults = scan.nextInt();
            
                    //checks if the input is out of range
                    if (adults >= 0 && adults <= 26) {
   
                        break;
                    } else {
                        //if input is out of range, prompts user to enter again
                        System.out.println("Enter a valid number of adult tickets: ");
                    }
                    //exception handling to handle invalid input ie. a letter
                } catch (InputMismatchException e) {
                    System.out.println("Enter a valid number of adult tickets: ");
                    scan.nextLine(); 
                }
            }
            
            while (true) {
                System.out.print("Enter the number of child tickets: ");
            
                try {
                    //retrieves the number of child tickets to book
                    children = scan.nextInt();
            
                    //checks if the input is out of range
                    if (children >= 0 && children <= 26) {

                        break;
                    } else {
                        //if input is out of range, prompts user to enter again
                        System.out.println("Enter a valid number of child tickets: ");
                    }
                    //exception handling to handle invalid input ie. a letter
                } catch (InputMismatchException e) {
                    System.out.println("Enter a valid number of child tickets: ");
                    scan.nextLine(); 
                }
            }
            
            while (true) 
            {
                    System.out.print("Enter the number of senior tickets: ");
                
                    try {
                        //retrieves the number of senior tickets to book
                        seniors = scan.nextInt();
                
                        //checks if the input is out of range
                        if (seniors >= 0 && seniors <= 26) {
    
                            break;
                        } else {
                            //if input is out of range, prompts user to enter again
                            System.out.println("Enter a valid number of senior tickets: ");
                        }
                        //exception handling to handle invalid input ie. a letter
                    } catch (InputMismatchException e) {
                        System.out.println("Enter a valid number of senior tickets: ");
                        scan.nextLine(); 
                    }
            }
                
                
            //sums up all tickets wanted by user
    	    tickets = adults + children + seniors;
            int[] startingSeat;
            
            //checks if seats selected by user are available
    	    boolean avail = A.checkAvailability (rowNumber, seatLetter, tickets);
    	    ArrayList<Seat> selected = new ArrayList<>();
    	    
    
            //if the seats chosen by user are available 
    	    if (avail)
    	      {
    	        //initializes the selected seats list with the seats selected by user
        		selected = A.reserveSeats (rowNumber, seatLetter, adults, children, seniors);

                //updates the ticket type counter for the order
        		o.adults = o.adults + adults;
        		o.children = o.children + children;
        		o.seniors = o.seniors + seniors;
        		
        		
        		//for every seat to be added to the order
        		for (Seat s: selected)
        		{
        		    //adds to the order's reserved seats
        		    o.selectedSeats.add(s);
        		    
        		    //rearranges the reserved seats to be in order
        		    o.selectedSeats = reArrange(o.selectedSeats);
        		    

        		}



    	      }
    	      
    	      
    	    //if the seats selected by the user were not available, displays message and returns to menu
    	    else
    	      {
    	          System.out.println("Selection not available");

            }   
        
        

        
    }
    
    
    //updates the order based on the selection made by the user
    public static void updateOrder(ArrayList<Object> data, Scanner scan, String username, HashMap<String,ArrayList<Object>> users)
    {
        
        
        //retrieves the user's data, ie. password and orders
        ArrayList<Object> temp = new ArrayList<>(data);
        
            //removes the password to leave only the orders
            temp.remove(0);

            //creates arraylist of orders
            ArrayList<Order> t = new ArrayList<>();
            
            //if there were no orders made by the user
            if (temp.isEmpty())
            {
                System.out.print("No orders");
            }
            
            else
            {
                //for every object in the list after the password was removed
                for (Object obj : temp) 
                {
                    //checks if object is order
                    if (obj instanceof Order) 
                    {
                        //adds order to list of orders
                        t.add((Order) obj);
                    }
                }
        
            double customerTotal = 0;
            int i = 1;
            int choice1 = 0, choice2 = 0;
            
            //displays each order to the user with a number to choose from
                for (Order o : t) 
                {
                    System.out.println(i + ". " + o);
                    System.out.println();
                    i++;

                }
                

            
            boolean validInput = false;
            
            //prompts user to choose an order, if the input is invalid or not a valid order number, 
            //loops again to allow user to enter a valid order number
            while (!validInput || (choice1 >= data.size() || choice1 < 1)) {
                try {
                    choice1 = scan.nextInt();
                    validInput = true;  // Break the loop if the input is valid
                } catch (Exception e) {
                    scan.next();  // Consume the invalid input to prevent an infinite loop
                }
            }
            
            
            
            int orderNum = 1; //represents order number
            
            //matches the order to the selection made by the user
            while (true)
            {
                //check if this is order wanted by the user
                if (orderNum == choice1)
                {
                    break;
                }
                
                //increases and moves onto next order
                orderNum++;
            }
            
            
            //displays menu of choices to user
            System.out.println("1. Add tickets to order");
            System.out.println("2. Delete tickets from order");
            System.out.println("3. Cancel order");
            
            
            //prompts user to choose an option, if the input is invalid or not a valid choice, 
            //loops again to allow user to enter a valid choice
            while (!validInput || (choice2 != 1 && choice2 != 2 && choice2 != 3)) {
                try {
                    choice2 = scan.nextInt();
                    validInput = true;  // Break the loop if the input is valid
                } catch (Exception e) {
                    scan.next();  // Consume the invalid input to prevent an infinite loop
                }
            }
            

            
            
            //if user chooses to add tickets
            if (choice2 == 1)
            {
                //adds ticket to order
                addTickets(scan, orderNum, username, users);
            }
            
            //if user chooses to delete tickets
            else if (choice2 == 2)
            {
                int rowRemove;
                char seatRemove;
                
                //promts for information about the ticket they want to remove
                System.out.print("Enter the row of the seat you want to remove: ");
                rowRemove = scan.nextInt();
                System.out.print("Enter the seat letter of the seat you want to remove: ");
                seatRemove = (scan.next()).charAt(0);
                
                //deletes ticket from order
                deleteTickets(rowRemove, seatRemove, orderNum, username, users);
                
            }
            
            
            //if user decides to cancel order
            else if (choice2 == 3)
            {
                //removes the order from user's orders
                cancelOrder(orderNum, username, users);
            }
            
            
            
            
                
            }
        
        
    }
    
    //displays receipt of all orders made by the user
     public static void displayReceipt (ArrayList<Object> data)
    {
        
        //retrieves the user's data, ie. password and orders
        ArrayList<Object> temp = new ArrayList<>(data);
        //removes the password, leaving only the orders
            temp.remove(0);

            //creates a list to hold the orders
            ArrayList<Order> t = new ArrayList<>();
            
            //if the customer made no orders
            if (temp.isEmpty())
            {
                System.out.println("Customer Total: $0.00");
            }
            
            else
            {
                //for every object in the list after the password was removed
                for (Object obj : temp) 
                {
                    //checks if object is order
                    if (obj instanceof Order) 
                    {
                        //adds order to list of orders
                        t.add((Order) obj);
                    }
                }
        
            double customerTotal = 0;
            
                //iterates through every order in the list of orders
                for (Order o : t) 
                {
                    //prints out the order
                    System.out.println(o);

                    //calculates the order total
                    double orderTotal = (o.adults * 10.00) + (o.children * 5.00) + (o.seniors * 7.50);
                    //displays total of current order to user
                    System.out.printf("Order Total: $%.2f%n", orderTotal);
                    System.out.println();
                    
                    //adds ordder to overall customer total 
                    customerTotal += orderTotal;

                }
                
                //prints out the total amount spent by user for all orders
                System.out.printf("Customer Total: $%.2f%n", customerTotal);
            }
            
         
        
        
    }
    
    
    //displays the orders made by the user
    public static void viewOrders (ArrayList<Object> data)
    {
        
        //retrieves the user's data, ie. password and orders
        ArrayList<Object> temp = new ArrayList<>(data);
        //removes the password, leaving only the orders
            temp.remove(0);
            
            
            
            //to cast each element individually to Order
            ArrayList<Order> t = new ArrayList<>();
            
            //if the customer has not made any orders
            if (temp.isEmpty())
            {
                System.out.print("No orders");
            }
            
            
            else
            {
                //for every object in the list after the password was removed
                for (Object obj : temp) 
                {
                    //checks if object is order
                    if (obj instanceof Order) 
                    {
                        //adds order to list of orders
                        t.add((Order) obj);
                    }
                }
        
            //iterates through every order in the list of orders
                for (Order o : t) 
                {
                    //prints out each order
                    System.out.println(o);
                    System.out.println();
                }
            }

        
        
    }
    

    //reserves the seats selected by the user
    public static Order reserveSeats(Scanner scan){
        
                //prompts user to select an auditorium
		        int audi = auditoriumMenu(scan);

		        //displays the auditorium selected to the user
		        Auditorium A = displayAuditoriumChoice(audi);
		        
		        int rowNumber;
		        char seatLetter;
		        int adults;
		        int seniors;
		        int children;
		        int tickets;
		        String[] seatsReserved;
		        
		        
		        //the following while statements retrieve all the information about the seat selection by user
		        while (true) 
		        {
                    System.out.print("Enter the row number: ");
                
                    try {
                        //retrieves the row number of the ticket to be added
                        rowNumber = scan.nextInt();
                
                
                        //checks if row input is within valid range
                        if (rowNumber >= 1 && rowNumber <= A.ROWS) {
                            
                            break;
                        } else {
                            //if number out of range, prompts user to enter valid row number
                            System.out.println("Enter a valid row number: ");
                        }
                        //exception handling to handle invalid input ie. a letter
                    } catch (InputMismatchException e) {
                        System.out.println("Enter a valid row number: ");
                        scan.nextLine(); 
                    }
                }
                
                while (true) 
                {
                    System.out.print("Enter the starting seat letter: ");
                
                    try {
                        //retrieves the seat letter of the ticket to be added
                        seatLetter = scan.next().charAt(0);
                
                        //checks if row input is within valid range
                        if (seatLetter >= 'A' && seatLetter <= 'Z') {
                            
                            break;
                        } else {
                            //if number out of range, prompts user to enter valid seat letter
                            System.out.println("Enter a valid starting seat letter: ");
                        }
                        //exception handling to handle invalid input ie. a letter
                    } catch (InputMismatchException e) {
                        System.out.println("Enter a valid starting seat letter: ");
                        scan.nextLine(); 
                    }
                }
                
                while (true) {
                System.out.print("Enter the number of adult tickets: ");
            
                try {
                    //retrieves the number of adult tickets to book
                    adults = scan.nextInt();
            
                    //checks if the input is out of range
                    if (adults >= 0 && adults <= 26) {
   
                        break;
                    } else {
                        //if input is out of range, prompts user to enter again
                        System.out.println("Enter a valid number of adult tickets: ");
                    }
                    //exception handling to handle invalid input ie. a letter
                } catch (InputMismatchException e) {
                    System.out.println("Enter a valid number of adult tickets: ");
                    scan.nextLine(); 
                }
            }
            
            while (true) {
                System.out.print("Enter the number of child tickets: ");
            
                try {
                    //retrieves the number of child tickets to book
                    children = scan.nextInt();
            
                    //checks if the input is out of range
                    if (children >= 0 && children <= 26) {

                        break;
                    } else {
                        //if input is out of range, prompts user to enter again
                        System.out.println("Enter a valid number of child tickets: ");
                    }
                    //exception handling to handle invalid input ie. a letter
                } catch (InputMismatchException e) {
                    System.out.println("Enter a valid number of child tickets: ");
                    scan.nextLine(); 
                }
            }
            
            while (true) 
            {
                    System.out.print("Enter the number of senior tickets: ");
                
                    try {
                        //retrieves the number of senior tickets to book
                        seniors = scan.nextInt();
                
                        //checks if the input is out of range
                        if (seniors >= 0 && seniors <= 26) {
    
                            break;
                        } else {
                            //if input is out of range, prompts user to enter again
                            System.out.println("Enter a valid number of senior tickets: ");
                        }
                        //exception handling to handle invalid input ie. a letter
                    } catch (InputMismatchException e) {
                        System.out.println("Enter a valid number of senior tickets: ");
                        scan.nextLine(); 
                    }
            }
                
                
            //sums up all tickets wanted by user
    	    tickets = adults + children + seniors;
            int[] startingSeat;
            
            //checks if seats selected by user are available
    	    boolean avail = A.checkAvailability (rowNumber, seatLetter, tickets);
    	    ArrayList<Seat> selected = new ArrayList<>();
    	    Order order = null;
    
            //if the seats chosen by user are available 
    	    if (avail)
    	      {
    	         
    	        //reserves and adds the reserved seats to the reserved seats
        		selected = A.reserveSeats (rowNumber, seatLetter, adults, children, seniors);
        		//creates an order with the aquired information
        		order = new Order(adults, seniors, children, audi, selected);
        		


    	      }
    	      
    	      

    	   //if the seats chosen by user are not available, reserves the best available seats
    	    else
    	      {
    	          

                //retrieves starting seat of closest consecutive seats matching user's needs
    		    startingSeat = A.bestAvailable(tickets, A.SEATS, A.ROWS);
    		    
    		    
    		    //if no consecutive empty seats were found
    		    if (startingSeat[0] == -1 || startingSeat[1] == -1) 
    		    {
                    System.out.println("No seats available");
                } 
                
                    //if best available seats were found
                    else 
                    {
                        
                        
                        
                        int bestRow = startingSeat[0];
                        int bestSeat = startingSeat[1];
                        
                        //displays bets avail empty consecutive seats to user
                        if (tickets > 1)
                        {
                            System.out.println(bestRow + String.valueOf((char) (bestSeat + 65)) + " - " + bestRow + String.valueOf((char) (bestSeat + tickets + 64)));
                        }
                        
                        else
                        {
                            System.out.println(bestRow + String.valueOf((char) (bestSeat + 65)));
                        }
                        
                        char reserve = ' ';
    
                        //asks user if they would like to book best avail seat, reserves if yes, breaks if not
                        while (true) {
                            System.out.print("Would you like to reserve these seats? (Y/N): ");
                            
                            if (scan.hasNext()) {
                                try {
                                    
                                    reserve = scan.next().charAt(0);
                                    //checks if input is valid
                                    if (reserve == 'Y' || reserve == 'y' || reserve == 'N' || reserve == 'n') {
                                        break; // if valid input, exit the loop
                                    } else {
                                        System.out.println("Enter a valid choice (Y/N): ");
                                    }
                                    //exception handling to handle invalid input ie. a number
                                } catch (StringIndexOutOfBoundsException e) {
                                    System.out.println("Enter a valid choice (Y/N): ");
                                    scan.nextLine(); 
                                }
                            } else {
                                
                                
                                break;
                            }
                        }
                        
                        
                        //if the user decides reserves best available seats
                        if (reserve == ('Y'))
                        {
                            //reserves and adds the best available seats to the reserved seats
                            selected = A.reserveSeats(bestRow, (char)(bestSeat + 65), adults, children, seniors);
                            
                            //creates an order with the aquired information
                            order = new Order(adults, seniors, children, audi, selected);
                        }

                        
                    }

                }   
        

        
        //returns the created order
        return order;
        
    }
    
    
    //diaplay the Auditorium to the user based on their choice
    public static Auditorium displayAuditoriumChoice(int x){
        
        String fileName = "";
        
        //if user selected Auditorium 1
        if(x == 1)
        {
            //prints Auditorium 1 to console
            a1.printAuditorium();
            return a1;
        }
        
        //if user selected Auditorium 2
        else if (x == 2)
        {
            //prints Auditorium 1 to console
            a2.printAuditorium();
            return a2;
        }
        
        //if user selected Auditorium 3
        else if (x == 3)
        {
            //prints Auditorium 1 to console
            a3.printAuditorium();
            return a3;
        }
        
        
        return null;
        
    }
    
    
    //displays the menu of Auditoriums to choose from and returns their selection
    public static int auditoriumMenu(Scanner scan)
    {
        int choice = 0;
        
        System.out.println("1. Auditorium 1");
		System.out.println("2. Auditorium 2");
		System.out.println("3. Auditorium 3");
		
		
		boolean validInput = false;
		
		//valides that the input in valid and inrange
        while (!validInput || (choice != 1 && choice != 2 && choice != 3)) {
            try {
                choice = scan.nextInt();
                validInput = true;  // Break the loop if the input is valid
                //exception handling to handle if invalid input in enterd ie. a letter
            } catch (Exception e) {
                scan.next();  // Consume the invalid input to prevent an infinite loop
            }
        }


		return choice;

    }
    
    
    //if the user is an admin, displays the following choices and returns selection made by admin
    public static int adminMenu(Scanner scan){
        int choice = 0;
        
        //displays menu
        System.out.println("-MAIN MENU-");
		System.out.println("1. Print Report");
		System.out.println("2. Logout");
		System.out.println("3. Exit");


		
		boolean validInput = false;
        //valides that the input in valid and inrange
        while (!validInput || (choice != 1 && choice != 2 && choice != 3)) {
            try {
                choice = scan.nextInt();
                validInput = true;  // Break the loop if the input is valid
                //exception handling to handle if invalid input in enterd ie. a letter
            } catch (Exception e) {
                scan.next();  // Consume the invalid input to prevent an infinite loop
            }
        }
		
		
		return choice;
        
    }
    
    
    
    //if the user is a not an admin, displays the following menu and returns selection
    public static int userMenu(Scanner scan){
        int choice = 0;
        
        System.out.println("-MAIN MENU-");
		System.out.println("1. Reserve Seats");
		System.out.println("2. View Orders");
		System.out.println("3. Update Order");
		System.out.println("4. Display Reciept");
		System.out.println("5. Log out");
		
		
		boolean validInput = false;
		//valides that the input in valid and inrange
        while (!validInput || (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5)) {
            try {
                choice = scan.nextInt();
                validInput = true;  // Break the loop if the input is valid
                //exception handling to handle if invalid input in enterd ie. a letter
            } catch (Exception e) {
                scan.next();  // Consume the invalid input to prevent an infinite loop
            }
        }
		
		return choice;
        
    }
    
    
    //allows the user to log in with their credentials
    public static String login(HashMap<String, ArrayList<Object>> users, Scanner scan)
    {
        //asking user for login information
		String username = ""; 
		String password;
		boolean loggedIn = false;

		
		
		//verifies log in information
		while (!loggedIn)
		{
		    //prompts for username
		    System.out.print("Username: ");
    		username = scan.nextLine();
    		
            //if the username is exists in the database
    		if (users.containsKey(username))
    		{
    		    //prompts for passwoord
    		    System.out.print("Password: ");
    		password = scan.nextLine();
    		
    		//retrieves the data associated with the user ie. password and orders
    		ArrayList<Object> list = (users.get(username));
		
		
		    //keeps track of the attemps made by the user to enter password
		    int attempts = 0;
		    
		    
		    //if the user entered the correct password associated with their username
    		    if (list.contains(password))
        		{
        		    loggedIn = true;
        		}
    		
    		//if the user entered the incorrect password
    		else
    		{
                //loops until a valid password is recieved or user exceeds 3 attempts
    		    while (!(list.contains(password)))
        		{
        		    attempts++;
        		    
        		    //if the user enters wrong password 3 times, return to log in screen
        		    if (attempts == 3)
        		    {
        		        System.out.println("Too many attemps. Unable to log in.");
        		        break;
        		    }
        		    
        		    
        		    System.out.println("Invalid password");
        		    
        		    //prompts for password again
        		    System.out.println("Password: ");
    		        password = scan.nextLine();
    		        
    		        //if the user enters the correct password, logs in
    		        if (list.contains(password))
            		{
            		    loggedIn = true;
            		}
        		}
    		}
    		}
    		
    		
		}
		return username;
    }
    
    
    //creates hashmap with user data
    public static void createDatabase (HashMap<String, ArrayList<Object>> users)
    {
        FileInputStream file = null;
        
        //file check
        try{
            //opens the file containing user data
            file = new FileInputStream("userdb.dat");
        }catch(Exception e)
        {
            //if the file could not be opened
            System.out.println("file not found");
        }
        
        Scanner scan = new Scanner(file);
        
        //reads each line in file
        while (scan.hasNextLine())
        {
            //scans the line as a string
            String line = scan.nextLine();
            //splits the string to separate each element
            String[] info = line.split(" ", -2);
            
            //creates a list to hold the user's data
            ArrayList<Object> userData = new ArrayList<>();
            
            //adds the password (second element in line) into the list
            userData.add(info[1]);

            //maps the username (as key) to list (containing password)
            users.put(info[0],userData);

        }
        

    }
    
    
    
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		
		
		HashMap<String, ArrayList<Object>> users = new HashMap<>();
		
		//reads in database file creates hashmap
		createDatabase(users);

        //creates Auditoriums
        a1.createAuditorium("A1.txt");
        a2.createAuditorium("A2.txt");
        a3.createAuditorium("A3.txt");
		
		String username;
		
		
		boolean exit = false;
		
		//loops unti the admin chooses to exit
		while(!exit)
		{
		    //retrieves the username after the user logs in
		    username = login(users, scan);
		    
		    //if the user logs in as an admin
    		if (username.equals("admin"))
    		{
    		    //displays the menu for admin and retrieves choice
    		    int choice = adminMenu(scan);

    		    
    		    //while admin decides to not log out or exit proggram
    		    while (true)
    		    {
        		        
        		    //if admin chooses to print report
        		    if (choice == 1)
        		    {
        		        //prints report for the Auditoriums
        		        printReport();

        		    }
        		    
        		    //if admin decides to log out
        		    if (choice == 2)
        		    {
        		        //breaks out of loop and returns to log in menu
        		        break;
        		    }
        		    
        		    //if admin decides to exit the program
        		    if (choice == 3)
        		    {

            		    //prints status of each Auditorium to files and exits program
            		    exitSystem();
            		    
            		    exit = true;
        		        
        		        break;
        		        
        		    }
    		        
    		        //allows admin to choose from menu
    		        choice = adminMenu(scan);
    		        
    		    }
    		    
    		    
    		}
    		
    		
    		//if the user that logged in is not an admin
    		else
    		{
    		    //allows user to choose from the user menu
    		    int choice = userMenu(scan);
    		    
    		    //loops until the user decides to log out
    		    while (choice != 5)
    		    {
    		        //if user decided to make a reservation
        		    if (choice == 1)
        		    {
        		        //retrieves order made from reservation
        		        Order order = reserveSeats(scan);
        		        //retrieves the current orders made by user
        		        ArrayList<Object> list = users.get(username);
        		        
        		        //if an order was successfully made
        		        if (order != null)
        		        {
        		            //adds new order to customer's orders
        		            list.add(order);
        		        }
        		        
        		        //updates the hashmap
        		        users.put(username, list);
        
        		    }
        		    
        		    
        		    //if the user decides to view their orders
        		    else if (choice == 2)
        		    {
        		        //retrieves the orders made by the user
        		        ArrayList<Object> list = users.get(username);
        		        //displays the user's orders to console
        		        viewOrders(list);
        		    }
        		    
        		    
        		    //if the user decides to update an order
        		    else if (choice == 3)
        		    {
        		        //retrieves the odres made by the user
        		        ArrayList<Object> list = users.get(username);
        		        //updates the order based on the user's selection
        		        updateOrder(list, scan, username, users);
        		    }
        		    
        		    
        		    //if the user decies to display their reciept
        		    else if (choice == 4)
        		    {
        		        //retrieves the odres made by the user
        		        ArrayList<Object> list = users.get(username);
        		        //displays the total for each order and their grand total
        		        displayReceipt(list);
        		    }
        		    
        		 
        		    System.out.println();
        		    

        		    //allows user to make another choose another option
        		    choice = userMenu(scan);
    		    }
  
    		    
    		}
    		

		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
		
	}
	
	
	
	
	
	
}
