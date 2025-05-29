# Sistema para Controle de Tarefas

## 📝 Descrição do Projeto
Este é um sistema em Java para gerenciar tarefas de equipes de desenvolvimento de software. O objetivo é facilitar a organização e o acompanhamento das atividades do projeto. Desenvolvido como projeto final da disciplina de Programação Orientada a Objetos.

## 👨‍💻 Autores
* Alex Dantas de Oliveira e Silva
* Diego Braga Fong
* Francisco Serafim da Silva
* Handrey Kaleu Matias Souza de Carvalho

## ✨ Funcionalidades Implementadas

O sistema permite as seguintes ações:

* **Gerentes:**
    * Cadastrar, modificar e excluir tarefas, categorias e colaboradores.
    * Associar tarefas a colaboradores.
    * Consultar listas de colaboradores e categorias.
    * Filtrar tarefas (por colaborador, categoria, status).
* **Colaboradores:**
    * Consultar suas tarefas.
    * Alterar o status de suas tarefas.
    * Filtrar tarefas (por categoria e status).

## 🚀 Instruções para Execução

**Pré-requisitos:**
* JDK (Java Development Kit) - Versão 11 ou superior.
* MySQL Server instalado e rodando.

**Configuração do Banco de Dados:**
1.  Crie um banco de dados no MySQL chamado `sistema_tarefas`.
2.  Execute o script SQL para criar as tabelas.
3.  Configure as credenciais do banco na classe `com.sistemarefas.util.connectionFactory.java`:
    * `DB_URL`: `jdbc:mysql://localhost:3306/sistema_tarefas`
    * `USER`: `root`
    * `PASS`: ``

**Executando (via IDE - Ex: IntelliJ IDEA, Eclipse):**
1.  Importe o projeto na sua IDE.
2.  Adicione o JAR do driver JDBC do MySQL às bibliotecas do projeto.
3.  Localize e execute a classe principal: `com.sistemarefas.SistemaAPP.java`.
