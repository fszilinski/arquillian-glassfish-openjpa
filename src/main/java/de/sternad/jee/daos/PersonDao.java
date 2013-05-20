package de.sternad.jee.daos;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.sternad.jee.entities.Person;

@Stateless
public class PersonDao {

	@PersistenceContext
	private EntityManager em;

	public Person findPerson(long id) {
		return em.find(Person.class, id);
	}

	public void save(Person p) {
		em.persist(p);
	}

	public List<Person> findAll() {
		return em.createNamedQuery(Person.QUERYNAME_GET_ALL_PERSONS,
				Person.class).getResultList();
	}

}
