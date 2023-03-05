package edu.northeastern.pokedex.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ParcelableUser implements Parcelable {
    private final String email;
    private final String name;
    private final String uid;

    public ParcelableUser(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.uid = user.getUid();
    }

    protected ParcelableUser(Parcel in) {
        email = in.readString();
        name = in.readString();
        uid = in.readString();
    }

    public static final Creator<ParcelableUser> CREATOR = new Creator<ParcelableUser>() {
        @Override
        public ParcelableUser createFromParcel(Parcel in) {
            return new ParcelableUser(in);
        }

        @Override
        public ParcelableUser[] newArray(int size) {
            return new ParcelableUser[size];
        }
    };

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getUid() {
        return this.uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(uid);
    }
}