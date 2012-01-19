package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Gruppe extends Model {

  public String navn;
  public String beskrivelse;
  public boolean aktiv = true;
  @OneToMany
  public List<Aktivitet> aktiviteter = new ArrayList<Aktivitet>();

  public Gruppe() {
    this.aktiv = true;
  }

  public Gruppe(String navn, String beskrivelse) {
    this.navn = navn;
    this.beskrivelse = beskrivelse;
  }

  @Override
  public String toString() {
    return navn + " " + beskrivelse;
  }

  public static List<Gruppe> finnAlleAktive() {
    return Gruppe.find("aktiv=true order by navn").fetch();
  }

}
