import java.util.Calendar;

public abstract class Pessoa {
    protected String nome, cpf, escolaridade, estadoCivil;
    Calendar dataNascimento;
    protected Endereco end;

    public boolean ValidaCPF () {
        int multiplicador = 10;
        int acc1 = 0, acc2 = 0;
        int digito1, digito2;
        for(int i = 0; i < cpf.length() - 3; ++i) {
            if(cpf.charAt(i) == '.') continue;
            acc1 += multiplicador * Integer.parseInt(String.valueOf(cpf.charAt(i)));
            acc2 += (multiplicador+1) * Integer.parseInt(String.valueOf(cpf.charAt(i)));
            multiplicador--;
        }
        if(acc1 % 11 < 2) digito1 = 0;
        else digito1 = 11 - (acc1 % 11);
        acc2 += (multiplicador+1) * digito1;

        if(acc2 % 11 < 2) digito2 = 0;
        else digito2 = 11 - (acc2 % 11);

        if(digito1 != Integer.parseInt(String.valueOf(cpf.charAt(12)))) return false;
        if(digito2 != Integer.parseInt(String.valueOf(cpf.charAt(13)))) return false;

        return true;
    }
}
