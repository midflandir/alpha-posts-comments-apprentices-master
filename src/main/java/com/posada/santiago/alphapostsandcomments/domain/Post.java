package com.posada.santiago.alphapostsandcomments.domain;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.domain.values.Author;
import com.posada.santiago.alphapostsandcomments.domain.values.CommentId;
import com.posada.santiago.alphapostsandcomments.domain.values.Content;
import com.posada.santiago.alphapostsandcomments.domain.values.Font;
import com.posada.santiago.alphapostsandcomments.domain.values.PostId;
import com.posada.santiago.alphapostsandcomments.domain.values.Title;
import com.posada.santiago.alphapostsandcomments.domain.events.FontChanged;
import com.posada.santiago.alphapostsandcomments.domain.events.PostCreated;
import com.posada.santiago.alphapostsandcomments.domain.events.CommentAdded;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Post extends AggregateEvent<PostId> {

    protected Title title;

    protected Author author;

    protected List<Comment> comments;

    public Post(PostId entityId, Title title, Author author) {
        super(entityId);
        subscribe(new PostChange(this));
        appendChange(new PostCreated(title.value(), author.value())).apply();
    }


    private Post(PostId id){
        super(id);
        subscribe(new PostChange(this));
    }

    public static Post from(PostId id, List<DomainEvent> events){
        Post post = new Post(id);
        events.forEach(event -> post.applyEvent(event));
        return post;
    }

    public void addAComment(CommentId id, Author author, Content content, Font font){
        Objects.requireNonNull(id);
        Objects.requireNonNull(author);
        Objects.requireNonNull(content);
        Objects.requireNonNull((font));
        appendChange(new CommentAdded(id.value(), author.value(), content.value(), font.value())).apply();
    }

    public Optional<Comment> getCommentById(CommentId id){
    Objects.requireNonNull(id);
        return comments.stream().filter((comment -> comment.identity().equals(id))).findFirst();
    }

    public void Changefont(CommentId id, Font font){
        Objects.requireNonNull(id);
        Objects.requireNonNull((font));
        appendChange(new FontChanged(id.value(), font.value())).apply();
    }

}
