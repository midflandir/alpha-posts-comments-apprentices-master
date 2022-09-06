package com.posada.santiago.alphapostsandcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class CommentAdded extends DomainEvent {

    private String id;
    private String author;
    private String content;

    private String font;


    public CommentAdded(String id, String author, String content, String font) {
        super("posada.santiago.commentcreated");
        this.id = id;
        this.author = author;
        this.content = content;
        this.font = font;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getFont() {
        return font;
    }
}
