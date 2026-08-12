# Programação para Web 3 (PRW3) - IFSP

Este repositório destina-se a armazenar e organizar todos os trabalhos, exercícios e atividades desenvolvidos durante a disciplina de **Programação para Web 3 (PRW3)** no **Instituto Federal de Educação, Ciência e Tecnologia de São Paulo (IFSP)**.

---

## 📚 Conteúdo do Repositório

Aqui serão postados os projetos práticos desenvolvidos ao longo do curso, abordando conceitos fundamentais de desenvolvimento web em Java, persisência de dados com JPA/Hibernate, arquitetura de software e boas práticas.

---

## 🚀 Projeto Atual: Sistema de Cadastro de Alunos (JPA / Hibernate)

Sistema de gerenciamento e cadastro de alunos via terminal (CLI), utilizando **Java 21**, **Jakarta Persistence (JPA)**, **Hibernate ORM** e banco de dados embarcado **H2**.

### 🛠️ Tecnologias Utilizadas
* **Linguagem**: Java 21
* **Persistência**: JPA (Jakarta Persistence) com Hibernate ORM
* **Banco de Dados**: H2 Database (modo arquivo em `./data/alunos`)
* **Gerenciador de Dependências**: Apache Maven

---

### 📋 Funcionalidades
1. **Cadastrar Aluno**: Permite registrar um aluno informando Nome, RA, Email e 3 Notas.
2. **Excluir Aluno**: Remove o registro do aluno com base no ID.
3. **Alterar Aluno**: Permite atualizar os dados ou notas de um aluno existente.
4. **Buscar Aluno pelo Nome**: Realiza busca por nome utilizando JPQL (`LIKE`).
5. **Listar Alunos (com status de aprovação)**: Exibe os alunos com o cálculo da média aritmética e a situação:
   * **Média $\ge$ 6.0**: `Aprovado`
   * **Média entre 4.0 e 5.9**: `Recuperação`
   * **Média < 4.0**: `Reprovado`
6. **FIM**: Encerra a execução do programa.

---

### 💻 Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/GustavoKurozawa/programa-o-web-3-IFSP.git
   ```
2. Abra o projeto na sua IDE Java preferida (IntelliJ IDEA, Eclipse, VS Code).
3. Certifique-se de ter o **JDK 21** e o **Maven** configurados.
4. Execute a classe principal:
   ```text
   src/main/java/br/edu/ifsp/naoki/testes/CadastroDeAluno.java
   ```

---

## 👤 Autor

* **Gustavo Kurozawa** - *Estudante no IFSP*
