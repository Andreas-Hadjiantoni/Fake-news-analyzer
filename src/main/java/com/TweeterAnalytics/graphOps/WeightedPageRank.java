package com.TweeterAnalytics.graphOps;

import org.ejml.simple.SimpleMatrix;
import org.jgrapht.Graph;
import java.util.ArrayList;
import java.util.HashMap;

public class WeightedPageRank<V, E> {

    private final Graph<V, E> g;
    private HashMap<V, Double> prValues;

    public WeightedPageRank( Graph<V, E> g, double tol, double damp ) {

        if ( damp > 1 )
            throw new IllegalArgumentException();

        this.g = g;
        prValues = new HashMap<>();
        SimpleMatrix PageRanks = computePageRank( tol, damp );
        setPrValues( PageRanks );
    }

    private void setPrValues(SimpleMatrix PageRanks ) {
        final ArrayList<V> nodes = new ArrayList<>( g.vertexSet() );
        int i = 0;
        for ( V v : nodes ) {
            prValues.put( v, PageRanks.get( i, 0 ) );
            i++;
        }
    }

    private SimpleMatrix computePageRank( double tolerance, double damping ) {
        /*
        *
        * Recommended:
        * tolerance = 0.07
        * damping = 0.15
        *
        * */
        SimpleMatrix A = getIterationMatrix();
        SimpleMatrix R = getInitialPageRanks();
        SimpleMatrix E = getDampingMatrix();

        SimpleMatrix M = A.scale( 1 - damping );
        M = M.plus(E.scale( damping ));

        SimpleMatrix R_; // next state of R
        double delta;

        do {
            R_ = M.mult(R);
            delta = R_.minus(R).elementSum();
            R_ = R_.scale( 1.0 / R_.elementSum() );
            R = R_;
        } while ( Math.abs( delta ) > tolerance );

        return R;
    }

    private SimpleMatrix getDampingMatrix() {
        final int nodeNumber = g.vertexSet().size();
        double[][] result = new double[nodeNumber][nodeNumber];

        for ( int i = 0; i < nodeNumber; i++ )
            for ( int j = 0; j < nodeNumber; j++ ) {
                result[i][j] = 1.0 / (double)nodeNumber;
            }

        return new SimpleMatrix(result);
    }

    private SimpleMatrix getInitialPageRanks() {
        final int nodeNumber = g.vertexSet().size();
        SimpleMatrix R = new SimpleMatrix( new double[nodeNumber][1] );

        for (int i = 0; i < nodeNumber; i++ )
            R.set( i, 0 , (double)1 / (double)nodeNumber);

        return R;
    }

    @org.jetbrains.annotations.NotNull
    private SimpleMatrix getIterationMatrix() {

        final ArrayList<V> nodes = new ArrayList<>( g.vertexSet() );
        final int nodeNumber = g.vertexSet().size();
        double[][] transformationMatrix = new double[nodeNumber][nodeNumber];

        for (int i = 0; i < nodeNumber; i++ )
            for (int j = 0; j < nodeNumber; j++ )
                if ( g.containsEdge( nodes.get( i ), nodes.get( j ) ) )
                    transformationMatrix[j][i] =  g.getEdgeWeight( g.getEdge( nodes.get( i ), nodes.get( j ) ) );
                else
                    transformationMatrix[j][i] = 0;

        return new SimpleMatrix( transformationMatrix );
    }

    public HashMap<V, Double> getPageRanks() {
        return (HashMap<V, Double>)prValues.clone();
    }
}
