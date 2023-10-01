package com.teamtwo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@Data public abstract class AbstractEntry {

    protected int id;
    protected String title, descr;
    protected LocalDate date;

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
}
