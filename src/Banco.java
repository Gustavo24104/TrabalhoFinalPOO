import java.io.*;
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
        //Inicialização
        clientes = new ArrayList<>();
        agencias = new ArrayList<>();
        funcionarios = new ArrayList<>();
        gerentes = new ArrayList<>();
        CarregarDados();
        //MenuLogin();
//        Cliente c1 = new Cliente("Teste", "113.336.716-01", "senha123");
//        Cliente c2 = new Cliente("remover", "113.336.716-01", "senhaSegura");
//        clientes.add(c1);
//        clientes.add(c2);




        SalvarESair();
    }


    //TODO: Talvez valha a pena trocar isso tudo pra busca binária
    public static Cliente AchaCliente(String nome) {
        for(Cliente c : clientes) {
            if(c.getNome().equalsIgnoreCase(nome)) return c;
        }
        return null;
    }


    public static Funcionario AchaFuncionario(String nome) {
        for(Funcionario f : funcionarios) {
            if(f.getNome().equalsIgnoreCase(nome)) return f;
        }
        return null;
    }

    public static Gerente AchaGerente(String nome) {
        for(Gerente g : gerentes) {
            if(g.getNome().equalsIgnoreCase(nome)) return g;
        }
        return null;
    }

    public static Conta AchaConta(String nro, Cliente dono) {
        for(Conta c : dono.getContas()) {
            if(c.getNroConta().equalsIgnoreCase(nro)) return c; //teoricamente nao era pra ter diferença de maisculo ne
        }
        return null;
    }

    public static Agencia AchaAgencia(String nome) {
        for(Agencia a : agencias) {
            if(a.getNomeFicticio().equalsIgnoreCase(nome)) return a;
        }
        return null;
    }


    public static void SalvarESair() {
        FileOutputStream fs = null;
        ObjectOutputStream os = null;

        try {
            fs = new FileOutputStream("clientes.sav");
            os = new ObjectOutputStream(fs);
            os.writeObject(clientes);

            fs.close();
            os.close();

            fs = new FileOutputStream("agencias.sav");
            os = new ObjectOutputStream(fs);
            os.writeObject(agencias);

            fs.close();
            os.close();

            fs = new FileOutputStream("funcionarios.sav");
            os = new ObjectOutputStream(fs);
            os.writeObject(funcionarios);

            fs.close();
            os.close();


            fs = new FileOutputStream("gerentes.sav");
            os = new ObjectOutputStream(fs);
            os.writeObject(gerentes);

            fs.close();
            os.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fs != null) fs.close();
                if (os != null) os.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void CarregarDados() {
        FileInputStream fs = null;
        ObjectInputStream os = null;

        try {
            fs = new FileInputStream("clientes.sav");
            os = new ObjectInputStream(fs);
            clientes = (ArrayList<Cliente>) os.readObject();
            fs.close();
            os.close();

            fs = new FileInputStream("agencias.sav");
            os = new ObjectInputStream(fs);
            agencias = (ArrayList<Agencia>) os.readObject();
            fs.close();
            os.close();

            fs = new FileInputStream("funcionarios.sav");
            os = new ObjectInputStream(fs);
            funcionarios = (ArrayList<Funcionario>) os.readObject();
            fs.close();
            os.close();

            fs = new FileInputStream("gerentes.sav");
            os = new ObjectInputStream(fs);
            gerentes = (ArrayList<Gerente>) os.readObject();
            fs.close();
            os.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo de salvamento nao encontrado!" + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Algo deu errado! " + e.getMessage());
        }
        finally {
            try {
                if(os!= null) os.close();
                if(fs != null) fs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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
                            case 1: {
                                while (!verificador){
                                    System.out.println("Digite seu nome e sua senha:");
                                    nome = scan.nextLine();
                                    l = AchaGerente(nome);
                                    if(l == null) {
                                        System.out.println("Usuario " + nome + "Nao encontrado!");
                                        continue;
                                    }
                                    senha = scan.nextLine();
                                    verificador = l.Login(nome, senha);
                                    if (verificador) {
                                        MenuGerente((Gerente) l);
                                    }
                                    else System.out.println("Senha incorreta! Tente novamente!");
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
                                        System.out.println("Usuario " + nome + "Nao encontrado!");
                                        continue;
                                    }
                                    verificador = l.Login(nome, senha);
                                    if (verificador) {
                                        MenuFuncionario((Funcionario) l);
                                    }
                                    else System.out.println("Senha incorreta! Tente novamente!");
                                }
                                continue;
                            }

                            case -1: {
                                System.out.println("Retornando ao menu anterior...");
                                break; //TODO: ??
                            }
                            default: {
                                System.out.println("Inválido! Tente novamente!");
                                continue;
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
                            System.out.println("Usuario " + nome + " nao encontrado!");
                            continue;
                        }
                        verificador = l.Login(nome, senha);
                        if (verificador) {
                            MenuCliente((Cliente) l);
                        }
                        else System.out.println("Senha incorreta! Tente novamente!");
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
        System.out.println("loguei!\n");
    }

}