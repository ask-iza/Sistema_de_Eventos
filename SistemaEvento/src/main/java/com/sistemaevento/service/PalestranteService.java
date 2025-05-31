package com.sistemaevento.service;

import org.springframework.stereotype.Service;

import com.sistemaevento.dao.PalestranteDao;
import com.sistemaevento.tabelas.Palestrante;

@Service
public class PalestranteService {
    
    private PalestranteDao dao = new PalestranteDao();

    public void cadastrarPalestrante(Palestrante p) {
        dao.salvar(p);
    }

    public int cadastrarRetornandoId(Palestrante p) {
        return dao.cadastrarPalestrante(p); 
    }

    public void vincularPalestrante(int eventoId, int palestranteId) {
        dao.vincularPalestranteAoEvento(eventoId, palestranteId);
    }
    
    public boolean validarCredenciais(int id, String email) {
        return dao.verificarPalestrante(id, email);
    }

    public Palestrante buscarPalestrantePorId(int id) {
        return dao.buscarPalestrantePorId(id);
    }

    public int obterOuCadastrarPalestrante(Palestrante novo) {
        Palestrante existente = dao.buscarPorNomeEEmail(novo.getNome(), novo.getEmail());
        if (existente != null) {
            return existente.getId();
        }
        return dao.cadastrarPalestrante(novo);
    }

}