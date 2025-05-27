package model;

public class cColaborador {
    private int id;
    private String nome;
    private String email;
    private eTipoColaborador tipo;

    public cColaborador(String nome, String email, eTipoColaborador tipo) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public cColaborador(int id, String nome, String email, eTipoColaborador tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

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

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public eTipoColaborador getTipo() {
        return tipo;
    }
    public void setTipo(eTipoColaborador tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\t Nome: " + nome + "\t Email: " + email + "\t Tipo: " + tipo;
    }
}
