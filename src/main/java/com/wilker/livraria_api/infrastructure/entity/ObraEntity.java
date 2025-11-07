package com.wilker.livraria_api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "obra")
@Entity

public class ObraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", length = 240)
    private String descricao;

    @Column(name = "dataPublicacao")
    private LocalDate dataPublicacao;

    @ManyToMany
    @JoinTable(name = "autor_obra", joinColumns = @JoinColumn(name = "obra_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<AutorEntity> autores;

}
