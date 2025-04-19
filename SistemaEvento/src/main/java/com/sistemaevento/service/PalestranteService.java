package com.sistemaevento.service;

import com.sistemaevento.dao.PalestranteDao;
import com.sistemaevento.tabelas.Palestrante;

public class PalestranteService {
    
    private PalestranteDao dao = new PalestranteDao();

    public void cadastrarPalestrante(Palestrante p) {
        dao.salvar(p);
    }

    public int cadastrarRetornandoId(Palestrante p) {
        return dao.cadastrarPalestrante(p); // já existe no DAO
    }

    public void vincularPalestrante(int eventoId, int palestranteId) {
        dao.vincularPalestranteAoEvento(eventoId, palestranteId);
    }
    
}