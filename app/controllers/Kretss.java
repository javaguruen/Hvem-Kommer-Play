package controllers;

import models.Krets;
import models.Trening;

import java.util.List;

public class Kretss extends CRUD {

  public static void velgKrets(){
    List<Krets> kretser = hentAktiveKretser();

  }

  private static List<Krets> hentAktiveKretser() {
    return Krets.find("aktiv=true").fetch();
  }
}