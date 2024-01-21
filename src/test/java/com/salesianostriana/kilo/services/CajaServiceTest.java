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


        KilosDisponibles kd = KilosDisponibles.builder()
                .id(1L)
                .cantidadDisponible(20.0)
                .build();

        TipoAlimento ta = TipoAlimento.builder()
                .id(1L)
                .kilosDisponibles(kd)
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
                .cantidadKgs(20)
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

        /*
        * Cuando guarda al final del método la caja, aunque anteriormente se setee la cantidad y se guarde el Tiene esto
        * no aplica en el guardado, por lo cuál el contenido de la caja con sus respectivos kg son correctos aunque el kgTotal de la
        * caja no se actualiza conforme a su contenido
        *
        * */
        assertTrue(cajaService.editKgOfALim(c.getId(), ta.getId(),50.0).isPresent());
        assertTrue(cajaService.editKgOfALim(c.getId(), ta.getId(),-1.0).isEmpty());
        assertEquals(cajaService.editKgOfALim(c.getId(), ta.getId(),50.0001).get().getContenido().get(0).getKg(), 50.0001);
        assertEquals(cajaService.editKgOfALim(c.getId(), ta.getId(),49.9999).get().getContenido().get(0).getKg(), 49.9999);
        assertEquals(cajaService.editKgOfALim(c.getId(), ta.getId(),0.0).get().getContenido().get(0).getKg(), 0.0);


    }
}