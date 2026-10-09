package br.com.charles.taskmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "jdbc:sqlite:taskmanager.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabelaTarefas() {
        String sql = """
            CREATE TABLE IF NOT EXISTS tarefas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descricao TEXT NOT NULL,
                concluida INTEGER NOT NULL DEFAULT 0,
                prioridade TEXT
            );
        """;

        try (Connection conexao = conectar();
             Statement statement = conexao.createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela de tarefas: " + e.getMessage());
        }
    }
}