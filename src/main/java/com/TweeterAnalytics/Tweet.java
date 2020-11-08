package com.TweeterAnalytics;

import java.math.BigInteger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Tweet implements Entity {

    private BigInteger tweetId;
    private BigInteger userId;
    private String tweetLanguage;
    private int tweetText;
    private LocalTime tweetTime;
    private String tweetClientName;
    private BigInteger inReplyToTweetId;
    private BigInteger quotedTweetId;
    private BigInteger retweetTweetId;
    private float latitude;
    private float longitude;
    private int quoteCount;
    private int replyCount;
    private int likeCount;
    private int retweetCount;
    private String[] hashtags;
    private int[] urls;
    private BigInteger[] userMentions;
    private String[] pollChoices;


    public Tweet(String[] fields) {

        try {
            this.tweetId = new BigInteger(fields[0], 10);
        } catch (Exception e) {
            this.tweetId = new BigInteger(fields[0], 16);
        }

        try {
            this.userId = new BigInteger(fields[1], 10);
        } catch (Exception e) {
            this.userId = new BigInteger(fields[1], 16);
        }

        this.tweetLanguage = fields[11];
        this.tweetText = fields[12].hashCode();

        this.tweetTime = LocalTime.parse(fields[13], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.tweetClientName = fields[14];

        if (fields[15].equals(""))
            this.inReplyToTweetId = null;
        else
            try {
                this.inReplyToTweetId  = new BigInteger(fields[15], 10);
            } catch (Exception e) {
                this.inReplyToTweetId  = new BigInteger(fields[15], 16);
            }

        if (fields[17].equals(""))
            this.quotedTweetId = null;
        else
            try {
                this.quotedTweetId = new BigInteger(fields[17], 10);
            } catch (Exception e) {
                this.quotedTweetId = new BigInteger(fields[17], 16);
            }

        if (fields[20].equals(""))
            this.retweetTweetId = null;
        else
            try {
                this.retweetTweetId = new BigInteger(fields[20], 10);
            } catch (Exception e) {
                this.retweetTweetId = new BigInteger(fields[20], 16);
            }

        if (fields[21].equals(""))
            this.latitude = -1;
        else
            this.latitude = Float.parseFloat(fields[21]);

        if (fields[22].equals(""))
            this.longitude = -1;
        else
            this.longitude = Float.parseFloat(fields[22]);

        if (fields[23].equals(""))
            this.quoteCount = 0;
        else
            this.quoteCount = Integer.parseInt(fields[23]);

        if (fields[24].equals(""))
            this.replyCount = 0;
        else
            this.replyCount = Integer.parseInt(fields[24]);

        if (fields[25].equals(""))
            this.likeCount = 0;
        else
            this.likeCount = Integer.parseInt(fields[25]);


        if (fields[26].equals(""))
            this.retweetCount = 0;
        else
            this.retweetCount = Integer.parseInt(fields[26]);

        this.hashtags = fields[27].split("[\\s*,\\s*]");

        String[] urls = fields[28].split("[\\s*,\\s*]");
        int i = 0;
        this.urls = new int[urls.length];
        for (String url : urls) {
            this.urls[i] = url.hashCode();
            i++;
        }

        String[] userMentions;
        if (fields[29].length() > 1)
            userMentions = fields[29].substring(1, fields[29].length() - 1).split("\\s*,\\s*");
        else
            userMentions = new String[]{};

        this.userMentions = new BigInteger[userMentions.length];
        i = 0;
        for (String str : userMentions) {
            try {
                this.userMentions[i] = new BigInteger(str, 10);
            } catch (Exception e) {

                this.userMentions[i] = new BigInteger(str, 16);
            }
            i++;
        }

        this.pollChoices = fields[30].split("[\\s*,\\s*]");

    }

    public BigInteger getUserId() {
        return userId;
    }

    public BigInteger getPreviousTweetTweetId() {
        return retweetTweetId;
    }

    @Override
    public BigInteger getId() {
        return tweetId;
    }

    public BigInteger getRetweetTweetId() { return retweetTweetId; }
}
