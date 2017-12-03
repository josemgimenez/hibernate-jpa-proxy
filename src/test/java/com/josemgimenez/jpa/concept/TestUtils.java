package com.josemgimenez.jpa.concept;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Test utilities.
 * 
 * @author josemgimenez
 *
 */
public class TestUtils {
	private static EntityManager entityManager;

	// Nobody needs an instance
	private TestUtils() {

	}

	public static EntityManager getTestEntityManager() {
		if (entityManager == null) {
			Map<String, String> properties = new HashMap<>();

			properties.put("javax.persistence.jdbc.driver", "org.h2.Driver");
			properties.put("javax.persistence.jdbc.user", "sa");
			properties.put("javax.persistence.jdbc.password", "");
			properties.put("javax.persistence.jdbc.url", "jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000");

			properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			properties.put("hibernate.hbm2ddl.auto", "create");
			properties.put("hibernate.provider", "org.hibernate.ejb.HibernatePersistence");

			EntityManagerFactory createEntityManagerFactory = Persistence.createEntityManagerFactory("jpaData",
					properties);
			entityManager = createEntityManagerFactory.createEntityManager();
		}

		return entityManager;
	}
}
