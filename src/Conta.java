import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;

public abstract class Conta implements Logavel, Serializable { //Essa eh a outra classse requisitada como abstrata pela questao 5
    private String nroConta, senha;
    private double saldoAtual;
    private Calendar dataAbertura, ultimaMovimentacao;
    private boolean estaAtiva, estaBloqueada;
    private Cliente[] donoDaConta;
    private Agencia ag;
    private int tentativasErradas;
    private boolean logado = false;

    public Conta(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                 Calendar ultimaMovimentacao, boolean estaAtiva, Cliente donoDaConta1, Agencia ag) {
        this.nroConta = nroConta;
        this.senha = senha;
        this.saldoAtual = saldoAtual;
        this.dataAbertura = dataAbertura;
        this.ultimaMovimentacao = ultimaMovimentacao;
        this.estaAtiva = estaAtiva;
        this.donoDaConta = new Cliente[2];
        this.donoDaConta[0] = donoDaConta1;
        this.donoDaConta[1] = null;
        this.ag = ag;
        this.estaBloqueada = false;
        this.tentativasErradas = 0;
    }

    public Conta(String nroConta, String senha, Calendar dataAbertura, Cliente donoDaConta1, Agencia ag) {
        this.nroConta = nroConta;
        this.senha = senha;
        this.dataAbertura = dataAbertura;
        this.donoDaConta = new Cliente[2];
        this.donoDaConta[0] = donoDaConta1;
        this.donoDaConta[1] = null;
        this.ag = ag;
    }

    public Conta(String nroConta, String senha, Calendar dataAbertura, Cliente donoDaConta1, Cliente donoDaConta2,
                 Agencia ag) {
        this.nroConta = nroConta;
        this.senha = senha;
        this.dataAbertura = dataAbertura;
        this.donoDaConta = new Cliente[2];
        this.donoDaConta[0] = donoDaConta1;
        this.donoDaConta[1] = donoDaConta2;
        this.ag = ag;
    }

    public abstract String getTipoConta();
    public abstract void Saque(double valor);


    public void ValidaSenha(String senha) throws SenhaInvalidaException {
        if (!this.senha.equals(senha)) {
            tentativasErradas++;
            if (tentativasErradas == 3) {
                System.out.println("3 tentativas de login erradas seguidas, conta foi bloqueada!");
                estaBloqueada = true;
            }
            throw new SenhaInvalidaException("Senha da conta invalida! mais " + (3 - tentativasErradas) +
                    " tentativas restantes até o bloqueio da conta");
        }
        tentativasErradas = 0;
    }

    public void Deposito(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("Depósito não pode ser menor que 0!");
        }
        saldoAtual += valor;
    }

    public void setUltimaMovimentacao(Calendar data) {
        ultimaMovimentacao = data;
    }


    public void Login(String usuario, String senha) {
        if(estaBloqueada) {
            System.out.println("Conta bloqueada! Por favor desbloqueie antes de tentar logar.");
            return;
        }
        try {
            ValidaSenha(senha);
            if(usuario.equals(this.nroConta)) {
                logado = true;
                Menu();
            }
        }
        catch (Exception e) {
            System.out.println("Senha incorreta!");
        }
    }

    public void DesbloquarConta(String senha) {

        try {
            ValidaSenha(senha);
            this.estaBloqueada = false;
            System.out.println("Conta desbloqueada!");
        } catch (Exception e) {
            System.out.println("Senha incorreta!");
        }
    }

    public void Menu() {
        if (!logado) {
            System.out.println("Usuário não logado!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int escolha = 0;

        System.out.println("Bem-vindo!");
        MostraInfos();

        while (escolha != -1) {
            System.out.println("""
                \nFaça sua escolha:
                1 - Sacar dinheiro
                2 - Depositar dinheiro
                3 - Transferência bancária
                5 - Verificar saldo e informações da conta
                4 - Trocar senha
                -1 - Sair
                """);
            System.out.print("Opção: ");

            if (sc.hasNextInt()) {
                escolha = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Por favor, insira um número válido.");
                sc.nextLine();
                continue;
            }

            switch (escolha) {
                case 1: {
                    System.out.print("Quanto quer sacar? (R$): ");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = sc.nextLine();

                    Transacao t = new Transacao(this, Calendar.getInstance());
                    t.setTipo("saque");
                    t.setValor(valor);
                    t.RealizaMovimentacao(senha, null);

                    continue;
                }
                case 2: {
                    System.out.print("Digite o valor do depósito: ");
                    double valorDep = sc.nextDouble();
                    sc.nextLine();

                    Transacao tDep = new Transacao(this, Calendar.getInstance());
                    tDep.setTipo("deposito");
                    tDep.setValor(valorDep);
                    tDep.RealizaMovimentacao(null, null);

                    continue;
                }
                case 3: {
                    // TODO: Implementar transferência
                    System.out.println("Qual a agência da outra conta?");
                    String aux = sc.nextLine();
                    Agencia outra = Banco.EncontrarAgencia(aux);
                    if(outra == null) {
                        System.out.println("Agência de número " + aux + " não encontrada!");
                        continue;
                    }

                    System.out.println("Qual o cpf do cliente que receberá transferencia?");
                    aux = sc.nextLine();
                    Cliente clienteEncontrado = outra.EncontraCliente(aux);
                    if(clienteEncontrado == null) {
                        System.out.println("Cliente de cpf " + aux + " não encontrado!");
                        continue;
                    }

                    System.out.println("Qual o número da conta que receberá a transferencia?");
                    aux = sc.nextLine();
                    Conta cn = clienteEncontrado.EncontraConta(aux);
                    if(cn == null) {
                        System.out.println("Conta de nro " + aux + " não encontrada!");
                        continue;
                    }

                    System.out.println("Valor da transação?");
                    double val = sc.nextDouble();
                    sc.nextLine();

                    Transacao t = new Transacao(this, null);
                    t.setTipo("transferencia");
                    t.setValor(val);
                    t.RealizaMovimentacao(null, cn);
                    continue;
                }
                case 4: {
                    System.out.print("Digite nova senha: ");
                    String nova = sc.nextLine();
                    if (nova.length() < 6) {
                        System.out.println("Senha deve ter ao menos 6 caracteres!");
                    } else {
                        setSenha(nova);
                        System.out.println("Senha modificada com sucesso!");
                    }
                    continue;
                }
                case 5: {
                    MostraInfos();
                    continue;
                }
                case -1: {
                    System.out.println("Saindo da conta...");
                    break;
                }
                default: {
                    System.out.println("Opção inválida! Tente novamente.");
                    continue;
                }
            }
        }
        logado = false;
    }

    public void MostraInfos(){

        System.out.println("Numero da conta: " + nroConta);
        System.out.println("Saldo atual: " + saldoAtual);
        System.out.println("Status " + (estaAtiva ? "ativa" : "inativa"));
        System.out.println("É conjunta? " + (donoDaConta[1] == null ? "Não" : "Sim\n" +  "Outro titular: " +
                donoDaConta[1].getNome()));
        System.out.println("Ultima movimentacao em: " + ultimaMovimentacao.get(Calendar.DAY_OF_MONTH)
        + "/" + ultimaMovimentacao.get(Calendar.MONTH) + "/" +  ultimaMovimentacao.get(Calendar.YEAR));
    }
    //Getters and Setters
    public boolean isEstaBloqueada() {return estaBloqueada;}
    public void setEstaBloqueada(boolean b) {estaBloqueada = b;}
    public String getNroConta() {return nroConta;}

    public void setNroConta(String nroConta) {this.nroConta = nroConta;}
    public void setSenha(String senha) {this.senha = senha;}

    public double getSaldoAtual() {return saldoAtual;}
    public void setSaldoAtual(double saldoAtual) {this.saldoAtual = saldoAtual;}

    public Calendar getDataAbertura() {return dataAbertura;}
    public void setDataAbertura(Calendar dataAbertura) {this.dataAbertura = dataAbertura;}

    public Calendar getUltimaMovimentacao() {return ultimaMovimentacao;}
    public boolean isEstaAtiva() {return estaAtiva;}

    public void setEstaAtiva(boolean estaAtiva) {this.estaAtiva = estaAtiva;}
    public Cliente[] getDonoDaConta() {return donoDaConta;}
    public void setDonoDaConta(Cliente[] donoDaConta) {this.donoDaConta = donoDaConta;}

    public Agencia getAg() {return ag;}

    public void setAg(Agencia ag) {this.ag = ag;}
    public int getTentativasErradas() {return tentativasErradas;}

    public void setTentativasErradas(int tentativasErradas) {this.tentativasErradas = tentativasErradas;}

}
