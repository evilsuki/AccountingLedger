package org.yearup;

import java.util.ArrayList;
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

    }


    // create hashmap for user search key
    private HashMap<String, Transaction> hashMap()
    {

    }


    // display home screen
    private String displayHomeScreen()
    {

    }


    // display ledger screen
    private void displayLedgerScreen()
    {

    }


    // display all transactions
    private void displayAllTransaction()
    {

    }


    // display only the deposits
    private void displayDeposit()
    {

    }


    // display only payments (negative entries)
    private void displayPayment()
    {

    }


    // display reports screen
    private void displayReportScreen()
    {

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
    private void displayTransaction()
    {

    }
}
