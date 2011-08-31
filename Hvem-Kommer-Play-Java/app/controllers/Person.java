package controllers;

import models.Trening;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.*;


//import models.*;

public class Person extends Controller {

    public static void ny(@Required models.Person person) {
        if( params.get("lagre") != null ){
            person.save();
            String melding = "Lagret person: " + person;
            render( melding );
        }
        render();
    }

    public static List<Trening> getDummyTreninger() {
        List<Trening> treninger = new ArrayList<Trening>();
        GregorianCalendar calendar = new GregorianCalendar();
        for( int i=0; i<4; i++){
            calendar.add(Calendar.DATE, i*7);
            Date dato = calendar.getTime();
            Trening trening = new Trening(dato, "19-20", "Bønes");
            trening.id = (long)i;
            treninger.add( trening );
        }
        return treninger;
    }
}