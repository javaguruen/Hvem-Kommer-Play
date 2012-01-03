package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Trening extends Model {

  public Date dato;
  public String tidspunkt;
  public String sted;
  @ManyToOne
  @Required
  public Krets krets;
  public boolean aktiv = true;

  public Trening() {
    this.aktiv = true;
  }

  public Trening(Date dato, String tidspunkt, String sted, Krets krets) {
    this.dato = dato;
    this.tidspunkt = tidspunkt;
    this.sted = sted;
    this.krets = krets;
  }

  public String toString() {
    return dato.toString() + " - " + sted;
  }

  public static List<Trening> finnAlleAktive() {
    return Trening.find("aktiv=true order by dato ASC").fetch();
  }

}
