package com.wilker.livraria_api.infrastructure.entity;

import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autor")

public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", length = 9)
    private SexoEnum sexoEnum;

    @Column(name = "email", unique = true, length = 220)
    private String email;

    @Column(name = "dataNascimento")
    private LocalDate dataNascimento;

    @Column(name = "pais_origem", nullable = false, length = 150)
    private String paisOrigem;

    @Column(name = "cpf", unique = true, length = 14)
    private String cpf;

    @OneToMany(mappedBy = "autorEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ObraEntity> obras;



}
