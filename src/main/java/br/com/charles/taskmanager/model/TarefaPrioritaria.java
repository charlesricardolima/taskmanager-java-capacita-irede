/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.charles.taskmanager.model;

/**
 *
 * @author Charles Ricardo
 */

// Classe que herda de Tarefa e representa uma tarefa com prioridade.
public class TarefaPrioritaria extends Tarefa {

    private String prioridade;

    // Construtor que reaproveita os atributos da classe Tarefa
    // e adiciona a prioridade.
    public TarefaPrioritaria(int id, String titulo, String descricao, String prioridade) {
        super(id, titulo, descricao);
        this.prioridade = prioridade;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    // Sobrescrita do metodo getTipo para aplicar polimorfismo.
    @Override
    public String getTipo() {
        return "Tarefa prioritaria";
    }

    @Override
    public String toString() {
        return super.toString() + " | Prioridade: " + prioridade;
    }
}