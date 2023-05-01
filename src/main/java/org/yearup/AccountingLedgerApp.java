package org.yearup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class AccountingLedgerApp
{
    Scanner scanner = new Scanner(System.in);
    ArrayList<Transaction> transactions = loadTransaction();
    HashMap<String, Transaction> transactionMap = hashMap();

    public void run()
    {
        try
        {
            while (true)
            {
                String selection = displayHomeScreen();

                if (selection.equalsIgnoreCase("d"))
                {
                    addDeposit();
                }
                else if (selection.equalsIgnoreCase("p"))
                {
                    makePayment();
                }
                else if (selection.equalsIgnoreCase("l"))
                {
                    displayLedgerScreen();
                }
                else if (selection.equalsIgnoreCase("x"))
                {
                    System.out.println();
                    System.out.println("Thank you. Good bye!");
                    break;
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    // read the transaction in csv file and load it
    private ArrayList<Transaction> loadTransaction()
    {
        FileReader fileReader;
        BufferedReader reader = null;
        ArrayList<Transaction> inventory = new ArrayList<>();

        try
        {
            fileReader = new FileReader("transactions.csv");
            reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] columm = line.split("\\|");
                LocalDate date = LocalDate.parse(columm[0]);
                LocalTime time = LocalTime.parse(columm[1]);
                String description = columm[2];
                String vendor = columm[3];
                float amount = Float.parseFloat(columm[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                inventory.add(transaction);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }

        Collections.sort(inventory);
        return inventory;
    }


    // create hashmap for user search key
    private HashMap<String, Transaction> hashMap()
    {
        FileReader fileReader;
        BufferedReader reader = null;
        HashMap<String, Transaction> hashMap = new HashMap<>();

        try
        {
            fileReader = new FileReader("transactions.csv");
            reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] columm = line.split("\\|");
                LocalDate date = LocalDate.parse(columm[0]);
                LocalTime time = LocalTime.parse(columm[1]);
                String description = columm[2];
                String vendor = columm[3];
                float amount = Float.parseFloat(columm[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                hashMap.put(vendor, transaction);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }

        return hashMap;
    }


    // display home screen
    private String displayHomeScreen()
    {
        System.out.println();
        System.out.println("Home Screen");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("What do you like to do today?");
        System.out.println("\t D) Add Deposit");
        System.out.println("\t P) Make Payment(Debit)");
        System.out.println("\t L) Ledger");
        System.out.println("\t X) Exit");
        System.out.print("Enter your selection: ");

        return scanner.nextLine().strip();
    }


    // display ledger screen
    private void displayLedgerScreen()
    {
        try
        {
            System.out.println();
            System.out.println("Ledger");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("What do you like to check?");
            System.out.println("\t A) All");
            System.out.println("\t D) Deposits");
            System.out.println("\t P) Payments");
            System.out.println("\t R) Reports");
            System.out.println("\t H) Home");
            System.out.print("Enter your selection: ");
            String selection = scanner.nextLine().strip();

            if (selection.equalsIgnoreCase("a"))
            {
                displayAllTransaction();
            }
            else if (selection.equalsIgnoreCase("d"))
            {
                displayDeposit();
            }
            else if (selection.equalsIgnoreCase("p"))
            {
                displayPayment();
            }
            else if (selection.equalsIgnoreCase("r"))
            {
                displayReportScreen();
            }
            else if (selection.equalsIgnoreCase("h"))
            {
                displayHomeScreen();
            }
            else
            {
                System.out.println();
                System.out.println("Invalid selection");
                displayLedgerScreen();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    // display all transactions
    private void displayAllTransaction()
    {
        System.out.println();
        System.out.println("Ledger");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time                Description                 Vendor             Amount");
        System.out.println(" ");

        for (Transaction transaction : transactions)
        {
            displayTransaction(transaction);
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display only the deposits
    private void displayDeposit()
    {
        System.out.println();
        System.out.println("Deposit");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time                Description                 Vendor             Amount");
        System.out.println(" ");



        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display only payments (negative entries)
    private void displayPayment()
    {
        System.out.println();
        System.out.println("Payments");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time                Description                 Vendor             Amount");
        System.out.println(" ");

       

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display reports screen
    private void displayReportScreen()
    {
        try
        {
            System.out.println();
            System.out.println("Reports");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("What kind of report you like to check?");
            System.out.println("\t 1) Month To Date");
            System.out.println("\t 2) Previous Month");
            System.out.println("\t 3) Year To Date");
            System.out.println("\t 4) Previous Year");
            System.out.println("\t 5) Search by Vendor");
            System.out.println("\t 0) Back");
            System.out.print("Enter your selection: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection == 1)
            {
                reportMTD();
            }
            else if (selection == 2)
            {
                reportPreviousMonth();
            }
            else if (selection == 3)
            {
                reportYTD();
            }
            else if (selection == 4)
            {
                reportPreviousYear();
            }
            else if (selection == 5)
            {
                reportSearchVendor();
            }
            else if (selection == 0)
            {
                displayLedgerScreen();
            }
            else
            {
                System.out.println();
                System.out.println("Invalid selection");
                displayReportScreen();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    // display report MTD
    private void reportMTD()
    {

    }


    // display report previous month
    private void reportPreviousMonth()
    {

    }


    // display report YTD
    private void reportYTD()
    {

    }


    // display report previous year
    private void reportPreviousYear()
    {

    }


    // display report with vendor name
    private void reportSearchVendor()
    {

    }


    // ask user for payment amount and save it to csv file
    private void makePayment()
    {

    }


    // ask user for deposit amount and save it to csv file
    private void addDeposit()
    {

    }


    // display transaction format
    private void displayTransaction(Transaction transaction)
    {
        System.out.printf("%-15s %-15s %-35s %-25s %.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
    }
}
