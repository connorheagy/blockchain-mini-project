import java.util.Arrays;

/**
 * This is the Hash class which is the wrapper class that wraps byte array.
 * 
 * @author Connor Heagy
 * @author Shibam Mukhopadhyay
 * @author Samuel A. Rebelsky (Stated the methods for implementation)
 */
public class Hash {

  //Fields
  byte[] data; 

  //Constructor
  public Hash(byte[] data) {
    this.data = data;
  }
  //Methods

  //preconditions: none 
  //postconditions: returns data type byte array
  public byte[] getData(){
    return this.data;
  }

  //preconditions: none
  //postconditions: returns true if the first three indexes of the hash is 0, returns false otherwise.
  public boolean isValid() {
    if (this.data[0] == 0 && this.data[1] == 0 && this.data[2] == 0) {
      return true;
    }
    return false;
  }

  //preconditions: none
  //postconditions: returns the string representation of the hash as a string of hexadecimal digits, 2 digits per byte.
  public String toString(){
    String str = "";
    for(int i = 0; i < this.data.length; i++){
      str = str + String.format("%02x", Byte.toUnsignedInt(this.data[i]));
    }
    return str;
  }

  //preconditions: takes in other (data type Object)
  //postconditions: returns true if the 
  public boolean equals(Object other) {
    if (other instanceof Hash) {
      Hash o = (Hash) other;
      return Arrays.equals(this.data, o.getData());
    } 
    return false;
  }
}//Hash