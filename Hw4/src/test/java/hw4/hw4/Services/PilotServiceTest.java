package hw4.hw4.Services;

import hw4.hw4.DTO.Pilot.PilotDTO_CarStatistic;
import hw4.hw4.Repository.PilotRepository;
import hw4.hw4.Service.PilotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PilotServiceTest {

    @Mock
    private PilotRepository pilotRepository;
    @InjectMocks
    private PilotService pilotService;

    @BeforeEach
    void setUp()
    {
        when(pilotRepository.getPilotsWithNumberOfCarsAsc()).thenReturn(Arrays.asList(
                new PilotDTO_CarStatistic(1L, "Andrew", "John", 10L),
                new PilotDTO_CarStatistic(2L, "Robert", "Maximilian", 15L)
        ));
    }

    @Test
    void getPilotsWithNumberOfCarsAsc() {
        List<PilotDTO_CarStatistic> pilots = this.pilotService.getPilotsWithNumberOfCarsAsc();
        assertEquals("Andrew", pilots.get(0).getFirstName());
        assertEquals("Robert", pilots.get(1).getFirstName());
        assertEquals(pilots.size(), 2);
    }

}
