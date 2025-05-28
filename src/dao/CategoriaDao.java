package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.cCategoria;
import util.connectionFactory;

public class CategoriaDao {

    public void adicionarCategoria(cCategoria cat) throws Exception {
        String sql = "INSERT INTO categorias (nome) VALUES (?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cat.getNome());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cat.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<cCategoria> listarCategoria() throws Exception {
        String sql = "SELECT * FROM categorias";
        List<cCategoria> lista = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new cCategoria(
                        rs.getInt("id"),
                        rs.getString("nome")
                ));
            }
        }

        return lista;
    }

    public cCategoria buscarCategoria(int id) throws Exception {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new cCategoria(
                            rs.getInt("id"),
                            rs.getString("nome")
                    );
                }
            }
        }

        return null;
    }

    public void atualizarCategoria(cCategoria cat) throws Exception {
        String sql = "UPDATE categorias SET nome = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cat.getNome());
            stmt.setInt(2, cat.getId());
            stmt.executeUpdate();
        }
    }

    public void removerCategoria(int id) throws Exception {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}