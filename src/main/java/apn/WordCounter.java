package apn;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class WordCounter 
{
	private final static String defaultFileName = "lyrics.txt";
	private final static int defaultWordsNumber = -1;
	
	private StringBuilder builder;
	private HashMap<String,WordPair> wordPairs;
	private ArrayList<WordPair> sortedPairs;
	private String fileName;
	private int wordsNumber;
	public WordCounter(String fileName, int wordsNumber)
	{
		this.fileName = fileName;
		this.wordsNumber=wordsNumber;
	}
	
	public void process()
	{
		builder = readAndFormatFile(fileName);
		if (builder.length()==0)
			return;
		wordPairs = countWords(builder);
		sortedPairs = sortPairs(wordPairs);
		printSortedWordPairs(sortedPairs, wordsNumber);
	}
	
	public StringBuilder readAndFormatFile(String fileName)
	{
		StringBuilder result = new StringBuilder();
		File inputFile = new File(fileName);
		try(Scanner scanner = new Scanner(new FileInputStream(inputFile)))  
		{
			while(scanner.hasNextLine())
				result.append(scanner.nextLine().toLowerCase().replace(",", " ").replace(".", " ").trim()+" ");
			while (result.indexOf("  ")>0) 
					result.deleteCharAt(result.indexOf("  "));
		}
		 catch (FileNotFoundException e) 
		{
			System.out.println("File " +fileName+ " not found");
		}
		return result;
	}
	
	public HashMap<String,WordPair> countWords(StringBuilder inputString)
	{
		HashMap<String,WordPair> result = new HashMap<>();
		while(inputString.indexOf(" ")>0)
		{
			String word = inputString.substring(0,inputString.indexOf(" "));
			if(result.containsKey(word))
				result.get(word).increaseRepeats();
			else
				result.put(word, new WordPair(word));
			inputString.replace(0, inputString.indexOf(" ")+1, "");
		}
		return result;
	}
	
	public ArrayList<WordPair> sortPairs(HashMap<String,WordPair> wordPairs)
	{
		ArrayList<WordPair> sortingPairs = new ArrayList<WordPair>(wordPairs.values());
		Collections.sort(sortingPairs);
		Collections.reverse(sortingPairs);
		return sortingPairs;
	}
	
	public void printSortedWordPairs(ArrayList<WordPair> sortedPairs, int wordsNumber)
	{
		int i = 0;
		while ((i<wordsNumber&&i<sortedPairs.size()) || (wordsNumber<0&&i<sortedPairs.size()))
		{	
			System.out.println(sortedPairs.get(i).getWord()+"="+sortedPairs.get(i).getRepeats());
			i++;
		}	
	}
	
	public static void main(String[] args) 
	{
		WordCounter wordCounter;
		switch(args.length)
		{
			case 0: {
						wordCounter = new WordCounter(defaultFileName,defaultWordsNumber); 
						break;
					}
			case 1: {
						wordCounter = new WordCounter(args[0],defaultWordsNumber); 
						break;
					}
			default:{
						wordCounter = new WordCounter(args[0],Integer.valueOf(args[1])); 
						break;
					}
		}
		wordCounter.process();
	}

}
