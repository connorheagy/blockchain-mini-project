import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
/**
 * This is the BlockChain class which implements Block class to create a singly-linked structure.
 * 
 * @author Connor Heagy
 * @author Shibam Mukhopadhyay
 * @author Samuel A. Rebelsky (Stated the methods for implementation)
 * 
 */
public class BlockChain {
    private static class Node {
        Block block;
        Node next;

        Node(Block block) {
            this.block = block;
            next = null;
        }
    }

    private Node first;
    private Node last;

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block beginBlock = new Block(0, initial, null);
        first = last = new Node(beginBlock);
    }

    public Block mine(int amount) throws NoSuchAlgorithmException {
        int num = last.block.getNum() + 1;
        Hash prevHash = last.block.getHash();
        return new Block(num, amount, prevHash);
    }

    public int getSize() {
        int size = 0;
        Node current = first;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    public void append(Block blk) throws IllegalArgumentException, NoSuchAlgorithmException {
        if (!isValidBlock(blk)) {
            throw new IllegalArgumentException("Invalid block");
        }

        Node newNode = new Node(blk);
        if (last == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
    }

    public boolean removeLast() {
        if (first == null) {
            return false;
        }

        if (first == last) {
            first = last = null;
            return true;
        }

        Node current = first;
        while (current.next != last) {
            current = current.next;
        }

        current.next = null;
        last = current;
        return true;
    }

    public Hash getHash() {
        return last != null ? last.block.getHash() : null;
    }

    public boolean isValidBlockChain() throws NoSuchAlgorithmException {
        int balanceAlexis = first.block.getAmount();
        int balanceBlake = 0;
        Node current = first.next;

        while (current != null) {
            balanceAlexis += current.block.getAmount();
            balanceBlake -= current.block.getAmount();

            if (balanceAlexis < 0 || balanceBlake < 0 || !isValidBlock(current.block)) {
                return false;
            }
            current = current.next;
        }

        return true;
    }

    public void printBalances(PrintWriter pen) {
        int balanceAlexis = first.block.getAmount();
        int balanceBlake = 0;
        Node current = first.next;

        while (current != null) {
            balanceAlexis += current.block.getAmount();
            balanceBlake -= current.block.getAmount();
            current = current.next;
        }

        pen.printf("Alexis: %d, Blake: %d\n", balanceAlexis, balanceBlake);
    }

    public String toString() {
        String s = "";
        Node current = first;
        while (current != null) {
            s = s + current.block.toString() + "\n";
            current = current.next;
        }
        return s;
    }

    private boolean isValidBlock(Block blk) throws NoSuchAlgorithmException {
        int num = blk.getNum();
        int amount = blk.getAmount();
        Hash prevHash = blk.getPrevHash();
        long nonce = blk.getNonce();
        Hash computedHash;

        computedHash = new Block(num, amount, prevHash, nonce).getHash();

        return blk.getHash().equals(computedHash);
    }
}

