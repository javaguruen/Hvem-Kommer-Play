package controllers;

import models.Gruppe;

import java.util.List;

public class Gruppes extends CRUD {

  public static void velgGruppe(){
    List<Gruppe> grupper = hentAktiveGrupper();

  }

  private static List<Gruppe> hentAktiveGrupper() {
    return Gruppe.find("aktiv=true").fetch();
  }
}