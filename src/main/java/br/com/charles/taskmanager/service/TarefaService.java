package br.com.charles.taskmanager.service;

import br.com.charles.taskmanager.dao.TarefaDAO;
import br.com.charles.taskmanager.exceptions.TarefaNaoEncontradaException;
import br.com.charles.taskmanager.model.Tarefa;
import br.com.charles.taskmanager.model.TarefaPrioritaria;
import br.com.charles.taskmanager.utils.ValidadorEntrada;
import java.util.ArrayList;

// Classe responsavel por centralizar as regras de negocio das tarefas.
// Ela faz a ponte entre a aplicacao e a camada DAO.
public class TarefaService {

    private TarefaDAO tarefaDAO;

    public TarefaService() {
        this.tarefaDAO = new TarefaDAO();
    }

    // Valida os dados e cadastra uma tarefa comum no banco.
    public void adicionarTarefa(String titulo, String descricao) {
        ValidadorEntrada.validarTextoObrigatorio(titulo, "titulo");
        ValidadorEntrada.validarTextoObrigatorio(descricao, "descricao");

        Tarefa tarefa = new Tarefa(0, titulo, descricao);
        tarefaDAO.inserir(tarefa);
    }

    // Valida os dados e cadastra uma tarefa prioritaria no banco.
    // Aqui tambem demonstramos o uso de heranca, pois TarefaPrioritaria herda de Tarefa.
    public void adicionarTarefaPrioritaria(String titulo, String descricao, String prioridade) {
        ValidadorEntrada.validarTextoObrigatorio(titulo, "titulo");
        ValidadorEntrada.validarTextoObrigatorio(descricao, "descricao");
        ValidadorEntrada.validarTextoObrigatorio(prioridade, "prioridade");

        TarefaPrioritaria tarefa = new TarefaPrioritaria(0, titulo, descricao, prioridade);
        tarefaDAO.inserir(tarefa);
    }

    // Retorna todas as tarefas salvas no banco de dados.
    public ArrayList<Tarefa> listarTarefas() {
        return tarefaDAO.listar();
    }

    // Marca uma tarefa como concluida a partir do ID informado.
    public void concluirTarefa(int id) throws TarefaNaoEncontradaException {
        tarefaDAO.concluir(id);
    }

    // Atualiza uma tarefa existente apos validar os dados informados.
    // Permite transformar uma tarefa comum em prioritaria e tambem o contrario.
    public void atualizarTarefa(int id, String titulo, String descricao, boolean prioritaria, String prioridade)
            throws TarefaNaoEncontradaException {

        ValidadorEntrada.validarTextoObrigatorio(titulo, "titulo");
        ValidadorEntrada.validarTextoObrigatorio(descricao, "descricao");

        Tarefa tarefaExistente = tarefaDAO.buscarPorId(id);

        Tarefa tarefaAtualizada;

        if (prioritaria) {
            ValidadorEntrada.validarTextoObrigatorio(prioridade, "prioridade");
            tarefaAtualizada = new TarefaPrioritaria(id, titulo, descricao, prioridade);
        } else {
            tarefaAtualizada = new Tarefa(id, titulo, descricao);
        }

        tarefaAtualizada.setConcluida(tarefaExistente.isConcluida());

        tarefaDAO.atualizar(tarefaAtualizada);
    }

    // Remove uma tarefa do banco de dados a partir do ID informado.
    public void removerTarefa(int id) throws TarefaNaoEncontradaException {
        tarefaDAO.remover(id);
    }

    // Verifica se existem tarefas cadastradas.
    public boolean possuiTarefas() {
        return !tarefaDAO.listar().isEmpty();
    }
}