package com.wilker.livraria_api.infrastructure.entity;

import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autor")
@Builder

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

    @ManyToMany(mappedBy = "autores")
    private Set<ObraEntity> obras;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutorEntity that = (AutorEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
