package model;

public class cGerente extends cColaborador {

    public cGerente(int id,  String nome, String email) {
        super(id, nome, email,eTipoColaborador.GERENTE);
    }

    public cGerente(String nome, String email) {
        super(nome, email,eTipoColaborador.GERENTE);
    }
}
