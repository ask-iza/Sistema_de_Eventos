package com.sistemaevento.service;

import java.util.List;

import com.sistemaevento.dao.EventoDao;
import com.sistemaevento.tabelas.Evento;

public class EventoService {
    private EventoDao dao = new EventoDao();

    public void criarEvento(Evento evento) {
        dao.salvar(evento);
    }

    public List<Evento> listarTodos() {
        return dao.listarTodos();
    }
    
    public Evento buscarPorNome(String nome) {
        return dao.buscarPorNome(nome);
    }
    
    public int salvarRetornandoId(Evento evento) {
        return dao.salvar(evento); // já existe no DAO e retorna o ID
    }

    public void vincularPalestrante(int eventoId, int palestranteId) {
        dao.vincularPalestranteAoEvento(eventoId, palestranteId);
        
    }    

}
