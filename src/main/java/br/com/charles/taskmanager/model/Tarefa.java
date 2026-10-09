/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.charles.taskmanager.model;

/**
 *
 * @author Charles Ricardo
 */

// Classe modelo que representa uma tarefa do sistema.
public class Tarefa {

    // Atributos privados para aplicar o conceito de encapsulamento.
    private int id;
    private String titulo;
    private String descricao;
    private boolean concluida;

    // Construtor utilizado para inicializar uma nova tarefa.
    public Tarefa(int id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = false;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    public String getTipo() {
        return "Tarefa comum";
    }

    // Metodo sobrescrito para definir como a tarefa sera exibida no console.
    @Override
    public String toString() {
        String status = concluida ? "Concluida" : "Pendente";

        return "ID: " + id
                + " | Tipo: " + getTipo()
                + " | Titulo: " + titulo
                + " | Descricao: " + descricao
                + " | Status: " + status;
    }
}