
import dao.*;
import model.*;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class SistemaAPP {

    private static Scanner scanner = new Scanner(System.in);
    private static ColaboradorDao colaboradorDao = new ColaboradorDao();
    private static CategoriaDao categoriaDao = new CategoriaDao();
    private static TarefaDao tarefaDao = new TarefaDao();

    public static void main(String[] args) throws Exception {

        int opcao;

        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuColaboradores();
                    break;

                case 2:
                    menuCategorias();
                    break;

                case 3:
                    menuTarefas();
                    break;

                case 0:
                    System.out.println("Finalizando o sistema.");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        }

        private static void exibirMenuPrincipal() {
            System.out.println("\n--- Sistema de Gerenciamento de Tarefas ---");
            System.out.println("1 - Gerenciar Colaboradores");
            System.out.println("2 - Gerenciar Categorias");
            System.out.println("3 - Gerenciar Tarefa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
        }

        private static int lerOpcao() {
            while(!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
                System.out.print("Escolha uma opção: ");
            }
            int opcao = scanner.nextInt();
            scanner.nextLine();
            return opcao;
        }

        private static void menuColaboradores() throws Exception {
            int opcao;

            do {
                System.out.println("\n--- Menu de Colaboradores ---");
                System.out.println("1 - Adicionar Colaborador");
                System.out.println("2 - Listar Colaboradores");
                System.out.println("3 - Buscar Colaborador por ID");
                System.out.println("4 - Atualizar Colaborador");
                System.out.println("5 - Excluir Colaborador");
                System.out.println("0 - Voltar ao Menu Principal");
                System.out.print("Escolha: ");
                opcao = lerOpcao();

                switch (opcao) {
                    case 1:
                        adicionarColaborador();
                        break;

                    case 2:
                        listarColaboradores();
                        break;

                    case 3:
                        buscarColaboradorPorId();
                        break;

                    case 4:
                        atualizarColaborador();
                        break;

                    case 5:
                        deletarColaborador();
                        break;

                    case 0:
                        System.out.println("Voltando ao Menu Principal");
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 0);

        }

        private static void adicionarColaborador() throws Exception {
            System.out.println("\n--- Adicionar Colaborador ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            eTipoColaborador tipo = null;
            while (tipo == null) {
                System.out.print("Tipo [Colaborador | Gerente]: ");
                String tipoStr = scanner.nextLine().toUpperCase();

                try {
                    tipo = eTipoColaborador.valueOf(tipoStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo inválido. Digite 'Colaborador | Gerente'.");
                }
            }
            cColaborador novoColaborador = new cColaborador(nome, email, tipo);
            colaboradorDao.adicionar(novoColaborador);
            System.out.println("Colaborador adicionado com sucesso.");
        }

        private static void listarColaboradores() throws Exception {
            System.out.println("\n--- Listar Colaboradores ---");
            List<cColaborador> colaboradores = colaboradorDao.listar();
            if (colaboradores.isEmpty()) {
                System.out.println("Nenhum colaborador encontrado.");
            } else {
               colaboradores.forEach(System.out::println);
            }
        }

        private static void buscarColaboradorPorId() throws Exception {
            System.out.println("\n--- Buscar Colaborador com ID ---");
            System.out.print("Digite o ID do colaborador: ");
            int id = lerOpcao();

            cColaborador colaborador = colaboradorDao.buscarPorId(id);
            if (colaborador != null) {
                System.out.println("Colaborador encontrado.");
                System.out.println(colaborador.toString());
            } else {
                System.out.println("Nenhum colaborador encontrado.");
            }
        }

        private static void atualizarColaborador() throws Exception {
            System.out.println("\n--- Atualizar Colaborador com ID ---");
            System.out.print("Digite o ID do colaborador a ser atualizado: ");
            int id = lerOpcao();

            cColaborador colaborador = colaboradorDao.buscarPorId(id);
            if (colaborador == null) {
                System.out.println("Nenhum colaborador encontrado.");

            }
            System.out.println("Colaborador atual: ");
            System.out.println(colaborador.toString());

            System.out.println("Novo nome (" + colaborador.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.isEmpty()) {
                colaborador.setNome(novoNome);
            }

            System.out.println("Novo email (" + colaborador.getEmail() + "): ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.isEmpty()) {
                colaborador.setEmail(novoEmail);
            }

            eTipoColaborador novoTipo = null;
            String tipoAtual = colaborador.getTipo().name();
            while (novoTipo == null) {
                System.out.println("Novo tipo (" + tipoAtual + ") (Colaborador | Gerente): ) ");
                String novoTipoStr = scanner.nextLine().toUpperCase();
                if (novoTipoStr.isEmpty()) {
                    novoTipo = eTipoColaborador.valueOf(tipoAtual);
                } else {
                    try {
                        novoTipo = eTipoColaborador.valueOf(novoTipoStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo inválido. Digite 'Colaborador | Gerente' ou deixe em " +
                                "branco para manter o tipo atual" +
                                ".");
                    }
                }
            }
            colaborador.setTipo(novoTipo);
            colaboradorDao.atualizar(colaborador);
            System.out.println("Colaborador atualizado com sucesso.");

        }

        private static void deletarColaborador() throws Exception {
            System.out.println("\n--- Deletar Colaborador com ID ---");
            System.out.print("Digite o ID do colaborador a ser deletado: ");
            int id = lerOpcao();

            cColaborador colaborador = colaboradorDao.buscarPorId(id);
            if (colaborador != null) {
                System.out.print("Tem certeza que deseja deletar o colaborador " + colaborador.getNome() +
                        " (ID: " + id + ")? [S/N]: ");
                String confirmacao = scanner.nextLine().toUpperCase();
                if (confirmacao.equals("S")) {
                    colaboradorDao.excluir(id);
                    System.out.println("Colaborador deletado com sucesso.");
                } else {
                    System.out.println("Operação cancelada.");
                }

            } else {
                System.out.println("Nenhum colaborador encontrado.");
            }
    }

//    MENU CATEGORIA
    private static void  menuCategorias () throws Exception {
        int opcao;

        do {
            System.out.println("\n--- Menu de Categorias ---");
            System.out.println("1 - Adicionar Categoria");
            System.out.println("2 - Listar Categorias");
            System.out.println("3 - Buscar Categoria por ID");
            System.out.println("4 - Atualizar Categoria");
            System.out.println("5 - Deletar Categoria");
            System.out.println("0 - Voltar ao Menu Principal");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    adicionarCategoria();
                    break;

                case 2:
                    listarCategorias();
                    break;

                case 3:
                    buscarCategoriaPorId();
                    break;

                case 4:
                    atualizarCategoria();
                    break;

                case 5:
                    deletarCategoria();
                    break;

                case 0:
                    System.out.println("Voltando ao Menu Principal");
                    break;

                default:
                    System.out.println("Opçãon inválida! Tente novamente.");

            }
        } while (opcao != 0);
    }


    private static void adicionarCategoria () throws Exception {
        System.out.println("\n--- Adicionar Categoria ---");
        System.out.print("Digite o nome do Categoria: ");
        String nome = scanner.nextLine();
        cCategoria novaCategoria = new cCategoria(nome);
        categoriaDao.adicionarCategoria(novaCategoria);
        System.out.println("Categoria " + novaCategoria.toString().toUpperCase() + " adicionado com sucesso!");
    }

    private static void listarCategorias () throws Exception {
        System.out.println("\n--- Listar Categorias ---");
        List<cCategoria> categorias = categoriaDao.listarCategoria();
        if (categorias.isEmpty()) {
            System.out.println("Nenhum categoria encontrado.");
        } else {
            categorias.forEach(System.out::println);
        }
    }

    private static void buscarCategoriaPorId () throws Exception {
        System.out.println("\n--- Buscar Categoria com ID ---");
        System.out.println("Digite o ID do Categoria: ");
        int id = lerOpcao();

        cCategoria categoria = categoriaDao.buscarCategoria(id);
        if (categoria != null) {
            System.out.println("Categoria encontrada:\n" + categoria.toString());
        } else {
            System.out.println("Categoria com ID " + id + " não encontrada.");
        }
    }

    private static void atualizarCategoria () throws Exception {
        System.out.println("\n--- Atualizar Categoria ---");
        System.out.print("Digite o ID do Categoria: ");
        int id = lerOpcao();

        cCategoria categoria = categoriaDao.buscarCategoria(id);
        if (categoria == null) {
            System.out.println("Nenhum categoria encontrado.");
            return;
        }
        System.out.println("Categoria atual:\n" + categoria.toString());
        System.out.println("Novo nome (" + categoria.getNome() + "): ");
        String novoNome = scanner.nextLine();

        if (!novoNome.isEmpty()) {
            categoria.setNome(novoNome);
        }
        categoriaDao.atualizarCategoria(categoria);
        System.out.println("Categoria atualizada com sucesso.");
    }

    private static void deletarCategoria () throws Exception {
            System.out.println("\n--- Deletar Categoria ---");
            System.out.print("Digite o ID do Categoria: ");
            int id = lerOpcao();

            cCategoria categoria = categoriaDao.buscarCategoria(id);
            if (categoria != null) {
                System.out.println("Deletar a categoria " + categoria.getNome() + "?\n[S/N]: ");
                String confirmacao = scanner.nextLine().toUpperCase();
                if (confirmacao.equals("S")) {
                    categoriaDao.removerCategoria(id);
                    System.out.println("Categoria removida com sucesso.");
                } else {
                    System.out.println("Operação cancelada.");
                }
            } else {
                System.out.println("Nenhum categoria encontrado.");
            }
    }

//     MENU TAREFAS
    private static void menuTarefas() throws Exception {
        int opcao;

        do {
            System.out.println("\n--- Menu Tarefas ---");
            System.out.println("1 - Adicionar Tarefa");
            System.out.println("2 - Listar Tarefa");
            System.out.println("3 - Buscar Tarefa por ID");
            System.out.println("4 - Atualizar Tarefa");
            System.out.println("5 - Deletar Tarefa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    adicionarTarefa();
                    break;

                case 2:
                    listarTarefas();
                    break;

                case 3:
                    buscarTarefaPorId();
                    break;

                case 4:
                    atualizarTarefa();
                    break;

                case 5:
                    deletarTarefa();
                    break;

                case 0:
                    System.out.println("Voltando ao Menu Principal");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");

            }

        } while (opcao != 0);
    }

    private static void adicionarTarefa () throws Exception {
        System.out.println("\n--- Adicionar Tarefa ---");
        System.out.print("Digite o nome do Tarefa: ");
        String nome = scanner.nextLine();

        System.out.println("Descrição (opcional): ");
        String descricao = scanner.nextLine();
        if(descricao.isEmpty()) {
            descricao = null;
        }

        LocalDate dataVencimento = null;
        boolean dataValida = false;

        while(!dataValida) {
            System.out.println("Data de vencimento (AAAA-MM-DD): )");
            String dataString = scanner.nextLine();
            if(dataString.isEmpty()) {
                dataValida = true;
            } else {
                try {
                    dataVencimento = LocalDate.parse(dataString);
                    dataValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Por favor, use AAAA-MM-DD.");
                }
            }
        }
        eStatusTarefa status = null;
        while(status == null) {
            System.out.print("Status (PENDENTE, EM_ANDAMENTO, CONCLUIDA): ");
            String statusString = scanner.nextLine();

            try {
                status = eStatusTarefa.valueOf(statusString);
            } catch (IllegalArgumentException e) {
                System.out.println("Status inválido! Por favor, use PENDENTE, EM_ANDAMENTO ou CONCLUIDA.");
            }
        }

        cColaborador colaborador = null;
        System.out.print("Digite o ID do Colaborador Responsável (0 para nenhum): ");
        int colaboradorId = lerOpcao();
        if(colaboradorId != 0) {
            colaborador = colaboradorDao.buscarPorId(colaboradorId);

            if (colaborador == null) {
                System.out.println("Colaborador com ID " + colaboradorId + " não encontrado.");
            }
        }

        cCategoria categoria = null;
        System.out.print("ID da categoria (0 para nenhuma): ");
        int categoriaId = lerOpcao();

        if(categoriaId != 0) {
            categoria = categoriaDao.buscarCategoria(categoriaId);
            if (categoria == null) {
                System.out.println("Categoria com ID " + categoriaId + " não encontrada.");
            }
        }

        cTarefa novaTarefa = new cTarefa(nome, descricao, dataVencimento, status, colaborador, categoria);
        tarefaDao.adicionarTarefa(novaTarefa);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private static void listarTarefas () throws Exception {
        System.out.println("\n--- Listar Tarefas ---");
        List<cTarefa> tarefas = tarefaDao.listarTarefas();
        if(tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            tarefas.forEach(System.out::println);
        }
    }

    private static void buscarTarefaPorId () throws Exception {
        System.out.println("\n--- Buscar Tarefa por ID ---");
        System.out.print("Digite o ID da tarefa para busca: ");
        int id = lerOpcao();

        cTarefa tarefa = tarefaDao.buscarTarefaPorId(id);
        if(tarefa != null) {
            System.out.println("Tarefa encontrada com sucesso! " + tarefa);
        } else {
            System.out.println("Nenhuma tarefa cadastrada.");
        }
    }

    private static void atualizarTarefa () throws Exception {
        System.out.println("\n--- Atualizar Tarefa ---");
        System.out.print("Digite o ID da tarefa a ser atualizada: ");
        int id = lerOpcao();

        cTarefa tarefa = tarefaDao.buscarTarefaPorId(id);

        if(tarefa == null) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        System.out.println("Tarefa atual: " + tarefa);

        System.out.println("Novo titulo (" + tarefa.getTitulo() + "): " );
        String novoTitulo = scanner.nextLine();

        if(!novoTitulo.isEmpty()) {
            tarefa.setTitulo(novoTitulo);
        }
        System.out.print("Nova Descrição (" + (tarefa.getDescricao() != null ? tarefa.getDescricao() : "N/A") + ") (opcional): ");
        String novaDescricao = scanner.nextLine();
        if(!novaDescricao.isEmpty()) {
            tarefa.setDescricao(novaDescricao);
        } else if (tarefa.getDescricao() != null) {

        } else {
            tarefa.setDescricao(null);
        }

        LocalDate novaDataVencimento = null;

        boolean dataValida = false;
        while(!dataValida) {
            System.out.print("Nova data de Vencimento ( " + tarefa.getDataVencimento() + "): ");
            String novaDataString = scanner.nextLine();
            if (novaDataString.isEmpty()) {
                novaDataVencimento = tarefa.getDataVencimento();
                dataValida = true;
            } else {
                try {
                    novaDataVencimento = LocalDate.parse(novaDataString);
                    dataValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Por favor, use AAAA-MM-DD.");
                }
            }
        }
        tarefa.setDataVencimento(novaDataVencimento);

        eStatusTarefa novoStatus = null;
        String statusAtual = tarefa.getStatus().name();
        while(novoStatus == null) {
            System.out.print("Novo Status (" + statusAtual + ") (PENDENTE, EM_ANDAMENTO, CONCLUIDA): ");
            String novoStatusString = scanner.nextLine();
            if (novoStatusString.isEmpty()) {
                novoStatus = eStatusTarefa.valueOf(statusAtual);
            } else {
                try {
                    novoStatus = eStatusTarefa.valueOf(novoStatusString);
                } catch (IllegalArgumentException e) {
                    System.out.println("Status inválido. Por favor, digite PENDENTE, EM_ANDAMENTO, CONCLUIDA ou deixe em branco para manter o atual.");
                }
            }
            tarefa.setStatus(novoStatus);


            System.out.print("Novo ID do Colaborador Responsável (" + (tarefa.getColaborador() != null ? tarefa.getColaborador().getId() : "N/A") + ") (0 para nenhum, ou novo ID): ");
            int novoColaboradorId = lerOpcao();
            if (novoColaboradorId != 0) {
                cColaborador novoColaborador = colaboradorDao.buscarPorId(novoColaboradorId);
                if (novoColaborador != null) {
                    tarefa.setColaborador(novoColaborador);
                } else {
                    System.out.println("Colaborador com ID " + novoColaboradorId + " não encontrado. Colaborador não alterado.");
                }
            } else {
                tarefa.setColaborador(null); // Desassociar colaborador
            }


            System.out.print("Novo ID da Categoria (" + (tarefa.getCategoria() != null ? tarefa.getCategoria().getId() : "N/A") + ") (0 para nenhuma, ou novo ID): ");
            int novaCategoriaId = lerOpcao();
            if (novaCategoriaId != 0) {
                cCategoria novaCategoria = categoriaDao.buscarCategoria(novaCategoriaId);
                if (novaCategoria != null) {
                    tarefa.setCategoria(novaCategoria);
                } else {
                    System.out.println("Categoria com ID " + novaCategoriaId + " não encontrada. Categoria não alterada.");
                }
            } else {
                tarefa.setCategoria(null); // Desassociar categoria
            }

            tarefaDao.atualizarTarefa(tarefa);
            System.out.println("Tarefa atualizada com sucesso! " + tarefaDao.buscarTarefaPorId(id));

        }

    }

    private static void deletarTarefa() throws Exception {
        System.out.println("\n--- Deletar Tarefa ---");
        System.out.print("Digite o ID da tarefa a ser deletada: ");
        int id = lerOpcao();

        cTarefa tarefa = tarefaDao.buscarTarefaPorId(id);
        if (tarefa != null) {
            System.out.print("Tem certeza que deseja deletar a tarefa \"" + tarefa.getTitulo() + "\" (ID: " + id + ")? (S/N): ");
            String confirmacao = scanner.nextLine().toUpperCase();
            if (confirmacao.equals("S")) {
                tarefaDao.deletarTarefa(id);
                System.out.println("Tarefa deletada com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }
        } else {
            System.out.println("Tarefa com ID " + id + " não encontrada.");
        }
    }

}
