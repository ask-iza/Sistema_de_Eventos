package com.sistemaevento.tabelas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "palestrante")
public class Palestrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String curriculo;
    private String area_atuacao;
    private String email;

    @OneToMany(mappedBy = "palestrante", cascade = CascadeType.ALL)
    private List<Evento> eventos;
}
