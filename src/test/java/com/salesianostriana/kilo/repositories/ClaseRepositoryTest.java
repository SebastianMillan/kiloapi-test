package com.salesianostriana.kilo.repositories;

import org.junit.jupiter.api.Disabled;
import com.salesianostriana.kilo.dtos.ranking.RankQueryResponseDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ActiveProfiles({"postgresql"})
@Testcontainers
//@Sql(value = "classpath:import-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = "classpath:delete-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ClaseRepositoryTest {

    @Autowired
    ClaseRepository claseRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("testUser")
            .withPassword("testSecret")
            .withDatabaseName("testDatabase");

    @Test
    void findKilos() {

        Double totalKilos = claseRepository.findKilos(1L);
        assertNotNull(totalKilos);

    }

    @Test
    void findClasesOrderedByRank() {
        List<RankQueryResponseDTO> result= claseRepository.findClasesOrderedByRank();
        assertEquals(result.size(),12);
    }
}