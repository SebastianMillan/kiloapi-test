package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.ranking.RankQueryResponseDTO;
import com.salesianostriana.kilo.dtos.ranking.RankingResponseDTO;
import com.salesianostriana.kilo.repositories.ClaseRepository;
import com.salesianostriana.kilo.services.ClaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaseServiceTest {

    @Mock
    ClaseRepository repository;

    @InjectMocks
    ClaseService service;

    @Test
    void getRanking() {

        List<RankQueryResponseDTO> mockResponse = Arrays.asList(
                new RankQueryResponseDTO(1L,"Paco", 10.0),
                new RankQueryResponseDTO(2L, "Pepe", 15.0)

        );
        when(repository.findClasesOrderedByRank()).thenReturn(mockResponse);


        List<RankingResponseDTO> result = service.getRanking();


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10,result.get(1).getTotalKilos());
        assertEquals("Paco",result.get(1).getNombre());

        verify(repository, times(1)).findClasesOrderedByRank();
    }
}
