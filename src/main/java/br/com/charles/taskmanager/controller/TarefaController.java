package br.com.charles.taskmanager.controller;

import br.com.charles.taskmanager.exceptions.TarefaNaoEncontradaException;
import br.com.charles.taskmanager.model.Tarefa;
import br.com.charles.taskmanager.model.TarefaPrioritaria;
import br.com.charles.taskmanager.service.TarefaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

// Controller JavaFX responsavel por controlar os eventos da tela de tarefas.
// Ele faz a ponte entre a interface grafica e a camada de servico.
public class TarefaController {

    @FXML
    private TableView<Tarefa> tabelaTarefas;

    @FXML
    private TableColumn<Tarefa, Integer> colunaId;

    @FXML
    private TableColumn<Tarefa, String> colunaTipo;

    @FXML
    private TableColumn<Tarefa, String> colunaTitulo;

    @FXML
    private TableColumn<Tarefa, String> colunaDescricao;

    @FXML
    private TableColumn<Tarefa, String> colunaStatus;

    @FXML
    private TableColumn<Tarefa, String> colunaPrioridade;

    @FXML
    private TextField campoTitulo;

    @FXML
    private TextArea campoDescricao;

    @FXML
    private CheckBox checkPrioritaria;

    @FXML
    private ComboBox<String> comboPrioridade;

    private TarefaService tarefaService;

    public TarefaController() {
        this.tarefaService = new TarefaService();
    }

    // Metodo executado automaticamente quando a tela FXML e carregada.
    // Configura a tabela, carrega as prioridades e busca as tarefas do banco.
    @FXML
    public void initialize() {
        configurarTabela();
        configurarComboPrioridade();
        configurarControlePrioridade();
        carregarTarefas();
    }

    // Configura como cada coluna da tabela deve obter os dados do objeto Tarefa.
    private void configurarTabela() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        colunaTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipo())
        );

        colunaStatus.setCellValueFactory(cellData -> {
            boolean concluida = cellData.getValue().isConcluida();
            return new SimpleStringProperty(concluida ? "Concluida" : "Pendente");
        });

        colunaPrioridade.setCellValueFactory(cellData -> {
            Tarefa tarefa = cellData.getValue();

            if (tarefa instanceof TarefaPrioritaria tarefaPrioritaria) {
                return new SimpleStringProperty(tarefaPrioritaria.getPrioridade());
            }

            return new SimpleStringProperty("-");
        });
    }

    // Configura as opcoes fixas de prioridade no ComboBox.
    // Isso evita digitacao livre e padroniza os dados salvos no banco.
    private void configurarComboPrioridade() {
        comboPrioridade.setItems(
                FXCollections.observableArrayList("Baixa", "Media", "Alta")
        );
    }

    // Habilita o ComboBox de prioridade apenas quando a tarefa for prioritaria.
    private void configurarControlePrioridade() {
        comboPrioridade.setDisable(true);

        checkPrioritaria.setOnAction(event -> {
            boolean prioritaria = checkPrioritaria.isSelected();

            comboPrioridade.setDisable(!prioritaria);

            if (!prioritaria) {
                comboPrioridade.getSelectionModel().clearSelection();
            }
        });
    }

    // Carrega as tarefas salvas no banco SQLite e exibe na tabela.
    @FXML
    public void carregarTarefas() {
        tabelaTarefas.setItems(
                FXCollections.observableArrayList(tarefaService.listarTarefas())
        );
    }

    // Cadastra uma nova tarefa comum ou prioritaria a partir dos campos da tela.
    @FXML
    public void adicionarTarefa() {
        try {
            String titulo = campoTitulo.getText();
            String descricao = campoDescricao.getText();

            if (checkPrioritaria.isSelected()) {
                String prioridade = comboPrioridade.getValue();
                tarefaService.adicionarTarefaPrioritaria(titulo, descricao, prioridade);
            } else {
                tarefaService.adicionarTarefa(titulo, descricao);
            }

            limparCampos();
            carregarTarefas();
            mostrarInformacao("Tarefa cadastrada com sucesso.");

        } catch (IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        }
    }

    // Marca como concluida a tarefa selecionada na tabela.
    @FXML
    public void concluirTarefa() {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();

        if (tarefaSelecionada == null) {
            mostrarErro("Selecione uma tarefa para concluir.");
            return;
        }

        try {
            tarefaService.concluirTarefa(tarefaSelecionada.getId());
            carregarTarefas();
            mostrarInformacao("Tarefa marcada como concluida.");

        } catch (TarefaNaoEncontradaException e) {
            mostrarErro(e.getMessage());
        }
    }

    // Remove a tarefa selecionada, solicitando confirmacao do usuario.
    @FXML
    public void removerTarefa() {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();

        if (tarefaSelecionada == null) {
            mostrarErro("Selecione uma tarefa para remover.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar remocao");
        confirmacao.setHeaderText("Remover tarefa");
        confirmacao.setContentText("Deseja realmente remover a tarefa selecionada?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                try {
                    tarefaService.removerTarefa(tarefaSelecionada.getId());
                    carregarTarefas();
                    mostrarInformacao("Tarefa removida com sucesso.");

                } catch (TarefaNaoEncontradaException e) {
                    mostrarErro(e.getMessage());
                }
            }
        });
    }

    // Limpa os campos do formulario.
    @FXML
    public void limparCampos() {
        campoTitulo.clear();
        campoDescricao.clear();
        comboPrioridade.getSelectionModel().clearSelection();
        comboPrioridade.setDisable(true);
        checkPrioritaria.setSelected(false);
    }

    // Exibe mensagem informativa para o usuario.
    private void mostrarInformacao(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacao");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Exibe mensagem de erro para o usuario.
    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}