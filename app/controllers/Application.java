package controllers;

import models.Deltakelse;
import models.Person;
import models.Trening;
import no.hvemkommer.Deltakerstatus;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

  public static void slettPaamelding(Long deltakelseId) {
    Deltakelse deltakelse = Deltakelse.findById(deltakelseId);
    String treningId = deltakelse.trening.getId().toString();
    deltakelse.delete();
    redirect("Application.index", treningId);
  }

  public static void settStatus(@Required Long personId, String status, Long treningId) {
    Logger.info(personId +", " + status + ", " + treningId);
    if (personId == null) {
      redirect("Application.index", treningId);
    }

    Trening trening = Trening.findById(treningId);
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);
    Person person = Person.findById(personId);
    Deltakelse deltakelse = Deltakelse.finn(person, trening);
    if (deltakelse == null) {
      deltakelse = new Deltakelse(person, trening);
    }

    deltakelse.status = deltakerstatus;
    deltakelse.save();
    redirect("Application.index", treningId);
  }


  public static void endreAktivTrening(@Required Long treningId) {
    Logger.info("Ny treningId: " + treningId);
    redirect("Application.index", treningId.toString());
  }

  public static void index(Long treningId) {
    List<Trening> treninger = Trening.finnAlleAktive();
    if (treningId == null) {
      treningId = finnNesteTreningId(treninger);
    }
    Trening gjeldendeTrening = Trening.findById(treningId);
    List<Deltakelse> deltakelserKommer = Deltakelse.finnAlleSomKommer(treningId);
    List<Deltakelse> deltakelserKommerIkke = Deltakelse.finnAlleSomIkkeKommer(treningId);
    List<Person> personerUtenStatus = Person.finnAlleUtenStatus(treningId);

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

  public static void listDeltakelser(Long treningId) {
    //Finn alle deltakelser for gitte trening
    List<Deltakelse> deltakelser = Deltakelse.find("trening.id=?", treningId).fetch();
    renderJSON(deltakelser);
  }

  public static void listDeltakelserNesteTrening() {
    List<Trening> treninger = Trening.find("aktiv=true order by dato ASC").fetch(1);
    //Finne neste trening
    listDeltakelser(treninger.get(0).getId());
  }
}