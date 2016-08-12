package example;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.queries.function.FunctionQuery;
import org.apache.lucene.queries.function.valuesource.LongFieldSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;



public class DocBoost {
	public static void main(String[] args) throws IOException {
		// add two documents to the index
		Analyzer analyzer = new StandardAnalyzer();
		Directory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(analyzer)) ;  
		
		Document doc = new Document();
		doc.add(new TextField("f", "test document1", Store.YES));
		doc.add(new NumericDocValuesField("boost", 1L));
		writer.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("f", "test document2", Store.YES));
		doc.add(new NumericDocValuesField("boost", 2L));
		writer.addDocument(doc);

		writer.close();
		// search for 'test' while boosting by field 'boost'
		Query baseQuery = new TermQuery(new Term("f", "test"));
		Query boostQuery = new FunctionQuery(new LongFieldSource("boost"));
		Query q = new CustomScoreQuery(baseQuery, (FunctionQuery) boostQuery);
		DirectoryReader reader = DirectoryReader.open(directory);  
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs topDocs = searcher.search(q, 10);
        ScoreDoc[] scores = topDocs.scoreDocs ;  
        int length = scores.length;
        
        for (int i = 0; i < length; i++) {
            Document result = searcher.doc(scores[i].doc) ;
            System.out.println("result:"+result.get("f"));
        }

	}
}
