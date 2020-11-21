package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph {
    HashMap<Integer,node_info> graph_members;//node key,node.
    HashMap<Integer,HashMap<Integer,Edge>> graph_node_edges;//node key, each node edges.
    HashMap<Integer,ArrayList<node_info>> node_neighbors;
    ArrayList<Edge> graph_edges;
    int mc,edges;

    public WGraph_DS(){//constructor.
        this.mc=0;
        this.edges=0;
        node_neighbors=new HashMap<>();
        graph_members=new HashMap<>();
        graph_node_edges=new HashMap<>();
        graph_edges=new ArrayList<>();
    }

    @Override
    public node_info getNode(int key) {
        return graph_members.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if(graph_node_edges.containsKey(node1)==false){
          return false;
        }

        //check if the to node are connected:
        HashMap<Integer,Edge> hashPointer=graph_node_edges.get(node1);//pointer to the inner hashmap
        if(hashPointer.containsKey(node2)==false){
            return false;
        }
        return true;
    }

    @Override
    public double getEdge(int node1, int node2){

        //if the Edge exist:
        if(hasEdge(node1,node2)){
            HashMap<Integer,Edge> hashPointer=graph_node_edges.get(node1);//pointer to the inner hashmap
            Edge edgePointer=hashPointer.get(node2);
            return edgePointer.getPriority();
        }

        //else:
        return -1;
    }

    @Override
    public void addNode(int key) {

        //if the node is already exist:
        if(graph_members.containsKey(key)){
            return;
        }

        //else:
        mc++;
        WGNode n =new WGNode(key);
        graph_members.put(key,n);
        node_neighbors.putIfAbsent(key,new ArrayList<node_info>());
    }

    @Override
    public void connect(int node1, int node2, double w) {

       //check if the two node is in the graph:
        if(graph_members.containsKey(node1)==false || graph_members.containsKey(node2)==false){
            return;
        }

        //if the edge exist:
        mc++;
        if(hasEdge(node1,node2)){
            HashMap<Integer,Edge> hashPointer1=graph_node_edges.get(node1);//pointer to the inner hashmap
            Edge edgePointer=hashPointer1.get(node2);
            edgePointer.setPriority(w);
        }

        //create the new edge:
        else{
            edges++;
            Edge ep=new Edge(node1,node2,w); //pointer to the new edge.
            graph_edges.add(ep);

            if(graph_node_edges.containsKey(node1)){  //if the node 1 has edges:
                HashMap<Integer,Edge> hashPointer1=graph_node_edges.get(node1);//pointer to the inner hashmap of node1
                if(graph_node_edges.containsKey(node2)){//if node 2 has edges:
                    HashMap<Integer,Edge> hashPointer2=graph_node_edges.get(node2);//pointer to the inner hashmap of node2
                    hashPointer1.put(node2,ep);//add the edge.
                    hashPointer2.put(node1,ep);//add the edge.
                }

                else{//if the node 1 has neighbors and node 2 doesn't have neighbors:
                    hashPointer1.put(node2,ep);//add the edge.
                    graph_node_edges.put(node2,new HashMap<Integer, Edge>());//add hassMap to the neighbors to node 2.
                    HashMap<Integer,Edge> hashPointer2=graph_node_edges.get(node2);//pointer to the inner hashmap of node2
                    hashPointer2.put(node1,ep);//add the edge.
                }
            }

            else{//if the node 2 has neighbors and node 1 doesn't have neighbors:
                HashMap<Integer,Edge> hashPointer2=graph_node_edges.get(node2);//pointer to the inner hashmap of node2
                hashPointer2.put(node1,ep);//add the edge.
                graph_node_edges.put(node1,new HashMap<Integer, Edge>());//add hassMap to the neighbors to node 1.
                HashMap<Integer,Edge> hashPointer1=graph_node_edges.get(node1);//pointer to the inner hashmap of node1
                hashPointer1.put(node2,ep);//add the edge.
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return graph_members.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return node_neighbors.get(node_id);
    }

    @Override
    public node_info removeNode(int key) {
        //ifthe node is in the graph:
        if(graph_members.containsKey(key)){
            mc++;
            node_info np=graph_members.get(key);//node pointer.
            int num_of_neighbors=0;
            if(graph_node_edges.containsKey(key)==false){//if the node has no neighbors.
                return np;
            }

            Collection<Edge> hashPointer= graph_node_edges.get(key).values();//pointer to the inner hash.
            for (Edge e: hashPointer) {
                int k_d;//the key of the node that connect to the node we want delete.
                if (e.getNodesKeys()[0] == key) {// if the first key of the edge is the key:
                    k_d = e.getNodesKeys()[1];
                } else {
                    k_d = e.getNodesKeys()[0];
                }
                Edge ep=graph_node_edges.get(k_d).get(key);//edge pointer.
                if(graph_edges.contains(ep)) graph_edges.remove(ep);
                graph_node_edges.get(k_d).remove(key);
                node_neighbors.get(k_d).remove(key);
            }
            node_neighbors.remove(key);
            return np;
        }
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(graph_members.containsKey(node1) || graph_members.containsKey(node2)){//if one of the node is not exist:
            return;
        }
        mc++;
        edges--;
        graph_edges.remove(graph_node_edges.get(node1).remove(node2));
        //remove the edge:
        graph_node_edges.get(node1).remove(node2);
        graph_node_edges.get(node2).remove(node1);
        //remove the connection between the node:
        node_neighbors.get(node1).remove(getNode(node2));
        node_neighbors.get(node2).remove(getNode(node1));

    }

    @Override
    public int nodeSize() {
        return graph_members.size();
    }

    @Override
    public int edgeSize() {
        return edges;
    }

    @Override
    public int getMC() {
        return mc;
    }

    /**
     * give a deep copy of the graph.
     * @return
     */
    public WGraph_DS getCopy(){
        WGraph_DS c_g=new WGraph_DS();

        //add all the nodes for the copy of the graph:
        for (int i=0; i<graph_members.size(); i++){
            WGNode pn= (WGNode)graph_members.get(i);//pointer to the original node.
            int key=pn.getKey();//the key of the node is copied
            c_g.addNode(key);
            WGNode cpn= (WGNode)c_g.getNode(key);//pointer to the node is copied
            cpn.setInfo(pn.getInfo());
        }

        //connecting the node like the original graph:
        for (int i=0; i<graph_edges.size(); i++){
            Edge pe=graph_edges.get(i);//pointer of th edge
            double ePriority= pe.getPriority();
            int k1=pe.getNodesKeys()[0];//the first node key
            int k2=pe.getNodesKeys()[1];//the second node key
            c_g.connect(k1,k2,ePriority);
        }

        return c_g;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////Private object class///////////////////////////
    ///////////////////////////////////////////////////////////////////////


    //Graph edges:
    private class Edge {
        double priority;
        int k1; // key of the first node;
        int k2; // key of the second node;

        public Edge(int k1, int k2, double priority){
            this.priority=priority;
            this.k1=k1;
            this.k2=k2;

        }
        public double getPriority() {
            return priority;
        }

        public void setPriority(double priotity) {
            this.priority = priotity;
        }

        /**
         * get the keys of the towo nodes that the edge connecting.
         * @return array
         */
        public int[] getNodesKeys(){
            int[] arrKeys=new int[2];
            arrKeys[0]=k1;
            arrKeys[1]=k2;
            return arrKeys;
        }


    }

}
