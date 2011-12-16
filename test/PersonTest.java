import models.Person;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersonTest {
    @Test
    public void createAndRetrieveUser() {
        new Person("Bjørn", "Hamre", "bjorn.hamre@gmail.com", "41434500").save();
        Person person = Person.find("byEpost", "bjorn.hamre@gmail.com").first();
        assertNotNull(person);
        assertEquals("Bjørn", person.fornavn);
    }

}
