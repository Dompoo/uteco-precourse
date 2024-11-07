package store.io.reader;

public interface Reader {

    String[] readLineAsStrings(String spliter);

    String readLineAsString();
}
