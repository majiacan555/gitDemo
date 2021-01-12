package relation;
public class VCNode {
	int p;//points can be used
    int e;//edges to cover!!
    int[] index = new int[20];//the index of each node in array [node], index[k]=i!!
    int[] edge = new int[20];//MAX_NODE the edge number of each node, edge[i]=j!!
    int[] node = new int[20];//the order of the node
    int[] state = new int[20];//the state of each node ** 0 can be used / 1 used / -1 can not be used
    public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getE() {
		return e;
	}
	public void setE(int e) {
		this.e = e;
	}
	public int[] getIndex() {
		return index;
	}
	public void setIndex(int[] index) {
		this.index = index;
	}
	public int[] getEdge() {
		return edge;
	}
	public void setEdge(int[] edge) {
		this.edge = edge;
	}
	public int[] getNode() {
		return node;
	}
	public void setNode(int[] node) {
		this.node = node;
	}
	public int[] getState() {
		return state;
	}
	public void setState(int[] state) {
		this.state = state;
	}
//    int graph[MAX_NODE][MAX_NODE];//the graph on the node//no need,just use the global graph
    // node k is in index[k]=i position in array [node]
    // node i has number of edge[i]=j edges
}
