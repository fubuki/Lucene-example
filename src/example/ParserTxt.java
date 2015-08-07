package example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;

public class ParserTxt {
	public static void main(String[] args) throws IOException, ParseException {

		int minGram = 1;
		int maxGram = 3;
		Reader reader = new StringReader("中文維基百科將自己定位為一個包含人類所有知識領域的百科全書，而不是一本字典、詞典、論壇或任何其他性質的網站");
		NGramTokenizer gramTokenizer = new NGramTokenizer(minGram, maxGram);
		gramTokenizer.setReader(reader);
		gramTokenizer.reset();
		CharTermAttribute charTermAttribute = gramTokenizer.addAttribute(CharTermAttribute.class);
	

		while (gramTokenizer.incrementToken()) {
		    String token = charTermAttribute.toString();
		    System.out.println(token);
		    //Do something
		}
		
		gramTokenizer.end();
		gramTokenizer.close();
	}
}
