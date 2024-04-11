import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * This is the BlockChainDriver class contains main method and interacts with BlockChain.
 * @author Connor Heagy
 * @author Shibam Mukhopadhyay
 * @author Samuel A. Rebelsky (Stated the methods for implementation)
 * 
 */

public class BlockChainDriver {
  public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java BlockChainDriver <initial_amount>");
            System.exit(1);
        }

        int initialAmount = 0;
        try {
            initialAmount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid initial amount: " + args[0]);
            System.exit(1);
        }
        Scanner eyes = new Scanner(System.in);
        PrintWriter pen = new PrintWriter(System.out, true);
        try {
            BlockChain blockchain = new BlockChain(initialAmount);


            while (true) {
                pen.println(blockchain);
                pen.printf("Command? ");
                String command = eyes.nextLine().trim();

                if (command.equalsIgnoreCase("mine")) {
                    pen.printf("Amount transferred? ");
                    int amount = eyes.nextInt();
                    eyes.nextLine();
                    Block minedBlock = blockchain.mine(amount);
                    pen.printf("amount = %d, nonce = %d\n", minedBlock.amount, minedBlock.getNonce());
                } else if (command.equalsIgnoreCase("append")) {
                    pen.printf("Amount transferred? ");
                    int amount = eyes.nextInt();
                    eyes.nextLine();
                    pen.printf("Nonce? ");
                    long nonce = eyes.nextLong();
                    eyes.nextLine();
                    try {
                        blockchain.append(new Block(blockchain.getSize(), amount, blockchain.getHash(), nonce));
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                } else if (command.equalsIgnoreCase("remove")) {
                    if (blockchain.removeLast()) {
                        pen.println("Last block removed.");
                    } else {
                        System.err.println("Cannot remove last block: blockchain is empty.");
                    }
                } else if (command.equalsIgnoreCase("check")) {
                    if (blockchain.isValidBlockChain()) {
                        pen.println("Chain is valid!");
                    } else {
                        pen.println("Chain is invalid!");
                    }
                } else if (command.equalsIgnoreCase("report")) {
                    blockchain.printBalances(pen);
                } else if (command.equalsIgnoreCase("help")) {
                    printHelpMenu();
                } else if (command.equalsIgnoreCase("quit")) {
                    break;
                } else {
                    System.err.println("Invalid command: " + command);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
        }
        eyes.close();
    }

    private static void printHelpMenu() {
        PrintWriter pen = new PrintWriter(System.out, true);
        pen.println("Valid commands:");
        pen.println("    mine: discovers the nonce for a given transaction");
        pen.println("    append: appends a new block onto the end of the chain");
        pen.println("    remove: removes the last block from the end of the chain");
        pen.println("    check: checks that the block chain is valid");
        pen.println("    report: reports the balances of Alexis and Blake");
        pen.println("    help: prints this list of commands");
        pen.println("    quit: quits the program");
    }
}

