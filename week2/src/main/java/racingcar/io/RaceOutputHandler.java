package racingcar.io;

import racingcar.dto.RaceResult;
import racingcar.io.writer.Writer;

public class RaceOutputHandler {

    private final Writer writer;
    private final RaceResultParser parser;

    public RaceOutputHandler(Writer writer, RaceResultParser parser) {
        this.writer = writer;
        this.parser = parser;
    }

    public void handle(RaceResult raceResult) {
        String resultString = parser.parse(raceResult);
        writer.write(resultString);
    }
}
