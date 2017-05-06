package apn;

public class WordPair implements Comparable<WordPair>
{
	private int repeats;
	private String word;
	
	public WordPair(String word, int repeats)
	{
		this.word = word;
		this.repeats = repeats;
	}
	public WordPair(String word)
	{
		this.word = word;
		repeats = 1;
	}
	
	public void increaseRepeats()
	{
		repeats++;
	}
	
	public int getRepeats()
	{
		return repeats;
	}
	
	public String getWord()
	{
		return word;
	}
	
	@Override
	public int compareTo(WordPair anotherPair) 
	{
		if(repeats<anotherPair.getRepeats())
			return -1;
		else
			if(repeats>anotherPair.getRepeats())
				return 1;
			else
				return -word.compareTo(anotherPair.getWord());
	}
	
}
