package JUnitTests;

import com.TweeterAnalytics.GraphBuilder;
import com.TweeterAnalytics.graphOps.BetweennessCentrality_;
import com.TweeterAnalytics.graphOps.Clustering;
import com.TweeterAnalytics.graphOps.WeightedPageRank;
import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class betCentralityTests {

    @Test
    public void testComputeBetCentrality1() {
        Graph<Character, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<>( DefaultWeightedEdge.class );

        g.addVertex('a');
        g.addVertex('b');
        g.addVertex('c');
        g.addVertex('d');
        g.addVertex('e');

        g.addEdge('a', 'b');
        g.addEdge('a', 'd');
        g.addEdge('a', 'e');
        g.addEdge('a', 'c');
        g.addEdge('a', 'e');
        g.addEdge('b', 'a');
        g.addEdge('b', 'd');
        g.addEdge('c', 'e');
        g.addEdge('c', 'a');
        g.addEdge('c', 'e');
        g.addEdge('d', 'c');
        g.addEdge('d', 'b');
        g.addEdge('e', 'e');
        g.addEdge('e', 'a');
        g.addEdge('e', 'd');
        g.addEdge('e', 'b');

        g.setEdgeWeight( 'a', 'b', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'd', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'e', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'c', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'e', (double) 1 / 5 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'b', 'd', (double) 1 / 2 );
        g.setEdgeWeight( 'c', 'e', (double) 1 / 1 );
        g.setEdgeWeight( 'c', 'a', (double) 1 / 5 );
        g.setEdgeWeight( 'c', 'e', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'c', (double) 1 / 2 );
        g.setEdgeWeight( 'd', 'b', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'e', (double) 1 / 1 );
        g.setEdgeWeight( 'e', 'a', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'd', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'b', (double) 1 / 1 );

        BetweennessCentrality_<Character> bc = new BetweennessCentrality_<>(g);
        List<Double> computed = bc.allCentralities();
        List<Double> expected = new ArrayList<>();

        expected.add(0.250);
        expected.add(0.041);
        expected.add(0.125);
        expected.add(0.083);
        expected.add(0.083);

        for ( int i = 0; i < expected.size(); i++ )
            assertEquals( expected.get( i ), computed.get( i ), 0.001 );
    }

    @Test
    public void testComputeBetCentrality2() {
        Graph<Character, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<>( DefaultWeightedEdge.class );

        g.addVertex('a');
        g.addVertex('b');
        g.addVertex('c');
        g.addVertex('d');
        g.addVertex('e');
        g.addVertex('f');
        g.addVertex('g');

        g.addEdge('a', 'f');
        g.addEdge('a', 'e');
        g.addEdge('b', 'g');
        g.addEdge('b', 'd');
        g.addEdge('b', 'a');
        g.addEdge('c', 'g');
        g.addEdge('d', 'c');
        g.addEdge('d', 'a');
        g.addEdge('e', 'g');
        g.addEdge('e', 'f');
        g.addEdge('f', 'g');
        g.addEdge('f', 'a');
        g.addEdge('f', 'e');
        g.addEdge('f', 'c');
        g.addEdge('g', 'd');
        g.addEdge('g', 'e');

        g.setEdgeWeight( 'a', 'f', (double) 1 / 2 );
        g.setEdgeWeight( 'a', 'e', (double) 1 / 2 );
        g.setEdgeWeight( 'b', 'g', (double) 1 / 3 );
        g.setEdgeWeight( 'b', 'd', (double) 1 / 3 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'c', (double) 1 / 2 );
        g.setEdgeWeight( 'd', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'g', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'f', (double) 1 / 2 );
        g.setEdgeWeight( 'f', 'g', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'a', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'e', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'c', (double) 1 / 4 );
        g.setEdgeWeight( 'g', 'd', (double) 1 / 2 );
        g.setEdgeWeight( 'g', 'e', (double) 1 / 2 );

        BetweennessCentrality_<Character> bc = new BetweennessCentrality_<>(g);
        List<Double> computed = bc.allCentralities();
        List<Double> expected = new ArrayList<>();

        expected.add(0.117);
        expected.add(0.000);
        expected.add(0.033);
        expected.add(0.133);
        expected.add(0.100);
        expected.add(0.133);
        expected.add(0.250);

        for ( int i = 0; i < expected.size(); i++ )
            assertEquals( expected.get( i ), computed.get( i ), 0.001 );
    }

    @Test
    public void testComputeBetCentrality3() {
        Graph<Character, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<>( DefaultWeightedEdge.class );

        g.addVertex('a');
        g.addVertex('b');
        g.addVertex('c');
        g.addVertex('d');
        g.addVertex('e');
        g.addVertex('f');
        g.addVertex('g');

        g.addEdge('a', 'b');
        g.addEdge('a', 'c');
        g.addEdge('a', 'd');
        g.addEdge('b', 'c');
        g.addEdge('b', 'e');
        g.addEdge('f', 'a');
        g.addEdge('c', 'b');
        g.addEdge('c', 'd');
        g.addEdge('d', 'e');
        g.addEdge('e', 'b');
        g.addEdge('e', 'a');

        g.setEdgeWeight( 'a', 'b', (double) 1 / 3 );
        g.setEdgeWeight( 'a', 'c', (double) 1 / 3 );
        g.setEdgeWeight( 'a', 'd', (double) 1 / 3 );
        g.setEdgeWeight( 'b', 'c', (double) 1 / 2 );
        g.setEdgeWeight( 'b', 'e', (double) 1 / 2 );
        g.setEdgeWeight( 'f', 'a', (double) 1 / 1 );
        g.setEdgeWeight( 'c', 'b', (double) 1 / 2 );
        g.setEdgeWeight( 'c', 'd', (double) 1 / 2 );
        g.setEdgeWeight( 'd', 'e', (double) 1 / 1 );
        g.setEdgeWeight( 'e', 'b', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'a', (double) 1 / 2 );

        BetweennessCentrality_<Character> bc = new BetweennessCentrality_<>(g);
        List<Double> computed = bc.allCentralities();
        List<Double> expected = new ArrayList<>();

        expected.add(0.200);
        expected.add(0.100);
        expected.add(0.033);
        expected.add(0.067);
        expected.add(0.166);
        expected.add(0.000);
        expected.add(0.000);

        for ( int i = 0; i < expected.size(); i++ )
            assertEquals( expected.get( i ), computed.get( i ), 0.001 );
    }

    @Test
    public void testComputeBetCentrality4() {
        Graph<Character, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<>( DefaultWeightedEdge.class );

        g.addVertex('a');
        g.addVertex('b');
        g.addVertex('c');
        g.addVertex('d');
        g.addVertex('e');

        g.addEdge('a', 'b');
        g.addEdge('a', 'e');
        g.addEdge('a', 'e');
        g.addEdge('a', 'c');
        g.addEdge('a', 'b');
        g.addEdge('b', 'a');
        g.addEdge('b', 'a');
        g.addEdge('c', 'c');
        g.addEdge('c', 'e');
        g.addEdge('c', 'a');
        g.addEdge('d', 'b');
        g.addEdge('d', 'd');
        g.addEdge('e', 'c');
        g.addEdge('e', 'b');
        g.addEdge('e', 'd');
        g.addEdge('e', 'a');

        g.setEdgeWeight( 'a', 'b', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'e', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'e', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'c', (double) 1 / 5 );
        g.setEdgeWeight( 'a', 'b', (double) 1 / 5 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'c', 'c', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'e', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'a', (double) 1 / 3 );
        g.setEdgeWeight( 'd', 'b', (double) 1 / 2 );
        g.setEdgeWeight( 'd', 'd', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'c', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'b', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'd', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'a', (double) 1 / 4 );

        BetweennessCentrality_<Character> bc = new BetweennessCentrality_<>(g);
        List<Double> computed = bc.allCentralities();
        List<Double> expected = new ArrayList<>();

        expected.add(0.458);
        expected.add(0.250);
        expected.add(0.000);
        expected.add(0.000);
        expected.add(0.292);

        for ( int i = 0; i < expected.size(); i++ )
            assertEquals( expected.get( i ), computed.get( i ), 0.001 );
    }


}