package com.waxtree.waxtree.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by inbkumar01 on 9/26/2017.
 */

public class Project implements Parcelable {

    String projectName;
    ProjectAttribute projectAttribute;

    public Project(){}

    public Project(String projectName, ProjectAttribute projectAttribute){
        this.projectName = projectName;
        this.projectAttribute = projectAttribute;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectAttribute getProjectAttribute() {
        return projectAttribute;
    }

    public void setProjectAttribute(ProjectAttribute projectAttribute) {
        this.projectAttribute = projectAttribute;
    }

    protected Project(Parcel in) {
        projectName = in.readString();
        projectAttribute = (ProjectAttribute) in.readValue(ProjectAttribute.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(projectName);
        parcel.writeValue(projectAttribute);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
