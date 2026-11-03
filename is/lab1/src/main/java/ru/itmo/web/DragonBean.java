package ru.itmo.web;

import ru.itmo.model.*;
import ru.itmo.service.DragonService;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="dragonBean")
@SessionScoped
public class DragonBean implements Serializable {
    @ManagedProperty(value="#{dragonService}")
    private DragonService service;

    public void setService(DragonService service) { this.service = service; }
    private Dragon dragon = fresh();
    private Long caveId;
    private Long killerId;
    private String text = "";
    private String order = "id";
    private String column = "name";
    private int page;
    private Integer age;
    private String prefix = "";
    private String teamName;
    private Long teamDragonId;
    private Double treasures;
    private Person person = new Person();
    private List<Dragon> results;
    private String result;

    private Dragon fresh() {
        Dragon d = new Dragon();
        d.setCoordinates(new Coordinates());
        d.setHead(new DragonHead());
        d.setAlive(true);
        return d;
    }

    private void message(String value) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(value));
    }

    private String error(Exception e) {
        Throwable last = e;
        while (last.getCause() != null) last = last.getCause();
        return last.getMessage() == null ? "Ошибка базы данных" : last.getMessage();
    }

    public String create() { dragon = fresh(); caveId = null; killerId = null; return "form.xhtml?faces-redirect=true"; }
    public String show(Long id) { dragon = service.find(id); return "view.xhtml?faces-redirect=true"; }
    public String edit(Long id) {
        dragon = service.find(id);
        caveId = dragon.getCave().getId();
        killerId = dragon.getKiller() == null ? null : dragon.getKiller().getId();
        return "form.xhtml?faces-redirect=true";
    }

    public String save() {
        try {
            if (dragon.getName() == null || dragon.getName().trim().isEmpty()) throw new IllegalArgumentException("Укажите имя дракона");
            if (caveId == null) throw new IllegalArgumentException("Выберите пещеру");
            if (dragon.getCoordinates().getX() > 689) throw new IllegalArgumentException("X не должен быть больше 689");
            if (dragon.getCoordinates().getY() == null || dragon.getCoordinates().getY() > 310) throw new IllegalArgumentException("Y обязателен и не должен быть больше 310");
            if (dragon.getAge() != null && dragon.getAge() <= 0) throw new IllegalArgumentException("Возраст должен быть больше 0");
            if (dragon.getWingspan() <= 0 || dragon.getWeight() <= 0) throw new IllegalArgumentException("Размах крыльев и вес должны быть больше 0");
            service.save(dragon, caveId, killerId);
            return "index.xhtml?faces-redirect=true";
        } catch (Exception e) { message(error(e)); return null; }
    }

    public String delete(Long id) {
        try { service.delete(id); return "index.xhtml?faces-redirect=true"; }
        catch (Exception e) { message(error(e)); return null; }
    }

    public List<Dragon> getRows() { return service.list(text, column, order, page); }
    public long getCount() { return service.count(text, column); }
    public boolean isPrevious() { return page > 0; }
    public boolean isNext() { return (page + 1) * 10 < getCount(); }
    public void back() { if (page > 0) page--; }
    public void next() { if (isNext()) page++; }
    public void filter() { page = 0; }
    public List<DragonCave> getCaves() { return service.caves(); }
    public List<Person> getPeople() { return service.people(); }

    public void addCave() {
        try {
            if (treasures != null && treasures <= 0) throw new IllegalArgumentException("Число сокровищ должно быть больше 0");
            service.addCave(treasures); treasures = null; message("Пещера добавлена");
        } catch (Exception e) { message(error(e)); }
    }

    public void addPerson() {
        try {
            if (person.getName() == null || person.getName().trim().isEmpty() || person.getEyeColor() == null || person.getLocation() == null || person.getLocation().getX() == null || person.getWeight() == null || person.getWeight() <= 0 || person.getNationality() == null)
                throw new IllegalArgumentException("Заполните обязательные поля человека; вес должен быть больше 0");
            service.addPerson(person); person = new Person(); person.setLocation(new Location()); message("Человек добавлен");
        } catch (Exception e) { message(error(e)); }
    }

    public void removeAge() { try { result = "Удалено: " + service.deleteAge(age); } catch (Exception e) { message(error(e)); } }
    public void average() { try { result = "Средний размах: " + service.average(); } catch (Exception e) { message(error(e)); } }
    public void starts() { try { results = service.starts(prefix); result = "Найдено: " + results.size(); } catch (Exception e) { message(error(e)); } }
    public void kill() { try { service.kill(teamDragonId); result = "Дракон убит"; } catch (Exception e) { message(error(e)); } }
    public void addTeam() { try { service.addTeam(teamName, teamDragonId); result = "Команда создана"; } catch (Exception e) { message(error(e)); } }

    public Dragon getDragon() {
        if (dragon != null && dragon.getId() != null && FacesContext.getCurrentInstance().getViewRoot().getViewId().endsWith("view.xhtml")) dragon = service.find(dragon.getId());
        return dragon;
    }
    public void setDragon(Dragon dragon) { this.dragon = dragon; }
    public Long getCaveId() { return caveId; }
    public void setCaveId(Long caveId) { this.caveId = caveId; }
    public Long getKillerId() { return killerId; }
    public void setKillerId(Long killerId) { this.killerId = killerId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getColumn() { return column; }
    public void setColumn(String column) { this.column = column; }
    public String getOrder() { return order; }
    public void setOrder(String order) { this.order = order; }
    public int getPage() { return page; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public Long getTeamDragonId() { return teamDragonId; }
    public void setTeamDragonId(Long teamDragonId) { this.teamDragonId = teamDragonId; }
    public Double getTreasures() { return treasures; }
    public void setTreasures(Double treasures) { this.treasures = treasures; }
    public Person getPerson() { if (person.getLocation() == null) person.setLocation(new Location()); return person; }
    public void setPerson(Person person) { this.person = person; }
    public List<Dragon> getResults() { return results; }
    public String getResult() { return result; }
    public Color[] getColors() { return Color.values(); }
    public Country[] getCountries() { return Country.values(); }
}
