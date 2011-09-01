package controllers;

import models.*;
import models.Person;
import no.hvemkommer.Deltakerstatus;
import no.hvemkommer.Dummydata;
import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

public class Application extends Controller {

  public static void settStatus(@Required String person, String status, String trening) {
    Logger.info("Setter status for person.id=" + person);
    Logger.info("\tstatus =" + status);
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);
    Dummydata dummydata = new Dummydata();
    List<Trening> treninger = dummydata.getDummyTreninger();
    Map<Deltakerstatus, List<Person>> deltakelser = dummydata.getDummeDeltakelse();

    Trening defaultTrening = treninger.get(Integer.valueOf(trening));
    renderTemplate("Application/index.html", treninger, defaultTrening, deltakelser);
  }

  public static void endreAktivTrening(@Required String trening) {
    Logger.info("Ny trening: " + trening);
    Dummydata dummydata = new Dummydata();
    List<Trening> treninger = dummydata.getDummyTreninger();
    Map<Deltakerstatus, List<Person>> deltakelser = dummydata.getDummeDeltakelse();

    Trening defaultTrening = treninger.get(Integer.valueOf(trening));
    renderTemplate("Application/index.html", treninger, defaultTrening, deltakelser);
  }

  public static void index() {
    Dummydata dummydata = new Dummydata();
    List<Trening> treninger = dummydata.getDummyTreninger();
    Map<Deltakerstatus, List<Person>> deltakelser = dummydata.getDummeDeltakelse();

    Trening defaultTrening = null;
    if (treninger.size() > 0) {
      defaultTrening = treninger.get(0);
    }
    render(treninger, defaultTrening, deltakelser);
  }
}