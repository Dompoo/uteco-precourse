package racingcar.io;

import java.util.List;
import java.util.StringJoiner;
import racingcar.dto.CarStatus;
import racingcar.dto.LapResult;
import racingcar.dto.RaceResult;

public class RaceResultParser {

    private static final String NEW_LINE = "\n";
    private static final String EXECUTION_RESULT = "실행 결과 : ";
    private static final String FINAL_WINNER = "최종 우승자 : ";
    private static final String WINNER_SEPARATOR = ", ";

    public String parse(RaceResult raceResult) {
        StringBuilder sb = new StringBuilder();

        appendLapResults(sb, raceResult.getLapResults());
        appendWinners(sb, raceResult.getWinners());

        return sb.toString();
    }

    private static void appendLapResults(StringBuilder sb, List<LapResult> lapResults) {
        sb.append(NEW_LINE)
                .append(EXECUTION_RESULT)
                .append(NEW_LINE);

        if (lapResults == null || lapResults.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        for (LapResult result : lapResults) {
            appendLapResult(sb, result);
        }
    }

    private static void appendLapResult(StringBuilder sb, LapResult result) {
        for (CarStatus carStatus : result.getCarStatuses()) {
            sb.append(carStatus)
                    .append(NEW_LINE);
        }
        sb.append(NEW_LINE);
    }

    private static void appendWinners(StringBuilder sb, List<CarStatus> winners) {
        sb.append(FINAL_WINNER);

        if (winners == null || winners.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        StringJoiner joiner = new StringJoiner(WINNER_SEPARATOR);
        for (CarStatus winner : winners) {
            joiner.add(winner.getName());
        }
        sb.append(joiner);
    }
}
