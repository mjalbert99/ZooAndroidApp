package com.example.myapplication;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

/**
 * This class represents the graph connecting exhibits
 */
public class ZooGraph {
    Context context;

    Graph<String, IdentifiedWeightedEdge> g;
    Map<String, ZooData.VertexInfo> vInfo;
    Map<String, ZooData.EdgeInfo> eInfo;
    public static DayPlannerItem START_ITEM;
    public static String START_LAT;
    public static String START_LNG;

    public ZooGraph(Context c) throws IOException {
        this.context = c;

        vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", context);
        eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", context);
        g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", context);
        for (Map.Entry<String, ZooData.VertexInfo> entry : vInfo.entrySet() ) {
            ZooData.VertexInfo value = entry.getValue();
            if (value.kind.equals(ZooData.VertexInfo.Kind.GATE)) {
                START_ITEM = new DayPlannerItem(value.id, "gate", null, value.name, value.tags);
                START_LAT = value.lat;
                START_LNG = value.lng;
            }
        }

        // System.out.println(this.shortestPath("entrance_exit_gate", Arrays.asList("lions", "entrance_exit_gate", "entrance_plaza", "elephant_odyssey", "gators")));
        // System.out.println(this.directions("gators", "elephant_odyssey"));
    }

    public String getIdName(String id) {
        return vInfo.get(id).name;
    }

    /**
     * Text-based directions for the path to take from one exhibit to another
     * @param start the start exhibit id
     * @param goal the goal exhibit id
     * @return a list of text-based directions on how to get from start to goal
     */
    public List<String> directions(String start, String goal) {
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);
        List<String> vertices = path.getVertexList();

        List<String> result = new ArrayList<>();

        int i = 1;
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            result.add(String.format("%d. Walk %.0f meters along %s to get to %s.\n",
                    i,
                    g.getEdgeWeight(e),
                    eInfo.get(e.getId()).street,
                    this.getIdName(vertices.get(i)) ) );

            i++;
        }

        return result;
    }

    public List<String> briefDirections(String start, String goal) {
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);
        List<String> vertices = path.getVertexList();

        List<String> result = new ArrayList<>();

        int v = 1;
        int stepCount = 1;
        float totalEdgeWeights = 0;

        List<IdentifiedWeightedEdge> edgeList = path.getEdgeList();
        for (int i = 0; i < edgeList.size(); i++) {
            if ( i == edgeList.size() - 1) {
                totalEdgeWeights += g.getEdgeWeight(edgeList.get(i));
                result.add(String.format("%d. Walk %.0f feet along %s to get to %s.\n",
                        stepCount,
                        totalEdgeWeights,
                        eInfo.get( edgeList.get(i).getId()).street,
                        this.getIdName(vertices.get(v)) ) );
                continue;
            }
            String currStreet = eInfo.get( edgeList.get(i).getId()).street;
            String nextStreet = eInfo.get( edgeList.get(i+1).getId()).street;
            if (currStreet.equals(nextStreet)) {
                totalEdgeWeights += g.getEdgeWeight(edgeList.get(i));

            }
            else {
                totalEdgeWeights += g.getEdgeWeight(edgeList.get(i));
                result.add(String.format("%d. Walk %.0f feet along %s to get to %s.\n",
                        stepCount,
                        totalEdgeWeights,
                        eInfo.get( edgeList.get(i).getId()).street,
                        this.getIdName(vertices.get(v)) ) );
                totalEdgeWeights = 0;
                stepCount++;

            }
            v++;

        }
        /*
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {

            result.add(String.format("%d. Walk %.0f meters along %s to get to %s.\n",
                    i,
                    g.getEdgeWeight(e),
                    eInfo.get(e.getId()).street,
                    this.getIdName(vertices.get(i)) ) );

            i++;
        }*/

        return result;
    }

    /**
     * Finds the shortest path between a subset of ZooGraph nodes (exhibits)
     * @param start the start exhibit
     * @param exhibits the desired exhibits to visit
     * @return an ordered list of the path to visit chosen exhibits
     */
    public List<String> shortestPath(String start, List<String> exhibits) {
        //GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);
        DijkstraShortestPath dsp = new DijkstraShortestPath(g);
        ShortestPathAlgorithm.SingleSourcePaths allPaths = dsp.getPaths(start);
        HashSet<String> visited = new HashSet<>();
        visited.add(start);
        String currentExhibit = start;
        List<String> res = new ArrayList<>();

        res.add(start);
        if (!exhibits.contains(start)) {
            exhibits.add(start);
        }

        while(visited.size() < exhibits.size()) {
            String closestExhibit = currentExhibit;
            double currMin = Double.MAX_VALUE;
            for(String exhibit : exhibits) {
                if(visited.contains(exhibit)) continue;
                double currWeight = allPaths.getWeight(exhibit);
                if( currWeight< currMin) {
                    closestExhibit = exhibit;
                    currMin = currWeight;
                }
            }
            visited.add(closestExhibit);

            res.add(closestExhibit);
            allPaths = dsp.getPaths(closestExhibit);
            currentExhibit = closestExhibit;

        }
        //res.add(start);
        return res;

    }


}
