package com.teamtwo.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractEntry {

    protected int id;
    protected String title, descr;
    protected LocalDateTime datetime;

    public AbstractEntry() {

    }

    /**
     * This constructor is used by subclasses to construct instances with a provided id, title, and descr. The date is initialized to the current date of the creation of the object.
     *
     * @param id id associated with this entry
     * @param title title associated with this entry
     * @param descr description associated with this entry
     */
    public AbstractEntry(int id, String title, String descr) {
        this.id = id;
        this.title = title;
        this.descr = descr;
        this.datetime = LocalDateTime.now();
    }

    /**
     * This constructor is used by subclasses to construct instances with a provided id, title, descr, and date.
     *
     * @param id id to be associated with this entry
     * @param title title to be associated with this entry
     * @param descr description to be associated with this entry
     * @param datetime date this entry was created on
     * {@link AbstractEntry#id}
     * {@link AbstractEntry#title}
     * {@link AbstractEntry#descr}
     * {@link AbstractEntry#datetime}
     */
    public AbstractEntry(int id, String title, String descr, LocalDateTime datetime) {
        this.id = id;
        this.title = title;
        this.descr = descr;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
    
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntry)) return false;
        AbstractEntry that = (AbstractEntry) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(descr, that.descr) && Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, descr, datetime);
    }
}
