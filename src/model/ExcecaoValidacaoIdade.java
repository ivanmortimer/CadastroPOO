/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Ivan
 */
public class ExcecaoValidacaoIdade extends Exception implements Serializable {
    private static final long serialVersionUID = -2321426394855140977L;
    // Atributos
    String msg_erro;

    public ExcecaoValidacaoIdade() {
    }

    public ExcecaoValidacaoIdade(String message) {
            super(message);
            this.msg_erro = message;
    }

    @Override
    public String toString() {
            return "ErroValidacaoIdade: " + this.msg_erro;
    }
}
