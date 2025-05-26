
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

    public static void main(String[] args) {

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

        private static void menuColaboradores() {
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

        private static void adicionarColaborador() {
            System.out.println("\n--- Adicionar Colaborador ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            eTipoColaborador tipo = null;
        }

}