package org.yearup;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction implements Comparable<Transaction>
{
    @Override
    public int compareTo(Transaction transaction)
    {
        if (this.date.isEqual(transaction.date))
        {
            return this.time.compareTo(transaction.time);
        }

        return this.date.compareTo(transaction.date);
    }


    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private float amount;


    public Transaction(LocalDate date, LocalTime time, String description, String vendor, float amount)
    {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }


    public LocalDate getDate() {
        return date;
    }

    public LocalDate setDate(LocalDate date) {
        this.date = date;
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalTime setTime(LocalTime time) {
        this.time = time;
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String setDescription(String description) {
        this.description = description;
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public String setVendor(String vendor) {
        this.vendor = vendor;
        return vendor;
    }

    public float getAmount() {
        return amount;
    }

    public float setAmount(float amount) {
        this.amount = amount;
        return amount;
    }
}
