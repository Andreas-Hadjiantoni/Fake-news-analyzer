package JUnitTests;

import com.TweeterAnalytics.graphOps.BetweennessCentrality_;
import com.TweeterAnalytics.graphOps.Clustering;
import com.TweeterAnalytics.graphOps.WeightedPageRank;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class clusteringTests {

    @Test
    public void testComputeClusters1() {
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

        Clustering<Character> c = new Clustering<>( g, 0.7 );
        List<Set<Character>> computed = c.getClusters();
        List<Set<Character>> expected = new ArrayList<>();

        expected.add( new HashSet<>() );
        expected.add( new HashSet<>() );

        expected.get(0).add('a');
        expected.get(0).add('d');
        expected.get(1).add('b');
        expected.get(1).add('e');

        assertEquals(expected, computed);
    }

    @Test
    public void testComputeClusters2() {
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
        g.setEdgeWeight( 'b', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'b', 'd', (double) 1 / 1 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'c', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'g', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'f', (double) 1 / 2 );
        g.setEdgeWeight( 'f', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'f', 'a', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'e', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'c', (double) 1 / 1 );
        g.setEdgeWeight( 'g', 'd', (double) 1 / 1 );
        g.setEdgeWeight( 'g', 'e', (double) 1 / 5 );

        Clustering<Character> c = new Clustering<>( g, 0.5 );
        List<Set<Character>> computed = c.getClusters();
        List<Set<Character>> expected = new ArrayList<>();

        expected.add( new HashSet<>() );
        expected.add( new HashSet<>() );

        expected.get(0).add('a');
        expected.get(0).add('e');
        expected.get(1).add('b');
        expected.get(1).add('c');
        expected.get(1).add('d');
        expected.get(1).add('f');
        expected.get(1).add('g');

        assertEquals(expected, computed);
    }

    @Test
    public void testComputeClusters3() {
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

        Clustering<Character> c = new Clustering<>( g, 0.6 );
        List<Set<Character>> computed = c.getClusters();
        List<Set<Character>> expected = new ArrayList<>();

        expected.add( new HashSet<>() );
        expected.add( new HashSet<>() );

        expected.get(0).add('a');
        expected.get(0).add('f');
        expected.get(1).add('c');
        expected.get(1).add('d');

        assertEquals(expected, computed);
    }

    @Test
    public void testComputeClusters4() {
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
        g.addEdge('e', 'b');
        g.addEdge('e', 'a');

        g.setEdgeWeight( 'a', 'b', (double) 1 / 10 );
        g.setEdgeWeight( 'a', 'e', (double) 1 / 10 );
        g.setEdgeWeight( 'a', 'e', (double) 1 / 10 );
        g.setEdgeWeight( 'a', 'c', (double) 1 / 1 );
        g.setEdgeWeight( 'a', 'b', (double) 1 / 10 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'c', 'c', (double) 1 / 30 );
        g.setEdgeWeight( 'c', 'e', (double) 1 / 30 );
        g.setEdgeWeight( 'c', 'a', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'b', (double) 1 / 2 );
        g.setEdgeWeight( 'd', 'd', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'c', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'b', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'b', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'a', (double) 1 / 4 );

        Clustering<Character> c = new Clustering<>( g, 0.5 );
        List<Set<Character>> computed = c.getClusters();
        List<Set<Character>> expected = new ArrayList<>();

        expected.add( new HashSet<>() );

        expected.get(0).add('b');
        expected.get(0).add('d');
        expected.get(0).add('e');

        assertEquals(expected, computed);
    }

    @Test
    public void testClustersTruncation1() {
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
        g.setEdgeWeight( 'b', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'b', 'd', (double) 1 / 1 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'c', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'g', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'f', (double) 1 / 2 );
        g.setEdgeWeight( 'f', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'f', 'a', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'e', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'c', (double) 1 / 1 );
        g.setEdgeWeight( 'g', 'd', (double) 1 / 1 );
        g.setEdgeWeight( 'g', 'e', (double) 1 / 5 );

        Clustering<Character> c = new Clustering<>( g, 0.5 );
        c.getBiggestClusters(3);
        List<Set<Character>> computed = c.getClusters();
        List<Set<Character>> expected = new ArrayList<>();

        expected.add( new HashSet<>() );

        expected.get(0).add('b');
        expected.get(0).add('c');
        expected.get(0).add('d');
        expected.get(0).add('f');
        expected.get(0).add('g');

        assertEquals(expected, computed);
    }

    @Test
    public void testClustersTruncation2() {
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
        g.setEdgeWeight( 'b', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'b', 'd', (double) 1 / 1 );
        g.setEdgeWeight( 'b', 'a', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'c', (double) 1 / 1 );
        g.setEdgeWeight( 'd', 'a', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'g', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'f', (double) 1 / 2 );
        g.setEdgeWeight( 'f', 'g', (double) 1 / 1 );
        g.setEdgeWeight( 'f', 'a', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'e', (double) 1 / 4 );
        g.setEdgeWeight( 'f', 'c', (double) 1 / 1 );
        g.setEdgeWeight( 'g', 'd', (double) 1 / 1 );
        g.setEdgeWeight( 'g', 'e', (double) 1 / 5 );

        Clustering<Character> c = new Clustering<>( g, 0.5 );
        c.getBiggestClusters(5);
        List<Set<Character>> computed = c.getClusters();
        List<Set<Character>> expected = new ArrayList<>();

        expected.add( new HashSet<>() );

        expected.get(0).add('b');
        expected.get(0).add('c');
        expected.get(0).add('d');
        expected.get(0).add('f');
        expected.get(0).add('g');

        assertEquals(expected, computed);
    }
}