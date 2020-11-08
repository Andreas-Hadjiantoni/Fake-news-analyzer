package com.TweeterAnalytics.graphOps;

import com.TweeterAnalytics.User;

import java.util.*;

public class UserMetrics {

    private Collection<User> C;
    private HashMap<User, Long> disinformation, misinformation;
    private HashMap<User, Double> disinfoToMisinfoRatio;

    public UserMetrics( Collection<User> c ) {
        C = c;
        disinformation = computeDisinfo();
        misinformation = computeMisinfo();
        disinfoToMisinfoRatio = computeDisinfoToMisinfoRatio();
    }

    private HashMap<User, Long> computeDisinfo() {
        HashMap<User, Long> result = new HashMap<>();

        C.stream().forEach( user -> {
            result.put(user, user.tweets.stream().filter( tweet ->
                    (tweet.getRetweetTweetId() == null)
            ).count() );
        } );

        return result;
    }

    private HashMap<User, Long> computeMisinfo() {
        HashMap<User, Long> result = new HashMap<>();

        C.stream().forEach( user -> {
            result.put(user, user.tweets.stream().filter( tweet ->
                    (tweet.getRetweetTweetId() != null)
            ).count() );
        } );

        return result;
    }

    private HashMap<User, Double> computeDisinfoToMisinfoRatio() {
        HashMap<User, Double> result = new HashMap<>();

        C.stream().forEach( user -> {

            if ( !( misinformation.get( user ) == 0 && disinformation.get( user ) == 0 ) )
                result.put( user, (double)( 1 + disinformation.get( user ) ) / (double)( 1 + misinformation.get( user ) ) );

        } );

        return result;
    }

    public double getCorrelation( Map<User, Double> distr1, Map<User, Double> distr2 ) {
        if ( ! distr1.keySet().equals(distr2.keySet()) )
            throw new IllegalArgumentException( "User sets mismatch!" );

        ArrayList<Double> m1 = new ArrayList<>( distr1.size() );
        ArrayList<Double> m2 = new ArrayList<>( distr1.size() );

        distr1.keySet().stream().forEach( user -> {
            m1.add( distr1.get(user) );
            m2.add( distr2.get(user) );
        } );

        return getCorrelation( m1, m2 );
    }

    public double getCorrelation( List<Double> distr1, List<Double> distr2 ) {
        if ( distr1.size() != distr2.size() )
            throw new IllegalArgumentException( "Metric sizes mismatch!" );

        double c;
        double mean1, mean2;

        mean1 = getMean( distr1 );
        mean2 = getMean( distr2 );

        c = covariance( distr1, mean1, distr2, mean2 ) / ( stdDev( distr1, mean1 ) * stdDev( distr2, mean2 ) );

        return c;
    }

    public double getMean( List<Double> distr ) {
        return  distr.stream().mapToDouble( num -> (double)num ).sum() / distr.size();
    }

    public double stdDev( List<Double> distr, double mean ) {
        return Math.sqrt( covariance( distr, mean, distr, mean ) );
    }

    private double covariance( List<Double> distr1, double mean1, List<Double> distr2, double mean2 ) {
        if ( distr1.size() != distr2.size() )
            throw new IllegalArgumentException( "Metric sizes mismatch!" );

        double sum = 0;

        for ( int i = 0; i < distr1.size(); i++ )
            sum += ( (double)distr1.get(i) - mean1 ) * ( (double)distr2.get(i) - mean2 );

        return sum;
    }

    public HashMap<User, Double> getDisinfoToMisinfoRatio() {
        return (HashMap<User, Double>)disinfoToMisinfoRatio.clone();
    }
}
