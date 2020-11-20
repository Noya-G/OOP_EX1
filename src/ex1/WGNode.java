package ex1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class WGNode implements node_info{
    int key;
    String info;
    double tag;
    HashMap<Integer, WGNode> node_neighbors_keys;
    ArrayList<node_info> node_neighbors;
    HashMap<Integer,Edge> edge_to_node; // Integer neighbor key, edge;

    public WGNode (int key){
        this.key=key;
        this.info="";
        this.tag=0;
        this.node_neighbors_keys = new HashMap<>();
        this.node_neighbors = new ArrayList<>();
        this.edge_to_node = new HashMap<>();
    }

    /**
     * Return the key (id) associated with this node.
     * Note: each node_data should have a unique key.
     * @return
     */
    public void setKey(int k){
        key=k;
        this.info="-";
        this.tag=Double.MIN_VALUE;
    }

    /**
     * return the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public int getKey() {
        return key;
    }

    /**
     * return the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info=s;
    }

    /**
     * Temporal data (aka distance, color, or state)
     * which can be used be algorithms
     * @return
     */
    @Override
    public double getTag() {
        return tag;
    }

    /**
     * Allow setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(double t) {
        tag=t;
    }

    /**
     * add a node as a neighbor
     * if it already exists and the priority is different change the priority.
     * @param n
     * @param priority
     */
    public void addNi(WGNode n, double priority){

        //if it ask to connect node to it's self:
        if(n.getKey()==key){
            return;
        }

        int n_k=n.getKey(); //neighbor key
        // if the two nodes already connected:
        if(node_neighbors_keys.containsKey(n.getKey())){
            Edge n_e = edge_to_node.get(n_k);
            double ep = n_e.getPriority();//edge priority
            if(priority!=ep) n_e.setPriority(priority);
        }

        //if the the two are not connected:
        else {
            node_neighbors_keys.put(n_k,n);
            node_neighbors.add(n);
            Edge e= new Edge(n_k,priority);
            edge_to_node.put(n_k,e);
        }
    }

    /**
     * check the node is a neighbor or not.
     * @param n
     * @return
     */
    public boolean hadNi(WGNode n){
        if(node_neighbors.contains(n)) return true;
        return false;
    }

    /**
     * get edge priority between two nodes.
     * @param n_k
     * @return
     */
    public double getEdgePriority(int n_k){
        if(node_neighbors_keys.containsKey(n_k)==false){
            return -1;
        }
        Edge e_p = edge_to_node.get(n_k);
        return e_p.getPriority();
    }

    /**
     * remove the the neighbor with the giving key.
     * @param key
     */
    public void removeNi(int key){
        if(node_neighbors_keys.containsKey(key)){
            WGNode np =node_neighbors_keys.get(key); //point on the node we want to remove
            //from thr neighbor.
            node_neighbors.remove(np);
            node_neighbors_keys.remove(key);
            edge_to_node.remove(key);
        }
    }

    public ArrayList<node_info> getNode_neighbors(){
        return node_neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGNode wgNode = (WGNode) o;
        return key == wgNode.key &&
                Double.compare(wgNode.tag, tag) == 0 &&
                Objects.equals(info, wgNode.info) &&
                edge_to_node.equals(wgNode.edge_to_node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, info, tag, edge_to_node);
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////Private object class///////////////////////////
    ///////////////////////////////////////////////////////////////////////

    private class Edge {
        double priority;
        int k;

        public Edge(int k, double priority){
            this.priority=priority;
            this.k=k;
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
                    k == edge.k;
        }

        @Override
        public int hashCode() {
            return Objects.hash(priority, k);
        }
    }

}
