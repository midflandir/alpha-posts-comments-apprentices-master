package com.posada.santiago.alphapostsandcomments.domain.commands;

import co.com.sofka.domain.generic.Command;

public class AddCommentCommand extends Command {

    private String postId;
    private String commentId;
    private String author;
    private String content;
    private String font;

    public AddCommentCommand() {
    }

    public AddCommentCommand(String postId, String commentId, String author, String content, String font) {
        this.postId = postId;
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.font = font;
    }

    public String getPostId() {
        return postId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getAuthor() {
        return author;
    }

    public String getFont(){
        return font;
}
    public String getContent() {
        return content;
    }

}
