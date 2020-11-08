package com.TweeterAnalytics;

import com.TweeterAnalytics.graphOps.BetweennessCentrality_;
import com.TweeterAnalytics.graphOps.Clustering;
import com.TweeterAnalytics.graphOps.UserMetrics;
import com.TweeterAnalytics.graphOps.WeightedPageRank;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

// load -users /extra/javaProjects/indProject/src/main/resources/iranian_users_csv_hashed
// load -tweets /extra/javaProjects/indProject/src/main/resources/iranian_tweets_csv_hashed.csv
// pagerank -compute -tolerance 0.12 -damp-factor 0.3

public class Terminal {

    private Map<BigInteger, User> users = null;
    private Map<BigInteger, Tweet> tweets = null;
    private boolean loadedAndLinked = false;
    private Graph<User, DefaultWeightedEdge> G = null;
    private WeightedPageRank<User, DefaultWeightedEdge> pageRank = null;
    private Clustering<User> cl = null;

    private static Terminal instance = new Terminal();

    private Terminal() {}

    public static Terminal getTerminal() { return instance; }

    public void start() {

        String line;
        boolean quit = false;
        String[] commandArguments;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while ( !quit ) {
            System.out.print(">>>> ");
            try {
                line = reader.readLine();
            } catch ( Exception e ) {
                return;
            }

            commandArguments = line.trim().split("\\s+");

            switch ( commandArguments[0] ) {
                case "":
                    break;
                case "load":
                    loadFile( commandArguments );
                    break;
                case "link":
                    if ( users != null && tweets != null ) {
                        int tweetsLeft = distributeTweetsToUsers( users, tweets );
                        System.out.println("Users are now linked with their tweets.");
                        System.out.println(tweetsLeft + " tweets could not be assigned to a user.");
                    }
                    else
                        System.out.println("Could not link users with their tweets.\nMake sure both are loaded first.");
                    G = GraphBuilder.getBuilder().constructGraph( tweets, users );
                    System.out.println("Influence graph is constructed.");
                    break;
                case "visualise":
                    if ( !loadedAndLinked ) {
                        System.out.println( "Load and link an account and a tweet dataset first!\nAborting operation." );
                        break;
                    }
                    GraphVisualisation<User, DefaultWeightedEdge> visualisation = new GraphVisualisation<>( G );
                    visualisation.visualise();
                    break;
                case "pagerank":
                    runPageRank( commandArguments );
                    break;
                case "cluster":
                    runClustering( commandArguments );
                    break;
                case "betweennessCentrality":
                    betweennesCentrality( commandArguments );
                    break;
                case "quit":
                    System.out.println("Terminating program...");
                    quit = true;
                    break;
                default:
                    System.out.println("Command \"" + commandArguments[0] + "\" not recognised.\n Please try again!");
            }
        }
    }

    private void runClustering( String[] arguments ) {
        switch ( arguments[1] ) {
            case "-alpha":
                if ( ! checkLength( arguments, 3 ) ) return;

                try {
                    double alpha = Double.parseDouble( arguments[2] );
                    if ( G != null )  {
                        cl = new Clustering<>(G, alpha);
                    }
                    else
                        System.out.println("Please load and link the users and tweets datasets first");
                } catch ( Exception e ) {
                    System.out.println("Please ensure the alpha value is a floating point positive number.");
                }
                break;

            case "-user-descriptions":
                if ( ! checkLength( arguments, 3 ) ) return;

                try {
                    int index = Integer.parseInt( arguments[2] );
                    if ( cl != null )  {
                        cl.getClusters().get( index ).forEach( user -> {

                            String desc = user.getDescription();
                            if ( ! desc.equals("") ) {

                                        if ( desc.length() > 80 ) {
                                            String s1 = desc.substring(0,80);
                                            String s2 = desc.substring(80, desc.length());
                                            desc = s1 + "\n  " + s2;
                                        }
                                        System.out.println( "> " + desc );
                                    }
                                }
                        );
                    }
                    else
                        System.out.println("Please compute clustering first and try again!");
                } catch ( Exception e ) {
                    System.out.println("Please ensure the cluster index is a positive integer.");
                }

                break;

            case "-cluster-distribution":
                if ( ! checkLength( arguments, 2 ) ) return;

                if ( cl != null ) {
                    int[] clusterSizes = cl.getClusters().stream().mapToInt(set -> set.size() ).toArray();
                    Arrays.stream(clusterSizes).sorted().forEach( size -> System.out.println( size ) );
                    System.out.println( "num of clusters = " + Arrays.stream(clusterSizes).count() );
                } else
                    System.out.println("Please compute clustering first and try again!");
                break;

            case "-cluster-pagerank-correlation":
                if ( ! checkLength( arguments, 2 ) ) return;

                if ( pageRank == null || cl == null ) {
                    System.out.println("Please compute pageranks and clusters first.");
                    break;
                }
                System.out.println("The following sequences correspond to the users that were\nassigned the highest pageRanks" +
                        "values from amongst all the users of their clusters.");
                System.out.println("Sequence 1: PageRanks of those users sorted using the size of their clusters.");
                System.out.println("Sequence 2: PageRanks of the same user set, but sorted with ascending PageRank value.");
                System.out.println("Pearson's correlation coefficient of sequences 1 & 2: " + clusterPageRankCorrelation() );
                break;
            case "-truncate-small-clusters":
                if ( ! checkLength( arguments, 3 ) ) return;

                if ( cl == null ) {
                    System.out.println("Please compute clusters first.");
                    break;
                }

                try {
                    cl.getBiggestClusters( Integer.parseInt( arguments[2] ) );
                } catch (Exception e) {
                    System.out.println("Please ensure that the second parameter is a positive integer.");
                }
                break;
            default:
                System.out.println("Incorrect usage of command \"cluster\".");

        }
    }

    private double clusterPageRankCorrelation() {

        // Sorts the clusters according to their size, in ascending order
        List<Set<User>> clustersSorted = cl.getClusters().stream()
                .filter( users1 -> users1.size() > 1 )
                .sorted(new Comparator<Set<User>>() {
                    @Override
                    public int compare(Set<User> users, Set<User> t1) {
                        int s1 = users.size();
                        int s2 = t1.size();
                        if (s1>s2) return 1;
                        if (s1<s2) return -1;
                        return 0;
                    }
                }).collect(Collectors.toList());

        List<User> usersOfMaxPageRanksFromClusters = new ArrayList<>();
        List<Double> maxPageRanksFromClusters = new ArrayList<>();

        clustersSorted.
                stream().
                forEachOrdered( set -> {
                    User maxPageRankUserInCluster =
                            set.
                                    stream().
                                    max( (user1, user2) -> {
                                        double p1 = pageRank.getPageRanks().get(user1);
                                        double p2 = pageRank.getPageRanks().get(user2);

                                        if ( p1 > p2 ) return 1;
                                        if ( p1 < p2 ) return -1;
                                        return 0;
                                    } ).get();
                    usersOfMaxPageRanksFromClusters.add( maxPageRankUserInCluster );
                    maxPageRanksFromClusters.add( pageRank.getPageRanks().get(maxPageRankUserInCluster) );
                } );

        List<Double> usersPageRanks = new ArrayList<>();

        pageRank.
                getPageRanks().
                keySet().
                stream().
                filter( user -> usersOfMaxPageRanksFromClusters.contains(user) ).
                mapToDouble( user -> pageRank.getPageRanks().get(user) ).
                sorted().
                forEachOrdered( prVal -> usersPageRanks.add( prVal ) );

        UserMetrics metrics = new UserMetrics(users.values());

        return metrics.getCorrelation( maxPageRanksFromClusters, usersPageRanks );
    }

    private Optional<Double> getPageRankOf( String[] arguments ) {

        try {
            int base = Integer.parseInt( arguments[5] );
            BigInteger id = new BigInteger( arguments[3], base );
            if ( users.containsKey( id ) ) {
                return Optional.of( pageRank.getPageRanks().get( users.get(id) ) );
            } else
                System.out.println( "User ID given does not correspond to a user. please try again." );
        } catch ( Exception e ) {
            System.out.println("Please ensure that the first and second parameters\nare non-negative integers and try again.");
        }
        return Optional.empty();
    }

    private void runPageRank( String[] arguments ) {
        double tolerance, damping;

        switch ( arguments[1] ) {
            case "-compute":

                if ( !( checkLength( arguments, 6 )      &&
                        arguments[2].equals("-tolerance")      &&
                        arguments[4].equals("-damp-factor") ) )
                    break;

                try {
                    tolerance = Double.parseDouble( arguments[3] );
                    damping = Double.parseDouble( arguments[5] );

                    if ( loadedAndLinked ) {

                        pageRank = new WeightedPageRank<>( G, tolerance, damping );

                    } else {
                        System.out.println("Please load and link the datasets first.");
                    }

                } catch ( Exception e ) {
                    System.out.println( "Please ensure that the first and second parameters\n" +
                            "are floating point numbers and try again." );
                }
                break;

            case "-distribution":

                if ( !( checkLength( arguments, 2 )  ) )
                    break;

                if ( pageRank != null ) {
                    pageRank.getPageRanks().values().stream().sorted().forEachOrdered( pageRankValue ->
                        System.out.println( pageRankValue )
                    );
                } else
                    System.out.println("PageRank has not been computed yet.");

                break;
            case "-of-user":

                if ( !( checkLength( arguments, 6 ) &&
                        arguments[2].equals("-id")        &&
                        arguments[4].equals("-base") ) )
                    break;

                if ( pageRank != null ) {
                    if ( !getPageRankOf( arguments ).equals( Optional.empty() ) ) {
                        System.out.println( getPageRankOf( arguments ).get());
                    }
                } else
                    System.out.println("PageRank has not been computed yet.");
                break;
            case "-disinfo-to-misinfo-pagerank-correlation":

                if ( !checkLength( arguments, 2 ) ) break;
                if ( pageRank == null ) {
                    System.out.println("Compute pagerank values first.");
                    break;
                }

                System.out.println( dissinfoToMissinfoPageRankCorrelation() );
                break;
            case "-followers-pagerank-correlation":

                if ( !checkLength( arguments, 2 ) ) break;
                if ( pageRank == null ) {
                    System.out.println("Compute pagerank values first.");
                    break;
                }

                System.out.println( followersPageRankCorrelation() );
                break;
            default:
                System.out.println("Incorrect use of command \"pagerank\".");
        }
    }

    private void betweennesCentrality( String[] arguments ) {
        if ( !checkLength( arguments, 2 ) ) return;

        if ( cl == null ) {
            System.out.println("Cluster the nodes first.");
            return;
        }

        BetweennessCentrality_<User> centrality = new BetweennessCentrality_<>( G  );

        switch ( arguments[1] ) {
            case "-distribution":
                centrality.allCentralities().stream().sorted().forEachOrdered( d ->
                    System.out.println( d )
                );

                break;
            case "-average":

                System.out.println( "Average centrality: " + centrality.averageCentrality() );
                break;

            case "-largest":

                System.out.println( "Largest centrality: " + centrality.getBiggestCentrality() );
                break;

            case "-inter-cluster-bet-centrality":

                double interclusterCentrality = cl.getClusters().stream().map( cluster1 -> {

                            System.out.print("Working on cluster " +
                                    cl.getClusters().indexOf(cluster1) +
                                    " out of " + (cl.getClusters().size() - 1));

                            return cl.getClusters().parallelStream().map( cluster2 -> {

                                        Set<User> usersNotInClusters = users.values().stream().filter(user ->
                                                        !cluster1.contains(user) &&
                                                        !cluster2.contains(user)).collect(Collectors.toSet());

                                        return usersNotInClusters.stream().mapToDouble( u ->
                                                centrality.
                                                        clusterBetweennessCentrality( u, cluster1, cluster2 )).
                                                max().
                                                getAsDouble();

                                    }
                            ).collect(Collectors.toList());
                }
                ).flatMap(List::stream).mapToDouble(num -> num.doubleValue()).max().getAsDouble();

                System.out.println("Max betweenness centrality between clusters is: " + interclusterCentrality );
                break;
        }
    }

    private double followersPageRankCorrelation() {
        UserMetrics metrics = new UserMetrics(users.values());
        HashMap<User, Double> usersFollowers = new HashMap<>();

        users.values().stream().forEach( user -> usersFollowers.put( user, (double)user.getFollowerCount() ) );

        return metrics.getCorrelation( usersFollowers, pageRank.getPageRanks() );
    }

    private double dissinfoToMissinfoPageRankCorrelation() {
        UserMetrics metrics = new UserMetrics(users.values());
        HashMap<User, Double> disinfoToMisinfo = metrics.getDisinfoToMisinfoRatio();
        Map<User, Double> reducedPageRankValues = new HashMap<>();

        pageRank.getPageRanks().forEach( (user, prVal) -> reducedPageRankValues.put( user, prVal ) );

        pageRank.getPageRanks().keySet().stream().forEach( user -> {
            if ( ! disinfoToMisinfo.containsKey(user) )
                reducedPageRankValues.remove(user);
        } );

        disinfoToMisinfo.keySet().stream().forEach( user -> {
            if ( ! reducedPageRankValues.containsKey(user) )
                disinfoToMisinfo.remove(user);
        } );

        return metrics.getCorrelation( reducedPageRankValues, disinfoToMisinfo );
    }

    private boolean checkLength( String[] arguments, int length ) {
        if ( arguments.length != length ) {
            System.out.println("Incorrect usage of command.");
            return false;
        }
        return true;
    }

    private void loadFile( String[] arguments ) {
        if ( !checkLength( arguments, 3 ) ) return;

        CSVentityLoad load;

        switch ( arguments[1] ) {
            case "-users":
                try {
                    load = new CSVentityLoad<User>( User.class );
                    users = load.loadData( arguments[2] );
                    System.out.println(users.size() + " users loaded successfully");
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                break;

            case "-tweets":
                try {
                    load = new CSVentityLoad<Tweet>( Tweet.class );
                    tweets = load.loadData( arguments[2] );
                    System.out.println(tweets.size() + " tweets loaded successfully");
                } catch ( Exception e ) {
                    System.out.println("Failed to load tweets");
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("Argument \"" + arguments[1] + "\" not recognised.\n Please try again!");
        }
    }

    // returns number of tweets not assigned to any user
    private int distributeTweetsToUsers(Map<BigInteger, User> users, Map<BigInteger, Tweet> tweets ) {
        int tweetsLeft = tweets.size();
        BigInteger currentUserId;
        User user;

        for ( Tweet tweet : tweets.values() ) {
            currentUserId = tweet.getUserId();
            if ( ( user = users.get( currentUserId ) ) != null ) {
                user.tweets.add( tweet );
                tweetsLeft--;
            }
        }
        loadedAndLinked = true;
        return tweetsLeft;
    }

}
