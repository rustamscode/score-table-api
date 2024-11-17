package rustamscode.scoretableapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rustamscode.scoretableapi.dto.ScoreboardUnit;
import rustamscode.scoretableapi.model.Game;
import rustamscode.scoretableapi.model.Season;
import rustamscode.scoretableapi.model.Team;
import rustamscode.scoretableapi.repository.GameRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private ScoreboardService scoreboardService;

    @Test
    void getScoreboard_success() {
        // Arrange: Подготовка данных
        Team teamA = new Team(1L, "Team A");
        Team teamB = new Team(2L, "Team B");
        Team teamC = new Team(3L, "Team C");

        List<Game> games = List.of(
                new Game(1L, Season.AUTUMN, LocalDate.of(2024, 11, 1), teamA, teamB, 3, 1),
                new Game(2L, Season.AUTUMN, LocalDate.of(2024, 11, 2), teamA, teamC, 2, 2),
                new Game(3L, Season.AUTUMN, LocalDate.of(2024, 11, 3), teamB, teamC, 1, 1)
        );

        when(gameRepository.findGamesBeforeDate(LocalDate.of(2024, 11, 4))).thenReturn(games);

        List<ScoreboardUnit> result = scoreboardService.getScoreboard(LocalDate.of(2024, 11, 4));

        assertNotNull(result);
        assertEquals(3, result.size());

        ScoreboardUnit teamAUnit = result.stream().filter(unit -> unit.getTeamName().equals("Team A")).findFirst().orElseThrow();
        assertEquals(2, teamAUnit.getGameCount());
        assertEquals(4, teamAUnit.getPointCount());

        ScoreboardUnit teamBUnit = result.stream().filter(unit -> unit.getTeamName().equals("Team B")).findFirst().orElseThrow();
        assertEquals(2, teamBUnit.getGameCount());
        assertEquals(1, teamBUnit.getPointCount());

        ScoreboardUnit teamCUnit = result.stream().filter(unit -> unit.getTeamName().equals("Team C")).findFirst().orElseThrow();
        assertEquals(2, teamCUnit.getGameCount());
        assertEquals(2, teamCUnit.getPointCount());

        assertEquals("Team A", result.get(0).getTeamName());
        assertEquals("Team C", result.get(1).getTeamName());
        assertEquals("Team B", result.get(2).getTeamName());

        verify(gameRepository).findGamesBeforeDate(LocalDate.of(2024, 11, 4));
    }

    @Test
    void getScoreboard_emptyGames() {
        when(gameRepository.findGamesBeforeDate(LocalDate.of(2024, 11, 4))).thenReturn(List.of());

        List<ScoreboardUnit> result = scoreboardService.getScoreboard(LocalDate.of(2024, 11, 4));

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(gameRepository).findGamesBeforeDate(LocalDate.of(2024, 11, 4));
    }

    @Test
    void getScoreboard_drawGames() {
        Team teamA = new Team(1L, "Team A");
        Team teamB = new Team(2L, "Team B");

        List<Game> games = List.of(
                new Game(1L, Season.AUTUMN, LocalDate.of(2024, 11, 1), teamA, teamB, 1, 1)
        );

        when(gameRepository.findGamesBeforeDate(LocalDate.of(2024, 11, 4))).thenReturn(games);

        List<ScoreboardUnit> result = scoreboardService.getScoreboard(LocalDate.of(2024, 11, 4));

        assertNotNull(result);
        assertEquals(2, result.size());

        ScoreboardUnit teamAUnit = result.stream().filter(unit -> unit.getTeamName().equals("Team A")).findFirst().orElseThrow();
        assertEquals(1, teamAUnit.getPointCount());

        ScoreboardUnit teamBUnit = result.stream().filter(unit -> unit.getTeamName().equals("Team B")).findFirst().orElseThrow();
        assertEquals(1, teamBUnit.getPointCount());

        verify(gameRepository).findGamesBeforeDate(LocalDate.of(2024, 11, 4));
    }
}
