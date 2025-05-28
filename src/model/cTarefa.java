// src/main/java/com/tarefas/model/Tarefa.java
package model;

import java.time.LocalDate;

public class cTarefa {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataVencimento;
    private eStatusTarefa status;
    private cColaborador colaborador; // Pode ser null
    private cCategoria categoria;     // Pode ser null

    public cTarefa(int id, String titulo, String descricao, LocalDate dataVencimento, eStatusTarefa status, cColaborador colaborador, cCategoria categoria) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.colaborador = colaborador;
        this.categoria = categoria;
    }

    public cTarefa(String titulo, String descricao, LocalDate dataVencimento, eStatusTarefa status, cColaborador colaborador, cCategoria categoria) {

        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.colaborador = colaborador;
        this.categoria = categoria;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public eStatusTarefa getStatus() { return status; }
    public void setStatus(eStatusTarefa status) { this.status = status; }
    public cColaborador getColaborador() { return colaborador; }
    public void setColaborador(cColaborador colaborador) { this.colaborador = colaborador; }
    public cCategoria getCategoria() { return categoria; }
    public void setCategoria(cCategoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Título: " + titulo +
                ", Descrição: " + (descricao != null ? descricao : "N/A") +
                ", Vencimento: " + (dataVencimento != null ? dataVencimento : "N/A") +
                ", Status: " + status +
                ", Colaborador: " + (colaborador != null ? colaborador.getNome() : "N/A") +
                ", Categoria: " + (categoria != null ? categoria.getNome() : "N/A");
    }
}