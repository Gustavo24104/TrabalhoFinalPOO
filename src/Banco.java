import java.io.*;
import java.util.*;




public class Banco {
    static TreeMap<String,Agencia> agencias; //mapear agencia e numero


    //TODO: Terminar menus (funcionário, gerente, conta e de agencias (tudo o que falta está marcado)
    //TODO: Tratar os problemas de buffer
    //TODO: Testar tudo direitinho (mas acredito que esteja funcionando)
    //TODO: Polir mais o código (organizar métodos e remover get/set/construtores que não estão sendo utilizados)

    public static void main(String[] args) {
        agencias = new TreeMap<>();
        CarregarDados();
        Menu();
    }


    public static Endereco CriarEndereco() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Cep?");
        String cep = sc.nextLine();
//        sc.next();

        System.out.println("Cidade?");
        String cidade = sc.nextLine();
//        sc.next();

        System.out.println("Estado?");
        String estado = sc.nextLine();
//        sc.next();

        System.out.println("Bairro?");
        String bairro = sc.nextLine();
//        sc.next();

        return new Endereco(cidade, estado, bairro, cep);
    }

    public static Calendar CriarData() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Dia (número): ");
        int dia = sc.nextInt();
        //sc.next();

        System.out.println("Mês (número): ");
        int mes = sc.nextInt();
        //sc.next();

        System.out.println("Ano (número): ");
        int ano = sc.nextInt();
        //sc.next();

        return new Calendar.Builder().
                setFields(Calendar.YEAR, ano, Calendar.MONTH, mes, Calendar.DAY_OF_MONTH, dia).build();
    }



    public static void Menu() {
        int escolha = 0;
        Scanner sc = new Scanner(System.in);
        while(escolha != -1) {
            System.out.println("""
                Selecione:
                1 - Listar agencias
                2 - Selecionar agencia
                3 - Cadastrar nova agencia
                4 - Remover agencia
                -1 Salvar e sair""");
            escolha = sc.nextInt();
            sc.nextLine(); //limpar buffer
            switch(escolha) {
                case 1: {
                    if(agencias.isEmpty()) System.out.println("Lista de agencias vazia!");
                    for (Agencia ag : agencias.values()) {
                        System.out.println(ag);
                    }
                    continue;
                }
                case 2: {
                    if(agencias.isEmpty()) {
                        System.out.println("Lista de agencias vazia!");
                        continue;
                    }
                    System.out.println("Digite número da agência que deseja administrar");
                    String nro = sc.nextLine();
                    Agencia ag = agencias.get(nro);
                    if(ag == null) {
                        System.out.println("Agencia de número " + nro + " não encontrada!");
                        continue;
                    }
                    ag.Menu();
                    continue;
                }
                case 3: {
                    //TODO: Implementar! (lembrar de nao permitir agencias com mesmo numero)
                    System.out.println("Nome ficticio da agencia?");
                    String nome = sc.nextLine();
                    String nro;
                    //sc.next();
                    while (true) {
                        System.out.println("Numero da agencia? (6 digitos)");
                        nro = sc.nextLine();
                        if(nro.length() != 6) {
                            System.out.println("Número de digitos incorreto!");
                            continue;
                        }
                        if(agencias.containsKey(nro)) {
                            System.out.println("Ja existe uma agência com esse número! Utilize outro");
                            continue;
                        }
                        break;
                    }
                    System.out.println("Insira o endereço");
                    Endereco end = CriarEndereco();

                    Agencia nova = new Agencia(nome, nro, end);
                    agencias.put(nro, nova);

                    System.out.println("Cadastre o gerente: ");
                    Gerente g = new Gerente();
                    g.LerDoTeclado(nova); //TODO: Mudar isso aqui
                    nova.setGerente(g);
                    nova.CadastraFuncionario(g); //o gerente também eh funcionário
                    continue;
                }
                case 4: {
                    if(agencias.isEmpty()) {
                        System.out.println("Lista de agencias vazia!");
                        continue;
                    }
                    System.out.println("Digite nro da agência que deseja remover");
                    String nro = sc.nextLine();
                    Agencia result = agencias.remove(nro);
                    if(result == null) {
                        System.out.println("Agencia de número " + nro + " não encontrada!");
                        continue;
                    }
                    System.out.println("Agencia de número " + nro + " removida com sucesso!");
                }
                case -1: {
                    SalvarDados();
                    return;
                }
                default: {
                    System.out.println("Escolha invalida!");
                    continue;
                }
            }
        }
    }



    public static void SalvarDados() {
        FileOutputStream fs = null;
        ObjectOutputStream os = null;
        try {
            fs = new FileOutputStream("agencias.sav");
            os = new ObjectOutputStream(fs);
            os.writeObject(agencias);

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
            fs = new FileInputStream("agencias.sav");
            os = new ObjectInputStream(fs);
            agencias = (TreeMap<String, Agencia>) os.readObject();
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




//    public static void MenuLogin() {
//        Scanner scan = new Scanner(System.in);
//        boolean verificador = false;
//        String nome, senha;
//        int escInit, escFunc;
//        escInit = escFunc = 0;
//        Logavel l;
//
//        while (escInit != -1){
//            System.out.println("(1) Entrar como funcionário\n" +
//                    "(2) Entrar como cliente\n" +
//                    "(-1) Fechar programa");
//            escInit = scan.nextInt();
//
//            switch (escInit){
//                case 1: {
//                    while (escFunc != -1){
//                        System.out.println("(1) Entrar como gerente\n" +
//                                "(2) Entrar como funcionário normal\n" +
//                                "(-1) Voltar");
//                        escFunc = scan.nextInt();
//                        switch (escFunc){
//                            case 1: {
//                                while (!verificador){
//                                    System.out.println("Digite seu nome e sua senha:");
//                                    nome = scan.nextLine();
//                                    l = AchaGerente(nome);
//                                    if(l == null) {
//                                        System.out.println("Usuario " + nome + "Nao encontrado!");
//                                        continue;
//                                    }
//                                    senha = scan.nextLine();
//                                    verificador = l.Login(nome, senha);
//                                    if (verificador) {
//                                        MenuGerente((Gerente) l);
//                                    }
//                                    else System.out.println("Senha incorreta! Tente novamente!");
//                                }
//                                continue;
//                            }
//
//                            case 2: {
//                                while (!verificador){
//                                    System.out.println("Digite seu nome e sua senha:");
//                                    nome = scan.nextLine();
//                                    senha = scan.nextLine();
//                                    l  = AchaFuncionario(nome);
//                                    if(l == null) {
//                                        System.out.println("Usuario " + nome + "Nao encontrado!");
//                                        continue;
//                                    }
//                                    verificador = l.Login(nome, senha);
//                                    if (verificador) {
//                                        MenuFuncionario((Funcionario) l);
//                                    }
//                                    else System.out.println("Senha incorreta! Tente novamente!");
//                                }
//                                continue;
//                            }
//
//                            case -1: {
//                                System.out.println("Retornando ao menu anterior...");
//                                break; //TODO: ??
//                            }
//                            default: {
//                                System.out.println("Inválido! Tente novamente!");
//                                continue;
//                            }
//                        }
//                    }
//                }
//
//                case 2: {
//                    while (!verificador){
//                        System.out.println("Digite seu nome e sua senha:");
//                        nome = scan.nextLine();
//                        senha = scan.nextLine();
//                        l = AchaCliente(nome);
//                        if(l == null) {
//                            System.out.println("Usuario " + nome + " nao encontrado!");
//                            continue;
//                        }
//                        verificador = l.Login(nome, senha);
//                        if (verificador) {
//                            MenuCliente((Cliente) l);
//                        }
//                        else System.out.println("Senha incorreta! Tente novamente!");
//                    }
//                    continue;
//
//                }
//
//                case -1: {
//                    System.out.println("Encerrando...");
//                    SalvarESair();
//                    continue;
//                }
//                default: {
//                    System.out.println("Inválido! Tente novamente!");
//                }
//            }
//        }
//    }
//
//
//    public static void MenuFuncionario(Funcionario logado) {
//
//        System.out.println("(1) Deixa conta inativa\n" +
//                "(2) Verificar saldo da conta de cliente\n" +
//                "(3) Desbloquear conta\n" +
//                "(4) Verificar salário\n" +
//                "(-1) Sair");
//    }
//
//    public static void MenuGerente(Gerente logado) {
//        int escolha = 0;
//
//        System.out.println("(1) Contrata funcionário\n" +
//                "(2) Demite funcionário\n" +
//                "(3) Ver clientes cadastrados\n" +
//                "(4) Ver funcionários cadastrados\n" +
//                "(5) Verificar salário\n" +
//                "(-1) Sair");
//    }
//
//    public static void MenuCliente(Cliente logado) {
//        System.out.println("loguei!\n");
//    }

}