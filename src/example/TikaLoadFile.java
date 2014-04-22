package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class TikaLoadFile {
	public static void main(String[] args) throws TikaException, IOException {

		InputStream is = null;
		InputStream txt = null;
		try {
			is = new FileInputStream("doc/example.doc");
			ContentHandler contenthandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			OfficeParser officeparser = new OfficeParser();
			officeparser.parse(is, contenthandler, metadata, new ParseContext());
			
			txt = new FileInputStream("doc/dic.txt");
			ContentHandler txtContent = new BodyContentHandler();
			Metadata Txtmetadata = new Metadata();
			TXTParser txtparser = new TXTParser();
			txtparser.parse(is, txtContent, Txtmetadata, new ParseContext());
			
			
			LanguageIdentifier identifier = new LanguageIdentifier("WikipediA. English The Free Encyclopedia 4 490 000+ articles");
			String language = identifier.getLanguage();
			System.out.println(language);
			System.out.println(contenthandler.toString());
			System.out.println(Txtmetadata+":"+txtContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
		}
	}
	
	
	
}
