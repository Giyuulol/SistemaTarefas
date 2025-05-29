import dao.*; // Importa todas as classes do pacote dao
import model.*; // Importa todas as classes do pacote model

import java.time.LocalDate; // Para lidar com datas
import java.time.format.DateTimeParseException; // Para tratar erros de formato de data
import java.util.InputMismatchException; // Para tratar erros de tipo de entrada
import java.util.Scanner; // Para ler a entrada do usuário
import java.util.List; // Para usar listas de objetos

public class SistemaAPP {

    // CRIACAO DE OBJETOS ESTÁTICOS QUE PODEM SER UTILIZADOS EM TODAS AS FUNCOES, EVITANDO A REDUNDANCIA
    private static Scanner scanner = new Scanner(System.in); // Objeto para leitura de entrada do console
    private static ColaboradorDao colaboradorDao = new ColaboradorDao(); // Objeto DAO para operações com Colaboradores
    private static CategoriaDao categoriaDao = new CategoriaDao(); // Objeto DAO para operações com Categorias
    private static TarefaDao tarefaDao = new TarefaDao(); // Objeto DAO para operações com Tarefas

    public static void main(String[] args) throws Exception {

        int opcao;

        // Loop principal que exibe o menu e processa as opções do usuário
        do {
            // CRIACAO DO MENU PRINCIPAL, BASE DE TODO O PROGRAMA
            exibirMenuPrincipal(); // Exibe o menu principal do sistema
            opcao = lerOpcao(); // Lê a opção digitada pelo usuário

            // Redireciona para o menu específico de acordo com a opção escolhida
            switch (opcao) {
                case 1:
                    menuColaboradores(); // Chama o menu de gerenciamento de Colaboradores
                    break;

                case 2:
                    menuCategorias(); // Chama o menu de gerenciamento de Categorias
                    break;

                case 3:
                    menuTarefas(); // Chama o menu de gerenciamento de Tarefas
                    break;

                case 0:
                    System.out.println("Finalizando o sistema."); // Mensagem de saída
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente."); // Mensagem para opção inválida
            }
        } while (opcao != 0); // O loop continua até que o usuário digite '0' para sair

        scanner.close(); // Fecha o objeto Scanner para liberar recursos
    }

    // FUNCAO PARA EXIBIR O MENU PRINCIPAL
    private static void exibirMenuPrincipal() {
        System.out.println("\n--- Sistema de Gerenciamento de Tarefas ---");
        System.out.println("1 - Gerenciar Colaboradores");
        System.out.println("2 - Gerenciar Categorias");
        System.out.println("3 - Gerenciar Tarefa");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    // FUNCAO PARA LER INPUT TIPO INTEIRO, UTILIZADO EM OUTRAS FUNCOES TAMBÉM, COM TRATAMENTO DE ERRO
    private static int lerOpcao() {
        // Loop para garantir que o usuário digite um número inteiro
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.next(); // Consome a entrada inválida para evitar loop infinito
            System.out.print("Escolha uma opção: ");
        }
        int opcao = scanner.nextInt(); // Lê o número inteiro digitado
        scanner.nextLine(); // Consome o restante da linha (o caractere de nova linha) após ler o int
        return opcao;
    }

//   Gerenciamento de Colaboradores

    private static void menuColaboradores() throws Exception {
        int opcao;

        // Loop para o menu de gerenciamento de colaboradores
        do {
            System.out.println("\n--- Menu de Colaboradores ---");
            System.out.println("1 - Adicionar Colaborador");
            System.out.println("2 - Listar Colaboradores");
            System.out.println("3 - Buscar Colaborador por ID");
            System.out.println("4 - Atualizar Colaborador");
            System.out.println("5 - Excluir Colaborador");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            opcao = lerOpcao(); // Lê a opção do usuário

            // Redireciona para a função CRUD de colaboradores de acordo com a opção
            switch (opcao) {
                case 1:
                    adicionarColaborador(); // Chama a função para adicionar um novo colaborador
                    break;

                case 2:
                    listarColaboradores(); // Chama a função para listar todos os colaboradores
                    break;

                case 3:
                    buscarColaboradorPorId(); // Chama a função para buscar um colaborador pelo ID
                    break;

                case 4:
                    atualizarColaborador(); // Chama a função para atualizar os dados de um colaborador
                    break;

                case 5:
                    deletarColaborador(); // Chama a função para excluir um colaborador
                    break;

                case 0:
                    System.out.println("Voltando ao Menu Principal");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0); // O loop continua até que o usuário digite '0' para voltar
    }

    // FUNCAO adicionarColaborador: Solicita dados ao usuário e adiciona um novo colaborador no banco
    private static void adicionarColaborador() throws Exception {
        System.out.println("\n--- Adicionar Colaborador ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine(); // Lê o nome do colaborador
        System.out.print("Email: ");
        String email = scanner.nextLine(); // Lê o email do colaborador

        // Variável para armazenar o tipo de colaborador (COLABORADOR ou GERENTE)
        eTipoColaborador tipo = null;
        // Loop para garantir que o tipo digitado seja válido
        while (tipo == null) {
            System.out.print("Tipo [Colaborador | Gerente]: ");
            String tipoStr = scanner.nextLine().toUpperCase(); // Lê o tipo e converte para maiúsculas

            try {
                // Tenta converter a string para o enum eTipoColaborador
                tipo = eTipoColaborador.valueOf(tipoStr);
            } catch (IllegalArgumentException e) {
                // Se a string não corresponder a nenhuma constante do enum, exibe erro
                System.out.println("Tipo inválido. Digite 'Colaborador | Gerente'.");
            }
        }
        // Cria um novo objeto cColaborador com os dados fornecidos
        cColaborador novoColaborador = new cColaborador(nome, email, tipo);
        colaboradorDao.adicionar(novoColaborador); // Chama o DAO para adicionar o colaborador no banco de dados
        System.out.println("Colaborador adicionado com sucesso.");
    }

    // listarColaboradores: Busca e exibe todos os colaboradores cadastrados
    private static void listarColaboradores() throws Exception {
        System.out.println("\n--- Listar Colaboradores ---");
        List<cColaborador> colaboradores = colaboradorDao.listar(); // Obtém a lista de todos os colaboradores do DAO
        if (colaboradores.isEmpty()) { // Verifica se a lista está vazia
            System.out.println("Nenhum colaborador encontrado.");
        } else {
            colaboradores.forEach(System.out::println); // Exibe cada colaborador na lista
        }
    }

    // buscarColaboradorPorId: Solicita um ID e busca um colaborador específico
    private static void buscarColaboradorPorId() throws Exception {
        System.out.println("\n--- Buscar Colaborador com ID ---");
        System.out.print("Digite o ID do colaborador: ");
        int id = lerOpcao(); // Lê o ID a ser buscado

        cColaborador colaborador = colaboradorDao.buscarPorId(id); // Busca o colaborador pelo ID usando o DAO
        if (colaborador != null) { // Verifica se o colaborador foi encontrado
            System.out.println("Colaborador encontrado.");
            System.out.println(colaborador.toString()); // Exibe os dados do colaborador
        } else {
            System.out.println("Nenhum colaborador encontrado.");
        }
    }

    // atualizarColaborador: Permite ao usuário atualizar os dados de um colaborador existente
    private static void atualizarColaborador() throws Exception {
        System.out.println("\n--- Atualizar Colaborador com ID ---");
        System.out.print("Digite o ID do colaborador a ser atualizado: ");
        int id = lerOpcao(); // Lê o ID do colaborador a ser atualizado

        cColaborador colaborador = colaboradorDao.buscarPorId(id); // Busca o colaborador pelo ID
        if (colaborador == null) { // Se não encontrar, informa e sai
            System.out.println("Nenhum colaborador encontrado.");
            return; // Sai do método
        }
        System.out.println("Colaborador atual: ");
        System.out.println(colaborador.toString()); // Mostra os dados atuais do colaborador

        System.out.println("Novo nome (" + colaborador.getNome() + "): ");
        String novoNome = scanner.nextLine(); // Lê o novo nome
        if (!novoNome.isEmpty()) { // Se o campo não estiver vazio, atualiza o nome
            colaborador.setNome(novoNome);
        }

        System.out.println("Novo email (" + colaborador.getEmail() + "): ");
        String novoEmail = scanner.nextLine(); // Lê o novo email
        if (!novoEmail.isEmpty()) { // Se o campo não estiver vazio, atualiza o email
            colaborador.setEmail(novoEmail);
        }

        // Atualização do tipo do colaborador, com validação
        eTipoColaborador novoTipo = null;
        String tipoAtual = colaborador.getTipo().name(); // Obtém o nome do tipo atual como string
        while (novoTipo == null) {
            System.out.println("Novo tipo (" + tipoAtual + ") (Colaborador | Gerente): ) ");
            String novoTipoStr = scanner.nextLine().toUpperCase();
            if (novoTipoStr.isEmpty()) { // Se o usuário não digitar nada, mantém o tipo atual
                novoTipo = eTipoColaborador.valueOf(tipoAtual);
            } else {
                try {
                    novoTipo = eTipoColaborador.valueOf(novoTipoStr); // Tenta converter para enum
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo inválido. Digite 'Colaborador | Gerente' ou deixe em " +
                            "branco para manter o tipo atual" +
                            ".");
                }
            }
        }
        colaborador.setTipo(novoTipo); // Define o novo tipo no objeto colaborador
        colaboradorDao.atualizar(colaborador); // Chama o DAO para atualizar o colaborador no banco de dados
        System.out.println("Colaborador atualizado com sucesso.");
    }

    // deletarColaborador: Solicita um ID e exclui um colaborador após confirmação
    private static void deletarColaborador() throws Exception {
        System.out.println("\n--- Deletar Colaborador com ID ---");
        System.out.print("Digite o ID do colaborador a ser deletado: ");
        int id = lerOpcao(); // Lê o ID do colaborador a ser deletado

        cColaborador colaborador = colaboradorDao.buscarPorId(id); // Busca o colaborador para exibir antes de deletar
        if (colaborador != null) { // Se o colaborador for encontrado
            System.out.print("Tem certeza que deseja deletar o colaborador " + colaborador.getNome() +
                    " (ID: " + id + ")? [S/N]: ");
            String confirmacao = scanner.nextLine().toUpperCase(); // Lê a confirmação do usuário
            if (confirmacao.equals("S")) { // Se a confirmação for 'S'
                colaboradorDao.excluir(id); // Chama o DAO para excluir o colaborador
                System.out.println("Colaborador deletado com sucesso.");
            } else {
                System.out.println("Operação cancelada."); // Se não confirmar, cancela
            }

        } else {
            System.out.println("Nenhum colaborador encontrado."); // Se o colaborador não for encontrado
        }
    }

//     Gerenciamento de Categorias

    private static void menuCategorias() throws Exception {
        int opcao;

        // Loop para o menu de gerenciamento de categorias
        do {
            System.out.println("\n--- Menu de Categorias ---");
            System.out.println("1 - Adicionar Categoria");
            System.out.println("2 - Listar Categorias");
            System.out.println("3 - Buscar Categoria por ID");
            System.out.println("4 - Atualizar Categoria");
            System.out.println("5 - Deletar Categoria");
            System.out.println("0 - Voltar ao Menu Principal");
            opcao = lerOpcao(); // Lê a opção do usuário

            // Redireciona para a função CRUD de categorias de acordo com a opção
            switch (opcao) {
                case 1:
                    adicionarCategoria(); // Adiciona uma nova categoria
                    break;

                case 2:
                    listarCategorias(); // Lista todas as categorias
                    break;

                case 3:
                    buscarCategoriaPorId(); // Busca uma categoria por ID
                    break;

                case 4:
                    atualizarCategoria(); // Atualiza uma categoria existente
                    break;

                case 5:
                    deletarCategoria(); // Deleta uma categoria
                    break;

                case 0:
                    System.out.println("Voltando ao Menu Principal");
                    break;

                default:
                    System.out.println("Opçãon inválida! Tente novamente.");
            }
        } while (opcao != 0); // O loop continua até que o usuário digite '0' para voltar
    }

    // adicionarCategoria: Solicita o nome da categoria e a adiciona no banco
    private static void adicionarCategoria() throws Exception {
        System.out.println("\n--- Adicionar Categoria ---");
        System.out.print("Digite o nome do Categoria: ");
        String nome = scanner.nextLine(); // Lê o nome da categoria
        cCategoria novaCategoria = new cCategoria(nome); // Cria um novo objeto cCategoria
        categoriaDao.adicionarCategoria(novaCategoria); // Adiciona a categoria usando o DAO
        System.out.println("Categoria " + novaCategoria.toString().toUpperCase() + " adicionado com sucesso!");
    }

    // listarCategorias: Busca e exibe todas as categorias cadastradas
    private static void listarCategorias() throws Exception {
        System.out.println("\n--- Listar Categorias ---");
        List<cCategoria> categorias = categoriaDao.listarCategoria(); // Obtém a lista de categorias do DAO
        if (categorias.isEmpty()) { // Verifica se a lista está vazia
            System.out.println("Nenhum categoria encontrado.");
        } else {
            categorias.forEach(System.out::println); // Exibe cada categoria
        }
    }

    // buscarCategoriaPorId: Solicita um ID e busca uma categoria específica
    private static void buscarCategoriaPorId() throws Exception {
        System.out.println("\n--- Buscar Categoria com ID ---");
        System.out.println("Digite o ID do Categoria: ");
        int id = lerOpcao(); // Lê o ID da categoria

        cCategoria categoria = categoriaDao.buscarCategoria(id); // Busca a categoria pelo ID
        if (categoria != null) { // Se a categoria for encontrada
            System.out.println("Categoria encontrada:\n" + categoria.toString()); // Exibe os dados da categoria
        } else {
            System.out.println("Categoria com ID " + id + " não encontrada.");
        }
    }

    // atualizarCategoria: Permite ao usuário atualizar o nome de uma categoria existente
    private static void atualizarCategoria() throws Exception {
        System.out.println("\n--- Atualizar Categoria ---");
        System.out.print("Digite o ID do Categoria: ");
        int id = lerOpcao(); // Lê o ID da categoria a ser atualizada

        cCategoria categoria = categoriaDao.buscarCategoria(id); // Busca a categoria
        if (categoria == null) { // Se não encontrar, informa e sai
            System.out.println("Nenhum categoria encontrado.");
            return;
        }
        System.out.println("Categoria atual:\n" + categoria.toString()); // Mostra os dados atuais da categoria
        System.out.println("Novo nome (" + categoria.getNome() + "): ");
        String novoNome = scanner.nextLine(); // Lê o novo nome

        if (!novoNome.isEmpty()) { // Se o novo nome não estiver vazio, atualiza
            categoria.setNome(novoNome);
        }
        categoriaDao.atualizarCategoria(categoria); // Atualiza a categoria no banco de dados
        System.out.println("Categoria atualizada com sucesso.");
    }

    // deletarCategoria: Solicita um ID e exclui uma categoria após confirmação
    private static void deletarCategoria() throws Exception {
        System.out.println("\n--- Deletar Categoria ---");
        System.out.print("Digite o ID do Categoria: ");
        int id = lerOpcao(); // Lê o ID da categoria a ser deletada

        cCategoria categoria = categoriaDao.buscarCategoria(id); // Busca a categoria para confirmação
        if (categoria != null) { // Se a categoria for encontrada
            System.out.println("Deletar a categoria " + categoria.getNome() + "?\n[S/N]: ");
            String confirmacao = scanner.nextLine().toUpperCase(); // Lê a confirmação
            if (confirmacao.equals("S")) { // Se a confirmação for 'S'
                categoriaDao.removerCategoria(id); // Remove a categoria usando o DAO
                System.out.println("Categoria removida com sucesso.");
            } else {
                System.out.println("Operação cancelada.");
            }
        } else {
            System.out.println("Nenhum categoria encontrado."); // Se a categoria não for encontrada
        }
    }

//    Gerenciamento de Tarefas

    private static void menuTarefas() throws Exception {
        int opcao;

        // Loop para o menu de gerenciamento de tarefas
        do {
            System.out.println("\n--- Menu Tarefas ---");
            System.out.println("1 - Adicionar Tarefa");
            System.out.println("2 - Listar Tarefa");
            System.out.println("3 - Buscar Tarefa por ID");
            System.out.println("4 - Atualizar Tarefa");
            System.out.println("5 - Deletar Tarefa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao(); // Lê a opção do usuário

            // Redireciona para a função CRUD de tarefas de acordo com a opção
            switch (opcao) {
                case 1:
                    adicionarTarefa(); // Adiciona uma nova tarefa
                    break;

                case 2:
                    listarTarefas(); // Lista todas as tarefas
                    break;

                case 3:
                    buscarTarefaPorId(); // Busca uma tarefa por ID
                    break;

                case 4:
                    atualizarTarefa(); // Atualiza uma tarefa existente
                    break;

                case 5:
                    deletarTarefa(); // Deleta uma tarefa
                    break;

                case 0:
                    System.out.println("Voltando ao Menu Principal");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 0); // O loop continua até que o usuário digite '0' para voltar
    }

    // adicionarTarefa: Solicita dados da tarefa (incluindo associações com colaborador e categoria) e a adiciona no banco
    private static void adicionarTarefa() throws Exception {
        System.out.println("\n--- Adicionar Tarefa ---");
        System.out.print("Digite o nome do Tarefa: ");
        String nome = scanner.nextLine(); // Lê o título/nome da tarefa

        System.out.println("Descrição (opcional): ");
        String descricao = scanner.nextLine(); // Lê a descrição (opcional)
        if (descricao.isEmpty()) {
            descricao = null; // Se vazia, define como null para o banco de dados
        }

        // Leitura e validação da data de vencimento
        LocalDate dataVencimento = null;
        boolean dataValida = false;
        while (!dataValida) {
            System.out.println("Data de vencimento (AAAA-MM-DD, opcional): )");
            String dataString = scanner.nextLine();
            if (dataString.isEmpty()) { // Se a data for opcional e o campo vazio
                dataValida = true; // Considera como válida
            } else {
                try {
                    dataVencimento = LocalDate.parse(dataString); // Tenta converter a string para LocalDate
                    dataValida = true; // Se converter, a data é válida
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Por favor, use AAAA-MM-DD."); // Erro de formato
                }
            }
        }
        // Leitura e validação do status da tarefa
        eStatusTarefa status = null;
        while (status == null) {
            System.out.print("Status (PENDENTE, EM_ANDAMENTO, CONCLUIDA): ");
            String statusString = scanner.nextLine().toUpperCase(); // Lê o status e converte para maiúsculas

            try {
                status = eStatusTarefa.valueOf(statusString); // Tenta converter para o enum
            } catch (IllegalArgumentException e) {
                System.out.println("Status inválido! Por favor, use PENDENTE, EM_ANDAMENTO ou CONCLUIDA.");
            }
        }

        // Associação com Colaborador (opcional)
        cColaborador colaborador = null;
        System.out.print("Digite o ID do Colaborador Responsável (0 para nenhum): ");
        int colaboradorId = lerOpcao(); // Lê o ID do colaborador
        if (colaboradorId != 0) { // Se o ID for diferente de 0, tenta buscar o colaborador
            colaborador = colaboradorDao.buscarPorId(colaboradorId);
            if (colaborador == null) {
                System.out.println("Colaborador com ID " + colaboradorId + " não encontrado. A tarefa será criada sem colaborador.");
            }
        }

        // Associação com Categoria (opcional)
        cCategoria categoria = null;
        System.out.print("ID da categoria (0 para nenhuma): ");
        int categoriaId = lerOpcao(); // Lê o ID da categoria
        if (categoriaId != 0) { // Se o ID for diferente de 0, tenta buscar a categoria
            categoria = categoriaDao.buscarCategoria(categoriaId);
            if (categoria == null) {
                System.out.println("Categoria com ID " + categoriaId + " não encontrada. A tarefa será criada sem categoria.");
            }
        }

        // Cria a nova tarefa com todos os dados
        cTarefa novaTarefa = new cTarefa(nome, descricao, dataVencimento, status, colaborador, categoria);
        tarefaDao.adicionarTarefa(novaTarefa); // Adiciona a tarefa no banco de dados
        System.out.println("Tarefa adicionada com sucesso!");
    }

    // listarTarefas: Busca e exibe todas as tarefas cadastradas
    private static void listarTarefas() throws Exception {
        System.out.println("\n--- Listar Tarefas ---");
        List<cTarefa> tarefas = tarefaDao.listarTarefas(); // Obtém a lista de tarefas do DAO
        if (tarefas.isEmpty()) { // Verifica se a lista está vazia
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            tarefas.forEach(System.out::println); // Exibe cada tarefa
        }
    }

    // buscarTarefaPorId: Solicita um ID e busca uma tarefa específica
    private static void buscarTarefaPorId() throws Exception {
        System.out.println("\n--- Buscar Tarefa por ID ---");
        System.out.print("Digite o ID da tarefa para busca: ");
        int id = lerOpcao(); // Lê o ID da tarefa a ser buscada

        cTarefa tarefa = tarefaDao.buscarTarefaPorId(id); // Busca a tarefa pelo ID
        if (tarefa != null) { // Se a tarefa for encontrada
            System.out.println("Tarefa encontrada com sucesso! " + tarefa); // Exibe a tarefa
        } else {
            System.out.println("Nenhuma tarefa cadastrada.");
        }
    }

    // atualizarTarefa: Permite ao usuário atualizar os dados de uma tarefa existente
    private static void atualizarTarefa() throws Exception {
        System.out.println("\n--- Atualizar Tarefa ---");
        System.out.print("Digite o ID da tarefa a ser atualizada: ");
        int id = lerOpcao(); // Lê o ID da tarefa a ser atualizada

        cTarefa tarefa = tarefaDao.buscarTarefaPorId(id); // Busca a tarefa
        if (tarefa == null) { // Se não encontrar, informa e sai
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        System.out.println("Tarefa atual: " + tarefa); // Mostra os dados atuais da tarefa

        System.out.println("Novo titulo (" + tarefa.getTitulo() + "): ");
        String novoTitulo = scanner.nextLine(); // Lê o novo título
        if (!novoTitulo.isEmpty()) { // Se não estiver vazio, atualiza o título
            tarefa.setTitulo(novoTitulo);
        }
        System.out.print("Nova Descrição (" + (tarefa.getDescricao() != null ? tarefa.getDescricao() : "N/A") + ") (opcional): ");
        String novaDescricao = scanner.nextLine(); // Lê a nova descrição
        if (!novaDescricao.isEmpty()) { // Se não estiver vazia, atualiza a descrição
            tarefa.setDescricao(novaDescricao);
        } else if (tarefa.getDescricao() != null) {
            // Se o usuário deixar em branco, mas a tarefa já tinha descrição, mantém a descrição existente.
        } else {
            tarefa.setDescricao(null); // Se o usuário deixar em branco e não tinha descrição, define como null.
        }

        // Leitura e validação da nova data de vencimento
        LocalDate novaDataVencimento = null;
        boolean dataValida = false;
        while (!dataValida) {
            System.out.print("Nova data de Vencimento ( " + (tarefa.getDataVencimento() != null ? tarefa.getDataVencimento() : "N/A") + "): ");
            String novaDataString = scanner.nextLine();
            if (novaDataString.isEmpty()) { // Se o campo for vazio, mantém a data atual
                novaDataVencimento = tarefa.getDataVencimento();
                dataValida = true;
            } else {
                try {
                    novaDataVencimento = LocalDate.parse(novaDataString); // Tenta converter
                    dataValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Por favor, use AAAA-MM-DD.");
                }
            }
        }
        tarefa.setDataVencimento(novaDataVencimento); // Define a nova data de vencimento

        // Atualização do status da tarefa, com validação
        eStatusTarefa novoStatus = null;
        String statusAtual = tarefa.getStatus().name(); // Obtém o nome do status atual como string
        while (novoStatus == null) {
            System.out.print("Novo Status (" + statusAtual + ") (PENDENTE, EM_ANDAMENTO, CONCLUIDA): ");
            String novoStatusString = scanner.nextLine().toUpperCase();
            if (novoStatusString.isEmpty()) { // Se o campo for vazio, mantém o status atual
                novoStatus = eStatusTarefa.valueOf(statusAtual);
            } else {
                try {
                    novoStatus = eStatusTarefa.valueOf(novoStatusString); // Tenta converter para enum
                } catch (IllegalArgumentException e) {
                    System.out.println("Status inválido. Por favor, digite PENDENTE, EM_ANDAMENTO, CONCLUIDA ou deixe em branco para manter o atual.");
                }
            }
            tarefa.setStatus(novoStatus); // Define o novo status na tarefa

            // Atualização do Colaborador Responsável (opcional)
            System.out.print("Novo ID do Colaborador Responsável (" + (tarefa.getColaborador() != null ? tarefa.getColaborador().getId() : "N/A") + ") (0 para nenhum, ou novo ID): ");
            int novoColaboradorId = lerOpcao();
            if (novoColaboradorId != 0) {
                cColaborador novoColaborador = colaboradorDao.buscarPorId(novoColaboradorId);
                if (novoColaborador != null) {
                    tarefa.setColaborador(novoColaborador); // Associa o novo colaborador
                } else {
                    System.out.println("Colaborador com ID " + novoColaboradorId + " não encontrado. Colaborador não alterado.");
                }
            } else {
                tarefa.setColaborador(null); // Desassocia o colaborador (define como null)
            }

            // Atualização da Categoria (opcional)
            System.out.print("Novo ID da Categoria (" + (tarefa.getCategoria() != null ? tarefa.getCategoria().getId() : "N/A") + ") (0 para nenhuma, ou novo ID): ");
            int novaCategoriaId = lerOpcao();
            if (novaCategoriaId != 0) {
                cCategoria novaCategoria = categoriaDao.buscarCategoria(novaCategoriaId);
                if (novaCategoria != null) {
                    tarefa.setCategoria(novaCategoria); // Associa a nova categoria
                } else {
                    System.out.println("Categoria com ID " + novaCategoriaId + " não encontrada. Categoria não alterada.");
                }
            } else {
                tarefa.setCategoria(null); // Desassocia a categoria (define como null)
            }

            tarefaDao.atualizarTarefa(tarefa); // Atualiza a tarefa no banco de dados
            System.out.println("Tarefa atualizada com sucesso! " + tarefaDao.buscarTarefaPorId(id));
        }
    }

    // deletarTarefa: Solicita um ID e exclui uma tarefa após confirmação
    private static void deletarTarefa() throws Exception {
        System.out.println("\n--- Deletar Tarefa ---");
        System.out.print("Digite o ID da tarefa a ser deletada: ");
        int id = lerOpcao(); // Lê o ID da tarefa a ser deletada

        cTarefa tarefa = tarefaDao.buscarTarefaPorId(id); // Busca a tarefa para confirmação
        if (tarefa != null) { // Se a tarefa for encontrada
            System.out.print("Tem certeza que deseja deletar a tarefa \"" + tarefa.getTitulo() + "\" (ID: " + id + ")? (S/N): ");
            String confirmacao = scanner.nextLine().toUpperCase(); // Lê a confirmação
            if (confirmacao.equals("S")) { // Se a confirmação for 'S'
                tarefaDao.deletarTarefa(id); // Deleta a tarefa usando o DAO
                System.out.println("Tarefa deletada com sucesso!");
            } else {
                System.out.println("Operação cancelada."); // Se não confirmar, cancela
            }
        } else {
            System.out.println("Tarefa com ID " + id + " não encontrada."); // Se a tarefa não for encontrada
        }
    }
}