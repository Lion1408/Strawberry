package com.example.strawberry.Model;

public class ReactionDTO {
    private Integer idPost, idUserReact;
    private String reactionType;

    public ReactionDTO() {
    }

    public ReactionDTO(Integer idPost, Integer idUserReact, String reactionType) {
        this.idPost = idPost;
        this.idUserReact = idUserReact;
        this.reactionType = reactionType;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Integer getIdUserReact() {
        return idUserReact;
    }

    public void setIdUserReact(Integer idUserReact) {
        this.idUserReact = idUserReact;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    @Override
    public String toString() {
        return "ReactionDTO{" +
                "idPost=" + idPost +
                ", idUserReact=" + idUserReact +
                ", reactionType='" + reactionType + '\'' +
                '}';
    }
}
