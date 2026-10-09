/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.charles.taskmanager.utils;

/**
 *
 * @author Charles Ricardo
 */

// Classe utilitaria responsavel por validar entradas do usuario.
public class ValidadorEntrada {

    // Verifica se um campo obrigatorio foi preenchido.
    public static void validarTextoObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + nomeCampo + " nao pode ficar vazio.");
        }
    }
}