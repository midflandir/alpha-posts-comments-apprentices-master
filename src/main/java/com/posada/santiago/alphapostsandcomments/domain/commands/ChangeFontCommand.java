package com.posada.santiago.alphapostsandcomments.domain.commands;

import co.com.sofka.domain.generic.Command;

public class ChangeFontCommand extends Command {

    private String postId;
    private String commentId;
    private String font;


    public ChangeFontCommand(String postId, String commentId, String font) {
        this.postId = postId;
        this.commentId = commentId;
        this.font = font;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getPostId() {
        return postId;
    }
}
