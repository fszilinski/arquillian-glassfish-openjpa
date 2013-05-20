package de.sternad.jee.daos;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.sternad.jee.daos.PersonDao;
import de.sternad.jee.entities.Person;

/**
 * 
 * This test is using the {@link PersistenceTest} annotations from Arquillian.
 * For a comprehensive overview of all Arquillian DBUnit annotations
 * 
 * @see <a href="https://docs.jboss.org/author/display/ARQ/Persistence">This
 *      link</a>
 * 
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.COMMIT)
public class PersonDaoIntegrationTest {

	@Inject
	private PersonDao personDao;

	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				// Create jar
				.addPackage(Person.class.getPackage())
				// Add classes
				.addPackage(PersonDao.class.getPackage())
				// Add more classes
				// FEST Assert is not part of Arquillian JUnit
				.addPackages(true, "org.fest")
				.addAsManifestResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml")) // cdi
				.addAsResource("META-INF/persistence.xml");
	}

	@Test
	@UsingDataSet("datasets/PersonDaoIntegrationTest.xml")
	// We already set the DataSeedStrategy to CLEAN_INSERT in arquillian.xml
	// If we hadn't, the following annotation would be necessary:
	// @SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
	public void findPersonbyId() {
		Person result = personDao.findPerson(100L);

		assertEquals("foo", result.getFirstName());
		assertEquals("bar", result.getLastName());
	}

	@Test
	@UsingDataSet("datasets/PersonDaoIntegrationTest.xml")
	public void findAll() {
		List<Person> result = personDao.findAll();

		assertEquals(2, result.size());
		assertEquals("foo", result.get(0).getFirstName());
		assertEquals("bar", result.get(0).getLastName());
		assertEquals("duke", result.get(1).getFirstName());
		assertEquals("java", result.get(1).getLastName());
	}

	@Test
	@UsingDataSet("datasets/PersonDaoIntegrationTest.xml")
	@ShouldMatchDataSet(value = "datasets/PersonDaoIntegrationTest_after.xml", excludeColumns = { "id" })
	public void save() {
		Person p = new Person();
		p.setFirstName("firstname");
		p.setLastName("lastname");
		personDao.save(p);
	}

	/**
	 * Same as {@link #save()}, but with files that adhere to the Arquillian
	 * DBUnit naming conventions. Therefore, they are detected automatically. No
	 * need to provide file names.
	 * 
	 * Also, the ShouldMatchDataSet annotation excludes the id column in this
	 * case because it is database generated and may not be predictable.
	 */
	@Test
	@UsingDataSet
	@ShouldMatchDataSet(excludeColumns = { "id" })
	public void saveWithAutoNaming() {
		Person p = new Person();
		p.setFirstName("Donald");
		p.setLastName("Duck");
		personDao.save(p);
	}
}
