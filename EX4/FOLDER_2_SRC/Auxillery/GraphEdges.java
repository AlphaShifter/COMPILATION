package Auxillery;

import java.util.ArrayList;

public class GraphEdges {
    public class Edge {
        private Integer src;
        private Integer dst;
        public Edge(Integer s, Integer d) {
            this.src = s;
            this.dst = d;
        }
        public Integer getSrc() { return src; }
        public Integer getDst() { return dst; }
    }
    public ArrayList<Edge> computeGraphEdges(ArrayList<Integer> nodes) {
        ArrayList<Edge> res = new ArrayList<>();
        for(Integer src : nodes) {
            for(Integer dst : nodes) {
                if(!src.equals(dst)) {
                    res.add(new Edge(src, dst));
                }
            }
        }
        return res;
    }
}
