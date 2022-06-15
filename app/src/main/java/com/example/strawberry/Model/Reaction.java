package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reaction implements Parcelable {
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

    protected Reaction(Parcel in) {
        if (in.readByte() == 0) {
            LOVE = null;
        } else {
            LOVE = in.readInt();
        }
        if (in.readByte() == 0) {
            ALL = null;
        } else {
            ALL = in.readInt();
        }
        if (in.readByte() == 0) {
            HAHA = null;
        } else {
            HAHA = in.readInt();
        }
        if (in.readByte() == 0) {
            LIKE = null;
        } else {
            LIKE = in.readInt();
        }
        if (in.readByte() == 0) {
            SAD = null;
        } else {
            SAD = in.readInt();
        }
        if (in.readByte() == 0) {
            ANGRY = null;
        } else {
            ANGRY = in.readInt();
        }
        if (in.readByte() == 0) {
            CARE = null;
        } else {
            CARE = in.readInt();
        }
        if (in.readByte() == 0) {
            WOW = null;
        } else {
            WOW = in.readInt();
        }
    }

    public static final Creator<Reaction> CREATOR = new Creator<Reaction>() {
        @Override
        public Reaction createFromParcel(Parcel in) {
            return new Reaction(in);
        }

        @Override
        public Reaction[] newArray(int size) {
            return new Reaction[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (LOVE == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(LOVE);
        }
        if (ALL == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(ALL);
        }
        if (HAHA == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(HAHA);
        }
        if (LIKE == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(LIKE);
        }
        if (SAD == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(SAD);
        }
        if (ANGRY == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(ANGRY);
        }
        if (CARE == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(CARE);
        }
        if (WOW == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(WOW);
        }
    }
}
