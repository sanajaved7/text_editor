package document;

import java.util.List;

/** 
 * A naive implementation of the Document abstract class. 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document 
{
	private String word;


	/** Create a new BasicDocument object
	 * 
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text)
	{
		super(text);
	}
	
	
	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords()
	{
		String text = getText();
		if (text.length() != 0) {
			String search = "[a-zA-Z]+";
			int size = getTokens(search).size();
			return size;
		}
	    return 0;
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an 
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of 
	 * characters in the document, even if they don't end with a punctuation mark.
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences()
	{
		String text = getText();
		if (text.length() != 0) {
			String[] words  = text.split("[.!?]+");
			int size = words.length;
			return size;
		}
		return 0;
	}
	
	/**
	 * Get the number of syllables in the document.
	 * Words are defined as above.  Syllables are defined as:
	 * a contiguous sequence of vowels, except for a lone "e" at the 
	 * end of a word if the word has another set of contiguous vowels, 
	 * makes up one syllable.   y is considered a vowel.
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables()
	{	
		// splits text into words using regex,
		// loops over each word,
		// if e at the end of the word and 2nd to last letter,
		// isn't a vowel as well, subtracts from syllable count,
		// then goes on to count syllables using regex.
		
		
		//turns text all into lower case so easier 
		//to check and manipulate text later on
		String text = getText().toLowerCase();
		
		//ensures text isn't empty
		if (text.length() !=0){
			String[] words = text.split("[^a-zA-Z0-9]+");
			int count = 0;
			
			for (String word : words) {
				// accounts for edge case with the word "the"
				if (word.equals("the")){
					count++;
				}
				int word_length = word.length();
				if (word_length > 2){
					char[] word_letters = word.toCharArray();
					char last_letter = word_letters[word_length-1];
					if ('e' == last_letter){
						char second_to_last_letter = word_letters[word_length-2];
						if ('a' != second_to_last_letter && 'e'!= second_to_last_letter && 'i'!=second_to_last_letter && 'o'!=second_to_last_letter && 'u'!=second_to_last_letter && 'y'!=second_to_last_letter){
							count--;
						}
					}
				}
				String search = "[aeiouyAEIOUY]+";
				int size = getWordTokens(search, word).size();
				count += size;
				System.out.print(word);
				System.out.println(count);
			}
			return count;
		}
		else {
			return 0;
		}
	}
	
	
	/* The main method for testing this class. 
	 * You are encouraged to add your own tests.  */
	public static void main(String[] args)
	{
		testCase(new BasicDocument("This is a test.  How many???  "
		        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
		        + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		testCase(new BasicDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new BasicDocument("Segue Double"), 3, 2, 1);
		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
		testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
	}
	
}
