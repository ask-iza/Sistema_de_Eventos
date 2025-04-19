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

    // Método para excluir um participante pelo ID
    public void excluirParticipante(int id) {
        String sql = "DELETE FROM participante WHERE id = ?";
        try (Connection conexao = new ConexaoBD().getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Participante excluído com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
