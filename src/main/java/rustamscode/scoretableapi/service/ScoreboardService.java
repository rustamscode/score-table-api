package rustamscode.scoretableapi.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rustamscode.scoretableapi.dto.ScoreboardUnit;
import rustamscode.scoretableapi.model.Game;
import rustamscode.scoretableapi.repository.GameRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreboardService {
    final GameRepository gameRepository;

    @Autowired
    public ScoreboardService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<ScoreboardUnit> getScoreboard(LocalDate date) {
        List<Game> games = gameRepository.findGamesBeforeDate(date);
        Map<String, ScoreboardUnit> scoreboardMap = new HashMap<>();

        for (Game game : games) {
            String homeTeamName = game.getHomeTeam().getName();
            String awayTeamName = game.getAwayTeam().getName();
            int homeScore = game.getHomeScore();
            int awayScore = game.getAwayScore();

            processGame(scoreboardMap, homeTeamName, homeScore, awayScore);
            processGame(scoreboardMap, awayTeamName, awayScore, homeScore);
        }

        return scoreboardMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(ScoreboardUnit::getPointCount).reversed())
                .toList();

    }

    private void processGame(Map<String, ScoreboardUnit> scoreboardMap, String teamName, int teamScore,
                             int opponentScore) {
        ScoreboardUnit scoreboardUnit = scoreboardMap.getOrDefault(teamName,
                new ScoreboardUnit(teamName, 0, 0));

        scoreboardUnit.increaseGameCount();

        if (teamScore > opponentScore) {
            scoreboardUnit.addWinPoints();
        } else if (teamScore == opponentScore) {
            scoreboardUnit.addDrawPoints();
        }

        scoreboardMap.put(teamName, scoreboardUnit);
    }
}
