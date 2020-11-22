package ex1;

import java.util.ArrayList;
import java.util.HashMap;
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
        WGraph_DS g_c=((WGraph_DS)graph).getCopy();//graph deep copy.
        return g_c;
    }

    @Override
    public boolean isConnected() {
        if(graph.nodeSize()==0){
            return true;
        }
        int n_fullGraph=((graph.nodeSize())*(graph.nodeSize()-1))/2;//check if the graph is full
        if(n_fullGraph==graph.edgeSize()){
            return true;
        }
        WGraph_DS g=((WGraph_DS)graph).getCopy();
        WGNode np=(WGNode)(g.getV().toArray()[0]);  //node pointer.
        return DijkstraAlgoConnected(np,g);
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        WGraph_DS g=((WGraph_DS)graph).getCopy();
        return shortestPathDistDijkstraAlgoConnected((WGNode) g.getNode(src),(WGNode) g.getNode(dest),g);
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

    //////////////////////////////////////////////////////////////////////
    /////////////////////////private methods:////////////////////////////
    ////////////////////////////////////////////////////////////////////

    private boolean DijkstraAlgoConnected(WGNode src,WGraph_DS g){
        if(g.nodeSize()==0 || g.nodeSize()==1){
            return true;
        }
        ArrayList<WGNode> c_n=new ArrayList<>();//contain all that connected to the src.
        ArrayList<WGNode> aidQueue=new ArrayList<>();//aid Queue.
        WGNode nodePointer=null;

        aidQueue.add(src);
        src.setTag(1);
        nodePointer=aidQueue.get(0);
        int pointer=0;
        while (pointer<aidQueue.size()){
            WGNode np=(WGNode)(aidQueue.get(pointer));//node pointer.
            ArrayList<node_info> nNi= (ArrayList<node_info>)g.getV(np.getKey());
            for(int j=0; j<nNi.size(); j++){
                WGNode pNi= (WGNode)nNi.get(j);
                if(pNi.getTag()==Double.MAX_VALUE){
                    pNi.setTag(1);
                    aidQueue.add(pNi);
                }
            }
            pointer++;
            np.setTag(2);// mark the node as one that the algo visited all it neighbors.
            if(aidQueue.size()==g.nodeSize()){ //if mey all the nodes.
                return true;
            }
        }

        if(c_n.size()<g.nodeSize()){
            return false;
        }
        return true;
    }

    private double shortestPathDistDijkstraAlgoConnected(WGNode src, WGNode dest, WGraph_DS g){
        if(g.nodeSize()==2 && g.edgeSize()==1){
            return g.getEdge(src.getKey(),dest.getKey());
        }

        ArrayList<WGNode> stack=new ArrayList<WGNode>();
        stack.add(src);
        src.setTag(1);
        int pointer=0;

        HashMap<Integer,Double> nodeShortPath=new HashMap<>();
        nodeShortPath.put(src.getKey(),0.0);


        while (pointer<stack.size()){

            WGNode np=stack.get(pointer); //node pointer.
            int key= np.getKey();//node key.
            ArrayList<node_info> nNi= (ArrayList<node_info>)g.getV(np.getKey());//node neighbors.
            int numNi=nNi.size();//number of neighbors.
            double priority=nodeShortPath.get(key);//the priority fo the last node

            for(int i=0; i<numNi; i++){
                WGNode pNi= (WGNode)nNi.get(i);//pointer to the node.
                int neighborKey=pNi.getKey();
                Double n1n2p=g.getEdge(key,neighborKey);
                WGNode nPointer= (WGNode)nNi.get(i);
                if(nPointer.getTag()==2){
                    if(n1n2p+priority<nodeShortPath.get(neighborKey)){
                        nodeShortPath.remove(neighborKey);
                        nodeShortPath.put(neighborKey,n1n2p+priority);
                    }
                }
               if(nPointer.getTag()!=2){
                   if (nPointer.getTag()==1){//if the algo met the node but all its neighbors:
                       if((n1n2p+priority)<nodeShortPath.get(neighborKey)){
                           nodeShortPath.remove(neighborKey);
                           nodeShortPath.put(neighborKey,n1n2p+priority);
                       }
                   }
                   if(nPointer.getTag()==Double.MAX_VALUE){//if the algo never met the node:
                       nodeShortPath.put(neighborKey,n1n2p+priority);
                       stack.add(pNi);
                       pNi.setTag(1);
                   }
               }
            }
            np.setTag(2);
            pointer++;
        }
        if(nodeShortPath.containsKey(dest.getKey())){
            return nodeShortPath.get(dest.getKey());
        }
        return -1;
    }



}
