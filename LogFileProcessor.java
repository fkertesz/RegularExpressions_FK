/**
* LogFileProcessor inputs a log file to process and the print flag. The program processes the file
* and prints certain information depending on the print flag.
* @author Fanni Kertesz
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileProcessor {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("2 arguments required: \n1. the file to process. \n2. the print flag.");
            return;
        }

        String file = args[0];//first argument is file name
        
        // Process the file. Count lines and create hashmaps of the IP addresses and
        // usernames
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Hashmaps for storing ip addresses and usernames
            HashMap<String, Integer> ips = new HashMap<String, Integer>();
            HashMap<String, Integer> users = new HashMap<String, Integer>();

            // Regex patterns for IP address and username
            String ipRegex = "\\b((1?\\d?\\d|2([0-4]\\d|5[0-5]))\\.){3}(1?\\d?\\d|2([0-4]\\d|5[0-5]))\\b";
            String userRegex = "\\buser\\s[a-zA-Z]+\\b";
            Pattern ipPattern = Pattern.compile(ipRegex);
            Pattern userPattern = Pattern.compile(userRegex);

            // read each line of the file
            String line;
            int lineCount = 0;// number of lines
            while ((line = br.readLine()) != null) {
                lineCount++;
                Matcher ipMatcher = ipPattern.matcher(line);
                Matcher userMatcher = userPattern.matcher(line);

                // while a pattern is found in the line, add 1 to the frequency of the pattern
                // in the hashmap
                while (ipMatcher.find()) {
                    String currentIp = ipMatcher.group();// stores ip the matcher found
                    if (ips.get(currentIp) == null) {
                        ips.put(currentIp, 1);
                    } else {
                        ips.put(currentIp, ips.get(currentIp) + 1);
                    }
                }

                while (userMatcher.find()) {
                    String userExtra = userMatcher.group();// stores "user " followed by the username
                    int index = userExtra.indexOf(" ");
                    String currentUser = userExtra.substring(index + 1);// stores just the username

                    if (users.get(currentUser) == null) {
                        users.put(currentUser, 1);
                    } else {
                        users.put(currentUser, users.get(currentUser) + 1);
                    }
                }
            }

            // If the print flag is 2, print the usernames and their frequencies in the log.
            if (args[1].equals("2")) {
                printHashMap(users);
            }

            // If the print flag is 1, print the IP addresses and their frequencies in the log,
            // and the lines parsed, and total unique ip addresses and users.
            else if (args[1].equals("1")) {
                printHashMap(ips);
                printTotals(lineCount, ips, users);
            }

            // If the print flag is 0 or anything else, print the lines parsed, and total unique
            // ip addresses and users.
            else {
                printTotals(lineCount, ips, users);
            }
        }
    }

    /**
     * Method for returning the size of the input hashmap.
     * 
     * @param hm hashmap whose size we want
     * @return size of the hashmap
     */
    private static int sizeHashMap(HashMap<String, Integer> hm) {
        return hm.keySet().size();
    }

    /**
     * Method for printing the contents of the input hashmap.
     * 
     * @param hm hashmap to print
     */
    private static void printHashMap(HashMap<String, Integer> hm) {
        for (String key : hm.keySet()) {
            System.out.println(key + ": " + hm.get(key));
        }
    }

    /**
     * Method to print out the lines parsed while processing, and the total number of unique IP addresses
     * and usernames in the log.
     * @param lineCount lines parsed
     * @param ips hashmap of ip addresses
     * @param users hashmap of usernames
     */
    private static void printTotals(int lineCount, HashMap<String, Integer> ips, HashMap<String, Integer> users) {
        System.out.println(lineCount + " lines in the log file were parsed.");
        System.out.println("There are " + sizeHashMap(ips) + " unique IP adresses in the log.");
        System.out.println("There are " + sizeHashMap(users) + " unique users in the log.");
    }
}