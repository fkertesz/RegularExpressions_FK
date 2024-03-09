/**
* NovelProcessor inputs a novel and a list of regex patterns and outputs a file novelname_wc.txt that
* contains the regex patterns and their frequencies in the novel.
* @author Fanni Kertesz
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelProcessor
{
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("2 arguments required: \n1. name of novel. \n2. name of file with regex patterns.");
            return;
        }

        String novelName = args[0];//name of novel file
        String patternFile = args[1];//name of pattern file

        //run the process method to process a novel, check for patterns, and create new file with pattern frequencies
        try{
        process(novelName, patternFile);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    //Method for recognizing patterns
    public static void process(String novelName, String patternFile) throws IOException
    {
        HashMap<String, Integer> patternCounter = new HashMap<>();//hashmap that stores the frequency of each word variation

        //Put patterns and 0 frequency into hashmap
        try(BufferedReader patternReader = new BufferedReader(new FileReader(patternFile)))
        {
            String line;//stores one pattern at a time as a String

            //read patterns until there are no more lines
            while((line = patternReader.readLine()) != null)
            {
                patternCounter.put(line, 0);
            }
        }

        //Process novel for patterns and record frequencies
        try(BufferedReader novelReader = new BufferedReader(new FileReader(novelName)))
        {
            String line;//stores one line of the novel at a time as a String

            //processes novel line by line and recognizes patterns until novel runs out of lines
            while((line = novelReader.readLine()) != null)
            {
                //iterator for patterns
                for(String pattern : patternCounter.keySet())
                {
                    Pattern currentPattern = Pattern.compile(pattern);//Pattern object created from String version of pattern
                    Matcher matcher = currentPattern.matcher(line);//Matcher object for the current pattern and current novel line

                    //while a pattern is found in the line, add 1 to the frequency of the pattern in the hashmap
                    while(matcher.find())
                    {
                        patternCounter.put(pattern, patternCounter.get(pattern)+1);
                    }
                }

            }

        }

        //Creating new file's name
        String shortNovelName = novelName.substring(0, novelName.indexOf('.')); //novel name without .txt
        String newFileName = shortNovelName + "_wc.txt";
        
        //Create new file with the patterns and their respective frequencies
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(newFileName)))
        {
            for(String pattern : patternCounter.keySet())
            {
                bw.write(pattern + "|" + patternCounter.get(pattern) + "\n");
            }
        }
        


    }
}