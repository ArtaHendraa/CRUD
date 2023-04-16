package com.tutorial;

// Import Java Library
import java.io.IOException;
import java.util.Scanner;

// Import CRUD Library
import CRUD.Operation;
import CRUD.Utility;


public class Main {
    public static void main(String[] args) throws IOException {

        Scanner terminalInput = new Scanner(System.in);
        String userChoice;
        boolean isContiue = true;

        while (isContiue){
            Utility.clearScreen();
            System.out.println("Database Library");
            System.out.println("1.\tAll books");
            System.out.println("2.\tSearch books data");
            System.out.println("3.\tadd books data");
            System.out.println("4.\tChange books data");
            System.out.println("5.\tDelete books data");

            System.out.print("\n\nYour choice: ");
            userChoice = terminalInput.next();

            switch (userChoice) {
                case "1":
                    System.out.println("=====================");
                    System.out.println("ALL LIST BOOKS");
                    System.out.println("=====================");
                    Operation.displayData();
                    break;
                case "2":
                    System.out.println("=====================");
                    System.out.println("SEARCH BOOKS");
                    System.out.println("=====================");
                    Operation.searchData();
                    break;
                case "3":
                    System.out.println("\n===================");
                    System.out.println("ADD BOOKS DATA");
                    System.out.println("=====================");
                    Operation.addData();
                    Operation.displayData();
                    break;
                case "4":
                    System.out.println("=====================");
                    System.out.println("CHANGE BOOKS DATA");
                    System.out.println("=====================");
                    Operation.updateData();
                    break;
                case "5":
                    System.out.println("=====================");
                    System.out.println("DELETE BOOKS DATA");
                    System.out.println("=====================");
                    Operation.deleteData();
                    break;
                default:
                    System.err.println("\nWe didn't find input: " + userChoice + "\nPlease choice input 1-5");
            }

            isContiue = CRUD.Utility.getYesOrNO("Do you want to continue? ");
        }

    }





}













