package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;

public class SmartChineseAnalysis {
	public static void main(String[] args) throws IOException, ParseException {

		Analyzer analyzer = new SmartChineseAnalyzer(true);
		String str = "中文維基百科將自己定位為一個包含人類所有知識領域的百科全書，而不是一本字典、詞典、論壇或任何其他性質的網站";
		List<String> result = new ArrayList<String>();
		try{
			 TokenStream tokenStream = analyzer.tokenStream("field", str);    
			 CharTermAttribute term=tokenStream.addAttribute(CharTermAttribute.class);    
			 tokenStream.reset();       
			 while( tokenStream.incrementToken() ){        
				 result.add( term.toString() );       
			 }       
			 tokenStream.end();       
			 tokenStream.close();   
		 } catch (IOException e) {    
			 e.printStackTrace();   
		 }
		
		System.out.println(result);
	
		analyzer.close();
	}
}
