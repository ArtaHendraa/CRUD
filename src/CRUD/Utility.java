package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {

    static long takeEntryYearly(String writer, String year) throws IOException {
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferedInput.readLine();
        Scanner dataScanner;
        String primaryKey;
        while (data != null){
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");
            writer = writer.replace("\\s+", "");

            if (writer.equalsIgnoreCase(dataScanner.next()) && year.equalsIgnoreCase(dataScanner.next()) ) {
                entry = dataScanner.nextInt();
            }

            data = bufferedInput.readLine();
        }
        return entry;
    }

    static boolean checkBookInDatabase(String[] keywords, boolean isDisplay) throws IOException{

        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        String data = bufferedInput.readLine();

        boolean isExist = true;
        int noData = 0;

        if (isDisplay) {
            System.out.println("\n| No |\tYear |\t       Writer          |\t     Publisher         |\t Book Title      |");
            System.out.println("------------------------------------------------------------------------------------------");
        }
        while (data != null){

            // check keyword inside the row
            isExist = true;

            for (String keyword:keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // if the keyword mach then display

            if (isExist){
                if (isDisplay) {
                    noData++;
                    StringTokenizer stringTokenizer = new StringTokenizer(data, ",");

                    stringTokenizer.nextToken();
                    System.out.printf("| %2d ", noData);
                    System.out.printf("|\t%4s ", stringTokenizer.nextToken());
                    System.out.printf("|\t%-20s   ", stringTokenizer.nextToken());
                    System.out.printf("|\t%-20s   ", stringTokenizer.nextToken());
                    System.out.printf("|\t%s   ", stringTokenizer.nextToken());
                    System.out.print("\n");
                }else {
                    break;
                }
            }
            data = bufferedInput.readLine();
        }
        if (isDisplay) {
            System.out.println("------------------------------------------------------------------------------------------");
        }
        return isExist;
    }

    protected static String takeYear() throws IOException{
        boolean validYear = false;
        Scanner terminalInput = new Scanner(System.in);
        String inputYear = terminalInput.nextLine();
        while (!validYear) {
            try {
                Year.parse(inputYear);
                validYear = true;
            } catch (Exception e) {
                System.out.println("Format year is not correct, Format = (YYYY)");
                System.out.print("year of publication is not correct please type again: ");
                validYear = false;
                inputYear = terminalInput.nextLine();
            }
        }

        return inputYear;
    }

    public static boolean getYesOrNO(String massage){
        Scanner terminalInput = new Scanner(System.in);
        System.out.println("\n" + massage + "(yes/no)? ");
        String userChoice = terminalInput.next();

        while (!userChoice.equalsIgnoreCase("yes") && !userChoice.equalsIgnoreCase("no")){
            System.err.println("please choice yes or no");
            System.out.println("\n" + massage + "(yes/no)? ");
            userChoice = terminalInput.next();
        }

        return userChoice.equalsIgnoreCase("yes");
    }

    public static void clearScreen() {
        try{
            if (System.getProperty("os.name").contains("windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception e){
            System.err.println("you can't clear screen");
        }
    }

}
