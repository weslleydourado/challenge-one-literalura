package dev.weslleydourado.challengeliteratura.entity;

import dev.weslleydourado.challengeliteratura.model.Autor;
import dev.weslleydourado.challengeliteratura.util.CadenasUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Autor")
public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer fechaNacimento;
    private Integer fechaFalecimento;


    @OneToOne
    @JoinTable(
            name = "Livro",
            joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private LivroEntity livro;


    public AutorEntity() {

    }

    public AutorEntity(Autor autor) {
        this.nome = CadenasUtil.limitarLongitud(autor.name(), 200);

        if (autor.birthYear() == null)
            this.fechaNacimento = 1980;
        else
            this.fechaNacimento = autor.birthYear();

        if (autor.deathYear() == null)
            this.fechaFalecimento = 3022;
        else
            this.fechaFalecimento = autor.deathYear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFechaNacimento() {
        return fechaNacimento;
    }

    public void setFechaNacimento(Integer fechaNacimento) {
        this.fechaNacimento = fechaNacimento;
    }

    public Integer getFechaFalecimento() {
        return fechaFalecimento;
    }

    public void setFechaFalecimento(Integer fechaFalecimento) {
        this.fechaFalecimento = fechaFalecimento;
    }


    @Override
    public String toString() {
        return "AutorEntity [id=" + id + ", nombre=" + nome + ", fechaNacimiento=" + fechaNacimento
                + ", fechaFallecimiento=" + fechaFalecimento + ", libro="  + "]";
    }

    public LivroEntity getLivro() {
        return livro;
    }

    public void setLivro(LivroEntity livro) {
        this.livro = livro;
    }

}