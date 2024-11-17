package rustamscode.scoretableapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rustamscode.scoretableapi.dto.GameRequest;
import rustamscode.scoretableapi.model.Game;
import rustamscode.scoretableapi.model.Season;
import rustamscode.scoretableapi.model.Team;
import rustamscode.scoretableapi.repository.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    void registerGame_success() {
        Team homeTeam = new Team(1L, "Team A");
        Team awayTeam = new Team(2L, "Team B");

        GameRequest gameRequest = new GameRequest();
        gameRequest.setHomeTeam(homeTeam);
        gameRequest.setAwayTeam(awayTeam);
        gameRequest.setHomeScore(3);
        gameRequest.setAwayScore(2);

        LocalDate currentDate = LocalDate.now();
        Season currentSeason = Season.getSeasonByDate(currentDate);

        when(teamRepository.findByName("Team A")).thenReturn(Optional.of(homeTeam));
        when(teamRepository.findByName("Team B")).thenReturn(Optional.of(awayTeam));
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameService.registerGame(gameRequest);

        assertNotNull(result);
        assertEquals("Team A", result.getHomeTeam().getName());
        assertEquals("Team B", result.getAwayTeam().getName());
        assertEquals(3, result.getHomeScore());
        assertEquals(2, result.getAwayScore());
        assertEquals(currentSeason, result.getSeason());
        assertEquals(currentDate, result.getDate());

        verify(teamRepository).findByName("Team A");
        verify(teamRepository).findByName("Team B");
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void registerGame_throwsException_whenHomeTeamNotFound() {
        Team awayTeam = new Team(2L, "Team B");

        GameRequest gameRequest = new GameRequest();
        gameRequest.setHomeTeam(new Team(1L, "Team A"));
        gameRequest.setAwayTeam(awayTeam);
        gameRequest.setHomeScore(3);
        gameRequest.setAwayScore(2);

        when(teamRepository.findByName("Team A")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> gameService.registerGame(gameRequest));

        assertEquals("Команда Team A не найдена", exception.getMessage());
        verify(teamRepository).findByName("Team A");
        verifyNoInteractions(gameRepository);
    }

    @Test
    void registerGame_throwsException_whenAwayTeamNotFound() {
        Team homeTeam = new Team(1L, "Team A");

        GameRequest gameRequest = new GameRequest();
        gameRequest.setHomeTeam(homeTeam);
        gameRequest.setAwayTeam(new Team(2L, "Team B"));
        gameRequest.setHomeScore(3);
        gameRequest.setAwayScore(2);

        when(teamRepository.findByName("Team A")).thenReturn(Optional.of(homeTeam));
        when(teamRepository.findByName("Team B")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> gameService.registerGame(gameRequest));

        assertEquals("Команда Team B не найдена", exception.getMessage());
        verify(teamRepository).findByName("Team A");
        verify(teamRepository).findByName("Team B");
        verifyNoInteractions(gameRepository);
    }
}

