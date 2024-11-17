package rustamscode.scoretableapi.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rustamscode.scoretableapi.dto.GameRequest;
import rustamscode.scoretableapi.model.*;
import rustamscode.scoretableapi.repository.*;

import java.time.LocalDate;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameService {
    final GameRepository gameRepository;
    final TeamRepository teamRepository;

    @Autowired
    public GameService(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    public Game registerGame(GameRequest gameRequest) {
        Game game = new Game();
        LocalDate date = LocalDate.now();

        String homeTeamName = gameRequest.getHomeTeam().getName();
        String awayTeamName = gameRequest.getAwayTeam().getName();

        teamRepository.findByName(homeTeamName)
                .orElseThrow(() -> new IllegalArgumentException("Команда " + homeTeamName + " не найдена"));
        teamRepository.findByName(awayTeamName)
                .orElseThrow(() -> new IllegalArgumentException("Команда " + awayTeamName + " не найдена"));

        game.setSeason(Season.getSeasonByDate(date));
        game.setDate(date);
        game.setHomeTeam(gameRequest.getHomeTeam());
        game.setAwayTeam(gameRequest.getAwayTeam());
        game.setHomeScore(gameRequest.getHomeScore());
        game.setAwayScore(gameRequest.getAwayScore());

        return gameRepository.save(game);
    }
}
