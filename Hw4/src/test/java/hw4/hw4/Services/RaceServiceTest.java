package hw4.hw4.Services;

import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic;
import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic_CountryUSA;
import hw4.hw4.Repository.RaceRepository;
import hw4.hw4.Service.RaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {

    @Mock
    private RaceRepository raceRepository;
    @InjectMocks
    private RaceService raceService;

    @BeforeEach
    void setUp()
    {
        lenient().when(raceRepository.getRacesWithNumberOfPilotsDesc()).thenReturn(Arrays.asList(
                new RaceDTO_PilotStatistic(1L, "Grand Tour: France", 11L),
                new RaceDTO_PilotStatistic(2L, "Grand Tour: Mexico", 7L)
        ));

        lenient().when(raceRepository.getRacesFromUSAWithNumberOfPilotsDesc()).thenReturn(Arrays.asList(
                new RaceDTO_PilotStatistic_CountryUSA(1L, "Grand Tour: Washington", "USA", 11L),
                new RaceDTO_PilotStatistic_CountryUSA(2L, "Grand Tour: Texas", "USA", 9L),
                new RaceDTO_PilotStatistic_CountryUSA(3L, "Grand Tour: Atlanta", "USA", 8L)
        ));
    }

    @Test
    void getRacesWithNumberOfPilotsDesc() {
        List<RaceDTO_PilotStatistic> pilots = this.raceRepository.getRacesWithNumberOfPilotsDesc();
        assertEquals("Grand Tour: France", pilots.get(0).getName());
        assertEquals("Grand Tour: Mexico", pilots.get(1).getName());
        assertEquals(pilots.size(), 2);
    }

    @Test
    void getRacesFromUSAWithNumberOfPilotsDesc() {
        List<RaceDTO_PilotStatistic_CountryUSA> pilots = this.raceRepository.getRacesFromUSAWithNumberOfPilotsDesc();
        assertEquals("Grand Tour: Washington", pilots.get(0).getName());
        assertEquals("Grand Tour: Texas", pilots.get(1).getName());
        assertEquals("Grand Tour: Atlanta", pilots.get(2).getName());
        assertEquals(pilots.size(), 3);
    }

}
