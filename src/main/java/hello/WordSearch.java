/*
  Creates an empty hashmap
  Loads words to ignore
  Parses HTML and builds hashmap of word occurences
  Run method calls all functions needed to parse HTML and return json
 */

package hello;

import java.io.*;
import java.util.*;


public class WordSearch {

	private Map<String, Integer> wordMap;
	private HashSet<String> badWords;
	private WebScraper scraper;

	WordSearch()
	{
		wordMap = new HashMap<>();
		badWords = new HashSet<>();
		scraper = new WebScraper();
	}

	public void buildMapFromText(String text)
	{
		this.tokenizeTextAndAddToMap(text);
		this.wordMap = sortByValue(wordMap);
	}

	public Map<String, Integer> getWordMap()
	{
		return wordMap;
	}

	void readWordsToIgnore() throws IOException
	{
		File file = new File("wordsToIgnore.txt");
		FileInputStream fis = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		String line = "";
		while((line = reader.readLine()) != null)
		{
			String[] tokens = line.split("[^a-zA-Z]");
			for(int i = 0; i < tokens.length; i++)
			{
				if(!tokens[i].equals(""))
				{
					badWords.add(tokens[i].toLowerCase());
				}
			}
		}
		reader.close();
	}

	public String wordOccurences(String word){
		word = word.toLowerCase();
		word = word.trim();
		if(word.equals("")){
			return "";
		}
		else if(wordMap.get(word) == null){
			return word.toUpperCase() + " does not appear.";
		}
		else
			return word.toUpperCase() + " appears " + wordMap.get(word) + " times.";
	}


	// Takes in a hashMap and returns an ordered hashMap
	public <K, V extends Comparable<? super V>> Map<K, V>
	sortByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list =
				new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
		{
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue() );
			}
		} );

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}

	void tokenizeTextAndAddToMap(String line)
	{
		String[] tokens = line.split("[^a-zA-Z]");
		for(int i = 0; i < tokens.length; i++)
		{
			if(!tokens[i].equals(""))
			{
				addToMap(tokens[i].toLowerCase());
			}
		}
	}

	private void addToMap(String word)
	{
		if(badWords.contains(word)) return; // if it is a meaningless word, don't add it.

		if(wordMap.containsKey(word)) // if the word is already in the map, replace it with a count + 1
		{
			int wordCount = wordMap.get(word);
			wordMap.remove(word);
			wordMap.put(word, wordCount + 1);
		}
		else
		{
			wordMap.put(word, 1); // otherwise add it with count of 1
		}
	}


	public String run(String domain, String json) throws IOException
	{
		readWordsToIgnore();              // reads the unimportant words into a set
		String[] urls = null;
		String jsonString = "";
		if(domain == null){
			throw new IOException("null domain");
		}
		else if(domain.equals("guardian"))
		{
			//urls = JsonConverter.readJsonFileToStringArrayGuardian(); // delete when im getting json input
			urls = JsonConverter.getStringUrlArrayFromGuardianLoop(json); // implement when im getting json input
		}
		else if(domain.equals("nytimes"))
		{
			//urls = JsonConverter.readJsonFileToStringArrayNYT();  // delete when im getting json input
			urls = JsonConverter.getStringUrlArrayFromNYT(json); // implement when im getting json input
		}
		else
		{
			System.out.println("not a valid domain name");
		}

		if(urls != null)
		{
			String text = scraper.getTextFromUrls(urls);                   // parses out text from html returned by web scraper
			buildMapFromText(text);                                 // creates an ordered hash map from string
			jsonString = JsonConverter.createJsonStringFromMap(getWordMap(), 15);  // creates jsonArray (as string) from wordMap
		}
		else
		{
			throw new IOException("null string");

		}
		return jsonString;
	}

}