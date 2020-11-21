package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


public class WGraph_DS implements weighted_graph{
    HashMap<Integer,Edge> e_one_direction;
    HashMap<Integer,Edge> e_sec_direction;
    HashMap<Integer,WGNode> graph_members_key;
    ArrayList<node_info> graph_members;
    HashMap<Integer,nodeNeighbors> nodes_neighbors;

    public WGraph_DS(){
        e_one_direction=new HashMap<>();
        e_sec_direction=new HashMap<>();
        graph_members=new ArrayList<>();
        graph_members_key=new HashMap<>();

    }

    

    @Override
    public node_info getNode(int key) {
        return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        return 0;
    }

    @Override
    public void addNode(int key) {

    }

    @Override
    public void connect(int node1, int node2, double w) {

    }

    @Override
    public Collection<node_info> getV() {
        return null;
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {

    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////Private object class///////////////////////////
    ///////////////////////////////////////////////////////////////////////

    //Graph edges:
    private class Edge {
        double priority;
        int k1; // key of the first node;

        public Edge(int k1, double priority){
            this.priority=priority;
            this.k1=k1;

        }
        public double getPriority() {
            return priority;
        }

        public void setPriority(double priotity) {
            this.priority = priotity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Double.compare(edge.priority, priority) == 0 &&
                    k1 == edge.k1;

        }

        @Override
        public int hashCode() {
            return Objects.hash(priority, k1);
        }
    }

    //each node neighbors:
    private class nodeNeighbors{
        ArrayList<Edge> node_paths_neighbors;
        HashMap<Integer,Edge> node_paths_neighbors_k; // Integer - edge key, edge;

        public nodeNeighbors(){
            node_paths_neighbors= new ArrayList<Edge>();
            node_paths_neighbors_k=new HashMap<>();
        }

        /**
         * add neighbors to the node.
         * @param key
         * @param priority
         */
        public void addNeighbor(int key, double priority){
            if(node_paths_neighbors_k.containsKey(key)==true){
                return;
            }
            Edge edge = new Edge(key,priority);
            node_paths_neighbors.add(edge);
            node_paths_neighbors_k.put(key,edge);
        }

        /**
         * remove neighbors.
         * @param key
         */
        public void removeNeighbor(int key){
            if(node_paths_neighbors_k.containsKey(key)==false){
                return;
            }
            Edge e_p=node_paths_neighbors_k.get(key); //edge remove.
            node_paths_neighbors.remove(e_p);
            node_paths_neighbors_k.remove(key);
        }

        /**
         * check if the node key (eich represent a node) is a neighbor
         * @param key
         * @return true/false
         */
        public boolean containNeighbors(int key){
            if (node_paths_neighbors_k.containsKey(key)==true){
                return true;
            }
            return false;
        }

        /**
         * give back array of edges that connect to the node.
         * @return ArrayList<Edge>
         */
        public ArrayList<Edge> getNeighborsEdges(){
            return node_paths_neighbors;
        }

        /**
         *
         * @return HashMap<Integer,Edge>
         */
        public HashMap<Integer,Edge> getNodePaths(){
            return node_paths_neighbors_k;
        }

        


    }
}
