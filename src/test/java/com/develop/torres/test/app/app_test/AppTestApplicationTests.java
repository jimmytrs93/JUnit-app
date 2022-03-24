package com.develop.torres.test.app.app_test;

import com.develop.torres.test.app.app_test.dto.CuentaDto;
import com.develop.torres.test.app.app_test.entities.BancoEntity;
import com.develop.torres.test.app.app_test.entities.CuentaEntity;
import com.develop.torres.test.app.app_test.entities.UsuarioEntity;
import com.develop.torres.test.app.app_test.exceptions.BancoException;
import com.develop.torres.test.app.app_test.repository.CuentaRepository;
import com.develop.torres.test.app.app_test.service.CuentaService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
    @Autowired
    CuentaService cuentaService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("soy beforeAll");
    }

    @BeforeEach
    void setUp() {
        System.out.println("soy BeforeEach");

        BancoEntity banco = new BancoEntity(1, "Banco pricipal");
        UsuarioEntity usuario = new UsuarioEntity(1, "Juan Perez");
        CuentaEntity cuenta = new CuentaEntity(1, "CA", banco, usuario, new BigDecimal("100.12"));
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));

        BancoEntity banco2 = new BancoEntity(1, "Banco Alfa");
        UsuarioEntity usuario2 = new UsuarioEntity(1, "Jorge Muñoz");
        CuentaEntity cuenta2 = new CuentaEntity(2, "CC", banco2, usuario2, new BigDecimal("50.06"));
        when(cuentaRepository.findById(2)).thenReturn(Optional.of(cuenta2));

        List<CuentaEntity> list2 = Arrays.asList(cuenta, cuenta2);
        when(cuentaRepository.findAllByUsuarioId(anyInt())).thenReturn(Optional.of(list2));
    }

    @Test
    void consultarSaldoTest() {
        System.out.println("soy consultarSaldoTest");
        BigDecimal saldo = cuentaService.consultarSaldo(1);
        assertEquals(new BigDecimal("100.12"), saldo);
    }

    @Test
    void retirarTest() {
        System.out.println("soy retirarTest");
        BancoException be = assertThrows(BancoException.class, () -> {
            cuentaService.retirar(10, new BigDecimal("10"));
        });
        assertNotEquals("101", be.getCode());
    }

    @Test
    void retirarMockTest() throws BancoException {
        System.out.println("soy retirarMockTest");
        cuentaService.retirar(1, new BigDecimal("10"));

        verify(cuentaRepository).findById(eq(1));
        verify(cuentaRepository).findById(argThat(arg -> arg > 0 && arg < 10));
    }

    @Test
    void retirarCaptorTest() throws BancoException {
        System.out.println("soy retirarCaptorTest");
        cuentaService.retirar(1, new BigDecimal("10"));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(cuentaRepository).findById(captor.capture());

        assertEquals(1, captor.getValue());
    }

    @Test
    void retirarExcTest() {
        System.out.println("soy retirarExcTest");
        when(cuentaRepository.findById(isNull())).thenThrow(NullPointerException.class);

        Exception e = assertThrows( NullPointerException.class, () -> {
            cuentaService.retirar(null, new BigDecimal("10"));
        });

        assertEquals(NullPointerException.class, e.getClass());
        assertTrue(e.getClass().equals(NullPointerException.class));
    }

    @Test
    void transferirTest() {
        System.out.println("soy transferirTest");
        assertThrows(BancoException.class, () -> {
            cuentaService.transferir(1,3, new BigDecimal("10"));
        });

        verify(cuentaRepository, times(1)).findById(anyInt());
        verify(cuentaRepository, never()).save(any());
    }

    @Test
    void transferirOkTest() throws BancoException {
        System.out.println("soy transferirOkTest");

        cuentaService.transferir(1,2, new BigDecimal("10"));
        verify(cuentaRepository, times(1)).findById(1);
        verify(cuentaRepository, times(2)).findById(anyInt());
    }

    @Test
    void cuentasPorUsuarioTest() {
        System.out.println("soy cuentasPorUsuarioTest");
        List<CuentaDto> list = cuentaService.cuentasPorUsuario(1);

        BancoEntity banco = new BancoEntity(1, "Banco pricipal");
        UsuarioEntity usuario = new UsuarioEntity(1, "Juan Perez");
        CuentaEntity cuenta = new CuentaEntity(1, "CA", banco, usuario, new BigDecimal("100.12"));


        assertAll(() -> assertEquals(2, list.size()),
                //() -> assertSame(cuenta, list.get(0)),
                //() -> assertEquals(3, list.size()),
                () -> assertEquals(1, list.get(0).getId()));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("soy afterAll");
    }
}
