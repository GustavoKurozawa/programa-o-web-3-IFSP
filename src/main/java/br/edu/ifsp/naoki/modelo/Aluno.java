package br.edu.ifsp.naoki.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String ra;
    private String email;
    private BigDecimal nota1;
    private BigDecimal nota2;
    private BigDecimal nota3;

    // Construtor padrão (obrigatório para JPA)
    public Aluno() {}

    // Construtor completo (sem id)
    public Aluno(String nome, String ra, String email, BigDecimal nota1, BigDecimal nota2, BigDecimal nota3) {
        this.nome = nome;
        this.ra = ra;
        this.email = email;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
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

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getNota1() {
        return nota1;
    }

    public void setNota1(BigDecimal nota1) {
        this.nota1 = nota1;
    }

    public BigDecimal getNota2() {
        return nota2;
    }

    public void setNota2(BigDecimal nota2) {
        this.nota2 = nota2;
    }

    public BigDecimal getNota3() {
        return nota3;
    }

    public void setNota3(BigDecimal nota3) {
        this.nota3 = nota3;
    }

    // Método utilitário para calcular a média
    public BigDecimal getMedia() {
        if (nota1 == null || nota2 == null || nota3 == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal soma = nota1.add(nota2).add(nota3);
        return soma.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
    }

    // Método utilitário para obter a situação de aprovação
    public String getSituacao() {
        BigDecimal media = getMedia();
        if (media.compareTo(new BigDecimal("4.00")) < 0) {
            return "Reprovado";
        } else if (media.compareTo(new BigDecimal("6.00")) < 0) {
            return "Recuperação";
        } else {
            return "Aprovado";
        }
    }

    public String getStatusAprovacao() {
        return getSituacao();
    }

    @Override
    public String toString() {
        return "Aluno [ID=" + id +
                ", Nome=" + nome +
                ", RA=" + ra +
                ", Email=" + email +
                ", Nota1=" + nota1 +
                ", Nota2=" + nota2 +
                ", Nota3=" + nota3 +
                ", Média=" + getMedia() +
                ", Situação=" + getSituacao() + "]";
    }
}
