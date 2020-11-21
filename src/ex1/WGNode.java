package ex1;

public class WGNode implements node_info{

    int key;
    String info;
    double tag;

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
