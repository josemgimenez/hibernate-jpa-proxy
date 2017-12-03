package com.josemgimenez.jpa.concept;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.hibernate.proxy.HibernateProxy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.josemgimenez.jpa.model.Department;
import com.josemgimenez.jpa.model.Person;

public class ProxyConceptTest {
	private final EntityManager entityManager = TestUtils.getTestEntityManager();

	private Long departmentId;
	private Long personId;

	@Before
	public void prepareTest() {
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

		entityManager.clear();
	}

	@After
	public void cleanup() {
		departmentId = null;
		personId = null;
	}

	@Test
	public void test1() {
		// First load person
		final Person person = entityManager.find(Person.class, personId);

		Assert.assertNotNull("Person with id " + personId + " not found", person);
		assertEquals(personId, person.getId());

		final Department department = person.getDepartment();
		Assert.assertNotNull(department);
		assertEquals(departmentId, department.getId());

		Assert.assertTrue("Department is instanceof HibernateProxy " + department.getClass(),
				department instanceof HibernateProxy);
	}

	@Test
	public void test2() {
		// First load department
		final Department department = entityManager.find(Department.class, departmentId);
		Assert.assertNotNull(department);
		assertEquals(departmentId, department.getId());

		final Person person = entityManager.find(Person.class, personId);
		Assert.assertNotNull("Person with id " + personId + " not found", person);
		assertEquals(personId, person.getId());

		Assert.assertTrue("Department is not instanceof HibernateProxy " + department.getClass(),
				department instanceof HibernateProxy);
	}
}
