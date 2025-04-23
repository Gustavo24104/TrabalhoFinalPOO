import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


/*
Minha ideia pro menu: A gente faz o menu como se fosse um gerenciador mesmo, então tem como cadastrar agencias,
clientes, adicionar pessoas etc.
Faz menus separados para gerenciador de contas (adicionar ou remover) e o menu da conta em si
O menu da conta em si contém as opções para realizar as transações especificadas no problema (sempre requisitando a senha)



Outra coisa: o que vai ser a interface ???????????? Pensei em talvez fazer algo como "validação" (ai teria cpf ou senha)
mas nao faz mto sentido em acho...
 */

public class Banco {
    static ArrayList<Agencia> agencias;
    static ArrayList<Cliente> clientes;
    static ArrayList<Funcionario> funcionarios;
    static ArrayList<Gerente> gerentes;


    public static void main(String[] args) {
        MenuLogin();
    }


    //TODO: Acho que vale a pena trocar isso tudo pra busca binária
    public static Cliente AchaCliente(String nome) {
        for(Cliente c : clientes) {
            if(c.getNome().equals(nome)) return c;
        }
        return null;
    }


    public static Funcionario AchaFuncionario(String nome) {
        for(Funcionario f : funcionarios) {
            if(f.getNome().equals(nome)) return f;
        }
        return null;
    }

    public static Gerente AchaGerente(String nome) {
        for(Gerente g : gerentes) {
            if(g.getNome().equals(nome)) return g;
        }
        return null;
    }

    public static Conta AchaConta(String nro, Cliente dono) {
        for(Conta c : dono.getContas()) {
            if(c.getNroConta().equals(nro)) return c;
        }
        return null;
    }

    public static Agencia AchaAgencia(String nome) {
        for(Agencia a : agencias) {
            if(a.getNomeFicticio().equals(nome)) return a;
        }
        return null;
    }


    public static void SalvarESair() {
        //TODO: Implementar função!
        //So sai percorrendo todas as arrays que tem aqui e salva eles em arquivo, eh tudo serializable.
    }

    public static void CarregarDados() {
        //TODO: Implementar função!
    }





    public static void MenuLogin() {
        Scanner scan = new Scanner(System.in);
        boolean verificador = false;
        String nome, senha;
        int escInit, escFunc;
        escInit = escFunc = 0;
        Logavel l;

        while (escInit != -1){
            System.out.println("(1) Entrar como funcionário\n" +
                    "(2) Entrar como cliente\n" +
                    "(-1) Fechar programa");
            escInit = scan.nextInt();

            switch (escInit){
                case 1: {
                    while (escFunc != -1){
                        System.out.println("(1) Entrar como gerente\n" +
                                "(2) Entrar como funcionário normal\n" +
                                "(-1) Voltar");
                        escFunc = scan.nextInt();
                        switch (escFunc){
                            default: {
                                System.out.println("Inválido! Tente novamente!");
                                continue;
                            }

                            case 1: {
                                while (!verificador){
                                    System.out.println("Digite seu nome e sua senha:");
                                    nome = scan.nextLine();
                                    senha = scan.nextLine();
                                    l = AchaGerente(nome);
                                    if(l == null) {
                                        System.out.println("Nao encontrado!");
                                        continue;
                                    }
                                    verificador = l.Login(nome, senha);
                                    if (verificador) {
                                        MenuGerente((Gerente) l);
                                    }
                                    else System.out.println("Nome e/ou senha incorretos! Tente novamente!");
                                }
                                continue;
                            }

                            case 2: {
                                while (!verificador){
                                    System.out.println("Digite seu nome e sua senha:");
                                    nome = scan.nextLine();
                                    senha = scan.nextLine();
                                    l  = AchaFuncionario(nome);
                                    if(l == null) {
                                        System.out.println("Nao encontrado!");
                                        continue;
                                    }
                                    verificador = l.Login(nome, senha);
                                    if (verificador) {
                                        MenuFuncionario((Funcionario) l);
                                    }
                                    else System.out.println("Nome e/ou senha incorretos! Tente novamente!");
                                }
                                continue;
                            }

                            case -1: {
                                System.out.println("Retornando ao menu anterior...");
                                break; //TODO: ??
                            }
                        }
                    }
                }

                case 2: {
                    while (!verificador){
                        System.out.println("Digite seu nome e sua senha:");
                        nome = scan.nextLine();
                        senha = scan.nextLine();
                        l = AchaCliente(nome);
                        if(l == null) {
                            System.out.println("Nao encontrado!");
                            continue;
                        }
                        verificador = l.Login(nome, senha);
                        if (verificador) {
                            MenuCliente((Cliente) l);
                        }
                        else System.out.println("Nome e/ou senha incorretos! Tente novamente!");
                    }
                    continue;

                }

                case -1: {
                    System.out.println("Encerrando...");
                    SalvarESair();
                    continue;
                }
                default: {
                    System.out.println("Inválido! Tente novamente!");
                }
            }
        }
    }


    public static void MenuFuncionario(Funcionario logado) {

        System.out.println("(1) Deixa conta inativa\n" +
                "(2) Verificar saldo da conta de cliente\n" +
                "(3) Desbloquear conta\n" +
                "(4) Verificar salário\n" +
                "(-1) Sair");
    }

    public static void MenuGerente(Gerente logado) {
        int escolha = 0;

        System.out.println("(1) Contrata funcionário\n" +
                "(2) Demite funcionário\n" +
                "(3) Ver clientes cadastrados\n" +
                "(4) Ver funcionários cadastrados\n" +
                "(5) Verificar salário\n" +
                "(-1) Sair");
    }

    public static void MenuCliente(Cliente logado) {

    }

}