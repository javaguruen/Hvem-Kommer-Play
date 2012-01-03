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

  public static void slettPaamelding(String deltakelseId){
    Deltakelse deltakelse = Deltakelse.findById(Long.valueOf(deltakelseId));
    String treningId = deltakelse.trening.getId().toString();
    deltakelse.delete();
    redirect("Application.index",treningId);
  }

  public static void settStatus(@Required String person, String status, String trening) {
    Logger.info("Setter status for person.id=" + person + ", for trening.id=" + trening);
    Logger.info("\tstatus =" + status);

    Trening gjeldendeTrening = Trening.findById(Long.valueOf(trening));
    Deltakerstatus deltakerstatus = Deltakerstatus.valueOf(status);
    if (person!=null) {
        Person endreForPerson = Person.findById(Long.valueOf(person));
        // TODO: Sjekk først om gitt deltakelse finnes fra før
        new Deltakelse(endreForPerson, gjeldendeTrening, deltakerstatus).save();
    }

    redirect("Application.index",trening);
  }


    public static void endreAktivTrening(@Required String trening) {
    Logger.info("Ny treningId: " + trening);
    redirect("Application.index",trening);
  }

  public static void index(String trening) {
    Long treningsId;
    List<Trening> treninger = hentAktiveTreninger();
    if (trening==null) {
        treningsId = finnNesteTreningId(treninger);
    } else {
        treningsId = Long.valueOf(trening);
    }

    Trening gjeldendeTrening = Trening.findById(treningsId);
    List<Deltakelse> deltakelserKommer = hentDeltakelserKommer(treningsId);
    List<Deltakelse> deltakelserKommerIkke = hentDeltakelserKommerIkke(treningsId);

    List<Person> personerUtenStatus = hentPersonerUtenStatus(treningsId);

    render(treninger, deltakelserKommer, deltakelserKommerIkke, personerUtenStatus, gjeldendeTrening);
  }

  private static Long finnNesteTreningId(List<Trening> treninger) {
    if( treninger.size() == 0){
      return null;
    }
    else{
      Trening nesteTrening = treninger.get(0);
      return nesteTrening.getId();
    }
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