package br.com.charles.taskmanager.repository;

import br.com.charles.taskmanager.model.Tarefa;
import br.com.charles.taskmanager.model.TarefaPrioritaria;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Classe de teste responsavel por validar o uso de Generics no repositorio.
public class RepositorioGenericoTest {

    // Verifica se o repositorio generico adiciona e lista itens corretamente.
    @Test
    public void deveAdicionarEListarItens() {
        RepositorioGenerico<Tarefa> repositorio = new RepositorioGenerico<>();

        Tarefa tarefa = new Tarefa(1, "Estudar", "Revisar Java");
        repositorio.adicionar(tarefa);

        assertEquals(1, repositorio.tamanho());
        assertFalse(repositorio.estaVazio());
        assertEquals("Estudar", repositorio.listar().get(0).getTitulo());
    }

    // Verifica o uso de ? extends T ao adicionar uma lista de subclasses.
    @Test
    public void deveAdicionarListaComExtends() {
        RepositorioGenerico<Tarefa> repositorio = new RepositorioGenerico<>();

        List<TarefaPrioritaria> tarefasPrioritarias = new ArrayList<>();
        tarefasPrioritarias.add(new TarefaPrioritaria(1, "Entrega", "Projeto final", "Alta"));

        repositorio.adicionarTodos(tarefasPrioritarias);

        assertEquals(1, repositorio.tamanho());
        assertEquals("Entrega", repositorio.listar().get(0).getTitulo());
    }

    // Verifica o uso de ? super T ao copiar dados para uma lista de tipo mais generico.
    @Test
    public void deveCopiarItensParaListaSuper() {
        RepositorioGenerico<Tarefa> repositorio = new RepositorioGenerico<>();

        Tarefa tarefa = new Tarefa(1, "Teste", "Testar generics");
        repositorio.adicionar(tarefa);

        List<Object> destino = new ArrayList<>();
        repositorio.copiarPara(destino);

        assertEquals(1, destino.size());
    }

    // Verifica se o repositorio remove corretamente um item.
    @Test
    public void deveRemoverItem() {
        RepositorioGenerico<Tarefa> repositorio = new RepositorioGenerico<>();

        Tarefa tarefa = new Tarefa(1, "Remover", "Teste de remocao");
        repositorio.adicionar(tarefa);

        boolean removido = repositorio.remover(tarefa);

        assertTrue(removido);
        assertTrue(repositorio.estaVazio());
    }
}