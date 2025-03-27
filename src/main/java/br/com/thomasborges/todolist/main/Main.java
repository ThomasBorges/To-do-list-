package br.com.thomasborges.todolist.main;

import br.com.thomasborges.todolist.model.Tarefa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private List<Tarefa> tarefas = new ArrayList<>();

    public static void main(String[] args) {
        Main app = new Main();
        app.exibeMenu();
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1. Adicionar tarefa
                    2. Listar tarefas
                    3. Marcar tarefa com concluida
                    4. Remover tarefa
                    5. Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    adicionarTarefa();
                    break;
                case 2:
                    listarTarefa();
                    break;
//          case 3:
//                marcarComoConcluida();
//          case 4:
//                removerTarefa();
                case 5:
                    System.out.println("Saindo...");
                    opcao = 0;
                    break;
                default:
                    System.out.println("Opção invalida");

            }
        }
    }

    private void adicionarTarefa() {
        System.out.println("Digite o nome da tarefa:");
        String nome = leitura.nextLine();

        System.out.println("Digite a descrição da tarefa (opcional, pressione ENTER para pular):");
        String descricao = leitura.nextLine();

        Tarefa tarefa;
        if (descricao.isEmpty()) {
            tarefa = new Tarefa(nome);
        } else {
            tarefa = new Tarefa(nome, descricao);
        }
        tarefas.add(tarefa);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private void listarTarefa() {
        for (Tarefa tarefa : tarefas) {
            System.out.println("Nome: " + tarefa.getNome());
        }
    }
}
