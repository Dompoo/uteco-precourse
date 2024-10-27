package racingcar.io;

import java.util.List;
import racingcar.dto.CarStatus;
import racingcar.dto.LapResult;
import racingcar.dto.RaceResult;

public class RaceResultParser {

    private static final String NEW_LINE = "\n";
    private static final String EXECUTION_RESULT = "실행 결과";
    private static final String FINAL_WINNER = "최종 우승자 : ";
    private static final String WINNER_SEPARATOR = ", ";

    public String parse(RaceResult raceResult) {
        StringBuilder sb = new StringBuilder();

        appendExecutionResults(sb, raceResult.getLapResults());
        appendWinners(sb, raceResult.getWinners());

        return sb.toString();
    }

    private void appendExecutionResults(StringBuilder sb, List<LapResult> lapResults) {
        sb.append(NEW_LINE)
                .append(EXECUTION_RESULT)
                .append(NEW_LINE);

        if (lapResults != null) {
            for (LapResult result : lapResults) {
                sb.append(result)
                        .append(NEW_LINE);
            }
        }
    }

    private void appendWinners(StringBuilder sb, List<CarStatus> winners) {
        sb.append(FINAL_WINNER);

        if (winners == null || winners.isEmpty()) {
            return;
        }

        for (int i = 0; i < winners.size(); i++) {
            sb.append(winners.get(i).getName());
            if (i != winners.size() - 1) {
                sb.append(WINNER_SEPARATOR);
            }
        }
    }
}
