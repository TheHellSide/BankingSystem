package util.conti;

import util.Cliente;
import util.TipoConto;

public class ContoRisparmio extends ContoBancario {
    public double interesse = 0.001d;

    public ContoRisparmio(String iban, double saldo, Cliente proprietario, TipoConto tipo) {
        this.iban = iban;
        this.tipo = tipo;
        this.saldo = saldo;
        this.proprietario = proprietario;
    }

    @Override
    public void stampaDettagli() {
        System.out.println("\n┌─────────────── \u001B[1mCONTI BANCARI\u001B[0m ──────────────┐\n" + "\nIntestatario: " + proprietario.nome + " " + proprietario.cognome + "\n  IBAN: " + iban + "\n  Saldo: " + saldo + "\n Tasso di interesse: " + interesse + "\n\n└────────────────────────────────────────────┘\n");
    }

    @Override
    public double calcolaInteresse() {
        return saldo * interesse;
    }
}
