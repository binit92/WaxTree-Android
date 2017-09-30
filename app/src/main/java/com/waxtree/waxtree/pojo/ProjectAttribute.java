package com.waxtree.waxtree.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by inbkumar01 on 9/26/2017.
 */

public class ProjectAttribute implements Parcelable {
    String title;
    String desc;
    String link;
    String image;
    String start_date;
    String end_date;
    String email;
    String type;

    public ProjectAttribute() {

    }

    public ProjectAttribute(String title, String desc, String link, String image, String startDate, String endDate, String email, String type) {
        this.title = title;
        this.desc = desc;
        this.link = link;
        this.image = image;
        this.start_date = startDate;
        this.end_date = endDate;
        this.email = email;
        this.type = type;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String startDate) {
        this.start_date = startDate;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String endDate) {
        this.end_date = endDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.desc);
        parcel.writeString(this.link);
        parcel.writeString(this.image);
        parcel.writeString(this.start_date);
        parcel.writeString(this.end_date);
        parcel.writeString(this.email);
        parcel.writeString(this.type);
    }

    protected ProjectAttribute(Parcel in) {
        this.title = in.readString();
        this.desc = in.readString();
        this.link = in.readString();
        this.image = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.email = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<ProjectAttribute> CREATOR = new Parcelable.Creator<ProjectAttribute>() {
        @Override
        public ProjectAttribute createFromParcel(Parcel source) {
            return new ProjectAttribute(source);
        }

        @Override
        public ProjectAttribute[] newArray(int size) {
            return new ProjectAttribute[size];
        }
    };
}
