package LA3Q2;
import java.util.*;
import LA3Q1.*;         //importing the classes and methods necessary
import static LA3Q1.AyeshaDemoHashingWithLinearProbing.*;

public class AyeshaDemoHashingWithAllOpenAddressingTech {

    public static void main (String [] lalaland){
        myHeader(3,2);

        System.out.println("Let's demonstrate our understanding on all the open addressing techniques");

        System.out.println("How many data items?");
        items = input.nextInt();            //scanner input takes the number of items user wants


        Scanner ld= new Scanner(System.in);
        System.out.println("What is the load factor? Recommended (<=0.5): ");
        lf = ld.nextDouble();           //load factor input


        //calculating the table capacity based on the number of items and load factor that the user wants
        tableCap= checkPrime((int)(items/lf));
        System.out.println("The minimum required table capacity would be: " + tableCap);

        AyeshaValueEntry[] a = new AyeshaValueEntry[tableCap];
        hashTable = a;


        //instantiating the content of our array (table) with the no argument constructor that we defined before
        for(int i=0; i<tableCap; i++){
            hashTable[i] = new AyeshaValueEntry();
        }

        //creating integer type arrays that will be used as a given data for our program
        Integer [] ayesha = {7,14,-21,-28,35};
        //Integer[] prof = {-11,22,-33,-44,55};
        //Integer[] prof2={7,14,-21,-28,45};

        System.out.println("Given dataset:");
        printArray(ayesha);

        //adding the array values to the hashtable using linear probing
        System.out.println("Adding data - linear probing resolves collision");
        for(int i=0; i<ayesha.length; i++){
            addValueLinearProbe(ayesha[i]);
        }

        System.out.print("The current hashtable: ");
        printHashTable();

        //removing all the values in the hashtable and 'emptying it'
        emptyHashTable();

        //readding the integer array values to our hashtable using quadratic probing
        System.out.println("\nAdding data - quadratic probing resolves collision");
        for(int i=0; i< ayesha.length; i++){
            addValueQuadraticProbe(ayesha[i]);
        }

        System.out.print("The current Hashtable ");
        printHashTable();

        //emptying the hashtable again
        emptyHashTable();

        System.out.println("\nAdding data - double hashing resolves collision");
        System.out.println("The q value for double hashing is " + thePrimeNumberForSecondHashFunction(tableCap));
        //adding the integer array values using the double hashing method defined
        for(int i=0; i<ayesha.length; i++){
            addValueDoubleHashing(ayesha[i]);
        }

        System.out.print("The current hash table: ");
        printHashTable();

        myFooter(3,2);

    }

    public static void addValueDoubleHashing(Integer f) {
        //this method will take a value as input to place in the array/tree using the double hashing method we defined
        //int prime2 = thePrimeNumberForSecondHashFunction(tableCap);

        int place = f.hashCode() % tableCap;
        if (place < 0) {
            //if the index generated is a negative value, we can convert it to a positive value by simply adding the capacity to it
            place = place + tableCap;
        }

        if ((hashTable[place].getKey() != -1 && (hashTable[place].getKey() != -111))) {
            //defining the double hashing function in the scenario that a collision occurs.

            int q = thePrimeNumberForSecondHashFunction(tableCap);  //using the prime number function I previously defined to find a q value

            //using the second function to find an index
            int place2 = q - (f % q);

            //taking out current index/position into account and setting the function so it finds a position as the multiplier
            //is incremented for each iteration.
            int probe=0;
            int j=0;    //multiplier
            while(hashTable[probe].getKey() != -1 && hashTable[probe].getKey() != -111) {
                j++;
                probe= ((place + (j * place2))) % tableCap;

            }

            hashTable[probe].setKey(f);
        } else{
            hashTable[place].setKey(f);
        }
    }

    public static void addValueQuadraticProbe(Integer y) {
        //this method will take a value as input and place it in our data based on quadratic probing

                int place = y  % tableCap;
                if (place < 0) {
                        //if the index generated is a negative value, we can convert it to a positive value by simply adding the capacity to it
                     place = place + tableCap;
                    }
                int n= 1;


                while ((hashTable[place].getKey() != -1 && (hashTable[place].getKey() != -111 && n < tableCap))) {
                    //these values from previous methods check that incase the index generated already has an item in place
                    System.out.println();
                    //using quadratic probing to generate an index for our data
                    place = ((y%tableCap) + ((int) Math.pow(n, 2))) % tableCap;
                    n++;

                    if (place > tableCap) {
                        //if the index comes out to be larger than the table capacity, simply subtract the table capacity from it and use as index
                        place = place - tableCap;
                    }
                    if (place < 0) {
                        //if the index generated is a negative value, we can convert it to a positive value by simply adding the capacity to it
                        place = place + tableCap;
                    }
//

                    //check the next spot to place the item, according to quadratic probing

                }

                if(n>=(tableCap)){
                    //if our multiplier increments to aobve the table capacity, the load factor is too large double probing to work, so we print an error message
                        System.out.println("Probing failed! Please select a load factor less than 0.5!");
                        System.out.println("Goodbye!");
                }

                hashTable[place].setKey(y);

        }





    public static void printArray(Integer [] k){
        //this method will print out our integer array based on the desired outcome

        String s= "[";
        System.out.print(s + "");
        for(int i=0; i<k.length; i++){
            System.out.print(k[i] + ",");

        }
        System.out.println("\b]");

    }

    public static void emptyHashTable(){

        //for loop traverses through the entire table contents and sets them to -1 as flag for labelling them null
        for(int i=0; i<tableCap; i++){
            hashTable[i].setKey(-1);
        }
    }

    public static int thePrimeNumberForSecondHashFunction(int s){
        //prime numbers are odd except 2, making that check:
        if (s%2 !=0){
            s = s-2;
        }else{
            s--;    //decrement s by 1 if it is divisible by 2
        }

        int i, j;
        //finding the previous prime number, based on our current value which is the table capacity
        //and this is done by doing some tests on the capacity

        for( i=s; i>=2; i-=2){
            if(i%2==0){
                continue;
                //divisible by 2 so not a prime number
            }
            //using the given prime number method:
            for(j=3; j<=i/2; j+=2 ){
                if(i%j ==0){
                    break;
                }
            }
            if (j>i/2){
                return checkPrime(i);
            }
        }

        return 2;   //if everything fails then the returned number will be 2, as a formality to close off the method


        }

    public static void myHeader(int c, int d){
        //creating a header method lists all regarding information

        System.out.print("Lab Assignment=" + c + "Q"+ d+" \n Prepared by: Ayesha Qaisar " + " \n Student Number: 251255914 " +
                "\n Goal of this exercise: We will be working with hashtables and some of the operations we can perform. \n");


        System.out.println("============================================================================================= \n");
    }

    public static void myFooter(int labnum, int labq){

        //a footer method to sign out of the program once main task is completed, and it is called by the main method

        System.out.println("============================================================================================= \n");
        System.out.print("Completion of lab Assignment " + labnum + "-Q" + labq + " is successful! \n Signing off - Ayesha :)");
        System.out.println("=============================================================================================");


    }





}
