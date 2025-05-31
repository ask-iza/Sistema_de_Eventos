package com.sistemaevento.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaevento.dao.EventoDao;
import com.sistemaevento.tabelas.Evento;


@Service
public class EventoService {
    private EventoDao dao = new EventoDao();

    public void criarEvento(Evento evento) {
        dao.salvar(evento);
    }

    public List<Evento> listarComPalestrante() {
        return dao.listarTodosComPalestrante();
    }

    public Evento buscarPorNome(String nome) {
        return dao.buscarPorNome(nome);
    }

    public boolean atualizar(Evento evento) {
        return dao.atualizar(evento);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }
    
    public int salvarRetornandoId(Evento evento) {
        return dao.salvar(evento); // já existe no DAO e retorna o ID
    }

    public void vincularPalestrante(int eventoId, int palestranteId) {
        dao.vincularPalestranteAoEvento(eventoId, palestranteId);
        
    } 

}
