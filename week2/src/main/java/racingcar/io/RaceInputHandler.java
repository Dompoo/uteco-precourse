package racingcar.io;

import racingcar.io.reader.Reader;
import racingcar.io.writer.Writer;

public class RaceInputHandler {

    private static final String CARNAME_DESCRIPTION = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)\n";
    private static final String LAPCOUNT_DESCRIPTION = "시도할 횟수는 몇 회인가요?\n";

    private final Reader reader;
    private final Writer writer;
    private final RaceInputValidator validator;

    public RaceInputHandler(
            Reader reader,
            Writer writer,
            RaceInputValidator validator
    ) {
        this.reader = reader;
        this.writer = writer;
        this.validator = validator;
    }

    public RaceInputDto handle() {
        String carNames = getCarNames();
        int lapCount = getLapCount();

        return RaceInputDto.of(carNames, lapCount);
    }

    private String getCarNames() {
        writer.write(CARNAME_DESCRIPTION);
        String carNames = reader.readLine();
        validator.validateCarNames(carNames);
        return carNames;
    }

    private int getLapCount() {
        writer.write(LAPCOUNT_DESCRIPTION);
        int lapCount = reader.readLineToInt();
        validator.validateLapCount(lapCount);
        return lapCount;
    }
}
