package LA3Q1;

public class AyeshaValueEntry {
    private Integer key;        //creating a variable called key which is really the value we insert to our hashtable

    public AyeshaValueEntry(){
        key=-1;
    }   //constructor without arguments sets all values to null flag

    public AyeshaValueEntry(Integer k){     //constructor with arguments
        this.key= k;

    }


    //setting up setter and getter methods for our key
    public void setKey(Integer a){

        this.key=a;

    }

    public Integer getKey(){
        return key;
    }





}
