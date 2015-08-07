package example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKanalyzer {
	public static void main(String[] args) throws IOException, ParseException {


		Reader reader = new StringReader("中文維基百科將自己定位為一個包含人類所有知識領域的百科全書，而不是一本字典、詞典、論壇或任何其他性質的網站");
			
        Analyzer a = new IKAnalyzer();      
       
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
