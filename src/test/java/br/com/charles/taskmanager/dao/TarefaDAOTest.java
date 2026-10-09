package br.com.charles.taskmanager.dao;

import br.com.charles.taskmanager.db.Conexao;
import br.com.charles.taskmanager.exceptions.TarefaNaoEncontradaException;
import br.com.charles.taskmanager.model.Tarefa;
import br.com.charles.taskmanager.model.TarefaPrioritaria;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Classe de teste responsavel por validar o DAO usando SQLite em memoria.
public class TarefaDAOTest {

    private Connection conexao;
    private TarefaDAO tarefaDAO;

    // Executa antes de cada teste.
    // Cria um banco SQLite em memoria e prepara a tabela de tarefas.
    @BeforeEach
    public void prepararBancoEmMemoria() throws SQLException {
        conexao = DriverManager.getConnection("jdbc:sqlite::memory:");
        Conexao.criarTabelaTarefas(conexao);
        tarefaDAO = new TarefaDAO(conexao);
    }

    // Executa depois de cada teste.
    // Fecha a conexao em memoria para isolar os testes.
    @AfterEach
    public void fecharBancoEmMemoria() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    // Verifica se uma tarefa comum e inserida e listada corretamente.
    @Test
    public void deveInserirEListarTarefaComum() {
        Tarefa tarefa = new Tarefa(0, "Estudar JDBC", "Praticar PreparedStatement");

        tarefaDAO.inserir(tarefa);

        ArrayList<Tarefa> tarefas = tarefaDAO.listar();

        assertEquals(1, tarefas.size());
        assertEquals("Estudar JDBC", tarefas.get(0).getTitulo());
        assertEquals("Praticar PreparedStatement", tarefas.get(0).getDescricao());
        assertFalse(tarefas.get(0).isConcluida());
    }

    // Verifica se uma tarefa prioritaria e recuperada mantendo a prioridade.
    @Test
    public void deveInserirEListarTarefaPrioritaria() {
        TarefaPrioritaria tarefa = new TarefaPrioritaria(
                0,
                "Entregar projeto",
                "Finalizar etapa intermediaria",
                "Alta"
        );

        tarefaDAO.inserir(tarefa);

        ArrayList<Tarefa> tarefas = tarefaDAO.listar();

        assertEquals(1, tarefas.size());
        assertTrue(tarefas.get(0) instanceof TarefaPrioritaria);

        TarefaPrioritaria tarefaPrioritaria = (TarefaPrioritaria) tarefas.get(0);
        assertEquals("Alta", tarefaPrioritaria.getPrioridade());
    }

    // Verifica se o DAO consegue buscar uma tarefa pelo ID.
    @Test
    public void deveBuscarTarefaPorId() throws TarefaNaoEncontradaException {
        Tarefa tarefa = new Tarefa(0, "Buscar tarefa", "Teste de busca por ID");

        tarefaDAO.inserir(tarefa);

        Tarefa tarefaEncontrada = tarefaDAO.buscarPorId(1);

        assertEquals(1, tarefaEncontrada.getId());
        assertEquals("Buscar tarefa", tarefaEncontrada.getTitulo());
    }

    // Verifica se o DAO marca uma tarefa como concluida.
    @Test
    public void deveConcluirTarefa() throws TarefaNaoEncontradaException {
        Tarefa tarefa = new Tarefa(0, "Concluir tarefa", "Teste de update");

        tarefaDAO.inserir(tarefa);
        tarefaDAO.concluir(1);

        Tarefa tarefaConcluida = tarefaDAO.buscarPorId(1);

        assertTrue(tarefaConcluida.isConcluida());
    }

    // Verifica se o DAO remove uma tarefa corretamente.
    @Test
    public void deveRemoverTarefa() throws TarefaNaoEncontradaException {
        Tarefa tarefa = new Tarefa(0, "Remover tarefa", "Teste de delete");

        tarefaDAO.inserir(tarefa);
        tarefaDAO.remover(1);

        ArrayList<Tarefa> tarefas = tarefaDAO.listar();

        assertTrue(tarefas.isEmpty());
    }

    // Verifica se uma busca por ID inexistente gera a excecao esperada.
    @Test
    public void deveLancarErroQuandoTarefaNaoExistir() {
        assertThrows(TarefaNaoEncontradaException.class, () -> {
            tarefaDAO.buscarPorId(99);
        });
    }
}