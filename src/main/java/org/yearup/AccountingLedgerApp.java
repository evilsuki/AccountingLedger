package org.yearup;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class AccountingLedgerApp
{
    Scanner scanner = new Scanner(System.in);
    ArrayList<Transaction> transactions = loadTransaction();

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
                else
                {
                    System.out.println();
                    System.out.println(ColorCodes.RED + "Invalid selection" + ColorCodes.RESET);
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
                String time = columm[1];
                String description = columm[2].toUpperCase();
                String vendor = columm[3].toUpperCase();
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

        inventory.sort(Collections.reverseOrder());
        return inventory;
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
                System.out.println(ColorCodes.RED + "Invalid selection" + ColorCodes.RESET);
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
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

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
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            float deposit = transaction.getAmount();
            if (deposit > 0)
            {
                displayTransaction(transaction);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display only payments (negative entries)
    private void displayPayment()
    {
        System.out.println();
        System.out.println("Payments");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            float payment = transaction.getAmount();
            if (payment < 0)
            {
                displayTransaction(transaction);
            }
        }

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
            System.out.println("\t 6) Custom Search");
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
            else if (selection == 6)
            {
                customSearch();
            }
            else if (selection == 0)
            {
                displayLedgerScreen();
            }
            else
            {
                System.out.println();
                System.out.println(ColorCodes.RED + "Invalid selection" + ColorCodes.RESET);
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
        System.out.println();
        System.out.println("Reports Month To Date");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            LocalDate currentDate = LocalDate.now();
            int dayNow = currentDate.getDayOfMonth();
            Month monthNow = currentDate.getMonth();
            int yearNow = currentDate.getYear();
            LocalDate transactionDay = transaction.getDate();
            int day = transactionDay.getDayOfMonth();
            Month month = transactionDay.getMonth();
            int year = transactionDay.getYear();

            // compare date
            if (year == yearNow && month == monthNow && day <= dayNow)
            {
                displayTransaction(transaction);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display report previous month
    private void reportPreviousMonth()
    {
        System.out.println();
        System.out.println("Reports Previous Month");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            LocalDate currentDate = LocalDate.now();
            LocalDate previous = currentDate.minusMonths(1); // get previous month
            Month monthPrevious = previous.getMonth();
            int yearNow = currentDate.getYear();
            LocalDate transactionDay = transaction.getDate();
            Month month = transactionDay.getMonth();
            int year = transactionDay.getYear();

            // compare date
            if (year == yearNow && month == monthPrevious)
            {
                displayTransaction(transaction);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display report YTD
    private void reportYTD()
    {
        System.out.println();
        System.out.println("Reports Year To Date");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            LocalDate currentDate = LocalDate.now();
            int dayNow = currentDate.getDayOfMonth();
            Month monthNow = currentDate.getMonth();
            int yearNow = currentDate.getYear();
            LocalDate transactionDay = transaction.getDate();
            int day = transactionDay.getDayOfMonth();
            Month month = transactionDay.getMonth();
            int year = transactionDay.getYear();

            // compare date
            if (year == yearNow && month != monthNow)
            {
                displayTransaction(transaction);
            }
            else if (year == yearNow && day <= dayNow)
            {
                displayTransaction(transaction);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display report previous year
    private void reportPreviousYear()
    {
        System.out.println();
        System.out.println("Reports Previous Year");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            LocalDate currentDate = LocalDate.now();
            LocalDate previous = currentDate.minusYears(1); // get previous year
            int yearPrevious = previous.getYear();
            LocalDate transactionDay = transaction.getDate();
            int year = transactionDay.getYear();

            // compare date
            if (year == yearPrevious)
            {
                displayTransaction(transaction);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display report with vendor name
    private void reportSearchVendor()
    {
        System.out.println();
        System.out.print("Enter the vendor name: ");
        String name = scanner.nextLine().toUpperCase().strip();

        System.out.println();
        System.out.println("Reports of " + name);
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            String vendor = transaction.getVendor();

            if (name.equalsIgnoreCase(vendor))
            {
                displayTransaction(transaction);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // ask user for payment amount and save it to csv file
    private void makePayment()
    {
        String description;
        String amountInput;
        String vendor;

        System.out.println();

        while (true)
        {
            System.out.print("Enter amount of payment: ");
            amountInput = scanner.nextLine();
            if (amountInput.isEmpty())
            {
                System.out.println(ColorCodes.YELLOW + "Warning: Please do not leave blank" + ColorCodes.RESET);
            }
            else
            {
                break;
            }
        }
        while (true)
        {
            System.out.print("Enter description: ");
            description = scanner.nextLine().toLowerCase().strip();
            if (description.isEmpty())
            {
                System.out.println(ColorCodes.YELLOW + "Warning: Please do not leave blank" + ColorCodes.RESET);
            }
            else
            {
                break;
            }
        }

        while (true)
        {
            System.out.print("Enter vendor name: ");
            vendor = scanner.nextLine().toUpperCase().strip();
            if (vendor.isEmpty())
            {
                System.out.println(ColorCodes.YELLOW + "Warning: Please do not leave blank" + ColorCodes.RESET);
            } else
            {
                break;
            }
        }


        float amount = Float.parseFloat(amountInput);

        FileWriter fileWriter;
        BufferedWriter writer = null;
        LocalDate date = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentTime = localTime.format(time);
        float paymentAmount = -amount;

        try
        {
            fileWriter = new FileWriter("transactions.csv", true);
            writer = new BufferedWriter(fileWriter);
            Transaction transaction = new Transaction(date, currentTime, description, vendor, paymentAmount);

            writer.write(transaction.setDate(date) + "|" + transaction.setTime(currentTime) + "|" + transaction.setDescription(description) + "|" + transaction.setVendor(vendor) + "|" + transaction.setAmount(paymentAmount) + "\n");
            writer.flush();

            transactions.add(transaction);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }

        transactions.sort(Collections.reverseOrder());
        System.out.println(ColorCodes.GREEN + "\nPayment Completed" + ColorCodes.RESET);
    }


    // ask user for deposit amount and save it to csv file
    private void addDeposit()
    {
        String description;
        String amountInput;
        String vendor;

        System.out.println();

        while (true)
        {
            System.out.print("Enter amount of deposit: ");
            amountInput = scanner.nextLine();
            if (amountInput.isEmpty())
            {
                System.out.println(ColorCodes.YELLOW + "Warning: Please do not leave blank" + ColorCodes.RESET);
            }
            else
            {
                break;
            }
        }
        while (true)
        {
            System.out.print("Enter description: ");
            description = scanner.nextLine().toLowerCase().strip();
            if (description.isEmpty())
            {
                System.out.println(ColorCodes.YELLOW + "Warning: Please do not leave blank" + ColorCodes.RESET);
            }
            else
            {
                break;
            }
        }

        while (true)
        {
            System.out.print("Enter vendor name: ");
            vendor = scanner.nextLine().toUpperCase().strip();
            if (vendor.isEmpty())
            {
                System.out.println(ColorCodes.YELLOW + "Warning: Please do not leave blank" + ColorCodes.RESET);
            } else
            {
                break;
            }
        }

        float amount = Float.parseFloat(amountInput);

        FileWriter fileWriter = null;
        LocalDate date = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentTime = localTime.format(time);

        try
        {
            fileWriter = new FileWriter("transactions.csv", true);
            Transaction transaction = new Transaction(date, currentTime, description, vendor, amount);

            fileWriter.write(transaction.setDate(date) + "|" + transaction.setTime(currentTime) + "|" + transaction.setDescription(description) + "|" + transaction.setVendor(vendor) + "|" + transaction.setAmount(amount) + "\n");
            fileWriter.flush();

            transactions.add(transaction);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (fileWriter != null)
            {
                try
                {
                    fileWriter.close();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }

        transactions.sort(Collections.reverseOrder());
        System.out.println(ColorCodes.GREEN + "\nDeposit Completed" + ColorCodes.RESET);
    }


    // challenge: Prompt the user for
    //search values for all ledger entry properties
    private void customSearch()
    {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd/MM/yyyy").toFormatter();

        System.out.println();
        System.out.println("Custom Search");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Enter the value for field search or Enter blank to skip");

        System.out.print("\t Start Date (dd/mm/yyyy): ");
        String startDateInput = scanner.nextLine().strip();
        boolean hasStartDate = !startDateInput.equalsIgnoreCase("");
        LocalDate startDate = null;
        if(hasStartDate) startDate = LocalDate.parse(startDateInput, formatter);

        System.out.print("\t End Date (dd/mm/yyyy): ");
        String endDateInput = scanner.nextLine().strip();
        boolean hasEndDate = !endDateInput.equalsIgnoreCase("");
        LocalDate endDate = null;
        if(hasEndDate) endDate = LocalDate.parse(endDateInput, formatter);

        System.out.print("\t Description: ");
        String description = scanner.nextLine().strip();
        boolean hasDescription = !description.equals("");

        System.out.print("\t Vendor: ");
        String vendor = scanner.nextLine().strip();
        boolean hasVendor = !vendor.equals("");

        System.out.print("\t Amount: ");
        String amountInput = scanner.nextLine();
        boolean hasAmount = !amountInput.equals("");
        Float amount = null;
        if(hasAmount) amount = Float.parseFloat(amountInput);

        System.out.println();
        System.out.println("Reports");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Date              Time              Description                 Vendor            Amount");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions)
        {
            LocalDate transactionDate = transaction.getDate();
            String transDescription = transaction.getDescription();
            String transVendor = transaction.getVendor();
            float transAmount = transaction.getAmount();

            if(hasStartDate)
            {
                //if it is outside the range - skip to the next transaction
                if (transactionDate.isBefore(startDate)) continue;
            }
            if (hasEndDate)
            {
                if (transactionDate.isAfter(endDate)) continue;
            }
            if (hasDescription)
            {
                // check if it not equal - skip next transaction
                if (!transDescription.equalsIgnoreCase(description)) continue;
            }
            if (hasVendor)
            {
                if (!transVendor.equalsIgnoreCase(vendor)) continue;
            }
            if (hasAmount)
            {
                if (transAmount != amount)continue;
            }

            displayTransaction(transaction);
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
    }


    // display transaction format
    private void displayTransaction(Transaction transaction)
    {
        System.out.printf("%-15s %-17s %-28s %-17s $ %.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
    }
}
