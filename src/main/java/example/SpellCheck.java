package example;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class SpellCheck {
  public static void main(String[] args) throws IOException {

    String spellCheckDir = "spell";
    String spellCheckDic = "doc/dic.txt";
    String wordToRespell = "wi";

    //先建立spellcheck縮需要的索引
    //在這裡會先把遠原始的字典檔轉成索引

    Directory spellIndexDir = FSDirectory.open(Paths.get(spellCheckDir));
    SpellChecker spellChecker = new SpellChecker(spellIndexDir);
    IndexWriterConfig config = new IndexWriterConfig(null);
    spellChecker.indexDictionary(new PlainTextDictionary(Paths.get(spellCheckDic)), config, false);


    //利用前面取得的目錄作拼寫檢查
    spellChecker.setStringDistance(new LevensteinDistance());
    String[] suggestions = spellChecker.suggestSimilar(wordToRespell, 5); //#C
    System.out.println(suggestions.length + " suggestions for '" + wordToRespell + "':");
    for (String suggestion : suggestions)
      System.out.println(" " + suggestion);

    // close
    spellIndexDir.close();
    spellChecker.close();


  }
}
