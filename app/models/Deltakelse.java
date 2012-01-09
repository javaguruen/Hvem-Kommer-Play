package models;

import no.hvemkommer.Deltakerstatus;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;


@Entity
public class Deltakelse extends Model {

  @ManyToOne
  @Required
  public Person person;
  @ManyToOne
  @Required
  public Aktivitet aktivitet;
  @Required
  public Deltakerstatus status;

  public Deltakelse(Person person, Aktivitet aktivitet) {
    this.person = person;
    this.aktivitet = aktivitet;
  }

  public String toString() {
    return person.toString() + " - " + status;
  }

  public static List<Deltakelse> finnAlleSomIkkeKommer(Long aktivitetId) {
    return Deltakelse.find("status=? and aktivitet.id=?", Deltakerstatus.Nei, aktivitetId).fetch();
  }

  public static List<Deltakelse> finnAlleSomKommer(Long aktivitetId) {
    return Deltakelse.find("status=? and aktivitet.id=?", Deltakerstatus.Ja, aktivitetId).fetch();
  }

  public static Deltakelse finn(Person person, Aktivitet aktivitet) {
    return Deltakelse.find("person=? and aktivitet=?", person,aktivitet).first();
  }
}
