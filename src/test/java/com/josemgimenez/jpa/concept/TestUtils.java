package com.josemgimenez.jpa.concept;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.JPA_JDBC_DRIVER;
import static org.hibernate.cfg.AvailableSettings.JPA_JDBC_PASSWORD;
import static org.hibernate.cfg.AvailableSettings.JPA_JDBC_URL;
import static org.hibernate.cfg.AvailableSettings.JPA_JDBC_USER;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test utilities.
 *
 * @author josemgimenez
 *
 */
public class TestUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class);

	private static EntityManager entityManager;

	// Nobody needs an instance
	private TestUtils() {

	}

	static EntityManager getTestEntityManager() {
		if (entityManager == null) {
			Map<String, String> properties = new HashMap<>();

			properties.put(JPA_JDBC_DRIVER, "org.h2.Driver");
			properties.put(JPA_JDBC_USER, "sa");
			properties.put(JPA_JDBC_PASSWORD, "");
			properties.put(JPA_JDBC_URL, "jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000");

			properties.put(DIALECT, "org.hibernate.dialect.H2Dialect");
			properties.put(HBM2DDL_AUTO, "create");

			EntityManagerFactory createEntityManagerFactory = Persistence.createEntityManagerFactory("jpaData",
					properties);
			entityManager = createEntityManagerFactory.createEntityManager();
		}

		return entityManager;
	}

	/**
	 * Extracts the value of the field id.
	 *
	 * @param entity
	 *            entity to use.
	 * @param clazz
	 *            class of this entity.
	 * @return the id if exists or <code>null</code> if an error has happened whilst
	 *         extracting the id.
	 * @throws IllegalArgumentException
	 *             if it's not possible to access the field id.
	 */
	static <T> Long getId(final T entity, Class<T> clazz) throws IllegalArgumentException {
		try {
			Field field = clazz.getDeclaredField("id");
			field.setAccessible(true);

			return (Long) field.get(entity);
		} catch (Exception e) {
			String errorMessage = "Error getting the id from " + entity + " class: " + clazz;
			LOGGER.error(errorMessage, e);
		}
		return null;
	}
}
