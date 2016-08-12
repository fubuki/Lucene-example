package example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.queryparser.classic.ParseException;

public class Kuromoji {
	public static void main(String[] args) throws IOException, ParseException {

		japan("10日放送の「中居正広のミになる図書館」（テレビ朝日系）で、SMAPの中居正広が、篠原信一の過去の勘違いを明かす一幕があった。");

		japan("スピッツ ");
		japan("すぴっつ ");
		
		japan("ありがとうございました ");	
		japan("アリガトウゴザイマシタ ");	

	}
	
	public static void japan(String input) throws IOException, ParseException {
		
		Reader reader = new StringReader(input);
		
        Analyzer a = new JapaneseAnalyzer();               
        TokenStream ts = a.tokenStream("", reader);   
        OffsetAttribute offsetAttribute = ts.getAttribute(OffsetAttribute.class);  
        CharTermAttribute termAttribute = ts.getAttribute(CharTermAttribute.class);           
        int n = 0;   
        ts.reset();
        while (ts.incrementToken()) {   
            int startOffset = offsetAttribute.startOffset();  
            int endOffset = offsetAttribute.endOffset();  
            String term = termAttribute.toString();  
            n++;   
            
            System.out.println("Token("+n+") 的內容為："+term);   
        }   
        System.out.println("==共有詞條"+n+"條=="); 
        
        ts.end();
        ts.close();
	}
}
