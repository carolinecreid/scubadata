/*
   	Establishes connection to the URL to be scraped
   	Loops through URLS and calls jsoup on each URL
   	Returns only wanted html text
 */

package hello;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebScraper {

	private HashSet<String> textSet;
	private HashSet<String> visitedUrls;

	WebScraper() {
		textSet = new HashSet<>();
		visitedUrls = new HashSet<>();
	}

	public String getTextFromUrls(String[] urls)
	{
		String result = "";
		for(String url : urls)
		{
			if(url == null) break;
			result += getTextFromUrl(url) + " ";
		}
		return result;
	}

	public String getTextFromUrl(String url) {
		String result = "";
		try {
			// fetch the document over HTTP
			if( !visitedUrls.contains(url) ) {
				Connection conn = Jsoup.connect(url);
				Document doc = conn.get();
				visitedUrls.add(url);

				Elements paras = doc.getAllElements();
				result = paras.toString();
				result = replacer(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String replacer(String htmlStr) {
		String temp = "";

		//Pattern matcher finds all text in between tags (Article text)
		Pattern p = Pattern.compile("<\\s*p[^>]*>(.*?)<\\s*/\\s*p>");
		Matcher m = p.matcher(htmlStr);

		while (m.find()) {
			String paragraph = m.group(1);
			if (!textSet.contains(paragraph)) {
				textSet.add(paragraph);
				temp += paragraph + "\n";
			}
		}
		temp = (temp.replaceAll("<(.|\\n)*?>", "")); // removes all tags
		return temp;
	}


	// GOING TO BE USED TO FIND SENTENCES SEE BELOW
//	public HashSet<String> getTextSet() {
//		return textSet;
//	}


// FUTURE METHOD TO BE ADDED
//	public String[] getSentencesContainingWord(String word)
//	{
//		ArrayList<String> listOfSentences = new ArrayList<String>();
//
//		for (String sentence : textSet) {
//			if (sentence.contains(word))
//			{
//				sentence.replace(word, word.toUpperCase());
//	            // Parse sentences on '.' into arrayList (except things like Mr. Mrs. F.B.I., etc.)
//				listOfSentences.add(sentence);
//			}
//		}
//
//		String[] toReturn = new String[listOfSentences.size()];
//		toReturn = listOfSentences.toArray(toReturn);
//		return toReturn;
//	}


}
