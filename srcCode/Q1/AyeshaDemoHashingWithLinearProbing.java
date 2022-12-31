package LA3Q1;

import com.sun.net.httpserver.Headers;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;

public class AyeshaDemoHashingWithLinearProbing {           //setting up some static fields which I will use for my methods later on
    public static int items;
    public static Scanner input = new Scanner(System.in);
    public static double lf;
    public static int tableCap;
    public static AyeshaValueEntry[] hashTable;
    public static AyeshaValueEntry[] workingHashTable;
    public static int numbers;

    public static void main(String [] hashtableee){
        myHeader(3,1);

        System.out.println("Let''s decide on the initial capacity based on the load factor and data set size");

        System.out.println("How many data items?");
        items = input.nextInt();            //scanner input takes the number of items user wants


        Scanner ld= new Scanner(System.in);
        System.out.println("What is the load factor? Recommended (<=0.5): ");
        lf = ld.nextDouble();           //load factor input


        //calculating the table capacity based on the number of items and load factor that the user wants
        tableCap= checkPrime((int)(items/lf));
        System.out.println("The minimum required table capacity would be: " + tableCap);

        //creating a new array and assigning it to out hashtable
        AyeshaValueEntry[] a = new AyeshaValueEntry[tableCap];
        hashTable = a;

        int data;

        //instantiating the content of our array (table) with the no argument constructor that we defined before
        for(int i=0; i<tableCap; i++){
            hashTable[i] = new AyeshaValueEntry();
        }

        //populating our table with values that the user has entered, based on the hash function described in the adding method (linear probing)
        for(int j=0; j<items; j++){
            System.out.println("Enter number " +(j+1) + ":");
            numbers = input.nextInt();
            addValueLinearProbe(numbers);
        }

        System.out.print("The current hashtable: ");
        printHashTable();

        System.out.println("\n Let's remove two values from the table and then add one");

        //asking the user to select which two values to remove from the table and to add a new one
        int remove1;
        System.out.println("Enter a value you want to remove: ");
        remove1 = input.nextInt();
        //removing first value using a defined method
        removeValueLinearProbe(remove1);
        System.out.print(" The current hashtable: ");
        printHashTable();

        int remove2;
        System.out.println("Enter a value you want to remove:");
        remove2= input.nextInt();
        //removing second value and printing the hashtable after it has been removed
        removeValueLinearProbe(remove2);
        System.out.print(" The current hashtable: ");
        printHashTable();

        //adding a new value to our table, also based on the user input
        int add1;
        System.out.println("Enter a value to add to the table:");
        add1= input.nextInt();
        addValueLinearProbe(add1);
        System.out.print(" The current hashtable: ");
        printHashTable();

        System.out.println("Rehashing the table:");
        //rehashing the table using a method that outlines the new hash function and then priting the table after it has been rearragned with the new function
        reHashingWithLinearProbe();
        System.out.print(" The current hashtable: ");
        printHashTable();

        myFooter(3,1);


    }

    public static void reHashingWithLinearProbe(){
        //initializing the new table with the old table's size and copying the values over

        workingHashTable = new AyeshaValueEntry[hashTable.length];

        //coping the elements in our hashtable to another array, and clearing out any available slots to become null
        System.arraycopy(hashTable,0,workingHashTable,0,hashTable.length);
        for( int i=0; i< workingHashTable.length; i++){
            if(workingHashTable[i].getKey()==-111){
                workingHashTable[i].setKey(-1);
            }
        }

        //doubling the table capacity, and still making sure that the capacity is a prime number
        tableCap =checkPrime((tableCap*2));
        System.out.println("The rehashed table capacity: " +tableCap);

        //creating a new hashtable, with the new table capacity
        hashTable= new AyeshaValueEntry[tableCap];

        //instantiating the new hashtable with values from the no argument constructor
        for (int i =0; i<tableCap; i++){
            hashTable[i]= new AyeshaValueEntry();
        }

        //adding the values from our old hash table to the new hashtable, based on our previously defined hash function
        for(int j=0; j< workingHashTable.length; j++){
            addValueLinearProbe(workingHashTable[j].getKey());
        }

//
    }

    public static void removeValueLinearProbe(Integer b){
        boolean keyFound = false;       //setting a boolean flag

        for(int i=0; i<tableCap; i++){
            //the for loop traverses through the items in the table

            if (((hashTable[i].getKey()).compareTo(b))==0){
                //if statemment checks to find a match in the traversing items for the key that is sent as a parameter, to be removed

                hashTable[i].setKey(-111) ;
                //once there is a match found, the key is updated to -111 and the boolean flag returns true
                keyFound= true;
                System.out.println(b + " is Found and removed! ");
            }
        }
        if(keyFound == false){
            //if the boolean flag, after traversing and searching for a match, still returns false, then the user is notified that the value wasn't found
            System.out.println("Value not found!");
        }
    }

    public static void printHashTable(){


        String s = "[";
        System.out.print(s);

        //using a for loop to traverse through all the items in the table
        for(int i=0; i<tableCap; i++){
            if ((hashTable[i].getKey()) ==-1){ //if there is an item -1, that has come from our
                // no argument constructor, meaning that no item was actually added , and so we can label that position as null

                System.out.print("null,");
            } else if (hashTable[i].getKey()==-111) {
                //if any item is -111, this has come from our remove method and so that index is labelled as available, which will
                //be first considered when an item is re added to the table.

                System.out.print("available,");
            }else{
                //if any item is not -11 or -111, then that is an item that the user has added to the table and it shouldbe printed as it is
            System.out.print( hashTable[i].getKey() + ","); }
        }
        System.out.println("\b]");
    }

    public static void myHeader(int c, int d){
            //creating a header method lists all regarding information

            System.out.print("Lab Assignment=" + c + "Q"+ d+" \n Prepared by: Ayesha Qaisar " + " \n Student Number: 251255914 " +
                    "\n Goal of this exercise: We will be working with hashtables and some of the operations we can perform. \n");


            System.out.println("============================================================================================= \n");
    }




    public static int checkPrime(int g){
        //in hashing the prime number has to br greater than 2, we can discard 2
        int m = g/2; //only need to check half of the g factors

        for(int i=3; i<=m; i++){
            if (g%i==0){ //code block if g is not a prime number
                i=2; //reset i to 2 so that it is incremented to 3 in the for-header

                g++; //next g value
                m=g/2;  //again, just checking half of the g factors.
            }
        }
        return g;
    }

    public static void myFooter(int labnum, int labq){

            //a footer method to sign out of the program once main task is completed, and it is called by the main method

            System.out.println("============================================================================================= \n");
            System.out.print("Completion of lab Assignment " + labnum + "-Q" + labq + " is successful! \n Signing off - Ayesha :)");
            System.out.println("=============================================================================================");


    }

    public static void addValueLinearProbe(Integer p){
        //this method adds a key to the table. To do that, we need to generate an index for that key to be added to

        //this index is generated using a hashfunction: converts the key
        // to hashcode(in the case of integers it won't really matter) and applies the mod function to it against the capacity

        int place = p.hashCode() % tableCap;
        if (place<0){
            //if the index generated is a negative value, we can conver it to a positive value by simply adding the capacity to it
            place = place + tableCap;
        }

        //this while loop ensures linear probing. If the generated index has an item already, it should iterate to the next index and place the item there
        while((hashTable[place].getKey() != -1 && (hashTable[place].getKey() != -111))){
            //these values from previous methods check that incase the index generated already has an item in place

            place++;        //increment the index by 1
                            //use the hashfunction again to generate new index
            place = place % (tableCap);

        }
        hashTable[place].setKey(p);
        //once the index generated works out well, the item(key) is then placed at that index

    }

}
