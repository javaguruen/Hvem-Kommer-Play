package models;

import no.hvemkommer.Deltakerstatus;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Deltakelse extends Model {

  @ManyToOne
  @Required
  public Person person;
  @ManyToOne
  @Required
  public Trening trening;
  @Required
  public Deltakerstatus status;

  public Deltakelse(Person person, Trening trening, Deltakerstatus status) {
    this.person = person;
    this.trening = trening;
    this.status = status;
  }

  public String toString() {
    return person.toString() + " - " + status;
  }
}
