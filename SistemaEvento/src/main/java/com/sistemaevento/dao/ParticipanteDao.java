package com.sistemaevento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sistemaevento.tabelas.Participante;
import com.sistemaevento.util.ConexaoBD;

public class ParticipanteDao {

    // Método para adicionar um novo participante ao banco
    public int adicionarParticipante(Participante participante) {
        String sql = "INSERT INTO participante (nome, email) VALUES (?, ?)";
        try (Connection conexao = new ConexaoBD().getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                System.out.println("Participante salvo com ID: " + idGerado);
                return idGerado;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar evento: " + e.getMessage());
        }
        
        return 1;
    }

    // Método para listar todos os participantes
    public List<Participante> listarParticipantes() {
        List<Participante> participantes = new ArrayList<>();
        String sql = "SELECT * FROM participante";

        try (Connection conexao = new ConexaoBD().getConnection();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Participante participante = new Participante();
                participante.setId(rs.getInt("id"));
                participante.setNome(rs.getString("nome"));
                participante.setEmail(rs.getString("email"));
                participantes.add(participante);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participantes;
    }
    
    public Participante buscarPorNome(String nome) {
        String sql = "SELECT * FROM participante WHERE nome = ? LIMIT 1";
        try (Connection conexao = new ConexaoBD().getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
    
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                return p;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null;
    }

    public void cadastrarParticipante(Participante participante) {
        String sql = "INSERT INTO participante (nome, email) VALUES (?, ?)";

        try (Connection conn = new ConexaoBD().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvar(Participante participante) {
        String sql = "INSERT INTO participante (nome, email) VALUES (?, ?)";

        try (Connection conn = new ConexaoBD().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inscrever(int participanteId, int eventoId) {
        String sql = "INSERT INTO inscricoes (participante_id, evento_id) VALUES (?, ?)";
    
        try (Connection conn = new ConexaoBD().connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);
            stmt.executeUpdate();
            System.out.println("Participante inscrito com sucesso!");
    
        } catch (SQLException e) {
            System.err.println("Erro ao inscrever participante: " + e.getMessage());
        }
    }

    public List<Participante> listarParticipantesPorEvento(int eventoId) {
        List<Participante> participantes = new ArrayList<>();
        String sql = "SELECT p.* FROM participante p " +
                     "JOIN inscricoes i ON p.id = i.participante_id " +
                     "WHERE i.evento_id = ?";

        try (Connection conn = new ConexaoBD().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Participante participante = new Participante();
                participante.setId(rs.getInt("id"));
                participante.setNome(rs.getString("nome"));
                participante.setEmail(rs.getString("email"));
                participantes.add(participante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participantes;
    }

    public boolean verificarCredenciais(int id, String email) {
        String sql = "SELECT 1 FROM participante WHERE id = ? AND email = ?";
        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // retorna true se encontrou
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Participante buscarPorId(int id) {
        String sql = "SELECT * FROM participante WHERE id = ?";
        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public boolean atualizar(Participante participante) {
        String sql = "UPDATE participante SET nome = ?, email = ? WHERE id = ?";

        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.setInt(3, participante.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        public boolean removerInscricao(int participanteId, int eventoId) {
        String sql = "DELETE FROM inscricoes WHERE participante_id = ? AND evento_id = ?";

        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        public boolean jaInscrito(int participanteId, int eventoId) {
        String sql = "SELECT COUNT(*) FROM inscricoes WHERE participante_id = ? AND evento_id = ?";
        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Participante buscarPorEmail(String email) {
        String sql = "SELECT * FROM participante WHERE email = ?";
        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String buscarEmailPorId(int id) {
        String sql = "SELECT email FROM participante WHERE id = ?";

        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("email");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar email do participante: " + e.getMessage());
        }

        return null;
    }
}
