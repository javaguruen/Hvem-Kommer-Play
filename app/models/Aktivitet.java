package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Entity
public class Aktivitet extends Model {

  public Date dato;
  public String tidspunkt;
  public String sted;
  @ManyToOne
  @Required
  public Gruppe gruppe;
  public boolean aktiv = true;

  public Aktivitet() {
    this.aktiv = true;
  }

  public Aktivitet(Date dato, String tidspunkt, String sted, Gruppe gruppe) {
    this.dato = dato;
    this.tidspunkt = tidspunkt;
    this.sted = sted;
    this.gruppe = gruppe;
  }

  public String toString() {
    return dato.toString() + " - " + sted;
  }

  public static List<Aktivitet> finnAlleAktive() {
    return Aktivitet.find("aktiv=true order by dato ASC").fetch();
  }

}
