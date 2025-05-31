package com.sistemaevento.tabelas;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "evento")
public class Evento {
    private int id;
    private String nome;
    private String descricao;
    private String data;
    private String local;
    private int capacidade;
    private List<Integer> palestrantesIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Integer> getPalestrantesIds() {
        return palestrantesIds;
    }

    public void setPalestrantesIds(List<Integer> palestrantesIds) {
        this.palestrantesIds = palestrantesIds;
    }

    @Transient // Não mapeia para o banco de dados diretamente
    private String palestranteNome;

    public String getPalestranteNome() {
        return palestranteNome;
    }

    public void setPalestranteNome(String palestranteNome) {
        this.palestranteNome = palestranteNome;
    }
}