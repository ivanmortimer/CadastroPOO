/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Ivan
 */
public class Pessoa implements Serializable {
    // Atributos
    private String nome;
    private int id;
    private static final GeradorIdExclusiva geradorId = new GeradorIdExclusiva();
    
    // Construtores
    public Pessoa() {
        this.nome = "";
        this.id = geradorId.gerarIdExclusiva();
    }
    public Pessoa(String nome) {
        this.nome = nome;
        this.id = geradorId.gerarIdExclusiva();
    }
    public Pessoa(String nome, int id) {
        this.nome = nome;
        this.id = id;
        geradorId.adicionarId(id);
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
        geradorId.adicionarId(id);
    }
    
    // Método 'exibir' para impressão dos dados de Pessoa
    public void exibir() {
        System.out.println("nome: " + this.getNome());
        System.out.println("id: " + this.getId());
    }
}

class GeradorIdExclusiva {
    private static final int MAX_ID = 1_000_000; // Limite superior
    private static final Set<Integer> idsUsadas = new HashSet<>();
    private static final Random random = new Random();

    public int gerarIdExclusiva() {
        if (idsUsadas.size() >= MAX_ID) {
            throw new RuntimeException("Todas as IDs exclusivas foram esgotadas!");
        }

        int id;
        do {
            id = random.nextInt(MAX_ID);
        } while (idsUsadas.contains(id));

        idsUsadas.add(id);
        return id;
    }
    
    public void adicionarId(int id) {
        if (idsUsadas.contains(id))
            throw new RuntimeException("Esta ID já foi utilizada!");
        else
            idsUsadas.add(id);
    }
}
