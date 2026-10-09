package br.com.charles.taskmanager.dao;

import br.com.charles.taskmanager.db.Conexao;
import br.com.charles.taskmanager.model.Tarefa;
import br.com.charles.taskmanager.model.TarefaPrioritaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TarefaDAO {

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

    public ArrayList<Tarefa> listar() {
        ArrayList<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT id, titulo, descricao, concluida, prioridade FROM tarefas;";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
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
                tarefas.add(tarefa);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }

        return tarefas;
    }
}