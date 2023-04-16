package CRUD;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operation {

    public static void updateData() throws IOException {
        // taking database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // making a while database
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // display data
        System.out.println("Books List");
        displayData();

        // taking user input / data option
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Enter the book number: ");
        int updateNum = terminalInput.nextInt();

        // display update data
        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null){
            entryCounts++;

            StringTokenizer st = new StringTokenizer(data, ",'");

            // display entryCounts if same as updateNum
            if (updateNum == entryCounts){
                System.out.println("\nData you want to update is: ");
                System.out.println("--------------------------------------------------------");
                System.out.println("Reference         : " + st.nextToken());
                System.out.println("Year              : " + st.nextToken());
                System.out.println("Writer            : " + st.nextToken());
                System.out.println("Publisher         : " + st.nextToken());
                System.out.println("Title             : " + st.nextToken());

                // Update Data

                // taking user input
                String[] fieldData = {"Year", "Writer ", "Publisher", "Title"};
                String[] tempData = new String[4];

                st = new StringTokenizer(data, ",'");
                String originalData = st.nextToken();

                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = Utility.getYesOrNO("do you want to change " + fieldData[i]);

                    originalData = st.nextToken();
                    if (isUpdate){
                        // user input
                        if (fieldData[i].equalsIgnoreCase("year")){
                            System.out.print("year of publication, Format = (YYYY): ");
                            tempData[i] = Utility.takeYear();
                        }else {
                            terminalInput = new Scanner(System.in);
                            System.out.println("\nEnter new " + fieldData[i] + ": ");

                            tempData[i] = terminalInput.nextLine();
                        }

                    } else {
                        tempData[i] = originalData;
                    }

                }
                // Display new data
                st = new StringTokenizer(data, ",'");
                st.nextToken();

                System.out.println("\nYour new data");
                System.out.println("--------------------------------------------------------");
                System.out.println("Year              : " + st.nextToken() + " ---> " + tempData[0]);
                System.out.println("Writer            : " + st.nextToken() + " ---> " + tempData[1]);
                System.out.println("Publisher         : " + st.nextToken() + " ---> " + tempData[2]);
                System.out.println("Title             : " + st.nextToken() + " ---> " + tempData[3]);

                boolean isUpdate = Utility.getYesOrNO("are you sure to update/change this data? ");
                if (isUpdate){

                    // Check new data in database
                    boolean isExist = Utility.checkBookInDatabase(tempData, false);

                    if (isExist){
                        System.err.println("data is in database");
                        bufferedOutput.write(data);
                    }else {
                        // Format new data to database
                        String year = tempData[0];
                        String writer = tempData[1];
                        String publisher = tempData[2];
                        String title = tempData[3];

                        // make primary Key
                        long numberEntry = Utility.takeEntryYearly(writer, year) + 1;

                        String writerWithoutSpace = writer.replace("\\s+", "");
                        String primaryKey = writerWithoutSpace + "_" + year + "_" + numberEntry;

                        // write data to database
                        bufferedOutput.write(primaryKey + "," + year + "," + writer + "," + publisher + "," + title);
                    }
                }else {
                    // Copy data
                    bufferedOutput.write(data);
                }
            } else {
                // Copy data
                bufferedOutput.write(data);
            }
            bufferedOutput.newLine();

            data = bufferedInput.readLine();
        }

        // write data to file
        bufferedOutput.flush();

        bufferedOutput.flush();

        bufferedOutput.close();
        fileOutput.close();
        bufferedInput.close();
        fileInput.close();

        System.gc();
        //delete original data base
        database.delete();
        // rename tempDB become database
        tempDB.renameTo(database);







    }
    public static void deleteData() throws IOException{
        // Taking Original Database
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // Temporary Database
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // Display Data
        System.out.println("Books List");
        displayData();

        // Taking User Input For Delete Data
        Scanner teminalInput = new Scanner(System.in);
        System.out.print("\nInput the book number to be delete: ");
        int deleteNum = teminalInput.nextInt();

        // Loop For Reading Every Single Data lane & Skip Data To Be Delete
        boolean isFound = false;
        int entryCounts = 0;

        String data = bufferedInput.readLine();

        while (data != null){
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");
            // Display delete data
            if (deleteNum == entryCounts){
                System.out.println("\nData you want to delete:");
                System.out.println("----------------------------------------------");
                System.out.println("Reference         : " + st.nextToken());
                System.out.println("Year              : " + st.nextToken());
                System.out.println("Writer            : " + st.nextToken());
                System.out.println("Publisher         : " + st.nextToken());
                System.out.println("Title             : " + st.nextToken());

                isDelete = Utility.getYesOrNO("Are you sure you want to delete this book?");
                isFound = true;
            }

            if (isDelete){
                // Skip move data from original --> while
                System.out.println("Delete data is Success");
            } else {
                // Move data from original --> while
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }

        if (!isFound){
            System.err.println("Book is not found");
        }

        // write data --> file
        bufferedOutput.flush();

        bufferedOutput.close();
        fileOutput.close();
        bufferedInput.close();
        fileInput.close();

        System.gc();
        // Delete file
        database.delete();
        //Rename File
        tempDB.renameTo(database);


    }

    public static void displayData() throws IOException{
        FileReader fileInput;
        BufferedReader bufferedInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferedInput = new BufferedReader(fileInput);
        }catch (Exception e){
            System.err.println("Data base is not exist");
            System.err.println("Please add data");
            addData();
            return;
        }

        System.out.println("\n| No |\tYear |\t       Writer          |\t     Publisher         |\t Book Title      |");
        System.out.println("------------------------------------------------------------------------------------------");

        String data = bufferedInput.readLine();
        int noData = 0;
        while (data != null) {
            noData++;
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");

            stringTokenizer.nextToken();
            System.out.printf("| %2d ", noData);
            System.out.printf("|\t%4s ", stringTokenizer.nextToken());
            System.out.printf("|\t%-20s   ", stringTokenizer.nextToken());
            System.out.printf("|\t%-20s   ", stringTokenizer.nextToken());
            System.out.printf("|\t%s   ", stringTokenizer.nextToken());
            System.out.print("\n");
            data = bufferedInput.readLine();
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }

    public static void searchData() throws IOException{
        // Read database, there or not
        try{
            File file = new File("database.txt");
        } catch (Exception e){
            System.err.println("you can't clear screen");
            return;
        }

        // take keyword from user

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Search book: ");
        String searchString = terminalInput.nextLine();
        String[] keywords = searchString.split("\\s+");

        // Check keyword in database
        Utility.checkBookInDatabase(keywords, true);

    }

    public static void addData() throws IOException{

        FileWriter fileOutput = new FileWriter("database.txt", true);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // taking input from user
        Scanner terminalInput = new Scanner(System.in);
        String writer, title, publisher, year;
        System.out.print("author name: ");
        writer = terminalInput.nextLine();
        System.out.print("book title: ");
        title = terminalInput.nextLine();
        System.out.print("publisher: ");
        publisher = terminalInput.nextLine();
        System.out.print("year of publication, Format = (YYYY): ");
        year = Utility.takeYear();

        // Checking book in database

        String[] keywords = {year + "," + writer + "," + publisher + "," + title};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.checkBookInDatabase(keywords, false);

        // write book in database
        if (!isExist){
//            fiersabesari_2012_1,2012,fiersa besari,media kita,jejak langkah
            System.out.println(Utility.takeEntryYearly(writer, year));
            long numberEntry = Utility.takeEntryYearly(writer, year) + 1;

            String writerWithoutSpace = writer.replace("\\s+", "");
            String primaryKey = writerWithoutSpace + "_" + year + "_" + numberEntry;
            System.out.println("\nyour data is");
            System.out.println("-----------------------------------");
            System.out.println("primaryKey     : " + primaryKey);
            System.out.println("year publish   : " + year);
            System.out.println("writer         : " + writer);
            System.out.println("title          : " + title);
            System.out.println("publisher      : " + publisher);

            boolean isPlus = Utility.getYesOrNO("do you want to add this data? ");

            if (isPlus){
                bufferedOutput.write(primaryKey + "," + year + "," + writer + "," + publisher + "," + title);
                bufferedOutput.newLine();
                bufferedOutput.flush();
            }

        } else {
            System.out.println("this book is in the database");
            Utility.checkBookInDatabase(keywords, true);
        }




        bufferedOutput.close();
    }


}
