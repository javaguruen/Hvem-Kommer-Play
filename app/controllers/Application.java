package controllers;

import models.Aktivitet;
import models.Deltakelse;
import models.Person;
import no.hvemkommer.Deltakerstatus;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

  public static void slettPaamelding(Long deltakelseId) {
    Deltakelse deltakelse = Deltakelse.findById(deltakelseId);
    String aktivitetId = deltakelse.aktivitet.getId().toString();
    deltakelse.delete();
    redirect("Application.index", aktivitetId);
  }

  public static void settStatus(@Required Long personId, String status, Long aktivitetId) {
    Logger.info(personId +", " + status + ", " + aktivitetId);
    if (personId == null) {
      redirect("Application.index", aktivitetId);
    }

    Aktivitet aktivitet = Aktivitet.findById(aktivitetId);
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);
    Person person = Person.findById(personId);
    Deltakelse deltakelse = Deltakelse.finn(person, aktivitet);
    if (deltakelse == null) {
      deltakelse = new Deltakelse(person, aktivitet);
    }

    deltakelse.status = deltakerstatus;
    deltakelse.save();
    redirect("Application.index", aktivitetId);
  }


  public static void endreAktivAktivitet(@Required Long aktivitetId) {
    Logger.info("Ny aktivitetId: " + aktivitetId);
    redirect("Application.index", aktivitetId);
  }

  public static void index(Long aktivitetId) {
    List<Aktivitet> aktiviteter = Aktivitet.finnAlleAktive();
    if (aktivitetId == null) {
      aktivitetId = finnNesteAktivitetId(aktiviteter);
    }
    Aktivitet gjeldendeAktivitet = Aktivitet.findById(aktivitetId);
    List<Deltakelse> deltakelserKommer = Deltakelse.finnAlleSomKommer(aktivitetId);
    List<Deltakelse> deltakelserKommerIkke = Deltakelse.finnAlleSomIkkeKommer(aktivitetId);
    List<Person> personerUtenStatus = Person.finnAlleUtenStatus(aktivitetId);

    render(aktiviteter, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeAktivitet);
  }

  private static Long finnNesteAktivitetId(List<Aktivitet> aktiviteter) {
    if (aktiviteter.size() == 0) {
      return null;
    } else {
      Aktivitet nesteTrening = aktiviteter.get(0);
      return nesteTrening.getId();
    }
  }

  public static void listDeltakelser(Long aktivitetId) {
    //Finn alle deltakelser for gitte aktivitet
    List<Deltakelse> deltakelser = Deltakelse.find("aktivitet.id=?", aktivitetId).fetch();
    renderJSON(deltakelser);
  }

  public static void listDeltakelserNesteAktivitet() {
    List<Aktivitet> treninger = Aktivitet.find("aktiv=true order by dato ASC").fetch(1);
    //Finne neste aktivitet
    listDeltakelser(treninger.get(0).getId());
  }
}