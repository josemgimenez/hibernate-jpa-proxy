package com.josemgimenez.jpa.concept;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.josemgimenez.jpa.model.Person;

public class ProxyConceptTest {

	private EntityManager entityManager;

	@Before
	public void prepareTest() {
		EntityManagerFactory createEntityManagerFactory = Persistence.createEntityManagerFactory("jpaData");
		entityManager = createEntityManagerFactory.createEntityManager();
	}

	@Test
	public void test1() {
		Person p1 = new Person();

		entityManager.persist(p1);
	}
}
