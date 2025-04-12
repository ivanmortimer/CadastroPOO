/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.io.Serial;

/**
 *
 * @author Ivan
 */
public class ExcecaoValidacaoIdade extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
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
