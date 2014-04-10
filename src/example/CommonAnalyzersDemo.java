package example;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.io.StringReader;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.UAX29URLEmailAnalyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;

public final class CommonAnalyzersDemo {

    private static void displayTtokens(Analyzer analyzer,
                            String text) throws IOException {
        TokenStream tokenStream =
                analyzer.tokenStream("bogus", new StringReader(text));
        CharTermAttribute termAttr =
                tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            String token = termAttr.toString();
            System.out.print("[" + token + "] ");
        }
        System.out.println();
        tokenStream.end();
        tokenStream.close();
    }

    public static void main(String[] args) throws IOException {

        String testinput =
            "he MSA looks at the destination address provided in the SMTP protocol (not from the message header), in this case bob@b.org. An Internet email address is a string of the form localpart@exampledomain. The part before the @ sign is the local part of the address, often the username of the recipient, and the part after the @ sign is a domain name or a fully qualified domain name. The MSA resolves a domain name to determine the fully qualified domain name of the mail server in the Domain Name System (DNS).";
        Version ver=Version.LUCENE_46;

        HashMap<String,Analyzer> analyzers = new HashMap<String,Analyzer>();
        // SimpleAnalyzer
        analyzers.put("SimpleAnalyzer", new SimpleAnalyzer(ver));
        // StopAnalyzer
        analyzers.put("StopAnalyzer", new StopAnalyzer(ver));
        // WhitespaceAnalyzer
        analyzers.put("WhitespaceAnalyzer", new WhitespaceAnalyzer(ver));
        // StandardAnalyzer
        analyzers.put("StandardAnalyzer", new StandardAnalyzer(ver));
        // ClassicAnalyzer
        analyzers.put("ClassicAnalyzer", new ClassicAnalyzer(ver));
        // UAX29URLEmailAnalyzer
        analyzers.put("UAX29URLEmailAnalyzer", new UAX29URLEmailAnalyzer(ver));

        System.out.println("INPUT STRING:" + testinput);
        for (Iterator it = analyzers.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry e = (Map.Entry)it.next();
            String name = (String)e.getKey();
            Analyzer analyzer = (Analyzer)e.getValue();
            System.out.println("Analyzer:" + name);
            displayTtokens(analyzer, testinput);
        }
    }
}
