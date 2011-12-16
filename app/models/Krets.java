package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Krets extends Model {

  public String navn;
  public String beskrivelse;
  public boolean aktiv = true;
  @OneToMany
  public List<Trening> treninger = new ArrayList<Trening>();

  public Krets() {
    this.aktiv = true;
  }

  public Krets(String navn, String beskrivelse) {
    this.navn = navn;
    this.beskrivelse = beskrivelse;
  }

  @Override
  public String toString() {
    return navn + " " + beskrivelse;
  }
}
