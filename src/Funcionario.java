import java.io.Serializable;
import java.util.Calendar;
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
    public Funcionario() {

    }

    public Funcionario(Funcionario f) {
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
//        sc.next();

        System.out.println("Digite cargo do funcionário: ");
        cargo = sc.nextLine();
//        sc.next();

        System.out.println("Digite RG: ");
        RG = sc.nextLine();
//        sc.next();

        System.out.println("Digite carteira de trabalho: ");
        carteiraTrabalho = sc.nextLine();
//        sc.next();

        System.out.println("Digite senha: ");
        senha = sc.nextLine();
        //sc.next();

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

    //TODO:
    public void Menu() {
        if(!logado) {
            System.out.println("Usuário nao logado!");
            return;
        }
        Scanner sc = new Scanner(System.in);
        int escolha = 0;
        Conta gerenciada = null;
        while(escolha != -1) {
            System.out.println("""
                    Faça sua seleção:
                    1 - Cadastrar novo cliente
                    2 - Cadastrar nova conta
                    3 - Gerenciar conta
                    4 - Trocar senha
                    -1 - Voltar""");
            escolha = sc.nextInt();
            switch (escolha){
                case 1: {
                    //TODO!
                }
                case 2: {
                    //TODO!
                }

                case 3: {
                    System.out.println("Digite cpf do dono da conta: ");
                    String cpfDono = sc.nextLine();
                    Cliente c = trabalho.EcontraCliente(cpfDono);
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
                    System.out.println("Digite nova senha: ");
                    senha = sc.nextLine();
                    System.out.println("Senha modificada!");
                    continue;
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
                    3 - Deixar conta inativa
                    -1 - Voltar""");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1: {
                    //TODO:
                }
                case 2: {
                    //TODO:
                }
                case 3: {
                    //TODO:

                }
                case -1: {
                    return;
                }
                default: {
                    System.out.println("Opção inválida");
                    continue;
                }
            }
        }
    }

    public void Login(String usuario, String senha) {
        //System.out.println("aqui!");
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
        return super.toString() + "Funcionario{" +
                "sexo='" + sexo + '\'' +
                ", carteiraTrabalho='" + carteiraTrabalho + '\'' +
                ", RG='" + RG + '\'' +
                ", cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", anoDeIngresso=" + anoDeIngresso +
                ", senha='" + senha + '\'' +
                ", ag=" + trabalho +
                ", logado=" + logado +
                '}';
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

    //Getters and Setters
    public Calendar getAnoDeIngresso() {
        return anoDeIngresso;
    }
    public void setAnoDeIngresso(Calendar anoDeIngresso) {
        this.anoDeIngresso = anoDeIngresso;
    }

    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getRG() {
        return RG;
    }
    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getCarteiraTrabalho() {
        return carteiraTrabalho;
    }
    public void setCarteiraTrabalho(String carteiraTrabalho) {
        this.carteiraTrabalho = carteiraTrabalho;
    }

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    protected void setLogado(boolean valor) {
        logado = valor;
    }

    protected boolean isLogado() {
        return logado;
    }

    public void ValidaSenha(String senha) throws SenhaInvalidaException {
        if(!this.senha.equals(senha)) {
            throw new SenhaInvalidaException("Senha invalida!\n");
        }
    }


    public Agencia getTrabalho() {
        return trabalho;
    }

    public void setTrabalho(Agencia trabalho) {
        this.trabalho = trabalho;
    }


}
