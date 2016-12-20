package example;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoadPdf {
  public static void main(String[] args) throws TikaException, IOException {

    InputStream is = null;
    try {
      is = new FileInputStream("doc/index.pdf");
      ContentHandler contenthandler = new BodyContentHandler();
      Metadata metadata = new Metadata();
      PDFParser pdfparser = new PDFParser();
      pdfparser.parse(is, contenthandler, metadata, new ParseContext());
      System.out.println(contenthandler.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (is != null)
        is.close();
    }
  }

}
