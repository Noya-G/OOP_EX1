package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class represents an undirectional weighted graph.
 * It should support a large number of nodes (over 10^6, with average degree of 10).
 * The implementation should be based on an efficient compact representation
 * (should NOT be based on a n*n matrix).
 *
 */
public class WGraph_DS implements weighted_graph{
    int mc;
    int edges;
    ArrayList<node_info> graph_members;
    HashMap<Integer, WGNode> graph_members_key;

    public WGraph_DS(){
        this.mc=0;
        this.edges=0;
        graph_members= new ArrayList<>();
        graph_members_key=new HashMap<>();
    }


    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return graph_members_key.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        WGNode n1=(WGNode)(graph_members_key.get(node1)); //node1
        WGNode n2=(WGNode)(graph_members_key.get(node2)); //node2
        return n2.hadNi(n1);
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        WGNode n1_p=(WGNode)(graph_members_key.get(node1));//node pointer;
        return n1_p.getEdgePriority(node2);
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(graph_members_key.containsKey(key)){
            return;
        }
        WGNode n = new WGNode(key);
        graph_members_key.put(key,n);
        graph_members.add(n);
        mc++;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(graph_members_key.containsKey(node1) || graph_members_key.containsKey(node2)){
            return;
        }
        WGNode n1=(WGNode)(graph_members_key.get(node1));
        WGNode n2=(WGNode)(graph_members_key.get(node2));
        n1.addNi(n2,w); //add function also connect to node
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return graph_members;
    }

    /**
     *
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        WGNode np= (WGNode)(graph_members_key.get(node_id));// node pointer
        return np.getNode_neighbors();
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        // if the node with the giving key in the graph:
        if (graph_members_key.containsKey(key)){
            mc++;
            WGNode np =(WGNode)(graph_members_key.get(key)); // point on the node we want to delete;
            int number_Ni = np.getNode_neighbors().size(); // the number of the node neighbors
            for (int i=0; i<number_Ni; i++){
                WGNode nN=(WGNode)(graph_members_key.get(key));// node neighbor
                nN.removeNi(key);
            }
            graph_members_key.remove(key);
            graph_members.remove(np);
        }
        //if the node with the giving key is not in the graph:
        return null;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {

        //if one of the nodes isn't in the graph:
        if(graph_members_key.containsKey(node1)==false){
            return;
        }
        if(graph_members_key.containsKey(node2)==false){
            return;
        }

        WGNode n1= (WGNode)(graph_members_key.get(node1)); //point on the node 1.
        WGNode n2= (WGNode)(graph_members_key.get(node2)); //point on the node 1.
        n1.removeNi(node2);
        n2.removeNi(node2);
        mc++;

    }

    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return graph_members.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return edges;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    @Override
    public int getMC() {
        return mc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return edges == wGraph_ds.edges;
    }

    @Override
    public int hashCode() {
        return Objects.hash(edges);
    }
}
