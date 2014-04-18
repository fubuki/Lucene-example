package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class TikaLoadFile {
	public static void main(String[] args) throws TikaException, IOException {

		InputStream is = null;
		try {
			is = new FileInputStream("doc/example.doc");
			ContentHandler contenthandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			OfficeParser officeparser = new OfficeParser();
			officeparser.parse(is, contenthandler, metadata, new ParseContext());
			
			LanguageIdentifier identifier = new LanguageIdentifier("WikipediA. English The Free Encyclopedia 4 490 000+ articles");
			String language = identifier.getLanguage();
			System.out.println(language);
			System.out.println(contenthandler.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
		}
	}
	
	
	
}
