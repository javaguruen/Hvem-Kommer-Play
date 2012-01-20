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

  public static List<Person> finnAlleUtenStatus(Long aktivitetId) {
  return Person.find("from Person as p WHERE not exists(select 'x' from Deltakelse as d where d.aktivitet.id=? and d.person.id=p.id)", aktivitetId).fetch();
  }

}
