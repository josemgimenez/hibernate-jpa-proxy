package com.josemgimenez.jpa.concept;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josemgimenez.jpa.model.Department;
import com.josemgimenez.jpa.model.Person;

public class ProxyConceptTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProxyConceptTest.class);

	private final EntityManager entityManager = TestUtils.getTestEntityManager();

	private Long departmentId;
	private Long personId;

	// ------------------------------------------------------------------------

	@Before
	public void prepareTest() {
		LOGGER.info("HEY");
		entityManager.getTransaction().begin();

		Department department = new Department();
		department.setName("default department");
		entityManager.persist(department);

		Person person = new Person();
		person.setDepartment(department);
		person.setName("default name");

		entityManager.persist(person);

		departmentId = department.getId();
		personId = person.getId();

		entityManager.getTransaction().commit();

		// detach everything
		entityManager.clear();
	}

	// ------------------------------------------------------------------------

	@After
	public void cleanup() {
		departmentId = null;
		personId = null;
	}

	// ------------------------------------------------------------------------

	@Test
	public void test1() {
		// First load person
		final Person person = entityManager.find(Person.class, personId);
		final Department department = person.getDepartment();

		validateEntities(person, department);
	}

	// ------------------------------------------------------------------------

	@Test
	public void test2() {
		// First load department
		final Department department = entityManager.find(Department.class, departmentId);
		final Person person = entityManager.find(Person.class, personId);

		validateEntities(person, department);
	}

	// ------------------------------------------------------------------------

	private void validateEntities(final Person person, final Department department) {
		Assert.assertNotNull(person);
		assertEquals(personId, person.getId());

		Assert.assertNotNull(department);
		assertEquals(departmentId, department.getId());

		final Long reflectionPersonId = TestUtils.getId(person, Person.class);
		final Long reflectionDepartmentId = TestUtils.getId(department, Department.class);

		assertEquals(personId, reflectionPersonId);
		assertEquals(departmentId, reflectionDepartmentId);
	}

}
