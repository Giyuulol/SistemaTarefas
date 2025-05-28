package model;

public class cCategoria {
    private int id;
    private String nome;

    public cCategoria(String nome) {
        this.nome = nome;
    }

    public cCategoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public void setId(int id) {this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() { return "ID: " + id + "\t Nome: " + nome; }
}