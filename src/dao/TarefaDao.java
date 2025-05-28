package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDao { 
  public void adicionar(Tarefa tarefa) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "INSERT INTO tarefas (titulo, descricao, status, colaborador_id, categoria_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, tarefa.getTitulo());
        stmt.setString(2, tarefa.getDescricao());
        stmt.setString(3, tarefa.getStatus());
        stmt.setInt(4, tarefa.getColaboradorId());
        stmt.setInt(5, tarefa.getCategoriaId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Tarefa> listar() throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM tarefas";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Tarefa> lista = new ArrayList<>();
        while (rs.next()) {
            Tarefa t = new Tarefa(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("status"),
                    rs.getInt("colaborador_id"),
                    rs.getInt("categoria_id")
            );
            lista.add(t);
        }

        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public List<Tarefa> listarPorStatus(String status) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM tarefas WHERE status = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, status);
        ResultSet rs = stmt.executeQuery();

        List<Tarefa> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Tarefa(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("status"),
                    rs.getInt("colaborador_id"),
                    rs.getInt("categoria_id")
            ));
        }

        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public List<Tarefa> listarPorColaborador(int colaboradorId) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM tarefas WHERE colaborador_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, colaboradorId);
        ResultSet rs = stmt.executeQuery();

        List<Tarefa> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Tarefa(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("status"),
                    rs.getInt("colaborador_id"),
                    rs.getInt("categoria_id")
            ));
        }

        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public List<Tarefa> listarPorCategoria(int categoriaId) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM tarefas WHERE categoria_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, categoriaId);
        ResultSet rs = stmt.executeQuery();

        List<Tarefa> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Tarefa(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("status"),
                    rs.getInt("colaborador_id"),
                    rs.getInt("categoria_id")
            ));
        }

        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public void alterarStatus(int id, String novoStatus) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "UPDATE tarefas SET status = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, novoStatus);
        stmt.setInt(2, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void excluir(int id) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "DELETE FROM tarefas WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
