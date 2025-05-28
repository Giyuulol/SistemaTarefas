// src/main/java/com/tarefas/dao/TarefaDao.java
package dao;

import model.cTarefa;
import model.cColaborador;
import model.cCategoria;
import model.eStatusTarefa;
import model.eTipoColaborador;
import util.connectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarefaDao {

    public void adicionarTarefa(cTarefa tarefa) throws SQLException {
        String sql = "INSERT INTO tarefas (titulo, descricao, data_vencimento, status, colaborador_id, categoria_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, tarefa.getDataVencimento() != null ? Date.valueOf(tarefa.getDataVencimento()) : null);
            stmt.setString(4, tarefa.getStatus().name());
            stmt.setObject(5, tarefa.getColaborador() != null ? tarefa.getColaborador().getId() : null, Types.INTEGER);
            stmt.setObject(6, tarefa.getCategoria() != null ? tarefa.getCategoria().getId() : null, Types.INTEGER);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tarefa.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public cTarefa buscarTarefaPorId(int id) {
        String sql = "SELECT t.*, c.nome AS colaborador_nome, c.email AS colaborador_email, c.tipo AS colaborador_tipo, cat.nome AS categoria_nome " +
                "FROM tarefas t " +
                "LEFT JOIN colaboradores c ON t.colaborador_id = c.id " +
                "LEFT JOIN categorias cat ON t.categoria_id = cat.id " +
                "WHERE t.id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cColaborador colaborador = null;
                    if (rs.getObject("colaborador_id") != null) {
                        colaborador = new cColaborador(
                                rs.getInt("colaborador_id"),
                                rs.getString("colaborador_nome"),
                                rs.getString("colaborador_email"),
                                eTipoColaborador.valueOf(rs.getString("colaborador_tipo"))
                        );
                    }

                    cCategoria categoria = null;
                    if (rs.getObject("categoria_id") != null) {
                        categoria = new cCategoria(
                                rs.getInt("categoria_id"),
                                rs.getString("categoria_nome")
                        );
                    }

                    return new cTarefa(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("descricao"),
                            rs.getDate("data_vencimento") != null ? rs.getDate("data_vencimento").toLocalDate() : null,
                            eStatusTarefa.valueOf(rs.getString("status")),
                            colaborador,
                            categoria
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<cTarefa> listarTarefas() {
        List<cTarefa> tarefas = new ArrayList<>();
        String sql = "SELECT t.*, c.nome AS colaborador_nome, c.email AS colaborador_email, c.tipo AS colaborador_tipo, cat.nome AS categoria_nome " +
                "FROM tarefas t " +
                "LEFT JOIN colaboradores c ON t.colaborador_id = c.id " +
                "LEFT JOIN categorias cat ON t.categoria_id = cat.id";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cColaborador colaborador = null;
                if (rs.getObject("colaborador_id") != null) {
                    colaborador = new cColaborador(
                            rs.getInt("colaborador_id"),
                            rs.getString("colaborador_nome"),
                            rs.getString("colaborador_email"),
                            eTipoColaborador.valueOf(rs.getString("colaborador_tipo"))
                    );
                }

                cCategoria categoria = null;
                if (rs.getObject("categoria_id") != null) {
                    categoria = new cCategoria(
                            rs.getInt("categoria_id"),
                            rs.getString("categoria_nome")
                    );
                }

                tarefas.add(new cTarefa(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getDate("data_vencimento") != null ? rs.getDate("data_vencimento").toLocalDate() : null,
                        eStatusTarefa.valueOf(rs.getString("status")),
                        colaborador,
                        categoria
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    public void atualizarTarefa(cTarefa tarefa) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, data_vencimento = ?, status = ?, colaborador_id = ?, categoria_id = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, tarefa.getDataVencimento() != null ? Date.valueOf(tarefa.getDataVencimento()) : null);
            stmt.setString(4, tarefa.getStatus().name());
            stmt.setObject(5, tarefa.getColaborador() != null ? tarefa.getColaborador().getId() : null, Types.INTEGER);
            stmt.setObject(6, tarefa.getCategoria() != null ? tarefa.getCategoria().getId() : null, Types.INTEGER);
            stmt.setInt(7, tarefa.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarTarefa(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}