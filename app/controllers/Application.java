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