package controllers;

import models.Gruppe;
import org.apache.log4j.Logger;
import play.data.binding.Binder;
import play.data.validation.Valid;
import play.db.Model;
import play.db.jpa.JPABase;
import play.exceptions.TemplateNotFoundException;
import play.i18n.Messages;
import play.mvc.Controller;

import java.util.List;

public class Gruppes extends Controller{
  private static final Logger logger = Logger.getLogger(Gruppes.class);

  public static void velgGruppe(){
    List<Gruppe> grupper = hentAktiveGrupper();
    render(grupper);

  }

  public static void index(){
    List<Gruppe> grupper = hentAktiveGrupper();
    render(grupper);
  }

  public static void show(Long id){
    logger.info("Søker gruppe " + id);
    Gruppe gruppe = Gruppe.findById(id);
    logger.error("Fant gruppe: " + gruppe);
    render(gruppe);
  }

  public static void create(){
    Gruppe gruppe = new Gruppe();
    render(gruppe);
  }

  public static void edit(Long id){
    logger.info("Søker gruppe " + id);
    Gruppe gruppe = Gruppe.findById(id);
    logger.error("Fant gruppe: " + gruppe);
    render(gruppe);
  }

  public static void save(Long id, String beskrivelse, String navn, Boolean aktiv){
    logger.info("Oppdater gruppe " + id);
    Gruppe gruppe = Gruppe.findById(id);
    gruppe.navn = navn;
    gruppe.beskrivelse = beskrivelse;
    gruppe.aktiv = aktiv!=null?true:false;
    gruppe.save();
    logger.info("Lagret gruppe: " + gruppe);
    show(gruppe.id);
  }

  public static void createNew(String beskrivelse, String navn, Boolean aktiv){
    logger.info("Oppretter gruppe " + navn);
    Gruppe gruppe = new Gruppe();
    gruppe.navn = navn;
    gruppe.beskrivelse = beskrivelse;
    gruppe.aktiv = aktiv!=null?true:false;
    gruppe.save();
    logger.info("Lagret gruppe: " + gruppe);
    show(gruppe.id);
  }

  private static List<Gruppe> hentAktiveGrupper() {
    return Gruppe.find("aktiv=true").fetch();
  }
}