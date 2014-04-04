package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class IndexDoc {
	public static void main(String[] args) throws TikaException, IOException {

		InputStream is = null;
		try {
			is = new FileInputStream("doc/esslli07_slides01.pdf");
			ContentHandler contenthandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			PDFParser pdfparser = new PDFParser();
			pdfparser.parse(is, contenthandler, metadata, new ParseContext());
			System.out.println(contenthandler.toString());
			
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
			Directory directory = new RAMDirectory();
			IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_46, analyzer)) ;  
			Document doc = new Document();
			doc.add(new TextField("content", contenthandler.toString(), Store.YES)) ; 
            FieldType type = new FieldType() ;  
            type.setIndexed(true) ;  
            type.setStored(true) ;
            
            writer.addDocument(doc);
            writer.close();
            
            DirectoryReader reader = DirectoryReader.open(directory);  
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new TermQuery(new Term("content", "wiki")) ;   
            TopDocs topDocs = searcher.search(query, 10) ;  
            ScoreDoc[] scores = topDocs.scoreDocs ;  
            int length = scores.length ; 
            System.out.println("length:"+length);
            Document result = searcher.doc(scores[0].doc) ;
            System.out.println("result:"+result.get("content"));
            
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
		}
	}
}
