package controllers;

import models.Aktivitet;
import models.Deltakelse;
import models.Gruppe;
import models.Person;
import no.hvemkommer.Deltakerstatus;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

  public static void index() {
    List<Gruppe> grupper = Gruppe.finnAlleAktive();
    List<Aktivitet> aktiviteter = Aktivitet.finnAlleAktive();
    render(grupper, aktiviteter);
  }


  public static void paamelding(String gruppeNavn, Long aktivitetId) {
    Gruppe gruppe = Gruppe.finnByNavn(gruppeNavn);
    if (gruppe==null) {
      notFound("Fant ikke gruppe " +gruppeNavn);
    }
    List<Aktivitet> aktiviteter = Aktivitet.finnAlleAktive(gruppe.id);
    if (aktiviteter.size()==0) {
      notFound("Fant ikke aktiviteter for " +gruppeNavn);
    }
    if (aktivitetId==null) {
      aktivitetId = finnNesteAktivitetId(aktiviteter);
    }

    Aktivitet gjeldendeAktivitet = Aktivitet.findById(aktivitetId);
    List<Deltakelse> deltakelserKommer = Deltakelse.finnAlleSomKommer(aktivitetId);
    List<Deltakelse> deltakelserKommerIkke = Deltakelse.finnAlleSomIkkeKommer(aktivitetId);
    List<Person> personerUtenStatus = Person.finnAlleUtenStatus(aktivitetId);

    renderTemplate("Application/paamelding.html",gruppe,aktiviteter, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeAktivitet);
  }

  public static void slettPaamelding(Long deltakelseId) {
    Deltakelse deltakelse = Deltakelse.findById(deltakelseId);
    String aktivitetId = deltakelse.aktivitet.getId().toString();
    deltakelse.delete();
    redirect("Application.paamelding", deltakelse.aktivitet.gruppe.navn,aktivitetId);
  }

  public static void settStatus(@Required Long personId, String status, Long aktivitetId) {
    Aktivitet aktivitet = Aktivitet.findById(aktivitetId);
    Logger.info(personId +", " + status + ", " + aktivitetId);
    if (personId == null) {
      redirect("Application.paamelding", aktivitet.gruppe.navn,aktivitetId);
    }
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);
    Person person = Person.findById(personId);
    Deltakelse deltakelse = Deltakelse.finn(person, aktivitet);
    if (deltakelse == null) {
      deltakelse = new Deltakelse(person, aktivitet);
    }

    deltakelse.status = deltakerstatus;
    deltakelse.save();
    redirect("Application.paamelding", aktivitet.gruppe.navn,aktivitetId);
  }


  public static void endreAktivAktivitet(@Required Long aktivitetId) {
    Logger.info("Ny aktivitetId: " + aktivitetId);
    Aktivitet aktivitet = Aktivitet.findById(aktivitetId);
    redirect("Application.paamelding", aktivitet.gruppe.navn,aktivitetId);
  }

  private static Long finnNesteAktivitetId(List<Aktivitet> aktiviteter) {
    if (aktiviteter.size() == 0) {
      return null;
    } else {
      Aktivitet nesteAktivitet = aktiviteter.get(0);
      return nesteAktivitet.getId();
    }
  }

  public static void listDeltakelser(Long aktivitetId) {
    //Finn alle deltakelser for gitte aktivitet
    List<Deltakelse> deltakelser = Deltakelse.find("aktivitet.id=?", aktivitetId).fetch();
    renderJSON(deltakelser);
  }

  public static void listDeltakelserNesteAktivitet() {
    List<Aktivitet> aktiviteter = Aktivitet.find("aktiv=true order by dato ASC").fetch(1);
    //Finne neste aktivitet
    listDeltakelser(aktiviteter.get(0).getId());
  }
}