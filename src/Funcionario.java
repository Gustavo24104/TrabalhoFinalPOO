import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

public class Funcionario extends Pessoa implements Logavel, Serializable {
    private String sexo, carteiraTrabalho, RG, cargo;
    private double salario;
    private Calendar anoDeIngresso;
    private String senha;

    private Agencia trabalho;
    private boolean logado = false; //Usada para indicar se podemos acessar o menu ou nao

    public Funcionario(double salarioBase, String RG, String cargo, String carteiraTrabalho,
                       String sexo, Calendar anoDeIngresso, String name, String CPF,
                       String scolarship, String civil, Calendar data, Endereco address, String senha, Agencia trabalho) {
        super(name, CPF, scolarship, civil, data, address);
        this.RG = RG;
        this.cargo = cargo;
        this.carteiraTrabalho = carteiraTrabalho;
        this.sexo = sexo;
        this.anoDeIngresso = anoDeIngresso;
        this.senha = senha;
        this.trabalho = trabalho;
        CalcularSalario(salarioBase);
    }

    //Construtor vazio
    public Funcionario() {}

    public Funcionario(Funcionario f) {
        super(f);
        this.anoDeIngresso = f.anoDeIngresso;
        this.dataNascimento = f.dataNascimento;
        this.senha = f.senha;
        this.carteiraTrabalho = f.carteiraTrabalho;
        this.RG = f.RG;
        this.salario = f.salario;
        this.sexo = f.sexo;
        this.cargo = f.cargo;
        this.trabalho = f.trabalho;
        this.logado = f.logado;
    }

    public void LerDoTeclado(Agencia ag) {
        this.trabalho = ag; // Agencia onde ele trabalha
        super.LerDoTeclado();
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite sexo: ");
        sexo = sc.nextLine();

        System.out.println("Digite cargo do funcionário: ");
        cargo = sc.nextLine();

        System.out.println("Digite RG: ");
        RG = sc.nextLine();

        System.out.println("Digite carteira de trabalho: ");
        carteiraTrabalho = sc.nextLine();

        System.out.println("Digite senha: ");
        senha = sc.nextLine();
        while(senha.length() < 6) {
            System.out.println("Senha deve ter ao menos 6 caracteres!");
            System.out.println("Digite senha: ");
            senha = sc.nextLine();
        }

        System.out.println("Digite data de entrada do funcionário: ");
        anoDeIngresso = Banco.CriarData();

        while(true) {
            try {
                System.out.println("Digite sálario base: ");
                String aux = sc.nextLine();
                double base = Double.parseDouble(aux);
                CalcularSalario(base);
                break;
            } catch (Exception e) {
                System.out.println("Número digitado inválido! Tente novamente");
            }
        }
    }


    public void Login(String usuario, String senha) {
        try {
            ValidaSenha(senha);
            if(usuario.equals(this.getCpf().toLowerCase())) {
                logado = true;
                Menu();
            }
        } catch (Exception e) {
            System.out.println("Senha errada!");
        }
    }

    @Override
    public String toString() {
         return super.toString() +
                "\nSexo: " + sexo +
                "\nCarteira de Trabalho: " + carteiraTrabalho +
                "\nRG: " + RG +
                "\nCargo: " + cargo +
                "\nSalario: " + String.format("%.4f", salario)+
                "\nFuncionário desde: " + anoDeIngresso.get(Calendar.DAY_OF_MONTH) + "/"
                 + anoDeIngresso.get(Calendar.MONTH) + "/" + anoDeIngresso.get(Calendar.YEAR);
    }

    //4,734e+11 <- 15 anos em milisegundos
    public void CalcularSalario(double sal) {
        Calendar agora = Calendar.getInstance();
        agora.add(Calendar.YEAR, -15);
        if(anoDeIngresso.before(agora)) {
            //funcionario a mais de 15 anos
            salario = sal + (1.10*sal);
            return;
        }
        salario = sal;
    }

    public void ValidaSenha(String senha) throws SenhaInvalidaException {
        if(!this.senha.equals(senha)) {
            throw new SenhaInvalidaException("Senha invalida!\n");
        }
    }


    public void Menu() {
        if(!logado) {
            System.out.println("Usuário nao logado!");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Olá, funcionário " + getNome() + "!");
        int escolha = 0;
        Conta gerenciada = null;
        while(escolha != -1) {
            System.out.println("""
                    Faça sua seleção:
                    1 - Cadastrar novo cliente
                    2 - Cadastrar nova conta
                    3 - Gerenciar conta
                    4 - Mostrar Informações de um Cliente
                    5 - Ver Clientes
                    6 - Trocar senha
                    7 - Trocar senha de cliente
                    -1 - Voltar""");
            escolha = sc.nextInt();
            switch (escolha){
                case 1: {
                    Cliente novo = new Cliente(trabalho);
                    novo.LerDoTeclado(trabalho);
                    trabalho.CadastraCliente(novo);
                    System.out.println("Cliente cadastrado com sucesso!");
                    continue;
                }
                case 2: {
                    sc.nextLine();
                    System.out.print("Digite CPF do cliente: ");
                    String cpfCliente = sc.nextLine();
                    Cliente clienteEncontrado = trabalho.EncontraCliente(cpfCliente);
                    if (clienteEncontrado == null) {
                        System.out.println("Cliente de CPF " + cpfCliente + " não encontrado!");
                        continue;
                    }

                    int tipoConta;
                    //System.out.print("Opção: ");
                    while (true) {
                        System.out.println("Selecione tipo de conta:" +
                                "\n1 - Corrente" +
                                "\n2 - Poupança" +
                                "\n3 - Salário");
                        tipoConta = sc.nextInt();
                        sc.nextLine();
                        if(tipoConta > 3 || tipoConta < 1) {
                            System.out.println("Tipo inválido!");
                            continue;
                        }
                        break;
                    }
                    //sc.nextLine();

                    System.out.print("Digite senha da conta: ");
                    String senhaConta = sc.nextLine();
                    Calendar dataAbertura = Calendar.getInstance();

                    Conta novaConta = null;

                    switch (tipoConta) {
                        case 1:
                            System.out.print("Digite limite do cheque especial: ");
                            double limCheq = sc.nextDouble();
                            sc.nextLine();

                            System.out.print("Digite valor da taxa administrativa: ");
                            double taxaAdm = sc.nextDouble();
                            sc.nextLine();

                            novaConta = new ContaCorrente(trabalho.GerarNumeroDeConta(clienteEncontrado), senhaConta, 0.0,
                                    dataAbertura, dataAbertura, true, clienteEncontrado, trabalho,
                                    limCheq, taxaAdm);
                            break;

                        case 2:
                            System.out.print("Digite rendimento mensal: ");
                            double rendimento = sc.nextDouble();
                            sc.nextLine();
                            novaConta = new ContaPoupanca(trabalho.GerarNumeroDeConta(clienteEncontrado), senhaConta, 0.0,
                                    dataAbertura, dataAbertura, true, clienteEncontrado, trabalho,
                                    rendimento);
                            break;

                        case 3:
                            System.out.print("Digite limite de saque: ");
                            double limSaque = sc.nextDouble();
                            sc.nextLine();

                            System.out.print("Digite limite de transferência: ");
                            double limTransf = sc.nextDouble();
                            sc.nextLine();

                            novaConta = new ContaSalario(trabalho.GerarNumeroDeConta(clienteEncontrado), senhaConta, 0.0,
                                    dataAbertura, dataAbertura, true, clienteEncontrado, trabalho,
                                    limSaque, limTransf);
                            break;
                    }

                    while (true) {
                        System.out.println("Essa conta eh conjunta? (true/false)");
                        String aux = sc.nextLine();
                        if (aux.equalsIgnoreCase("false")) {
                            break;
                        }
                        else if (aux.equalsIgnoreCase("true")) {
                            System.out.println("Digite cpf da outra conta: ");
                            String outroCpf = sc.nextLine();
                            Cliente outro = trabalho.EncontraCliente(outroCpf);
                            if (outro == null) {
                                System.out.println("cpf " + outroCpf +  "não encontrado!");
                                continue;
                            }
                            outro.NovaConta(novaConta);
                            break;
                        }
                        else {
                            System.out.println("Opção invalida!");
                        }
                    }

                    clienteEncontrado.NovaConta(novaConta);
                    System.out.println("Conta criada com sucesso: " + novaConta.getNroConta());
                    break;
                }
                case 3: {
                    sc.nextLine();
                    System.out.println("Digite cpf do dono da conta: ");
                    String cpfDono = sc.nextLine();
                    Cliente c = trabalho.EncontraCliente(cpfDono);
                    if(c == null) {
                        System.out.println("Cliente de cpf " + cpfDono + " não encontrado!");
                        continue;
                    }
                    System.out.println("Digite número da conta");
                    String nro = sc.nextLine();
                    gerenciada = c.EncontraConta(nro);
                    if(gerenciada == null) {
                        System.out.println("Conta de número " + nro + " não encontrado!");
                        continue;
                    }
                    MenuGerenciaConta(gerenciada);
                    continue;
                }
                case 4: {
                    sc.nextLine();
                    System.out.print("Digite CPF do cliente: ");
                    String cpf = sc.nextLine();
                    Cliente c = trabalho.EncontraCliente(cpf);
                    if (c == null) {
                        System.out.println("Cliente não encontrado.");
                        break;
                    }

                    c.MostrarInfos();
                    break;
                }
                case 5: {
                    if (trabalho.getClientes() != null && !trabalho.getClientes().isEmpty()) {
                        System.out.printf("%-20s | %-15s | %-15s | %-12s | %-5s%n",
                                "NOME", "CPF", "TIPO CONTA", "Nº CONTA", "ATIVA");

                        for (Cliente cliente : trabalho.getClientes().values()) {
                            Map<String, Conta> contas = cliente.getContas();

                            if (contas == null || contas.isEmpty()) {
                                // Cliente sem contas
                                System.out.printf("%-20s | %-15s | %-15s | %-12s | %-5s%n",
                                        cliente.getNome(), cliente.getCpf(), "-", "-", "-");
                            } else {
                                // Cliente com uma ou mais contas
                                for (Conta conta : contas.values()) {
                                    String ativa = conta.isEstaAtiva() ? "Sim" : "Não";
                                    System.out.printf("%-20s | %-15s | %-15s | %-12s | %-5s%n",
                                            cliente.getNome(), cliente.getCpf(),
                                            conta.getTipoConta(), conta.getNroConta(), ativa);
                                }
                            }
                        }
                        System.out.println();
                    } else {
                        System.out.println("Não tem clientes cadastrados na agência.");
                    }
                    break;
                }
                case 6: {
                    sc.nextLine();
                    System.out.println("Digite nova senha: ");
                    String nova = sc.nextLine();
                    if(nova.length() < 6) {
                        System.out.println("Senha deve ter ao menos 6 caracteres!\n");
                        continue;
                    }
                    senha = nova;
                    System.out.println("Senha modificada!");
                    continue;
                }
                case 7: {
                    sc.nextLine();
                    System.out.println("Digite cpf do cliente");
                    String aux = sc.nextLine();
                    Cliente c  = trabalho.EncontraCliente(aux);
                    if(c == null) {
                        System.out.println("Cliente nao encontrado!");
                        continue;
                    }

                    System.out.println("Nova senha do cliente: ");
                    aux = sc.nextLine();

                    if(aux.length() < 6) {
                        System.out.println("Senha deve ter 6 ou mais caracteres!");
                        continue;
                    }

                    c.setSenha(aux);
                    System.out.println("Senha alterada!");
                }
                case -1: {
                    break;
                }
                default:{
                    System.out.println("Opção inválida!");
                    continue;
                }
            }
        }
        logado = false;
    }

    private void MenuGerenciaConta(Conta gerenciada) {
        Scanner sc = new Scanner(System.in);
        int escolha = 0;
        while(escolha != -1) {
            System.out.println("""
                    Faça sua seleção
                    1 - Alterar limite de cheque especial
                    2 - Alterar limite de saque
                    3 - Deixar conta ativa/inativa
                    4 - Desbloquear conta
                    5 - Trocar senhas
                    -1 - Voltar""");
            escolha = sc.nextInt();

            switch (escolha) {
                case 1: {
                    if (gerenciada instanceof ContaCorrente) {
                        System.out.print("Novo limite de cheque especial: ");
                        double novoLimite = sc.nextDouble(); sc.nextLine();
                        ((ContaCorrente) gerenciada).setLimChequeEspecial(novoLimite);
                        System.out.println("Limite atualizado com sucesso!");
                    } else {
                        System.out.println("Esta conta não é Conta Corrente.");
                    }
                    break;
                }
                case 2: {
                    if (gerenciada instanceof ContaSalario) {
                        System.out.print("Novo limite de saque: ");
                        double novoLimite = sc.nextDouble(); sc.nextLine();
                        ((ContaSalario) gerenciada).setLimSaque(novoLimite);
                        System.out.println("Limite de saque atualizado com sucesso!");
                    } else {
                        System.out.println("Esta conta não é Conta Salário.");
                    }
                    break;
                }
                case 3: {
                    gerenciada.setEstaAtiva(!gerenciada.isEstaAtiva());
                    System.out.println("Conta agora está " + (gerenciada.isEstaAtiva() ? "Ativa." : "Inativa."));
                    break;
                }
                case 4: {
                    gerenciada.setEstaBloqueada(false);
                    System.out.println("Conta desbloqueada!");
                    continue;
                }
                case 5: {
                    System.out.println("Digite nova senha: ");
                    String nova = sc.nextLine();
                    gerenciada.setSenha(nova);
                    System.out.println("Senha modificada!");
                }
                case -1:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    //Getters and Setters
    public Calendar getAnoDeIngresso() {return anoDeIngresso;}
    public void setAnoDeIngresso(Calendar anoDeIngresso) {this.anoDeIngresso = anoDeIngresso;}

    public double getSalario() {return salario;}
    public void setSalario(double salario) {this.salario = salario;}

    public String getCargo() {return cargo;}
    public void setCargo(String cargo) {this.cargo = cargo;}

    public String getRG() {return RG;}
    public void setRG(String RG) {this.RG = RG;}

    public String getCarteiraTrabalho() {return carteiraTrabalho;}
    public void setCarteiraTrabalho(String carteiraTrabalho) {this.carteiraTrabalho = carteiraTrabalho;}

    public String getSexo() {return sexo;}
    public void setSexo(String sexo) {this.sexo = sexo;}

    protected boolean isLogado() {return logado;}
    protected void setLogado(boolean valor) {logado = valor;}

    public Agencia getTrabalho() {return trabalho;}
    public void setTrabalho(Agencia trabalho) {this.trabalho = trabalho;}

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
