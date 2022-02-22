package com.develop.torres.test.app.app_test;

import com.develop.torres.test.app.app_test.dto.BancoDto;
import com.develop.torres.test.app.app_test.dto.CuentaDto;
import com.develop.torres.test.app.app_test.dto.UsuarioDto;
import com.develop.torres.test.app.app_test.entities.BancoEntity;
import com.develop.torres.test.app.app_test.entities.CuentaEntity;
import com.develop.torres.test.app.app_test.entities.UsuarioEntity;
import com.develop.torres.test.app.app_test.exceptions.BancoException;
import com.develop.torres.test.app.app_test.mapper.CuentaMapper;
import com.develop.torres.test.app.app_test.mapper.CuentaMapperImpl;
import com.develop.torres.test.app.app_test.repository.BancoRepository;
import com.develop.torres.test.app.app_test.repository.CuentaRepository;
import com.develop.torres.test.app.app_test.service.CuentaService;
import com.develop.torres.test.app.app_test.service.impl.CuentaServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class AppTestApplicationTests {

    @MockBean
    CuentaRepository cuentaRepository;
    @MockBean
    BancoRepository bancoRepository;
    @Autowired
    CuentaMapper cuentaMapper;
    @Autowired
    CuentaService cuentaService;


    @BeforeEach
    void setUp() {
        System.out.println("Se llama antes de ejecutar cualquier test");
        cuentaMapper = new CuentaMapperImpl();
        BancoEntity banco = new BancoEntity(1, "Banco pricipal");
        UsuarioEntity usuario = new UsuarioEntity(1, "Juan Perez");
        CuentaEntity cuenta1 = new CuentaEntity(1, "CA", banco, usuario, new BigDecimal("100.123"));
        CuentaEntity cuenta2 = new CuentaEntity(2, "CC", banco, usuario, new BigDecimal("20.123"));
        Optional<CuentaEntity> Cuenta1Opt = Optional.of(cuenta1);
        Optional<CuentaEntity> Cuenta2Opt = Optional.of(cuenta2);

        cuentaRepository = mock(CuentaRepository.class);
        //when(cuentaRepository.findById(anyInt())).thenReturn(CuentaOpt);

        when(cuentaRepository.findById(1)).thenReturn(Cuenta1Opt);
        when(cuentaRepository.findById(2)).thenReturn(Cuenta2Opt);
        List<CuentaEntity> list = Arrays.asList(cuenta1, cuenta2);
        when(cuentaRepository.findAllByUsuarioId(anyInt())).thenReturn(Optional.of(list));

        CuentaEntity cuenta3 = new CuentaEntity(null, "CC", banco, usuario, new BigDecimal("20.123"));
        when(cuentaRepository.save(any())).thenReturn(cuenta3);

        cuentaService = new CuentaServiceImpl(cuentaRepository, cuentaMapper);
    }


    @Test
    void consultarSaldo() {
        System.out.println("Test consultarSaldo");
        BigDecimal saldo = cuentaService.consultarSaldo(1);
        assertEquals(new BigDecimal("100.123"), saldo);
    }

    @Test
    void retirar() {
        System.out.println("Test retirar");
        BancoException be = assertThrows(BancoException.class, () -> {
            cuentaService.retirar(2, new BigDecimal("80.10"));
        });
        //assertEquals("100", be.getCode());
        assertNotEquals("100", be.getCode());
    }

    @Test
    void retirarExc() {
        System.out.println("Test retirarExc");
        when(cuentaRepository.findById(isNull())).thenThrow(IllegalArgumentException.class);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            cuentaService.retirar(null, new BigDecimal("5.10"));
        });
        assertEquals(IllegalArgumentException.class, e.getClass());
    }

    @Test
    void retirarArg() throws BancoException {
        System.out.println("Test retirarArg");
        cuentaService.retirar(1, new BigDecimal("5.10"));
        verify(cuentaRepository).findById(eq(1));
        verify(cuentaRepository).findById(argThat(arg -> arg > 0 && arg.equals(1)));
    }

    @Test
    void transferirError() {
        System.out.println("Test transferirError");
        BancoException be = assertThrows(BancoException.class, () -> {
            cuentaService.transferir(2, 1, new BigDecimal("80.10"));
        });
        assertTrue("101".equals(be.getCode()));
    }

    @Test
    void transferirErrorVerify() {
        System.out.println("Test transferirErrorVerify");
        assertThrows(BancoException.class, () -> {
            cuentaService.transferir(1, 3, new BigDecimal("1000.10"));
        });
        verify(cuentaRepository, times(1)).findById(anyInt());
        verify(cuentaRepository, never()).save(any());
    }

    @Test
    void transferirOk() throws BancoException {
        System.out.println("Test transferirOk");
        cuentaService.transferir(1, 2, new BigDecimal("10.10"));
        verify(cuentaRepository, times(2)).findById(anyInt());
        verify(cuentaRepository, times(2)).save(any());
    }

    @Test
    void cuentasPorUsuario() {
        System.out.println("Test cuentasPorUsuario");
        List<CuentaDto> list = cuentaService.cuentasPorUsuario(1);
        BancoDto banco = new BancoDto(1, "Banco pricipal");
        UsuarioDto usuario = new UsuarioDto(1, "Juan Perez");
        CuentaDto cuenta1 = new CuentaDto(1, "CA", banco, usuario, new BigDecimal("100.123"));

        // AsertAll ejecuta todas las pruebas sin importar que alguna falle
        assertAll(() -> assertEquals(2, list.size()),
                //() -> assertSame(cuenta1, list.get(0)), // No son iguales por que son instancias diferentes
                () -> assertEquals(cuenta1.getId(), list.get(0).getId()),
                () -> verify(cuentaRepository, times(1)).findAllByUsuarioId(anyInt())); // Se llama solo una vez
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Se llama al terminar los test");
    }
}
