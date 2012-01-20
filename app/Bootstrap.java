import models.Aktivitet;
import models.Deltakelse;
import models.Gruppe;
import models.Person;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
  public void doJob() {
    Deltakelse.deleteAll();
    Aktivitet.deleteAll();
    Person.deleteAll();
    Gruppe.deleteAll();

    Logger.info("Antall personer :" + Person.count());
    if(Person.count() == 0) {
      Fixtures.deleteAllModels();
      Fixtures.loadModels("initial-data.yml");
    }
  }
}