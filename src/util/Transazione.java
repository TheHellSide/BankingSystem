package util;

import java.time.LocalDate;

// TRANSAZIONI
public class Transazione {
    public LocalDate data;
    public double importo;
    public TipoTransazione tipo;
    public String descrizione;

    public Transazione(double importo, TipoTransazione tipo, String descrizione) {
        this.data = LocalDate.now();
        this.importo = importo;
        this.tipo = tipo;
        this.descrizione = descrizione;
    }
}
