package store.testUtil.testDouble;

import java.util.ArrayList;
import java.util.List;
import store.io.writer.Writer;

public class WriterFake implements Writer {

    private final List<String> outputs = new ArrayList<>();

    @Override
    public void writeLine(String value) {
        outputs.add(value);
    }

    @Override
    public void writeEmptyLine() {
        outputs.add("\n");
    }

    public List<String> getOutputs() {
        return outputs;
    }
}
