package controllers;

import models.Person;
import models.Trening;
import play.data.validation.Required;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.Controller;
import play.*;
import play.mvc.*;

import java.util.*;


//import models.*;

public class Persons extends CRUD {

  public static void listXml() {
    List<Person> persons = Person.findAll();
    Logger.info("er innne i listXml. Antall personer=" + persons.size());
    render(persons);
  }
}