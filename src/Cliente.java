import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class Cliente extends Pessoa implements Logavel {
    private HashMap<String, Conta> contas;
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
        contas = new HashMap<>();
        this.ag = ag;
    }

    public Cliente() {
        contas = new HashMap<>();
    }

    //métodos

    public Conta EncontraConta(String nro) {
        return contas.get(nro);
    }

    //Getters and Setters

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
            if(usuario.equals(this.getNome().toLowerCase())) {
                logado = true;
                Menu();
            }
        } catch (Exception e) {
            System.out.println("Senha errada!");
        }
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
                -1 - Sair""");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1: {
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
