package ru.itmo.service;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class Db {
    private final EntityManagerFactory factory = createFactory();

    private EntityManagerFactory createFactory() {
        Map<String, String> settings = new HashMap<>();
        settings.put("javax.persistence.jdbc.url", "jdbc:postgresql://" + env("DB_HOST", "pg") + ":5432/" + env("DB_NAME", "studs"));
        settings.put("javax.persistence.jdbc.user", env("DB_USER", "s465987"));
        settings.put("javax.persistence.jdbc.password", env("DB_PASSWORD", ""));
        return Persistence.createEntityManagerFactory("dragon", settings);
    }

    private String env(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isEmpty() ? fallback : value;
    }

    public <T> T read(Function<EntityManager, T> work) {
        EntityManager em = factory.createEntityManager();
        try { return work.apply(em); }
        finally { em.close(); }
    }

    public <T> T write(Function<EntityManager, T> work) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            T result = work.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @PreDestroy
    public void close() { factory.close(); }
}
