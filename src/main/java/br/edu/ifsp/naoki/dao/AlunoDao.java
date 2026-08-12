package br.edu.ifsp.naoki.dao;

import br.edu.ifsp.naoki.modelo.Aluno;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AlunoDao {

    private EntityManager em;

    public AlunoDao(EntityManager em) {
        this.em = em;
    }

    // Cadastrar aluno
    public void cadastrar(Aluno aluno) {
        this.em.persist(aluno);
    }

    // Alterar aluno
    public void alterar(Aluno aluno) {
        this.em.merge(aluno);
    }

    // Excluir aluno
    public void excluir(Aluno aluno) {
        Aluno alunoParaRemover = this.em.contains(aluno) ? aluno : this.em.find(Aluno.class, aluno.getId());
        if (alunoParaRemover != null) {
            this.em.remove(alunoParaRemover);
        }
    }

    // Buscar por ID
    public Aluno buscarPorId(Long id) {
        return em.find(Aluno.class, id);
    }

    // Buscar aluno pelo nome
    public List<Aluno> buscarPorNome(String nome) {
        String jpql = "SELECT a FROM Aluno a WHERE LOWER(a.nome) LIKE LOWER(:nome)";
        return em.createQuery(jpql, Aluno.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    // Listar todos os alunos
    public List<Aluno> buscarTodos() {
        String jpql = "SELECT a FROM Aluno a";
        return em.createQuery(jpql, Aluno.class).getResultList();
    }
}
