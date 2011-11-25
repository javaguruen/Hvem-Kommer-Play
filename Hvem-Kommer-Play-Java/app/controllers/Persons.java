package controllers;

import play.mvc.Before;


public class Persons extends CRUD {
   @Before
   public static void addFilter() {
       request.args.put("where", "aktiv=true");
   }
}