import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.Long;
/**
 * This is the Block class which is a class for the data contained in each node of the blockchain.
 * 
 * @author Connor Heagy
 * @author Shibam Mukhopadhyay
 * @author Samuel A. Rebelsky (Stated the methods for implementation and use of code implemented in MP7 assignment and in Q&A section)
 * 
 */


public class Block {
  
  //Fields
  int num;

  int amount;

  Hash prevHash;

  long nonce;

  Hash hash;

  //Constructors
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = mineNonce();
    this.hash = computeHash(num, amount, prevHash, nonce);
  }

  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    this.hash = computeHash(num, amount, prevHash, nonce);
  }

  //Methods
  public int getNum(){
    return this.num;
  }

  public int getAmount() {
    return this.amount;
  }

  public long getNonce(){
    return this.nonce;
  }

  public Hash getPrevHash(){
    return this.prevHash;
  }

  public Hash getHash(){
    return this.hash;
  }

  public String toString(){
    return "Block " + this.num + " (Amount: " + this.amount + " , Nonce: " + this.nonce + ", prevHash: " + this.prevHash + ", hash: " + this.hash.toString() + ")";
  }

  private Hash computeHash(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
  MessageDigest md = MessageDigest.getInstance("SHA-256");
  md.update(ByteBuffer.allocate(Integer.BYTES).putInt(num).array());
  md.update(ByteBuffer.allocate(Integer.BYTES).putInt(amount).array());
  if (prevHash != null) {
      md.update(prevHash.getData());
  }
  md.update(ByteBuffer.allocate(Long.BYTES).putLong(nonce).array());
  return new Hash(md.digest());
}

  private long mineNonce() throws NoSuchAlgorithmException {
    long nonce = 0;
    do{
      nonce++;
      hash = computeHash(num, amount, prevHash, nonce);
    }while(!hash.isValid());
    return nonce;
  }
}

