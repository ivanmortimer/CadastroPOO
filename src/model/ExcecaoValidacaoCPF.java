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
public class ExcecaoValidacaoCPF extends Exception implements Serializable {
    private static final long serialVersionUID = -8745070459212389971L;
    String msg_erro;

    public ExcecaoValidacaoCPF() {
    }

    public ExcecaoValidacaoCPF(String message) {
        super(message);
        this.msg_erro = message;
    }

    @Override
    public String toString() {
        return "ErroValidacaoCPF: " + this.msg_erro;
    }
}
