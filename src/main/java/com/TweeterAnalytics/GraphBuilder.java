package com.TweeterAnalytics;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.*;
//import org.jgrapht.io.*;


/*
*
* Used as a singleton object to construct graph G = ( V, E ) where,
* V = Set of accounts
* E = { (v1, v2) | v1, v2 in V, and v1 has retweeted at least once from v2 }
*
* */

public class GraphBuilder {
    private static GraphBuilder builder = null;

    private GraphBuilder(){}

    public static GraphBuilder getBuilder() {
        if ( builder == null ) {
            builder = new GraphBuilder();
        }
        return builder;
    }

    public DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> constructGraph(
            Map<BigInteger, Tweet> tweets,
            Map<BigInteger, User> users ) {

        DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<>( DefaultWeightedEdge.class );

        addVertices(users, g);
        addEdges(tweets, users, g);
        normaliseEdgeWeights(g);

        return g;
    }

    private DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> addVertices(
            Map<BigInteger, User> users,
            DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> g ) {

        User u;
        Set<BigInteger> userIds = users.keySet();

        for ( BigInteger id : userIds ) {
            u = users.get( id );
            g.addVertex( u );
        }
        return g;
    }

    private DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> addEdges(
            Map<BigInteger, Tweet> tweets,
            Map<BigInteger, User> users,
            DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> g ) {

        Tweet ancestorTweet;
        double currentWeight;

        for ( User u : users.values() ) {
            for ( Tweet t : u.tweets ) {
                if ( t.getPreviousTweetTweetId() != null )
                    try {
                        ancestorTweet = tweets.get( t.getPreviousTweetTweetId() );
                        if ( !g.containsEdge( u, users.get( ancestorTweet.getUserId() ) ) )
                            g.addEdge( u, users.get( ancestorTweet.getUserId() ) );
                        else {
                            currentWeight = g.getEdgeWeight( g.getEdge( u, users.get( ancestorTweet.getUserId() ) ) );
                            g.setEdgeWeight( u, users.get( ancestorTweet.getUserId() ), currentWeight + 1 );
                        }
                    } catch ( Exception e ) { }
            }
        }
        return g;
    }

    private DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> normaliseEdgeWeights (
            DefaultDirectedWeightedGraph<User, DefaultWeightedEdge> g ) {

        User retweeter;
        double currentWeight;
        Map<User, Double> outDegreeWeights = new HashMap<>();

        g.vertexSet().stream().forEach( user -> {
            outDegreeWeights.put( user, g.outgoingEdgesOf(user).stream().mapToDouble( edge -> g.getEdgeWeight(edge) ).sum() );
        } );

        for ( DefaultWeightedEdge e : g.edgeSet() ) {

            retweeter = g.getEdgeSource( e );

            currentWeight = g.getEdgeWeight( e );

            g.setEdgeWeight( e, currentWeight / outDegreeWeights.get( retweeter ) );
        }
        return g;
    }

}
