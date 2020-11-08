package com.TweeterAnalytics;

import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import com.mxgraph.view.mxGraphView;
import org.jgrapht.*;
import org.jgrapht.ext.*;

import javax.swing.*;
import java.awt.*;

public class GraphVisualisation<N, E> extends JApplet {

    private Graph<N, E> graph;

    private static final Dimension DEFAULT_SIZE = new Dimension(1920, 1200);

    private JGraphXAdapter<N, E> jgxAdapter;


    public GraphVisualisation( Graph<N, E> g) {
        graph = g;
    }

    public void visualise() {

        this.init();
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("Influence Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }

    @Override
    public void init() {

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<N, E>( graph );

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        layout.setUseBoundingBox(true);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        mxGraphView view = jgxAdapter.getView();
        view.setScale(0.011);

        layout.execute(jgxAdapter.getDefaultParent());

    }
}
