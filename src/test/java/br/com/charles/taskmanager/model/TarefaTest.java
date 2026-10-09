package br.com.charles.taskmanager.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Classe de teste responsavel por validar o comportamento basico da classe Tarefa.
public class TarefaTest {

    // Verifica se uma nova tarefa e criada inicialmente como pendente.
    @Test
    public void deveCriarTarefaComoPendente() {
        Tarefa tarefa = new Tarefa(1, "Estudar Java", "Revisar POO e ArrayList");

        assertEquals(1, tarefa.getId());
        assertEquals("Estudar Java", tarefa.getTitulo());
        assertEquals("Revisar POO e ArrayList", tarefa.getDescricao());
        assertFalse(tarefa.isConcluida());
    }

    // Verifica se o metodo marcarComoConcluida altera corretamente o status.
    @Test
    public void deveMarcarTarefaComoConcluida() {
        Tarefa tarefa = new Tarefa(1, "Entregar projeto", "Finalizar atividade");

        tarefa.marcarComoConcluida();

        assertTrue(tarefa.isConcluida());
    }

    // Verifica se uma tarefa comum retorna o tipo correto.
    @Test
    public void deveRetornarTipoTarefaComum() {
        Tarefa tarefa = new Tarefa(1, "Teste", "Descricao teste");

        assertEquals("Tarefa comum", tarefa.getTipo());
    }
}