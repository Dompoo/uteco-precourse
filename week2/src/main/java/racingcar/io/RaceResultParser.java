package racingcar.io;

import java.util.List;
import java.util.StringJoiner;
import racingcar.constants.OutputMessages;
import racingcar.constants.StringConstants;
import racingcar.dto.CarStatus;
import racingcar.dto.LapResult;
import racingcar.dto.RaceResult;

public class RaceResultParser {

    public String parse(RaceResult raceResult) {
        StringBuilder sb = new StringBuilder();

        appendExecutionResults(sb, raceResult.getLapResults());
        appendWinners(sb, raceResult.getWinners());

        return sb.toString();
    }

    private void appendExecutionResults(StringBuilder sb, List<LapResult> lapResults) {
        sb.append(StringConstants.NEW_LINE)
                .append(OutputMessages.EXECUTION_RESULT)
                .append(StringConstants.NEW_LINE);

        if (lapResults == null || lapResults.isEmpty()) {
            throw new IllegalArgumentException("경주 결과가 존재하지 않습니다.");
        }

        for (LapResult result : lapResults) {
            sb.append(result)
                    .append(StringConstants.NEW_LINE);
        }
    }

    private void appendWinners(StringBuilder sb, List<CarStatus> winners) {
        sb.append(OutputMessages.FINAL_WINNER);

        if (winners == null || winners.isEmpty()) {
            throw new IllegalArgumentException("최종 우승자가 존재하지 않습니다.");
        }

        StringJoiner stringJoiner = new StringJoiner(StringConstants.SEPARATOR);
        for (CarStatus winner : winners) {
            stringJoiner.add(winner.getName());
        }
        sb.append(stringJoiner);
    }
}
