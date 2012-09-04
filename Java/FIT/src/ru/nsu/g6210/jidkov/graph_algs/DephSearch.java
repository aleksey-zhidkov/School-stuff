package ru.nsu.g6210.jidkov.graph_algs;

import ru.jdev.utils.math.graph.*;

import java.util.List;
import java.util.HashMap;

/**
 * User: jdev
 * Date: 17.12.2007
 */
public class DephSearch {

    private Graph getGraph() {
        Graph g = new Graph(false);
        g.addPeak("a");
        g.addPeak("b");
        g.addPeak("c");
        g.addPeak("d");
        g.addPeak("e");
        g.addPeak("f");
        g.addPeak("g");
        g.addPeak("h");
        g.addPeak("i");
        g.addPeak("j");
        g.addReference("a", "b", 5);
        g.addReference("b", "c", 6);
        g.addReference("c", "d", 3);
        g.addReference("d", "e", 3);
        g.addReference("e", "a", 4);
        g.addReference("a", "f", 8);
        g.addReference("b", "h", 3);
        g.addReference("c", "g", 3);
        g.addReference("d", "j", 7);
        g.addReference("e", "i", 7);
        g.addReference("f", "g", 2);
        g.addReference("g", "h", 8);
        g.addReference("h", "i", 3);
        g.addReference("i", "j", 8);
        g.addReference("j", "f", 4);
        return g;
    }

    public static void main(String[] args) {
        DephSearch ds = new DephSearch();
        ds.runWalk();
        //ds.runPrim();
    }

    private int alpha(IGraphNode x, IGraphNode a) {
        GraphEdge edge = a.getEdge(x);
        if (edge != null)
            return edge.getCost();
        else
            return Integer.MAX_VALUE;
    }

    private IGraphNode beta(IGraphNode x, IGraphNode a) {
        if (a.getEdge(x) != null)
            return a;
        else
            return null;
    }

    private void runPrim() {
        Graph g = getGraph();
        Graph tree = new Graph();
        IGraphNode a = g.getPeak("a");
        tree.addPeak(a);
        struct[][] table = new struct[g.nodesCount()][g.nodesCount() - 1];
        int i = 0;
        for (IGraphNode j : g.getNodes()) {
            if (a.getEdge(j) != null) {
                struct s = new struct();
                s.alpha = a.getEdge(j).getCost();
                s.beta = a.getValue().toString();
            }
        }
    }

    private void runWalk() {
        Graph g = getGraph();
        final Graph tree = new Graph();
        tree.addPeak(g.getPeak("c").getValue());
        g.walk(false, g.getPeak("c"), new IGraphPeakAction() {

            public void doAction(GraphEdge edge, List visited, List queue) {
                System.out.print(visited.toString() + " - ");
                System.out.println(queue);
                if (edge != null) {
                    tree.addPeak(edge.getTo().getValue());
                    if (!tree.hasReference(tree.getPeak(edge.getFrom().getValue()), tree.getPeak(edge.getTo().getValue())))
                        tree.addReference(new GraphEdge(tree.getPeak(edge.getFrom().getValue()), tree.getPeak(edge.getTo().getValue()), edge.getCost()));
                }
            }
        });
        System.out.println(tree);
    }

    private class struct {
        int alpha;
        String beta;
    }

}
