package models;

import play.db.jpa.Model;

import javax.persistence.*;

@Entity
public class Person extends Model {
    public String fornavn;
    public String etternavn;
    public String epost;
    public String mobilnr;
    public boolean aktiv = true;

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
}
