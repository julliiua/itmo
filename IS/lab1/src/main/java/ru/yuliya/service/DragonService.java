package ru.yuliya.service;

import ru.yuliya.model.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("dragonService")
@ApplicationScoped
public class DragonService {
    @Inject private Db db;

    public List<Dragon> list(String text, String column, String order, int page) {
        String field = "name";
        if ("color".equals(order)) field = "color";
        if ("killer".equals(order)) field = "killerName";
        if ("id".equals(order)) field = "id";
        final String sort = "killerName".equals(field) ? "k.name" : "d." + field;
        String filter = "lower(d.name)";
        if ("color".equals(column)) filter = "lower(str(d.color))";
        if ("killer".equals(column)) filter = "lower(k.name)";
        final String where = filter;
        return db.read(em -> em.createQuery("select d from Dragon d left join fetch d.cave left join fetch d.killer k where " + where + " like :text order by " + sort, Dragon.class)
                .setParameter("text", "%" + (text == null ? "" : text.toLowerCase()) + "%")
                .setFirstResult(Math.max(0, page) * 10).setMaxResults(10).getResultList());
    }

    public long count(String text, String column) {
        String filter = "lower(d.name)";
        if ("color".equals(column)) filter = "lower(str(d.color))";
        if ("killer".equals(column)) filter = "lower(k.name)";
        final String where = filter;
        return db.read(em -> em.createQuery("select count(d) from Dragon d left join d.killer k where " + where + " like :text", Long.class)
                .setParameter("text", "%" + (text == null ? "" : text.toLowerCase()) + "%").getSingleResult());
    }

    public Dragon find(Long id) { return db.read(em -> em.find(Dragon.class, id)); }
    public List<DragonCave> caves() { return db.read(em -> em.createQuery("select c from DragonCave c order by c.id", DragonCave.class).getResultList()); }
    public List<Person> people() { return db.read(em -> em.createQuery("select p from Person p order by p.id", Person.class).getResultList()); }

    public void save(Dragon dragon, Long caveId, Long killerId) {
        db.write(em -> {
            dragon.setCave(em.getReference(DragonCave.class, caveId));
            dragon.setKiller(killerId == null ? null : em.getReference(Person.class, killerId));
            if (dragon.getId() == null) em.persist(dragon); else em.merge(dragon);
            return null;
        });
    }

    public void delete(Long id) {
        db.write(em -> {
            Dragon dragon = em.find(Dragon.class, id);
            if (dragon != null) {
                long links = em.createQuery("select count(t) from Team t where t.dragon = :dragon", Long.class)
                        .setParameter("dragon", dragon).getSingleResult();
                if (links > 0) throw new IllegalStateException("Дракон связан с командой. Удаление невозможно.");
                em.remove(dragon);
            }
            return null;
        });
    }

    public void addCave(Double treasures) {
        db.write(em -> { DragonCave cave = new DragonCave(); cave.setNumberOfTreasures(treasures); em.persist(cave); return null; });
    }

    public void addPerson(Person person) { db.write(em -> { em.persist(person); return null; }); }

    public int deleteAge(Integer age) {
        return db.write(em -> ((Number) em.createNativeQuery("select delete_dragons_by_age(?1)").setParameter(1, age).getSingleResult()).intValue());
    }

    public Double average() {
        return db.read(em -> { Number n = (Number) em.createNativeQuery("select average_wingspan()").getSingleResult(); return n == null ? null : n.doubleValue(); });
    }

    public List<Dragon> starts(String prefix) {
        return db.read(em -> em.createNativeQuery("select * from dragons_starting_with(?1)", Dragon.class)
                .setParameter(1, prefix).getResultList());
    }

    public void kill(Long id) {
        db.write(em -> { em.createNativeQuery("select kill_dragon(?1)").setParameter(1, id).getSingleResult(); return null; });
    }

    public void addTeam(String name, Long dragonId) {
        db.write(em -> { em.createNativeQuery("select create_team(?1, ?2)").setParameter(1, name).setParameter(2, dragonId).getSingleResult(); return null; });
    }
}
