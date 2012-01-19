package controllers;

import models.Aktivitet;
import models.Deltakelse;
import models.Gruppe;
import models.Person;
import no.hvemkommer.Deltakerstatus;
import play.Logger;
import play.data.validation.Required;

import java.util.List;

public class Deltakelses  extends CRUD {

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

    renderTemplate("Deltakelses/paamelding.html",gruppe,aktiviteter, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeAktivitet);
  }

  public static void slettPaamelding(Long deltakelseId) {
    Deltakelse deltakelse = Deltakelse.findById(deltakelseId);
    String aktivitetId = deltakelse.aktivitet.getId().toString();
    deltakelse.delete();
    redirect("Deltakelses.paamelding", deltakelse.aktivitet.gruppe.navn,aktivitetId);
  }

  public static void settStatus(@Required Long personId, String status, Long aktivitetId) {
    Aktivitet aktivitet = Aktivitet.findById(aktivitetId);
    Logger.info(personId + ", " + status + ", " + aktivitetId);
    if (personId == null) {
      redirect("Deltakelses.paamelding", aktivitet.gruppe.navn,aktivitetId);
    }
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);
    Person person = Person.findById(personId);
    Deltakelse deltakelse = Deltakelse.finn(person, aktivitet);
    if (deltakelse == null) {
      deltakelse = new Deltakelse(person, aktivitet);
    }

    deltakelse.status = deltakerstatus;
    deltakelse.save();
    redirect("Deltakelses.paamelding", aktivitet.gruppe.navn,aktivitetId);
  }


  public static void endreAktivAktivitet(@Required Long aktivitetId) {
    Logger.info("Ny aktivitetId: " + aktivitetId);
    Aktivitet aktivitet = Aktivitet.findById(aktivitetId);
    redirect("Deltakelses.paamelding", aktivitet.gruppe.navn,aktivitetId);
  }

  private static Long finnNesteAktivitetId(List<Aktivitet> aktiviteter) {
    if (aktiviteter.size() == 0) {
      return null;
    } else {
      Aktivitet nesteAktivitet = aktiviteter.get(0);
      return nesteAktivitet.getId();
    }
  }


}
