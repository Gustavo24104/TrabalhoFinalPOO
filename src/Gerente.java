import java.util.*;

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
    public Gerente() {}

    public Gerente(Funcionario f) {
        super(f);
    }

    public void LerDoTeclado(boolean mudarData) {
        //super.LerDoTeclado(ag);
        Scanner sc = new Scanner(System.in);
        if(mudarData) {
            System.out.println("Digite data de ingresso como gerente: ");
            dataDeIngressoComoGerente = Banco.CriarData();
        }
        else dataDeIngressoComoGerente = Calendar.getInstance();
        while (true) {
            System.out.println("O gerente tem curso? (true/false)");
            String aux = sc.nextLine();
            if (aux.equalsIgnoreCase("false")) temCurso = false;
            else if (aux.equalsIgnoreCase("true")) temCurso = true;
            else {
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
        System.out.println("Olá, gerente " + getNome() + "!");
        while(escolha != -1) {
            System.out.println("""
                    Faça sua seleção:
                    1 - Contratar funcionário
                    2 - Demitir funcionário
                    3 - Trocar salário de funcionário
                    4 - Trocar cargo de funcionário
                    5 - Promover funcionário a gerente
                    6 - Acessar como funcionário (gerenciamento de contas)
                    7 - Ver dados de um funcionário
                    8 - Ver próprios dados
                    9 - Ver funcionários da Agência
                    -1 - Voltar""");
            escolha = sc.nextInt();
            sc.nextLine(); // limpar buffer
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
                    Funcionario funcionario = getTrabalho().EncontraFuncionario(key);
                    if (funcionario == null) {
                        System.out.println("Funcionário não encontrado!");
                        continue;
                    } System.out.println("Funcionário encontrado: " + funcionario.getNome());
                    System.out.println("Tem certeza que deseja demitir este funcionário? (true/false)");
                    String confirmacao = sc.nextLine();
                    if (confirmacao.equalsIgnoreCase("true")) {
                        getTrabalho().DemiteFuncionario(key);
                        System.out.println("Funcionário demitido com sucesso!");
                    } else {
                        System.out.println("Operação cancelada.");
                    }
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
                    continue;
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
                                continue;
                            }
                            Gerente g = new Gerente(novoGerente);
                            g.LerDoTeclado(false);
                            System.out.println("Feito! funcionário " + g.getNome() + " é o novo gerente.");
                            getTrabalho().setGerente(g);
                            System.out.println("É necessário logar novamente! Retornando ao menu anterior");
                            return;
                        }
                        else {
                            System.out.println("Valor inválido!");
                            continue;
                        }
                    }
                    continue;
                }
                case 6: {
                    super.Menu();
                }
                case 7: {
                    System.out.println("Digite cpf do funcionário: ");
                    String chave = sc.nextLine();
                    Funcionario escolhido = getTrabalho().EncontraFuncionario(chave);
                    if(escolhido == null) {
                        System.out.println("funcionário nao encontrado");
                        continue;
                    }
                    System.out.println(escolhido);
                    continue;
                }
                case 8: {
                    System.out.println(this);
                }
                case 9: {
                    TreeMap<String, Funcionario> mapa = getTrabalho().getFuncionariosMap(); // getter que retorna o TreeMap
                    if (mapa.isEmpty()) {
                        System.out.println("Nenhum funcionário cadastrado.");
                    } else {
                        System.out.printf("%-20s | %-10s | %-15s\n", "Nome", "Cargo", "CPF");
                        for (Funcionario f : mapa.values()) {
                            System.out.printf("%-20s | %-10s | %-15s\n", f.getNome(), f.getCargo(), f.getCpf());
                        }
                        System.out.println();
                    }
                    continue;
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

    public String toString() {
        return super.toString() + "\nTornou-se gerente em: " + dataDeIngressoComoGerente.get(Calendar.DAY_OF_MONTH) +
                "/" + dataDeIngressoComoGerente.get(Calendar.MONTH) + "/" +
                dataDeIngressoComoGerente.get(Calendar.YEAR) +
                "\n" + (temCurso ? "Possui curso de gerente" : "Não Possui curso de gerente");
    }

    //Getters and Setters
    public static double getComissao() {
        return comissao;
    }
    public static void setComissao(double comissao) {
        Gerente.comissao = comissao;
    }
}
