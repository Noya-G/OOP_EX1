package ex1;


import java.util.Objects;

public class WGNode implements node_info, java.io.Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGNode wgNode = (WGNode) o;
        return key == wgNode.key &&
                info.equals(wgNode.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, info);
    }
}
