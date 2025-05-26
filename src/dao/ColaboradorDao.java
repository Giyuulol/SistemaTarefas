package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.connectionFactory;
import model.cColaborador;

public class ColaboradorDao {

    public void adicionar(cColaborador col) throws Exception {
        Connection conn = connectionFactory.getConnection();
        String sql = "INSERT INTO colaboradores (nome, email) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, col.getNome());
        stmt.setString(2, col.getEmail());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<cColaborador> listar() throws Exception {
        Connection conn = connectionFactory.getConnection();
        String sql = "SELECT * FROM colaboradores";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<cColaborador> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new cColaborador(rs.getInt("id"), rs.getString("nome"), rs.getString("email")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public cColaborador buscarPorId(int id) throws Exception {
        Connection conn = connectionFactory.getConnection();
        String sql = "SELECT * FROM colaboradores WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

    cColaborador col = null;
        if (rs.next()) {
            col = new cColaborador(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email")
            );
        }

        rs.close();
        stmt.close();
        conn.close();
        return col;
    }
    public void atualizar(cColaborador col) throws Exception {
        Connection conn = connectionFactory.getConnection();
        String sql = "UPDATE colaboradores SET nome = ?, email = ?, telefone = ?, cpf = ?, cargo = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, col.getNome());
        stmt.setString(2, col.getEmail());
        stmt.setInt(6, col.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void excluir(int id) throws Exception {
        Connection conn = connectionFactory.getConnection();
        String sql = "DELETE FROM colaboradores WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}