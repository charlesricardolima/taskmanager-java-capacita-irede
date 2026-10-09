package br.com.charles.taskmanager.controller;

import br.com.charles.taskmanager.exceptions.TarefaNaoEncontradaException;
import br.com.charles.taskmanager.model.Tarefa;
import br.com.charles.taskmanager.model.TarefaPrioritaria;
import br.com.charles.taskmanager.utils.ValidadorEntrada;
import java.util.ArrayList;

// Classe controladora responsavel por gerenciar a lista de tarefas.
public class TaskManager {

    // Lista utilizada para armazenar as tarefas em memoria.
    private ArrayList<Tarefa> tarefas;
    private int proximoId;

    public TaskManager() {
        this.tarefas = new ArrayList<>();
        this.proximoId = 1;
    }

    public void adicionarTarefa(String titulo, String descricao) {
        ValidadorEntrada.validarTextoObrigatorio(titulo, "titulo");
        ValidadorEntrada.validarTextoObrigatorio(descricao, "descricao");

        Tarefa tarefa = new Tarefa(proximoId, titulo, descricao);
        tarefas.add(tarefa);
        proximoId++;
    }

    public void adicionarTarefaPrioritaria(String titulo, String descricao, String prioridade) {
        ValidadorEntrada.validarTextoObrigatorio(titulo, "titulo");
        ValidadorEntrada.validarTextoObrigatorio(descricao, "descricao");
        ValidadorEntrada.validarTextoObrigatorio(prioridade, "prioridade");

        TarefaPrioritaria tarefa = new TarefaPrioritaria(proximoId, titulo, descricao, prioridade);
        tarefas.add(tarefa);
        proximoId++;
    }

    public ArrayList<Tarefa> listarTarefas() {
        return tarefas;
    }

    public void concluirTarefa(int id) throws TarefaNaoEncontradaException {
        Tarefa tarefa = buscarTarefaPorId(id);
        tarefa.marcarComoConcluida();
    }

    public void removerTarefa(int id) throws TarefaNaoEncontradaException {
        Tarefa tarefa = buscarTarefaPorId(id);
        tarefas.remove(tarefa);
    }

    // Metodo auxiliar que percorre a lista procurando uma tarefa pelo ID.
    // Caso nao encontre, lanca uma excecao personalizada.
    private Tarefa buscarTarefaPorId(int id) throws TarefaNaoEncontradaException {
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getId() == id) {
                return tarefa;
            }
        }

        throw new TarefaNaoEncontradaException("Nenhuma tarefa encontrada com o ID " + id + ".");
    }

    public boolean possuiTarefas() {
        return !tarefas.isEmpty();
    }
}