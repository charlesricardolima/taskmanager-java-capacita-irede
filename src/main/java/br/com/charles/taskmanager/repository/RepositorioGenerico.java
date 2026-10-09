package br.com.charles.taskmanager.repository;

import java.util.ArrayList;
import java.util.List;

// Classe generica responsavel por manipular uma lista de qualquer tipo.
// O uso de <T> permite reaproveitar esta estrutura com Tarefa,
// TarefaPrioritaria ou outros objetos do sistema.
public class RepositorioGenerico<T> {

    private List<T> itens;

    public RepositorioGenerico() {
        this.itens = new ArrayList<>();
    }

    // Adiciona um unico item do tipo generico T.
    public void adicionar(T item) {
        itens.add(item);
    }

    // Adiciona varios itens de uma lista.
    // O uso de ? extends T permite receber objetos do tipo T
    // ou de qualquer subclasse de T.
    public void adicionarTodos(List<? extends T> novosItens) {
        itens.addAll(novosItens);
    }

    // Copia os itens do repositorio para outra lista.
    // O uso de ? super T permite enviar os dados para uma lista
    // que aceite T ou alguma superclasse de T.
    public void copiarPara(List<? super T> destino) {
        destino.addAll(itens);
    }

    // Retorna uma nova lista com os itens cadastrados,
    // evitando expor diretamente a lista interna da classe.
    public List<T> listar() {
        return new ArrayList<>(itens);
    }

    // Remove um item da lista generica.
    public boolean remover(T item) {
        return itens.remove(item);
    }

    // Retorna a quantidade de itens armazenados.
    public int tamanho() {
        return itens.size();
    }

    // Verifica se o repositorio esta vazio.
    public boolean estaVazio() {
        return itens.isEmpty();
    }

    // Limpa todos os itens do repositorio.
    public void limpar() {
        itens.clear();
    }
}