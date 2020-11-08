package JUnitTests;

import com.TweeterAnalytics.graphOps.WeightedPageRank;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class pageRankTests {


    @Test
    public void testPageRankCompute1() {
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

        HashMap<Character, Double> computedPageRank;
        HashMap<Character, Double> expectedPageRank = new HashMap<>();

        WeightedPageRank<Character, DefaultWeightedEdge> weightedPR = new WeightedPageRank<>( g, 0.02, 0.15 );
        computedPageRank = weightedPR.getPageRanks();

        expectedPageRank.put('a', 0.142);
        expectedPageRank.put('b', 0.242);
        expectedPageRank.put('c', 0.172);
        expectedPageRank.put('d', 0.141);
        expectedPageRank.put('e', 0.259);
        expectedPageRank.put('f', 0.022);
        expectedPageRank.put('g', 0.022);

        computedPageRank.entrySet().stream().
                forEach( entry -> assertEquals( entry.getValue(), expectedPageRank.get(entry.getKey()), 0.001 ) );
    }

    @Test
    public void testPageRankCompute2() {
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

        HashMap<Character, Double> computedPageRank;
        HashMap<Character, Double> expectedPageRank = new HashMap<>();

        WeightedPageRank<Character, DefaultWeightedEdge> weightedPR = new WeightedPageRank<>( g, 0.02, 0.15 );
        computedPageRank = weightedPR.getPageRanks();

        expectedPageRank.put('a', 0.153);
        expectedPageRank.put('b', 0.021);
        expectedPageRank.put('c', 0.112);
        expectedPageRank.put('d', 0.123);
        expectedPageRank.put('e', 0.173);
        expectedPageRank.put('f', 0.143);
        expectedPageRank.put('g', 0.274);

        computedPageRank.entrySet().stream().
                forEach( entry -> assertEquals( entry.getValue(), expectedPageRank.get(entry.getKey()), 0.001 ) );
    }

    @Test
    public void testPageRankCompute3() {
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
        g.setEdgeWeight( 'c', 'e', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'a', (double) 1 / 3 );
        g.setEdgeWeight( 'c', 'e', (double) 1 / 3 );
        g.setEdgeWeight( 'd', 'c', (double) 1 / 2 );
        g.setEdgeWeight( 'd', 'b', (double) 1 / 2 );
        g.setEdgeWeight( 'e', 'e', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'a', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'd', (double) 1 / 4 );
        g.setEdgeWeight( 'e', 'b', (double) 1 / 4 );

        HashMap<Character, Double> computedPageRank;
        HashMap<Character, Double> expectedPageRank = new HashMap<>();

        WeightedPageRank<Character, DefaultWeightedEdge> weightedPR = new WeightedPageRank<>( g, 0.09, 0.15 );
        computedPageRank = weightedPR.getPageRanks();

        expectedPageRank.put('a', 0.223);
        expectedPageRank.put('b', 0.216);
        expectedPageRank.put('c', 0.175);
        expectedPageRank.put('d', 0.216);
        expectedPageRank.put('e', 0.169);

        computedPageRank.entrySet().stream().
                forEach( entry -> assertEquals( entry.getValue(), expectedPageRank.get(entry.getKey()), 0.001 ) );
    }

    @Test
    public void testPageRankCompute4() {
        Graph<Character, DefaultWeightedEdge> g =
                new DefaultDirectedWeightedGraph<>( DefaultWeightedEdge.class );

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

        HashMap<Character, Double> computedPageRank;
        HashMap<Character, Double> expectedPageRank =
                new HashMap<>();

        WeightedPageRank<Character, DefaultWeightedEdge> weightedPR =
                new WeightedPageRank<>( g, 0.18, 0.15 );

        computedPageRank = weightedPR.getPageRanks();

        expectedPageRank.put('a', 0.253);
        expectedPageRank.put('b', 0.226);
        expectedPageRank.put('c', 0.193);
        expectedPageRank.put('d', 0.186);
        expectedPageRank.put('e', 0.142);

        computedPageRank.entrySet().stream().
                forEach( entry ->
                        assertEquals(
                                entry.getValue(),
                                expectedPageRank.get(entry.getKey()), 0.001
                        )
                );
    }

}