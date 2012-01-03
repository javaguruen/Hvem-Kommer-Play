package controllers;

import models.*;
import models.Person;
import no.hvemkommer.Deltakerstatus;
import play.*;
import play.data.validation.Required;
import play.db.jpa.JPABase;
import play.mvc.*;

import java.util.*;

public class Application extends Controller {

  public static void slettPaamelding(String deltakelseId) {
    Deltakelse deltakelse = Deltakelse.findById(Long.valueOf(deltakelseId));
    String treningId = deltakelse.trening.getId().toString();
    deltakelse.delete();
    redirect("Application.index", treningId);
  }

  public static void settStatus(@Required String person, String status, String trening) {
    Logger.info("Setter status for person.id=" + person + ", for trening.id=" + trening);
    Logger.info("\tstatus =" + status);

    Trening gjeldendeTrening = Trening.findById(Long.valueOf(trening));
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);
    if (person != null) {
      Person endreForPerson = Person.findById(Long.valueOf(person));
      // TODO: Sjekk først om gitt deltakelse finnes fra før
      new Deltakelse(endreForPerson, gjeldendeTrening, deltakerstatus).save();
    }

    redirect("Application.index", trening);
  }


  public static void endreAktivTrening(@Required String trening) {
    Logger.info("Ny treningId: " + trening);
    redirect("Application.index", trening);
  }

  public static void index(String trening) {
    Long treningsId;
    List<Trening> treninger = Trening.finnAlleAktive();
    if (trening == null) {
      treningsId = finnNesteTreningId(treninger);
    } else {
      treningsId = Long.valueOf(trening);
    }

    Trening gjeldendeTrening = Trening.findById(treningsId);
    List<Deltakelse> deltakelserKommer = Deltakelse.finnAlleSomKommer(treningsId);
    List<Deltakelse> deltakelserKommerIkke = Deltakelse.finnAlleSomIkkeKommer(treningsId);
    List<Person> personerUtenStatus = Person.finnAlleUtenStatus(treningsId);

    render(treninger, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeTrening);
  }

  private static Long finnNesteTreningId(List<Trening> treninger) {
    if (treninger.size() == 0) {
      return null;
    } else {
      Trening nesteTrening = treninger.get(0);
      return nesteTrening.getId();
    }
  }

  public static void listDeltakelser(long treningsid) {
    //Finn alle deltakelser for gitte trening
    List<Deltakelse> deltakelser = Deltakelse.find("trening.id=?", treningsid).fetch();
    renderJSON(deltakelser);
  }

  public static void listDeltakelserNesteTrening() {
    List<Trening> treninger = Trening.find("aktiv=true order by dato ASC").fetch(1);
    //Finne neste trening
    listDeltakelser(treninger.get(0).getId());
  }
}