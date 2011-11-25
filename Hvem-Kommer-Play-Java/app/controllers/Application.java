package controllers;

import models.*;
import models.Person;
import no.hvemkommer.Deltakerstatus;
import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

public class Application extends Controller {

  public static void slettPaamelding(){
      Logger.info("Sletter påmelding");
      renderText("done");
  }

  public static void settStatus(@Required String person, String status, String trening) {
    Logger.info("Setter status for person.id=" + person + ", for trening.id=" + trening);
    Logger.info("\tstatus =" + status);

    Person endreForPerson = Person.findById(Long.valueOf(person));
    Trening gjeldendeTrening = Trening.findById(Long.valueOf(trening));
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);

    new Deltakelse(endreForPerson, gjeldendeTrening, deltakerstatus).save();

    List<Trening> treninger = hentAktiveTreninger();

    Long treningsId = finnNesteTreningId(treninger);

    List<Deltakelse> deltakelserKommer = hentDeltakelserKommer(treningsId);
    List<Deltakelse> deltakelserKommerIkke = hentDeltakelserKommerIkke(treningsId);

    List<Person> personerUtenStatus = hentPersonerUtenStatus(treningsId);

    //todo: Må ta inn gjeldendeTrening
    renderTemplate("Application/index.html", treninger, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeTrening);
  }

  public static void endreAktivTrening(@Required String trening) {
    Logger.info("Ny treningId: " + trening);
    List<Trening> treninger = hentAktiveTreninger();
    Long treningsId = Long.valueOf(trening);
    List<Deltakelse> deltakelserKommer = hentDeltakelserKommer(treningsId);
    List<Deltakelse> deltakelserKommerIkke = hentDeltakelserKommerIkke(treningsId);
    List<Person> personerUtenStatus = hentPersonerUtenStatus(treningsId);
    Trening gjeldendeTrening = Trening.findById(treningsId);

    renderTemplate("Application/index.html", treninger, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeTrening);

  }

  public static void index() {
    List<Trening> treninger = hentAktiveTreninger();
    Long treningsId = finnNesteTreningId(treninger);
    Trening gjeldendeTrening = Trening.findById(treningsId);
    List<Deltakelse> deltakelserKommer = hentDeltakelserKommer(treningsId);
    List<Deltakelse> deltakelserKommerIkke = hentDeltakelserKommerIkke(treningsId);

    List<Person> personerUtenStatus = hentPersonerUtenStatus(treningsId);

    render(treninger, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeTrening);
  }

  private static Long finnNesteTreningId(List<Trening> treninger) {
    Trening nesteTrening = treninger.get(0);
    return nesteTrening.getId();
  }

  private static List<Person> hentPersonerUtenStatus(Long treningsId) {
    return Person.find("from Person as p WHERE p.aktiv=true and not exists(select 'x' from Deltakelse as d where d.trening.id=? and d.person.id=p.id)", treningsId).fetch();
  }

  private static List<Deltakelse> hentDeltakelserKommerIkke(Long treningsId) {
    return Deltakelse.find("status=? and trening.id=?", Deltakerstatus.Nei, treningsId).fetch();
  }

  private static List<Deltakelse> hentDeltakelserKommer(Long treningsId) {
    return Deltakelse.find("status=? and trening.id=?", Deltakerstatus.Ja, treningsId).fetch();
  }

  private static List<Trening> hentAktiveTreninger() {
    return Trening.find("aktiv=true order by dato ASC").fetch();
  }


  public static void listDeltakelser(long treningsid){
    //Finn alle deltakelser for gitte trening
    List<Deltakelse> deltakelser = Deltakelse.find("trening.id=?", treningsid).fetch();
    renderJSON( deltakelser );
  }

  public static void listDeltakelserNesteTrening(){
    List<Trening> treninger = Trening.find("aktiv=true order by dato ASC").fetch(1);
    //Finne neste trening
    listDeltakelser(treninger.get(0).getId());
  }
}