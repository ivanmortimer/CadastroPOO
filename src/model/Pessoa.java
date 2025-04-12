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
public class Pessoa implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // Atributos
    private String nome;
    private int id;
    
    // Construtores
    public Pessoa() {
        this.nome = "";
        this.id = this.hashCode();
    }
    public Pessoa(String nome) {
        this.nome = nome;
        this.id = this.hashCode();
    }
    public Pessoa(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }
    
    // Métodos 'getters'
    public String getNome() {
        return this.nome;
    }
    public int getId() {
        return this.id;
    }
    
    // Métodos 'setters'
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    // Método 'exibir' para impressão dos dados de Pessoa
    public void exibir() {
        System.out.println("Id: " + this.getId());
        System.out.println("Nome: " + this.getNome());
    }
}
