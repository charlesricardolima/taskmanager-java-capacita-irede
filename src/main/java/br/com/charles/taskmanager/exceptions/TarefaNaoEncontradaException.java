/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.charles.taskmanager.exceptions;

/**
 *
 * @author Charles Ricardo
 */

// Excecao personalizada utilizada quando uma tarefa nao e encontrada.
public class TarefaNaoEncontradaException extends Exception {

    public TarefaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}