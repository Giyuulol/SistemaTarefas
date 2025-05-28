package model;

public class cTarefa {
    private int id;
    private String titulo;
    private String descricao;
    private String status;
    private int colaboradorId;
    private int categoriaId;

    public cTarefa(String titulo, String descricao, int colaboradorId, int categoriaId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = "Pendente";
        this.colaboradorId = colaboradorId;
        this.categoriaId = categoriaId;
    }

    public cTarefa(int id, String titulo, String descricao, String status, int colaboradorId, int categoriaId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.colaboradorId = colaboradorId;
        this.categoriaId = categoriaId;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getStatus() { return status; }
    public int getColaboradorId() { return colaboradorId; }
    public int getCategoriaId() { return categoriaId; }

    public void setId(int id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
}
