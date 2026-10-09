package br.com.charles.taskmanager.app;

import br.com.charles.taskmanager.db.Conexao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Classe principal da versao JavaFX.
// Ela inicia a aplicacao visual e carrega a tela definida em FXML.
public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Garante que a tabela do banco exista antes da tela ser carregada.
        Conexao.criarTabelaTarefas();

        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("/br/com/charles/taskmanager/view/tarefa-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);

        scene.getStylesheets().add(
                MainApplication.class.getResource("/br/com/charles/taskmanager/view/style.css").toExternalForm()
        );

        stage.setTitle("TaskManager - Gerenciador de Tarefas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}