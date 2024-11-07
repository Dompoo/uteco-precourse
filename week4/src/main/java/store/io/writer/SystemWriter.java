package store.io.writer;

public class SystemWriter implements Writer {

    @Override
    public void writeLine(String value) {
        System.out.println(value);
    }

    @Override
    public void writeEmptyLine() {
        System.out.println();
    }
}
