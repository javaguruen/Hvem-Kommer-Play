package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Trening extends Model{
    public Date dato;
    public String tidspunkt;
    public String sted;
    public boolean aktiv = true;

    public Trening(Date dato, String tidspunkt, String sted) {
        this.dato = dato;
        this.tidspunkt = tidspunkt;
        this.sted = sted;
    }
}
