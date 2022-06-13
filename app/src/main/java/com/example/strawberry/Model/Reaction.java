package com.example.strawberry.Model;

public class Reaction {
    private Integer LOVE, ALL, HAHA, LIKE, SAD, ANGRY, CARE, WOW;

    public Reaction() {
    }

    public Reaction(Integer LOVE, Integer ALL, Integer HAHA, Integer LIKE, Integer SAD, Integer ANGRY, Integer CARE, Integer WOW) {
        this.LOVE = LOVE;
        this.ALL = ALL;
        this.HAHA = HAHA;
        this.LIKE = LIKE;
        this.SAD = SAD;
        this.ANGRY = ANGRY;
        this.CARE = CARE;
        this.WOW = WOW;
    }

    public Integer getLOVE() {
        return LOVE;
    }

    public void setLOVE(Integer LOVE) {
        this.LOVE = LOVE;
    }

    public Integer getALL() {
        return ALL;
    }

    public void setALL(Integer ALL) {
        this.ALL = ALL;
    }

    public Integer getHAHA() {
        return HAHA;
    }

    public void setHAHA(Integer HAHA) {
        this.HAHA = HAHA;
    }

    public Integer getLIKE() {
        return LIKE;
    }

    public void setLIKE(Integer LIKE) {
        this.LIKE = LIKE;
    }

    public Integer getSAD() {
        return SAD;
    }

    public void setSAD(Integer SAD) {
        this.SAD = SAD;
    }

    public Integer getANGRY() {
        return ANGRY;
    }

    public void setANGRY(Integer ANGRY) {
        this.ANGRY = ANGRY;
    }

    public Integer getCARE() {
        return CARE;
    }

    public void setCARE(Integer CARE) {
        this.CARE = CARE;
    }

    public Integer getWOW() {
        return WOW;
    }

    public void setWOW(Integer WOW) {
        this.WOW = WOW;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "LOVE=" + LOVE +
                ", ALL=" + ALL +
                ", HAHA=" + HAHA +
                ", LIKE=" + LIKE +
                ", SAD=" + SAD +
                ", ANGRY=" + ANGRY +
                ", CARE=" + CARE +
                ", WOW=" + WOW +
                '}';
    }
}
