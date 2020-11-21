package ex1;

import java.util.ArrayList;
import java.util.List;

public class WGraph_Algo implements weighted_graph_algorithms {
    weighted_graph graph;

    public WGraph_Algo(){
        graph=new WGraph_DS();
    }

    @Override
    public void init(weighted_graph g) {
        this.graph=g;
    }

    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph g_c=new WGraph_DS();//graph deep copy.

        //get all graph nodes:
        ArrayList<node_info> nodes_graph= (ArrayList<node_info>)graph.getV();
        int graph_size= graph.nodeSize();

        //deep copy of graph nodes:
        for(int i=0; i<graph_size; i++){
            //node
        }

        //copy all the graph edges:
        int edgesize =graph.edgeSize();
        return g_c;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
