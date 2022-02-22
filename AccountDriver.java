import java.util.Scanner;

public class AccountDriver {
    
    // Entry point of program
    public static void main(String [] args) {

        Scanner keyboard = new Scanner(System.in);
        
        // Create array of Accounts
        Account accounts [] = new Account[10];
        int numAccounts = 0; 

        int choice;

        do {
            choice = menu(keyboard);
            System.out.println();
            
            if(choice == 1) {
                accounts[numAccounts++] = createAccount(keyboard);
            } else if(choice == 2) {
                doDeposit(accounts, numAccounts, keyboard);
            } else if(choice == 3) {
                doWithdraw(accounts, numAccounts, keyboard);
            } else if(choice == 4) {
                applyInterest(accounts, numAccounts, keyboard);
            } else {
                System.out.println("GoodBye!");
            }
            System.out.println();
        } while(choice != 5);
    }

    /**
     * Account choice
     * 
     * @param keyboard 
     * @return choice
     */
    public static int accountMenu(Scanner keyboard) {
        System.out.println("Select Account Type");
        System.out.println("1. Checking Account");
        System.out.println("2. Savings Account");

        int choice;
        do {
            System.out.print("Enter choice: ");
            choice = keyboard.nextInt();
        }while(choice < 1 || choice > 2);
        
        return choice;
    } 

    public static int searchAccount(Account accounts [], int count, int accountNumber) {

        for(int i=0; i<count; i++) {
            if(accounts[i].getAccountNumber() == accountNumber) {
                return i;
            }
        }

        return -1; 
    }

    /**
     * Function to perform Deposit on a selected account
     */
    public static void doDeposit(Account accounts [], int count, Scanner keyboard) {
        // Get the account number
        System.out.print("\nPlease enter account number: ");
        int accountNumber = keyboard.nextInt();

        // search for account
        int index = searchAccount(accounts, count, accountNumber);

        if(index >= 0) {
            // Amount
            System.out.print("Please enter Deposit Amount: ");
            double amount = keyboard.nextDouble();

            accounts[index].deposit(amount);
        } else {
            System.out.println("No account exist with AccountNumber: " + accountNumber);
        }
    }

    public static void doWithdraw(Account accounts [], int count, Scanner keyboard) {
        // Get the account number
        System.out.print("\nPlease enter account number: ");
        int accountNumber = keyboard.nextInt();

        // search for account
        int index = searchAccount(accounts, count, accountNumber);

        if(index >= 0) {
            // Amount
            System.out.print("Please enter Withdraw Amount: ");
            double amount = keyboard.nextDouble();
            accounts[index].withdraw(amount);
        } else {
            System.out.println("No account exist with AccountNumber: " + accountNumber);
        }
    }

    public static void applyInterest(Account accounts [], int count, Scanner keyboard) {
        // Get the account number
        System.out.print("\nPlease enter account number: ");
        int accountNumber = keyboard.nextInt();

        // search for account
        int index = searchAccount(accounts, count, accountNumber);

        if(index >= 0) {
            
            // must be instance of savings account
            if(accounts[index] instanceof SavingsAccount) {
                ((SavingsAccount)accounts[index]).applyInterest();
            }
        } else {
            System.out.println("No account exist with AccountNumber: " + accountNumber);
        }
    }

    /**
     * Function to create a new Account
     */
    public static Account createAccount(Scanner keyboard) {

        Account account = null; 
        int choice = accountMenu(keyboard);

        int accountNumber;
        System.out.print("Enter Account Number: ");
        accountNumber = keyboard.nextInt();

        if(choice == 1)  { // chekcing account
            System.out.print("Enter Transaction Fee: ");
            double fee = keyboard.nextDouble();
            account = new CheckingAccount(accountNumber, fee);
        } else { // Savings account
            
            System.out.print("Please enter Interest Rate: ");
            double ir = keyboard.nextDouble();
            account = new SavingsAccount(accountNumber, ir);
        }
        return account;
    }

    /**
     * Menu to display options and get the user's selection
     * 
     * @param keyboard
     * @return choice
     */
    public static int menu(Scanner keyboard) {
        System.out.println("Bank Account Menu");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Funds");
        System.out.println("3. Withdraw Funds");
        System.out.println("4. Apply Interest");
        System.out.println("5. Quit");

        int choice;

        do {
            System.out.print("Enter choice: ");
            choice = keyboard.nextInt();
        } while(choice < 1 || choice > 5);

        return choice;
    }    
}

// Checking Account
public class CheckingAccount extends Account {

    // Default Transaction Fee
    private static double FEE = 2.5;

    // default constructor
    public CheckingAccount() {
        super();
    }

    /**
     * Parameter constructor to intialize CheckingAccount
     * with a custom Account Number and a Customer Transaction
     * Fee. 
     */
    public CheckingAccount(int accountNumber, double fee) {
        super(accountNumber);
        FEE = fee;
    }

    /**
     * Function to deposit funds into the account as long as the amount parameter is
     * > 0
     * 
     * Apply Transaction fee for the CheckingAccount
     * 
     * @param amount value to be deposited
     */
    public void deposit(double amount) {

        // First Check amount
        if( amount > 0) {
            balance += amount;
            System.out.printf("Amount %.2f deposited%n", amount);

            // Apply Transaction Fee
            balance -= FEE;
            System.out.printf("Fee %.2f Applied%n", FEE);
            System.out.printf("Current Balance is: %.2f%n", balance);
            
        } else {
            System.out.println("A negative amount cannot be deposited");
        }
    }

    /**
     * Function to withdraw funds from the Account as long as 1. Amount to withdraw
     * must be > 0 2. Amount to withdraw must be <= balance
     * 
     * @param amount value to be withdrawn
     */
    public void withdraw(double amount) {

        // Same check
        if(amount > 0) {
            // Check sufficient balance
            if((amount+FEE) <= balance) {

                System.out.printf("Amount of %.2f withdrawn from Account%n", amount);
                balance -= amount;
                balance -= FEE;
                System.out.printf("Fee of %.2f applied%n", FEE);
                System.out.printf("Current Balance is: %.2f%n", balance);
            }
        } else {
            System.out.println("Negative amount cannot be withdrawn!");
        }
    }
}
// Savings Account child class
// has an interest rate
// a method to apply interest - profit 

public class SavingsAccount extends Account {

    // interest rate
    private double interestRate;

    // default constructor
    public SavingsAccount() {
        super();
    }

    /**
     * Parameter constructor to intialize Savings account with a customer account
     * number and interest rate
     */
    public SavingsAccount(int accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }

    // getter function
    public double getInterestRate() {
        return this.interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double calcInterest() {
        return balance * interestRate;
    }

    public void applyInterest() {
        double interest = calcInterest();
        System.out.printf("Interest amount %.2f added to balance%n", interest);
        deposit(interest);
    }

    /**
     * Function to deposit funds into the account as long as the amount parameter is
     * > 0
     * 
     * Apply Transaction fee for the CheckingAccount
     * 
     * @param amount value to be deposited
     */
    public void deposit(double amount) {

        // First Check amount
        if (amount > 0) {
            balance += amount;
            System.out.printf("Amount %.2f deposited%n", amount);
            System.out.printf("Current Balance is: %.2f%n", balance);

        } else {
            System.out.println("A negative amount cannot be deposited");
        }
    }

    /**
     * Function to withdraw funds from the Account as long as 1. Amount to withdraw
     * must be > 0 2. Amount to withdraw must be <= balance
     * 
     * @param amount value to be withdrawn
     */
    public void withdraw(double amount) {

        // Same check
        if (amount > 0) {
            // Check sufficient balance
            if ((amount) <= balance) {
                System.out.printf("Amount of %.2f withdrawn from Account%n", amount);
                balance -= amount;
                System.out.printf("Current Balance is: %.2f%n", balance);
            }
        } else {
            System.out.println("Negative amount cannot be withdrawn!");
        }
    }
}
// Base clase
// Abstract

public abstract class Account {

    private int accountNumber;

    protected double balance; 

    // Default constructor
    public Account() {

    }

    public Account(int accountNumber) {
        this.accountNumber = accountNumber;
        balance = 0; 
    }

    // Getter methods
    public double getBalance() {
        return this.balance;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    // Abstract
    /**
     * Function to deposit funds into the account as long as the amount parameter is > 0 
     * 
     * Apply Transaction fee for the CheckingAccount
     * 
     */
    public abstract void deposit(double amount); 

    /**
     * Function to withdraw funds from the Account as long as
     *  1. Amount to withdraw must be > 0
     *  2. Amount to withdraw must be <= balance
     * 
     */    
    public abstract void withdraw(double amount); 
}
