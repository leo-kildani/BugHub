package com.teamtwo.entity;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractEntry {

    protected int id;
    protected String title, descr;
    protected LocalDate date;

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
        this.date = LocalDate.now();
    }

    /**
     * This constructor is used by subclasses to construct instances with a provided id, title, descr, and date.
     *
     * @param id id to be associated with this entry
     * @param title title to be associated with this entry
     * @param descr description to be associated with this entry
     * @param date date this entry was created on
     * {@link AbstractEntry#id}
     * {@link AbstractEntry#title}
     * {@link AbstractEntry#descr}
     * {@link AbstractEntry#date}
     */
    public AbstractEntry(int id, String title, String descr, LocalDate date) {
        this.id = id;
        this.title = title;
        this.descr = descr;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntry)) return false;
        AbstractEntry that = (AbstractEntry) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(descr, that.descr) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, descr, date);
    }
}
