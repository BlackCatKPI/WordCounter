package apn.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import apn.WordCounter;
import apn.WordPair;

public class WordCounterTest 
{
     
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private PrintStream original;
	@Before
	public void setUpStream() 
	{
		original = System.out;
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() 
	{
	    System.setOut(original);
	}
		
    @Test
    public void testMain1()  
    {
    	String args[]={};
    	WordCounter.main(args);
    }
        
    @Test
    public void testMain2()  
    {
    	String args[]={"dummy.txt"};
    	WordCounter.main(args);
    	assertEquals("File dummy.txt not found"+System.lineSeparator(), outContent.toString());
    }   
     
    @Test
    public void testMain3()  
    {
    	String args[]={"testfiles"+File.separator+"omar.txt","2"};
    	WordCounter.main(args);
    	assertEquals("лучше=2"+System.lineSeparator()+"попало=2"+System.lineSeparator(), outContent.toString());
    }
    
    @Test
    public void testMain4()  
    {
    	String args[]={"lyrics.txt","1"};
        WordCounter.main(args);
        assertEquals("merry=16"+System.lineSeparator(), outContent.toString());
    }  
    
    @Test
    public void testMain5()  
    {
    	String args[]={"testfiles"+File.separator+"clear.txt","-1"};
        WordCounter.main(args);
        assertEquals("", outContent.toString());
    } 
    
    @Test
    public void testMain6()  
    {
    	String args[]={"testfiles"+File.separator+"simple.txt","-1"};
        WordCounter.main(args);
        assertEquals("a=3"+System.lineSeparator()+"c=3"+System.lineSeparator()+"b=2"+System.lineSeparator(), outContent.toString());
    }  
    
    @Test
    public void testWordCounter1()  
    {
    	WordCounter wordCounter = new WordCounter("lyrics.txt",1);
    	HashMap<String,WordPair> result = wordCounter.countWords(new StringBuilder().append("a a b b a c a "));
    	WordPair desired = new WordPair("a",4);
    	assertEquals(desired.compareTo(result.get("a")),0);
     }  
    
    @Test
    public void testWordCounter2()  
    {
    	WordCounter wordCounter = new WordCounter("lyrics.txt",1);
    	HashMap<String,WordPair> result = wordCounter.countWords(new StringBuilder().append("a a c b b a c a "));
    	ArrayList<WordPair> sorted = wordCounter.sortPairs(result);
    	WordPair desired = new WordPair("b",2);
    	assertEquals(sorted.get(1).compareTo(desired),0);
    	assertEquals(sorted.size(), 3);
     }  
}
