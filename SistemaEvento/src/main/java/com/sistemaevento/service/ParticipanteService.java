package com.sistemaevento.service;

import java.util.List;

import com.sistemaevento.dao.ParticipanteDao;
import com.sistemaevento.tabelas.Participante;

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
}
