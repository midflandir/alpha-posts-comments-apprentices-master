package com.posada.santiago.alphapostsandcomments.domain;

import co.com.sofka.domain.generic.Entity;
import com.posada.santiago.alphapostsandcomments.domain.values.Author;
import com.posada.santiago.alphapostsandcomments.domain.values.CommentId;
import com.posada.santiago.alphapostsandcomments.domain.values.Content;
import com.posada.santiago.alphapostsandcomments.domain.values.Font;

import java.util.Objects;

public class Comment extends Entity<CommentId> {

    private Author author;
    private Content content;

    private Font font;
    public Comment(CommentId entityId, Author author, Content content, Font font) {
        super(entityId);
        this.font = font;
        this.author = author;
        this.content = content;
    }

    //////////////////////
    public void ChangeFont(Font font){
        this.font = Objects.requireNonNull(font);
    }
//////////////////////
    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }

    public Font font() {
        return font;
    }
}
