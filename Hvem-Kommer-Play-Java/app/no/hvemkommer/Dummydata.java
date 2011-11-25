package no.hvemkommer;

import models.Person;
import models.Trening;
import no.hvemkommer.Deltakerstatus;

import java.util.*;

public class Dummydata {

  public Map<Deltakerstatus, List<Person>> getDummeDeltakelse(){
    Map<Deltakerstatus, List<Person>> deltakerstatuser = new HashMap<Deltakerstatus, List<Person>>();
    List<Person> kommer = new ArrayList<Person>();
    kommer.add( createPerson(1L, "Bjørn", "Hamre", "bjorn.hamre@gmail.com", "41434500" ));
    kommer.add( createPerson(2L, "Glenn", "Jung", "glennjung@gmail.com", "22222222" ));

    List<Person> kommerIkke = new ArrayList<Person>();
    kommerIkke.add( createPerson(3L, "Robert", "Hetleflåt", "robert@gmail.com", "33333333" ));
    kommerIkke.add( createPerson(4L, "Rune", "Jung", "runejung@gmail.com", "44444444" ));

    List<Person> vetIkke = new ArrayList<Person>();
    vetIkke.add( createPerson(5L, "Erik", "Aasmul", "erik@gmail.com", "55555555" ));
    vetIkke.add( createPerson(6L, "Bjarte", "Aasmul", "bjarte@gmail.com", "66666666" ));

    deltakerstatuser.put(Deltakerstatus.Ja, kommer);
    deltakerstatuser.put(Deltakerstatus.Nei, kommerIkke);

    return deltakerstatuser;
  }

  private Person createPerson(long id, String fornavn, String etternavn, String epost, String mobilnr) {
    Person p = new Person(fornavn, etternavn, epost, mobilnr);
    p.id = id;
    return p;
  }

  public List<Trening> getDummyTreninger() {
    List<Trening> treninger = new ArrayList<Trening>();
    GregorianCalendar calendar = new GregorianCalendar();
    for (int i = 0; i < 4; i++) {
      calendar.add(Calendar.DATE, i * 7);
      Date dato = calendar.getTime();
      Trening trening = new Trening(dato, "19-20", "Bønes");
      trening.id = (long) i;
      treninger.add(trening);
    }
    return treninger;
  }
}
