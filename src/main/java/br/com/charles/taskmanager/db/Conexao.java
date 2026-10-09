package br.com.charles.taskmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Classe responsavel por centralizar a conexao com o banco de dados SQLite.
public class Conexao {

    private static final String URL = "jdbc:sqlite:taskmanager.db";

    // Cria uma conexao com o banco principal da aplicacao.
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Cria a tabela de tarefas usando a conexao principal da aplicacao.
    public static void criarTabelaTarefas() {
        try (Connection conexao = conectar()) {
            criarTabelaTarefas(conexao);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela de tarefas: " + e.getMessage());
        }
    }

    // Cria a tabela de tarefas usando uma conexao recebida por parametro.
    // Esse metodo sera usado nos testes com SQLite em memoria.
    public static void criarTabelaTarefas(Connection conexao) {
        String sql = """
            CREATE TABLE IF NOT EXISTS tarefas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descricao TEXT NOT NULL,
                concluida INTEGER NOT NULL DEFAULT 0,
                prioridade TEXT
            );
        """;

        try (Statement statement = conexao.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela de tarefas: " + e.getMessage());
        }
    }
}