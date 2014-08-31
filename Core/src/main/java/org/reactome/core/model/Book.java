package org.reactome.core.model;

// Generated Jul 8, 2011 1:48:55 PM by Hibernate Tools 3.4.0.CR1

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Book generated by hbm2java
 */
@XmlRootElement
public class Book extends Publication {

    private String isbn;
    private String chapterTitle;
    private String pages;
    private Integer publisher;
    private String publisherClass;
    private Integer year;

    public Book() {
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getChapterTitle() {
        return this.chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getPages() {
        return this.pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Integer getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public String getPublisherClass() {
        return this.publisherClass;
    }

    public void setPublisherClass(String publisherClass) {
        this.publisherClass = publisherClass;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}