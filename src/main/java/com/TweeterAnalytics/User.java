package com.TweeterAnalytics;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;

public class User implements Cloneable, Entity {

    private BigInteger userId;
    private String userDisplayName;
    private String userScreenName;
    private String userReportedLocation;
    private String userProfileDescription;
    private String userProfileUrl;
    private int followerCount;
    private int followingCount;
    private Date accountCreationDate;
    private String accountLanguage;
    public ArrayList<Tweet> tweets;

    private String[] fields;

    public User() {

    }

    public User(String[] fields) {

        this.fields = fields;

        DateFormat df = new SimpleDateFormat();

        try {
            this.userId = new BigInteger(fields[0], 10);
        } catch(Exception e) {
            try {

                this.userId = new BigInteger(fields[0], 16);
            } catch (Exception e2) {}
        }

        try {
            this.userDisplayName = fields[1];
            this.userScreenName = fields[2];
            this.userReportedLocation = fields[3];
            this.userProfileDescription = fields[4];
            this.userProfileUrl = fields[5];
            this.followerCount = Integer.parseInt(fields[6]);
            this.followingCount = Integer.parseInt(fields[7]);
        } catch ( Exception e ) {}

        try {
            this.accountCreationDate = df.parse(fields[8]);
        } catch(Exception e) {
            try {

                this.accountCreationDate = new Date();
            } catch (Exception e2) {}
        }

        try {

            this.accountLanguage = fields[9];
        } catch (Exception e) {}

        this.tweets = new ArrayList<>();
    }

    @Override
    public BigInteger getId() {
        return this.userId;
    }

    public int getFollowerCount() { return this.followerCount; }

    @Override
    public Object clone() {
        return new User( this.fields );
    }

    public String getDescription() { return this.userProfileDescription; }
}
