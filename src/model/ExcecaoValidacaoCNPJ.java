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
public class ExcecaoValidacaoCNPJ extends Exception implements Serializable {
        @Serial
	private static final long serialVersionUID = 6L;
	// Atributes
	String msg_erro;

        // Construtores
	public ExcecaoValidacaoCNPJ() {
	}
	public ExcecaoValidacaoCNPJ(String message) {
		super(message);
		this.msg_erro = message;
	}
	
	@Override
	public String toString() {
		return "ErroValidacaoCNPJ: " + this.msg_erro;
	}
}
