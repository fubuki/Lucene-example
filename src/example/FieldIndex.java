package example;

import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;




public class FieldIndex {
	public static void main(String[] args) throws IOException {
		
		Directory directory = new RAMDirectory();
		Document doc = new Document(); 
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
		IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_46, analyzer)) ; 
		//DateTools
		//Indexing dates & times
		doc.add(new Field("Date", 
				DateTools.dateToString(new Date(), DateTools.Resolution.DAY),
				Field.Store.YES, 
				Field.Index.NOT_ANALYZED)); 
		
		System.out.println("Date:"+doc.get("Date"));
		
		//Indexing Number
		doc.add(new Field("size", "4096", Field.Store.YES, Field.Index.NOT_ANALYZED)); 
		System.out.println("size:"+doc.get("size"));
		doc.add(new Field("price", "10.99", Field.Store.YES, Field.Index.NOT_ANALYZED)); 
		System.out.println("price:"+doc.get("price"));
		doc.add(new Field("author", "Arthur C. Clark", Field.Store.YES, Field.Index.NOT_ANALYZED)); 
		System.out.println("author:"+doc.get("author"));
				
		writer.addDocument(doc);
		
	}
}
