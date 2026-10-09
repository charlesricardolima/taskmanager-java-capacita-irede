package br.com.charles.taskmanager.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Classe de teste responsavel por validar as regras de entrada do sistema.
public class ValidadorEntradaTest {

    // Verifica se um texto valido nao gera erro.
    @Test
    public void naoDeveLancarErroQuandoTextoForValido() {
        assertDoesNotThrow(() -> {
            ValidadorEntrada.validarTextoObrigatorio("Projeto Java", "titulo");
        });
    }

    // Verifica se campo vazio gera excecao.
    @Test
    public void deveLancarErroQuandoTextoForVazio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ValidadorEntrada.validarTextoObrigatorio("", "titulo");
        });

        assertEquals("O campo titulo nao pode ficar vazio.", exception.getMessage());
    }

    // Verifica se campo preenchido apenas com espacos tambem gera excecao.
    @Test
    public void deveLancarErroQuandoTextoTiverApenasEspacos() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ValidadorEntrada.validarTextoObrigatorio("   ", "descricao");
        });

        assertEquals("O campo descricao nao pode ficar vazio.", exception.getMessage());
    }
}