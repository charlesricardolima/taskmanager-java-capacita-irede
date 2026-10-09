/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.charles.taskmanager.app;
import br.com.charles.taskmanager.db.Conexao;
/**
 *
 * @author Charles Ricardo
 */

import br.com.charles.taskmanager.controller.TaskManager;
import br.com.charles.taskmanager.exceptions.TarefaNaoEncontradaException;
import br.com.charles.taskmanager.model.Tarefa;
import java.util.Scanner;

// Classe principal da aplicacao. Responsavel por exibir o menu
// e controlar a interacao do usuario pelo console.
public class App {

    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager = new TaskManager();
    
// Metodo principal que mantem o sistema em execucao
// ate que o usuario escolha a opcao de sair.
    public static void main(String[] args) {
        Conexao.criarTabelaTarefas();
        
        int opcao = 0;

        do {
            exibirMenu();

            try {
                opcao = lerInteiro("Escolha uma opcao: ");

                switch (opcao) {
                    case 1:
                        criarTarefa();
                        break;
                    case 2:
                        listarTarefas();
                        break;
                    case 3:
                        concluirTarefa();
                        break;
                    case 4:
                        removerTarefa();
                        break;
                    case 5:
                        System.out.println("Sistema encerrado. Ate mais!");
                        break;
                    default:
                        System.out.println("Opcao invalida. Tente novamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Erro: digite apenas numeros.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (TarefaNaoEncontradaException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println();

        } while (opcao != 5);
    }

    private static void exibirMenu() {
        System.out.println("===== GERENCIADOR DE TAREFAS =====");
        System.out.println("1. Criar nova tarefa");
        System.out.println("2. Listar tarefas");
        System.out.println("3. Marcar tarefa como concluida");
        System.out.println("4. Remover tarefa");
        System.out.println("5. Sair");
    }

    private static void criarTarefa() {
        System.out.println("===== CRIAR NOVA TAREFA =====");
        System.out.println("1. Tarefa comum");
        System.out.println("2. Tarefa prioritaria");

        int tipo = lerInteiro("Escolha o tipo da tarefa: ");

        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo de tarefa invalido.");
            return;
        }

        System.out.print("Digite o titulo da tarefa: ");
        String titulo = scanner.nextLine();

        System.out.print("Digite a descricao da tarefa: ");
        String descricao = scanner.nextLine();

        if (tipo == 1) {
            taskManager.adicionarTarefa(titulo, descricao);
            System.out.println("Tarefa comum cadastrada com sucesso.");
        } else {
            System.out.print("Digite a prioridade da tarefa: ");
            String prioridade = scanner.nextLine();

            taskManager.adicionarTarefaPrioritaria(titulo, descricao, prioridade);
            System.out.println("Tarefa prioritaria cadastrada com sucesso.");
        }
    }

    private static void listarTarefas() {
        System.out.println("===== LISTA DE TAREFAS =====");

        if (!taskManager.possuiTarefas()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }

        for (Tarefa tarefa : taskManager.listarTarefas()) {
            System.out.println(tarefa);
        }
    }

    private static void concluirTarefa() throws TarefaNaoEncontradaException {
        int id = lerInteiro("Digite o ID da tarefa que deseja concluir: ");
        taskManager.concluirTarefa(id);
        System.out.println("Tarefa marcada como concluida.");
    }

    private static void removerTarefa() throws TarefaNaoEncontradaException {
        int id = lerInteiro("Digite o ID da tarefa que deseja remover: ");
        taskManager.removerTarefa(id);
        System.out.println("Tarefa removida com sucesso.");
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine();
        return Integer.parseInt(entrada);
    }
}