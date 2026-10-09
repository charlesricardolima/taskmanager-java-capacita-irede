package br.com.charles.taskmanager.dao;

import br.com.charles.taskmanager.db.Conexao;
import br.com.charles.taskmanager.exceptions.TarefaNaoEncontradaException;
import br.com.charles.taskmanager.model.Tarefa;
import br.com.charles.taskmanager.model.TarefaPrioritaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Classe responsavel por realizar as operacoes de persistencia das tarefas.
// Ela isola o acesso ao banco de dados e utiliza JDBC com PreparedStatement.
public class TarefaDAO {

    // Insere uma nova tarefa no banco de dados.
    public void inserir(Tarefa tarefa) {
        String sql = """
            INSERT INTO tarefas (titulo, descricao, concluida, prioridade)
            VALUES (?, ?, ?, ?);
        """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getDescricao());
            statement.setInt(3, tarefa.isConcluida() ? 1 : 0);

            if (tarefa instanceof TarefaPrioritaria tarefaPrioritaria) {
                statement.setString(4, tarefaPrioritaria.getPrioridade());
            } else {
                statement.setString(4, null);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tarefa: " + e.getMessage());
        }
    }

    // Lista todas as tarefas cadastradas no banco de dados.
    public ArrayList<Tarefa> listar() {
        ArrayList<Tarefa> tarefas = new ArrayList<>();

        String sql = """
            SELECT id, titulo, descricao, concluida, prioridade
            FROM tarefas
            ORDER BY id;
        """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Tarefa tarefa = montarTarefa(resultSet);
                tarefas.add(tarefa);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }

        return tarefas;
    }

    // Busca uma tarefa especifica pelo ID.
    // Caso nao encontre, lanca uma excecao personalizada.
    public Tarefa buscarPorId(int id) throws TarefaNaoEncontradaException {
        String sql = """
            SELECT id, titulo, descricao, concluida, prioridade
            FROM tarefas
            WHERE id = ?;
        """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return montarTarefa(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar tarefa: " + e.getMessage());
        }

        throw new TarefaNaoEncontradaException("Nenhuma tarefa encontrada com o ID " + id + ".");
    }

    // Atualiza o status da tarefa para concluida.
    public void concluir(int id) throws TarefaNaoEncontradaException {
        String sql = """
            UPDATE tarefas
            SET concluida = 1
            WHERE id = ?;
        """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new TarefaNaoEncontradaException("Nenhuma tarefa encontrada com o ID " + id + ".");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao concluir tarefa: " + e.getMessage());
        }
    }

    // Remove uma tarefa do banco de dados com base no ID informado.
    public void remover(int id) throws TarefaNaoEncontradaException {
        String sql = """
            DELETE FROM tarefas
            WHERE id = ?;
        """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new TarefaNaoEncontradaException("Nenhuma tarefa encontrada com o ID " + id + ".");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover tarefa: " + e.getMessage());
        }
    }

    // Metodo auxiliar que transforma uma linha do ResultSet em um objeto Tarefa.
    private Tarefa montarTarefa(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String titulo = resultSet.getString("titulo");
        String descricao = resultSet.getString("descricao");
        boolean concluida = resultSet.getInt("concluida") == 1;
        String prioridade = resultSet.getString("prioridade");

        Tarefa tarefa;

        if (prioridade != null && !prioridade.isBlank()) {
            tarefa = new TarefaPrioritaria(id, titulo, descricao, prioridade);
        } else {
            tarefa = new Tarefa(id, titulo, descricao);
        }

        tarefa.setConcluida(concluida);

        return tarefa;
    }
}