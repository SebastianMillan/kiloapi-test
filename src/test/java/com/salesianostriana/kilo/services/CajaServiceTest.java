package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.entities.keys.DetalleAportacionPK;
import com.salesianostriana.kilo.entities.keys.TienePK;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.repositories.TieneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class CajaServiceTest {

    @InjectMocks
    CajaService cajaService;

    @Mock
    TipoAlimentoService alimentoService;

    @Mock
    KilosDisponiblesService kilosDisponiblesService;

    @Mock
    TieneRepository tieneRepository;

    @Mock
    CajaRepository cajaRepository;


    @Test
    void editKgOfALim() {
        Destinatario d = Destinatario.builder()
                .id(1L)
                .nombre("Salesianos 1")
                .build();

        TipoAlimento ta = TipoAlimento.builder()
                .id(1L)
                .build();
        KilosDisponibles kd = KilosDisponibles.builder()
                .id(1L)
                .cantidadDisponible(20.0)
                .build();

        kd.addTipoAlimento(ta);

        DetalleAportacion da = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(1L,1L))
                .build();

        da.addToTipoAlimento(ta);

        Clase cla = Clase.builder()
                .id(1L)
                .build();
        Aportacion a = Aportacion.builder()
                .id(1L)
                .build();
        a.addDetalleAportacion(da);
        a.addToClase(cla);

        Tiene t = Tiene.builder()
                .tipoAlimento(ta)
                .tienePK(new TienePK(1L, 1L))
                .build();
        Caja c = Caja.builder()
                .id(1L)
                .numCaja(1)
                .kilosTotales(20)
                .build();
        c.addTiene(t);
        c.addDestinatario(d);

        when(cajaRepository.findById(1L)).thenReturn(Optional.of(c));
        when(alimentoService.findById(1L)).thenReturn(Optional.of(ta));
        when(kilosDisponiblesService.findById(1L)).thenReturn(Optional.of(kd));
        when(tieneRepository.findOneTiene(c.getId(), ta.getId())).thenReturn(t);
        when(tieneRepository.save(Mockito.any(Tiene.class))).thenAnswer(i -> i.getArguments()[0]);
        //when(kilosDisponiblesService.add(new KilosDisponibles(ta.getId(),20.0))).thenReturn(kd);
        when(cajaRepository.save(Mockito.any(Caja.class))).thenAnswer(i -> i.getArguments()[0]);

        assertTrue(cajaService.editKgOfALim(c.getId(), ta.getId(),21.22).isPresent());
        assertEquals(cajaService.editKgOfALim(c.getId(), ta.getId(),25.2).get().getKilosTotales(), 20.0);




    }
}