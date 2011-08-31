package controllers;

import models.Trening;
import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

//import models.*;

public class Application extends Controller {


    public static void endreAktivTrening(@Required String trening){
        renderText("Ny trening: " + trening);
    }
    public static void index() {

        List<Trening> treninger = getDummyTreninger();
        render(treninger);
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