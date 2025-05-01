import java.util.Calendar;
import java.util.Scanner;

public class Gerente extends Funcionario {
    private static double comissao;
    private Calendar dataDeIngressoComoGerente;
    private boolean temCurso;

    public Gerente(double salarioBase, String RG, String carteiraTrabalho, String sexo,
                   Calendar anoDeIngresso, Calendar dataDeIngressoComoGerente, Agencia gerenciada, boolean temCurso,
                   String name, String CPF, String scolarship, String civil, Calendar data, Endereco address,
                   String senha) {
        super(salarioBase, RG, "Gerente", carteiraTrabalho, sexo, anoDeIngresso, name, CPF,
                scolarship, civil, data, address, senha, gerenciada);
        this.dataDeIngressoComoGerente = dataDeIngressoComoGerente;
        this.temCurso = temCurso;
        CalcularSalario(salarioBase);
    }

    //Construtor vazio
    public Gerente() {

    }

    public Gerente(Funcionario f, boolean temCurso) {
        super(f);
        this.temCurso = temCurso;
    }



    public void LerDoTeclado(Agencia ag) {
        super.LerDoTeclado(ag);
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite data de ingresso como gerente: ");
        dataDeIngressoComoGerente = Banco.CriarData();
        while (true) {
            System.out.println("O gerente tem curso? (true/false)");
            String aux = sc.nextLine(); //sc.nextBoolean nao funciona, não sei porquê.
            if (aux.equalsIgnoreCase("false")) temCurso = false;
            if (aux.equalsIgnoreCase("true")) temCurso = true;
            else {
                System.out.println("Valor inválido!");
                continue;
            }
            break;
        }
    }

    public void CalcularSalario(double sal) {
        super.CalcularSalario(sal);
        setSalario(getSalario() + comissao);
    }


    public void Menu() {
        if(!isLogado()) {
            System.out.println("Usuário não logado!");
            return;
        }
        int escolha = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Olá, " + getNome() + "!");
        while(escolha != -1) {
            System.out.println("""
                    Faça sua seleção:
                    1 - Contratar funcionário
                    2 - Demitir funcionário
                    3 - Trocar salário de funcionário
                    4 - Trocar cargo de funcionário
                    5 - Promover funcionário a gerente
                    6 - Acessar como funcionário (gerenciamento de contas)
                    -1 - Voltar""");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1: {
                    Funcionario novo = new Funcionario();
                    novo.LerDoTeclado(getTrabalho());
                    getTrabalho().CadastraFuncionario(novo);
                    continue;
                }
                case 2: {
                    System.out.println("Digite cpf do funcionário a ser demitido: ");
                    String key = sc.nextLine();
                    //Talvez colocar uma confirmação aqui
                    getTrabalho().DemiteFuncionario(key);
                    continue;
                }
                case 3: {
                    System.out.println("Digite cpf do funcionário: ");
                    String key = sc.nextLine();
                    Funcionario f = getTrabalho().EncontraFuncionario(key);
                    if(f == null) {
                        System.out.println("Funcionário de cpf " + key + " não encontrado!");
                        continue;
                    }
                    System.out.println("Digite novo salário base do funcionário: ");
                    double novoSal = sc.nextDouble();
                    f.CalcularSalario(novoSal);
                    continue;
                }
                case 4: {
                    System.out.println("Digite cpf do funcionário: ");
                    String key = sc.nextLine();
                    Funcionario f = getTrabalho().EncontraFuncionario(key);
                    if(f == null) {
                        System.out.println("Funcionário de cpf " + key + " não encontrado!");
                        continue;
                    }
                    System.out.println("Digite novo cargo: ");
                    f.setCargo(sc.nextLine());
                    System.out.println("Cargo mudado!");
                }
                case 5: {
                    while (true) {
                        System.out.println("Você tem certeza? Você deixará de ser o gerente dessa agência (true/false)");
                        String aux = sc.nextLine();
                        if (aux.equalsIgnoreCase("false")) break;
                        if (aux.equalsIgnoreCase("true")) {
                            System.out.println("Digite cpf do funcionário");
                            String chave = sc.nextLine();
                            Funcionario novoGerente = getTrabalho().EncontraFuncionario(chave);
                            if(novoGerente == null) {
                                System.out.println("Funcionário nao encontrado!");
                            }

                        }
                        else {
                            System.out.println("Valor inválido!");
                            continue;
                        }
                        break;
                    }
                }
                case 6: {
                    super.Menu();
                }
                case -1: {
                    break;
                }
                default: {
                    System.out.println("Opção inválida!");
                    break;
                }
            }
        }
        setLogado(false);
    }

    //Getters and Setters
    public static double getComissao() {
        return comissao;
    }

    public static void setComissao(double comissao) {
        Gerente.comissao = comissao;
    }

    public String toString() {
        return super.toString() + "Gerente{" +
                "dataDeIngressoComoGerente=" + dataDeIngressoComoGerente +
                ", temCurso=" + temCurso +
                '}';
    }

}
