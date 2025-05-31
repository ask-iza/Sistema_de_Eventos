package com.sistemaevento.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaevento.dao.ParticipanteDao;
import com.sistemaevento.tabelas.Participante;

@Service
public class ParticipanteService {
    private ParticipanteDao dao = new ParticipanteDao();

    public void cadastrarParticipante(Participante p) {
        dao.salvar(p);
    }

    public void inscrever(int participanteId, int eventoId) {
        dao.inscrever(participanteId, eventoId);
    }
    

    public List<Participante> listarTodos() {
        return dao.listarParticipantes();
    }
    
    public Participante buscarPorNome(String nome) {
        return dao.buscarPorNome(nome);
    }
    
    public int adicionarRetornandoId(Participante p) {
        return dao.adicionarParticipante(p); // já existe no seu DAO
    } 

    public List<Participante> listarParticipantesPorEvento(int eventoId) {
        return dao.listarParticipantesPorEvento(eventoId);
    }

    public boolean verificarCredenciais(int id, String email) {
        return dao.verificarCredenciais(id, email);
    }
    
    public Participante buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean atualizar(Participante participante) {
        return dao.atualizar(participante);
    }

    public boolean removerInscricao(int participanteId, int eventoId) {
        return dao.removerInscricao(participanteId, eventoId);
    } 

    public boolean jaInscritoNoEvento(int participanteId, int eventoId) {
        return dao.jaInscrito(participanteId, eventoId);
    }

    public int obterOuCadastrarParticipante(Participante p) {
        Participante existente = dao.buscarPorEmail(p.getEmail());
        if (existente != null) {
            return existente.getId(); // Já existe
        }
        return dao.adicionarParticipante(p); // Cadastrar novo
    }

    public String obterEmailCensurado(int participanteId) {
        String email = dao.buscarEmailPorId(participanteId);
        return censurarEmail(email);
    }

        private String censurarEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        String[] partes = email.split("@");
        String inicio = partes[0];
        String dominio = partes[1];

        if (inicio.length() <= 2) {
            return "***@" + dominio;
        }

        String visivel = inicio.substring(0, 2);
        String censurado = "*".repeat(inicio.length() - 2);
        return visivel + censurado + "@" + dominio;
    }
}
