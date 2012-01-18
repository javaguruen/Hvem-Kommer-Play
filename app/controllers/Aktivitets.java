package controllers;


import play.db.Model;
import play.exceptions.TemplateNotFoundException;

public class Aktivitets extends CRUD {
    /*@Before
    public static void addFilter() {
        request.args.put("where", "aktiv=true");
    }*/
    public static void show(String id) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        try {
            render(type, object);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object);
        }
    }

}