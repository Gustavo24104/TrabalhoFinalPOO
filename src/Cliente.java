import java.util.Calendar;
import java.util.Scanner;
import java.util.TreeMap;

public class Cliente extends Pessoa implements Logavel, PodeSerLidoDoTeclado {
    private TreeMap<String, Conta> contas;
    private Agencia ag;
    private String senha;
    boolean logado = false;

    public Cliente(String nome, String cpf, String senha) throws CPFInvalidoException {
        this.setNome(nome);
        this.setCpf(cpf);
        this.senha = senha;
        if(!ValidaCPF()) throw new CPFInvalidoException("CPF invalido!"); //tratar
    }

    public Cliente(String nome, String cpf, String escolaridade,
                   String estadoCivil, Calendar dataNascimento, Endereco end, Agencia ag) {
        super(nome, cpf, escolaridade, estadoCivil, dataNascimento, end);
        contas = new TreeMap<>();
        this.ag = ag;
    }

    //Construtor vazio: Lê do teclado
    public Cliente(Agencia ag) {
        contas = new TreeMap<>();
    }

    public void LerDoTeclado(Agencia ag) {
        this.ag = ag;
        super.LerDoTeclado();

        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Digite uma senha: ");
            senha = sc.nextLine();

            if (senha.length() < 6) {
                System.out.println("A senha deve ter ao menos 6 caracteres!");
            }
        } while (senha.length() < 6);
    }

    //Métodos
    public Conta EncontraConta(String nro) {
        return contas.get(nro);
    }

    public void MostrarInfos() {
        System.out.println("=== Cliente ===");
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());

        Calendar dataNasc = getDataNascimento();
        //Data em formato de "String"
        if (dataNasc != null) {
            int dia = dataNasc.get(Calendar.DAY_OF_MONTH);
            int mes = dataNasc.get(Calendar.MONTH) + 1;
            int ano = dataNasc.get(Calendar.YEAR);
            System.out.printf("Data de nascimento: %02d/%02d/%04d\n", dia, mes, ano);
        }

        System.out.println("Estado civil: " + getEstadoCivil());
        System.out.println("Escolaridade: " + getEscolaridade());
        System.out.println("Endereço: " + getEnd());

        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        System.out.println();

        System.out.println("Contas Associadas: ");
        for (Conta c : contas.values()) {
            System.out.println("Número da Conta: " + c.getNroConta());
            System.out.println("Saldo: R$ " + c.getSaldoAtual());
            System.out.println("Ativa: " + c.isEstaAtiva());
            System.out.println("Tipo: " + c.getTipoConta());


            if (c instanceof ContaCorrente cc) {
                System.out.println("Limite Cheque Especial: R$ " + cc.getLimChequeEspecial());
                System.out.println("Taxa Administrativa: R$ " + cc.getValorTaxaAdm());
            } else if (c instanceof ContaPoupanca cp) {
                System.out.println("Rendimento Mensal: " + cp.getRendimentoMes() + "%");
            } else if (c instanceof ContaSalario cs) {
                System.out.println("Limite de Saque: R$ " + cs.getLimSaque());
                System.out.println("Limite de Transferência: R$ " + cs.getLimTransferencia());
            }
            System.out.println();
        }
    }

    public void NovaConta(Conta c) {
        contas.put(c.getNroConta(), c);
    }

    public void ValidaSenha(String senha) throws SenhaInvalidaException {
        if(!this.senha.equals(senha)) {
            throw new SenhaInvalidaException("Senha invalida!\n");
        }
    }

    public void Login(String usuario, String senha) {
        try {
            ValidaSenha(senha);
            if(usuario.equals(this.getCpf())) {
                logado = true;
                Menu();
            }
        } catch (Exception e) {
            System.out.println("Senha errada!");
        }
    }

    public TreeMap<String, Conta> getContas() {
        return contas;
    }

    public void Menu(){
        if(!logado) {
            System.out.println("Usuário nao logado!");
            return;
        }
        Scanner sc = new Scanner(System.in);
        int escolha = 0;
        while(escolha != -1) {
            System.out.println("""
                Selecione:
                1 - Acessar conta
                2 - Debloquear conta
                3 - Trocar senha
                -1 - Sair""");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1: {
                    sc.nextLine();
                    System.out.println("Digite numero da conta a ser acessada:");
                    String nro;
                    nro = sc.nextLine();
                    Conta c = EncontraConta(nro);
                    if(c == null) {
                        System.out.println("Conta de numero " + nro + " nao encontrada!");
                        continue;
                    }
                    System.out.println("Digite a senha da conta:");
                    String pwd = sc.nextLine();
                    c.Login(nro, pwd);
                    continue;
                }
                case 2: {
                    sc.nextLine();
                    System.out.println("Digite numero da conta a ser desbloqueada:");
                    String nro;
                    nro = sc.nextLine();
                    Conta c = EncontraConta(nro);
                    if(c == null) {
                        System.out.println("Conta de numero " + nro + " não encontrada!");
                        continue;
                    }
                    if(!c.isEstaBloqueada()) {
                        System.out.println("A conta de numero " + nro + "não esta bloqueada!");
                    }
                    System.out.println("Digite a senha da conta:");
                    String pwd = sc.nextLine();
                    c.DesbloquarConta(pwd);
                    continue;
                }
                case 3: {
                    sc.nextLine();
                    System.out.print("Digite a nova senha: ");
                    String nova = sc.nextLine();
                    if (nova.length() < 6) {
                        System.out.println("Senha deve ter ao menos 6 caracteres!");
                    } else {
                        senha = nova;
                        System.out.println("Senha modificada com sucesso!");
                    }
                    continue;
                }
                case -1: {
                    break;
                }
                default: {
                    System.out.println("Escolha invalida!");
                    continue;
                }
            }
        }
        logado = false;
    }
}
