package util.conti;

import util.Cliente;
import util.TipoConto;

public class ContoCorrente extends ContoBancario {
    public double scopertoMax = 1500d;

    public ContoCorrente(String iban, double saldo, Cliente proprietario, TipoConto tipo){
        this.iban = iban;
        this.tipo = tipo;
        this.saldo = saldo;
        this.proprietario = proprietario;
    }

    @Override
    public void stampaDettagli(){
        System.out.println("\n┌─────────────── \u001B[1mCONTI BANCARI\u001B[0m ──────────────┐\n" + "\n  Intestatario: " + proprietario.cognome + " " + proprietario.nome + "\n  IBAN: " + iban + "\n  Saldo: " + saldo + "\n  Scoperto Massimo: " + scopertoMax + "\n\n└────────────────────────────────────────────┘\n");
    }

    @Override
    public double calcolaInteresse(){
        return 0;
    }
}
