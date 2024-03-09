/**
* WordCounter inputs the 6 output files from the processed novels, counts the total
* number of frequencies of each regex pattern, and outputs the pattern with its
* respective total frequency throughout the 6 novels.
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
public class WordCounter
{
    public static void main(String[] args) throws IOException
    {
        //if not 6 arguments, ends program
        if(args.length != 6)
        {
            System.out.println("6 arguments required for processing the 6 novels.");
            return;
        }

        //store names of the novel count files in file and check if they're _wc.txt files
        //if not the right files, ends program
        String[] countFiles = new String[6];
        for(int i = 0; i < 6; i++)
        {
            if(! args[i].endsWith("_wc.txt"))
            {
                System.out.println("Wrong file inputted. Only input XXX_wc.txt files.");
                return;
            }
            countFiles[i] = args[i];
        }

        HashMap<String, Integer> patternCounter = new HashMap<>();//hashmap that stores the frequency of each pattern

        //Iterate through all 6 files
        for(int i = 0; i < 6; i++)
        {
            //Iterate through each line and record pattern and add frequency
            try(BufferedReader br = new BufferedReader(new FileReader(countFiles[i])))
            {
                String line = "";
                while((line = br.readLine()) != null)
                {
                    //Get index of last '|' and split up line by that for pattern and frequency
                    int index = line.lastIndexOf("|");
                    String pattern = line.substring(0, index);
                    String frequency = line.substring(index+1);

                    //if pattern hasn't been recorded 
                    if(patternCounter.get(pattern) == null)
                    {
                        patternCounter.put(pattern, Integer.parseInt(frequency));
                    }
                    //if pattern has ben recorded
                    else
                    {
                        patternCounter.put(pattern, patternCounter.get(pattern)+Integer.parseInt(frequency));
                    }

                }
            }
        }

        //Print out the patterns and their total frequencies
        for(String pattern : patternCounter.keySet())
        {
            System.out.println(pattern + "|" + patternCounter.get(pattern));
        }

    }
}