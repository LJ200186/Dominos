package ka4q3;

import java.util.*;

public class KA4Q3 {

    private static String Player1Name;
    private static ArrayList<String> Player1Dominos = new ArrayList<>();
    
    private static String Player2Name;
    private static ArrayList<String>  Player2Dominos = new ArrayList<>();
    
    private static int Turn;
    private static String StartDouble;

    private static String[] DominoSet = {"0:0","0:1","0:2","0:3","0:4","0:5","0:6","1:1","1:2","1:3","1:4","1:5","1:6","2:2","2:3","2:4","2:5","2:6","3:3","3:4","3:5","3:6","4:4","4:5","4:6","5:5","5:6","6:6"};
    
    private static Random random = new Random();
    private static Scanner Input = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("**DOMINOS**\n");
        
        getUsernames(); // Gets players usernames
        
        for (int i = 0; i < 7; i++) {
            Player1Dominos.add(getRandomDomino()); // Adds 7 random dominos to the players hand
        }
        
        for (int i = 0; i < 7; i++) {
            Player2Dominos.add(getRandomDomino()); // Adds 7 random dominos to the players hand
        }
        
        String DoubleOwner = FindDouble(); // Checks who owns the highest double
        
        
        while (DoubleOwner.equals("None")) { // If no doubles are owned
            Player1Dominos.add(getRandomDomino()); // Add an extra domino to player
            Player2Dominos.add(getRandomDomino()); // Add an extra domino to player
            DoubleOwner = FindDouble(); // Checks if anyone has any doubles
        }

        
        if (DoubleOwner.equals("Player1")){
            Turn = 2; // Player 2 next to play
            System.out.println(Player1Name+" places the highest double |"+StartDouble+"|\n");
            
        }else if(DoubleOwner.equals("Player2")){
            Turn = 1; // Player 1 next to play
            System.out.println(Player2Name+" places the highest double |"+StartDouble+"|\n");
            
        }
        
        // INSERT ADDING STARTING DOMINO TO BOARD HERE - not required for test
        
        while(!((Player1Dominos.isEmpty()) || (Player2Dominos.isEmpty()))){ //While players still have dominos
            
            if(Turn==1){
                
                
                String P1Hand = "";
                
                for (int i = 0; i < Player1Dominos.size(); i++) {
                    P1Hand = P1Hand +"|"+Player1Dominos.get(i)+"|, ";
                }
                
                P1Hand = P1Hand.substring(0, P1Hand.length());
                
                System.out.println(Player1Name+"'s turn\n\nYour Hand: "+ P1Hand);
                
                System.out.println("\nDo you want to play (1) or pass (2)");
                int PlayOption = Input.nextInt();
                
                switch(PlayOption){
                    
                    case(1):
                        //Play
                        playRound();
                        break;
                        
                    case(2):
                        
                        String newDomino = getRandomDomino(); // Gets a random domino
                        Player1Dominos.add(newDomino); // Adds domino to players hand
                        System.out.println("You picked up |"+newDomino+"|");
                        break;
                        
                    default:
                        System.out.println("Invalid entry");
                        System.exit(0); // Exits program if invalid entry
                }
                
                Turn = 2;
            }else{
                String P2Hand = "";
                
                for (int i = 0; i < Player2Dominos.size(); i++) {
                    P2Hand = P2Hand +"|"+Player2Dominos.get(i)+"|, ";
                }
                
                P2Hand = P2Hand.substring(0, P2Hand.length());
                
                System.out.println(Player2Name+"'s turn\n\nYour Hand: "+ P2Hand);
                
                System.out.println("\nDo you want to play (1) or pass (2)");
                int PlayOption = Input.nextInt();
                
                switch(PlayOption){
                    
                    case(1):
                        //Play
                        playRound();
                        break;

                    case(2):
                        String newDomino = getRandomDomino(); // Gets a random domino
                        Player2Dominos.add(newDomino); // Adds domino to players hand
                        System.out.println("You picked up |"+newDomino+"|");  
                        break;
                        
                    default:
                        System.out.println("Invalid entry");
                        System.exit(0);
                }
                
                Turn = 1; // Player 1 turn
            }
        }

    } 

    
    private static void playRound(){
        
        System.out.println("Which domino do you want to play?");
        String EnteredDomino = Input.next()
                .replace("|", "");

        while (!(getDominos(Turn).contains(EnteredDomino))) {
            System.out.println("Invalid domino entered, try again: (E.G 5:5)");
            EnteredDomino = Input.next()
                    .replace("|", "");
        }

        getDominos(Turn).remove(EnteredDomino);
        System.out.println("You played |" + EnteredDomino + "|");

        // INSERT ADDING DOMINO TO BOARD HERE - not required for test
        
        if(isDouble(EnteredDomino)){
            System.out.println("You played a double, do you want to play (1) or pass (2)");
            int PlayOption = Input.nextInt();
            
            if(PlayOption == 1){
                playRound();
            }
        }
    }
    
    private static String getName(int Player){ //Returns the players name depending on who's turn it is (stops duplicated code)
        if(Player==1){
            return Player1Name;
        }else{
            return Player2Name;
        }
    }
    
    private static ArrayList<String> getDominos(int Player){ //Returns the players dominos depending on who's turn it is (stops duplicated code)
        if(Player==1){
            return Player1Dominos;
        }else{
            return Player2Dominos;
        }
    }
    
    private static String FindDouble(){ //Returns who owns the highest double
        
        for (int i = Player1Dominos.size(); i != -1; i--) { // Uses player 1 size of list of dominos as this is only called at the start (both hands have the same amount)
            boolean P1Has = false;
            boolean P2Has = false;
            
            for (int j = 0; j < Player1Dominos.size();j++) {
                P1Has = isDouble(Player1Dominos.get(j));
                StartDouble = Player1Dominos.get(j);
            }
            
            if(P1Has == true){
                return "Player1";
            }
            
            for (int j = 0; j < Player2Dominos.size();j++) {
                P2Has = isDouble(Player2Dominos.get(j));
                StartDouble = Player2Dominos.get(j);
            }

            if(P2Has == true){
                return "Player2";
            }
            
            
        }
        
        return "None";
    }
    
    private static boolean isDouble(String Domino){ //Returns if domino is a double
        if(Domino.charAt(0) == Domino.charAt(2)){
            return true;
        }else{
            return false;
        }
    }

    
    private static void outputDominoLists(){ // Used for task 3
        
        System.out.print(Player1Name+" dominos: ");
        
        for (int i = 0; i < Player1Dominos.size(); i++) {
            System.out.print("|"+Player1Dominos.get(i)+"|, ");
        }
        
        System.out.println("");
        
        System.out.print(Player2Name+" dominos: ");
        
        for (int i = 0; i < Player2Dominos.size(); i++) {
            System.out.print("|"+Player2Dominos.get(i)+"|, ");
        }
        
        System.out.println("");
        
        System.out.print("Boneyard dominos: ");
        
        for (int i = 0; i < DominoSet.length; i++) {
            if(!(DominoSet[i].equals("N"))){
                System.out.print("|"+DominoSet[i]+"|, ");
            }
        }
    }
    
    private static void getUsernames(){ // Used to get player names at start of game.
        
        System.out.println("Enter Player 1 name:");
        Player1Name = Input.next();
        
        System.out.println("Enter Player 2 name:");
        Player2Name = Input.next();
    }  
    
    private static String getRandomDomino(){ // Returns a random domino (removes it from the boneyard)
        
        int SelectedDominoPosition = random.nextInt(DominoSet.length); // Gets a random number
        String SelectedDomino = DominoSet[SelectedDominoPosition]; // Finds the random
        
        if(SelectedDomino.equals("N")){
            return (getRandomDomino());
        }else{
            DominoSet[SelectedDominoPosition] = "N"; // Set the domino to N (Removed from boneyard)
            return SelectedDomino;
        }
    }
}

