package models;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person extends Model {

  public String fornavn;
  public String etternavn;
  public String epost;
  public String mobilnr;
  public boolean aktiv = true;

  public Person() {
    this.aktiv = true;
  }

  public Person(String fornavn, String etternavn, String epost, String mobilnr) {
    this.fornavn = fornavn;
    this.etternavn = etternavn;
    this.epost = epost;
    this.mobilnr = mobilnr;
  }

  @Override
  public String toString() {
    return fornavn + " " + etternavn;
  }

  public static List<Person> finnAlleUtenStatus(Long treningsId) {
  return Person.find("from Person as p WHERE p.aktiv=true and not exists(select 'x' from Deltakelse as d where d.trening.id=? and d.person.id=p.id)", treningsId).fetch();
  }
}
