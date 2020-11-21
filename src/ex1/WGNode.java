package ex1;

public class WGNode implements node_info{

    private int key;
    private String info;
    private double tag;

    public WGNode(int key){
        this.key=key;
        this.info="";
        this.tag=Double.MAX_VALUE;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
    info=s;
    }

    @Override
    public double getTag() {
        return tag;
    }

    @Override
    public void setTag(double t) {
    tag=t;
    }
}
