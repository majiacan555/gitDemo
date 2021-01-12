package relation;
import java.util.Scanner;
import java.util.Vector;

public class CopyOfMinheap {
	Vector<VCNode> nodes = new Vector<>();
	static int n = 19;
	static int m = 19;
	static int k = 6;
	static int[][] graph = new int[n+1][n+1];
	
    public void insert(VCNode node) {
    	nodes.add(node);
	    //  cout << "size is " << nodes.size() << endl;//
	    int curpos = (int)nodes.size() - 1; // current position
	    int parent = (curpos - 1) / 2; //parent position
	    while (curpos != parent && parent >= 0) { //parent is still in heap
	        if (nodes.get(parent).e > nodes.get(curpos).e) { //swap parent and child
	            VCNode temp = nodes.get(parent);
	            nodes.set(parent, nodes.get(curpos));
	            nodes.set(curpos,temp );
	        } else {
	            break; //no longer level up!!!
	        }
	        curpos = parent; //when curpos=parent=0, exit!!!
	        parent = (curpos - 1) / 2; //relocate the parent position
	    }
	}
    public VCNode popmin() {
    	VCNode node = new VCNode();
        if (nodes.size() > 0) { //have nodes left
            node = nodes.get(0); //get the first element
            nodes.remove(0); //remove the first element
            if (nodes.size() > 0) { //at least have one element more
                VCNode last = nodes.get(nodes.size() - 1); //get the last element
                nodes.remove(nodes.size() - 1); //pop the last element
//                nodes.set(0, last); //put it in the first place
                nodes.insertElementAt(last, 0);
                int csize = (int)nodes.size(); //current size
                int curpos = 0; //current position
     
                // rebuild the minheap
                while (curpos < (csize / 2)) { //reach to the last parent node!!
                    int left = 2 * curpos + 1; //left child
                    int right = 2 * curpos + 2; //right child
                    int min = left; //min store the min child
                    if (right < csize) { //have left and right childs
                        if (nodes.get(right).e < nodes.get(left).e) {
                            min = right;
                        }
                    }
                    if (min < csize) { //min child exist!!
                        if (nodes.get(min).e < nodes.get(curpos).e) { //need to swap current position with child
                            VCNode temp = nodes.get(min);
                            nodes.set(min, nodes.get(curpos));
                            nodes.set(curpos, temp);
                        }else { //min child no exits!! exit!!
                            break; //can break now!!
                        }
                    }
                    curpos = min;
                }
            }
        }
        return node;
	}
    public static void printArray(int a[], int start, int end){
//        if (INDEBUG) {
            System.out.println("print array form " + start + " to " + end);
            for (int i=start; i<=end; i++) {
                System.out.println(a[i]+" ");
            }
            System.out.println("print array end");
//        }
    }
    public void printGraph(int[][] graph){
//        if (INDEBUG) {
            for(int i=1;i<=n;i++){//0 no need
                for(int j=1;j<=n;j++){
                    System.out.println(graph[i][j] + " ");
                }
            }
//        }
    }
    public static int partition2(int a[], int low, int high, int b[]){
        int key = a[high];
        int i=low-1;
        for (int j=low; j<high; j++) {
            if (a[j]>=key) {
                i++;
                swap(a,i,j);
                swap(b,i,j);
            }
        }
        swap(a,high, i+1);
        swap(b,high, i+1);
        return i+1;
    }
    private static void swap(int[] a, int i, int j) {
		int k = a[i];
		a[i] = a[j];
		a[j] = k;
	}
	public static void quicksort2(int a[], int low, int high, int b[]) {
        if (low < high) {
            int p = partition2(a,low,high, b);
            quicksort2(a, low, p-1, b);
            quicksort2(a, p+1, high, b);
        }
    }
    public static int sumofkmax(int edges[], int p, int nodes[], int state[]){
        quicksort2(edges, 1, n, nodes);
        int sum=0,count=0;
        // edges[i] corresponse to nodes[i], its state is state[nodes[i]]
        for(int i=1;i<=n;i++){//attention to i range!!
            if (state[nodes[i]]==0) {
                sum+=edges[i];
                count++;
                if (count == p) {//enough!
                    break;
                }
            }
        }
        return sum;
    }
    public static boolean verify(int edges[], int p, int e, int nodes[], int state[]){//edges是所有的边的集合，p是可以选的点数，e是需要覆盖的边
        //caculate the sum of the first p max elements in array edges!!
        int sum = sumofkmax(edges, p, nodes, state);
        // edge of nodes[i] is edges[i]!!!
        if(sum >= e){// may be this can be achieved
            return true;
        }
        return false;
    }
    public static void buildIndex(int node[],int index[]){
        for (int i=1; i<=n; i++) {
            index[node[i]] = i;
        }
    }
    public static int nextNode(int state[], int nodes[]){
        for (int i=1; i<=n; i++) {
            if (state[nodes[i]]==0) {
                return nodes[i];
            }
        }
        return -1;
    }
    
    public static VCNode genLeft(VCNode curnode, int label){
        VCNode left = new VCNode();//choose node label!
        left.p = curnode.p - 1;//remove one node
        left.e = curnode.e;
        for (int i=0; i<=n; i++) {//first copy all infos
            left.index[i]=curnode.index[i];
            left.state[i]=curnode.state[i];//init node state
            left.edge[i]=curnode.edge[i];//copy edge info
            left.node[i]=curnode.node[i];//copy node info
//            for (int j=0; j<=n; j++) {
//                left.graph[i][j] = curnode.graph[i][j];
//            }
        }
        // following code will not use curnode anymore!!
     
        ///
        int sum=0;//removed edge
        for (int j=1; j<=n; j++) {
            //new
            if (label < j && left.state[j]!=1 && graph[label][j]==1 ) {//row!
                sum++;
//                left.graph[label][j]=0;
                left.edge[left.index[j]]--;//how to cut it down
            }else if(label > j && left.state[j]!=1 && graph[j][label]==1 ){ // col
                sum++;
//                left.graph[j][label]=0;
                left.edge[left.index[j]]--;//how to cut it down
            }
        }
        ///
     
        left.state[label] = 1;//use label directly!
        left.edge[left.index[label]] = 0;//only use index!!
//        cout << "remove edge sum is " << sum << endl;
        quicksort2(left.edge, 1, n, left.node);
        left.e = left.e - sum;//remove some edges
        buildIndex(left.node, left.index);
     
//        if (INDEBUG) {
           System.out.println("======== " + label + " gen left begin===========" ); 
            System.out.println("edge is "+left.e+ " node is " + left.p); 
            System.out.println("array edge:"); 
            printArray(left.edge,1,n);
            System.out.println("array node:"); 
            printArray(left.node, 1, n);
            System.out.println("array index:"); 
            printArray(left.index, 1, n);
            System.out.println("array state:");
            printArray(left.state, 1, n);
//            printGraph(left.graph);
            System.out.println("======== " + label + " gen left end===========" );
//        }
     
        return left;
    }
    public static VCNode genRight(VCNode curnode, int label){
        VCNode right = new VCNode();//choose node label!
        right.p = curnode.p;//remain
        right.e = curnode.e;
        for (int i=0; i<=n; i++) {//first copy all infos
            right.index[i]=curnode.index[i];
            right.state[i]=curnode.state[i];//init node state
            right.edge[i]=curnode.edge[i];//copy edge info
            right.node[i]=curnode.node[i];//copy node info
//            for (int j=0; j<=n; j++) {
//                right.graph[i][j] = curnode.graph[i][j];
//            }
        }
        // following code will not use curnode anymore!!
        right.state[label] = -1;//use label directly!
     
//        if (INDEBUG) {
            System.out.println("======== "+ label+ " gen right begin==========="); 
            System.out.println( "edge is " + right.e+" node is "+ right.p);
//            cout << "array edge:" << endl;
//            printArray(right.edge,1,n);
//            cout << "array node:" << endl;
//            printArray(right.node, 1, n);
//            cout << "array index:" << endl;
//            printArray(right.index, 1, n);
//            cout << "array state:" << endl;
//            printArray(right.state, 1, n);
//            printGraph(right.graph);
            System.out.println("======== "+label+" gen right end==========="); 
//        }
        return right;
    }
    public static void greedyFind(int edges[], int nodes[]/*, int graph[][MAX_NODE]*/){
        VCNode node = new VCNode();
        node.e = m;
        node.p = k;
     
        for (int i=0; i<=n; i++) {
            node.index[i]=0;
            node.state[i]=0;//init node state
            node.edge[i]=edges[i];//copy edge info
            node.node[i]=nodes[i];//copy node info
//            for (int j=0; j<=n; j++) {
//                node.graph[i][j] = graph[i][j];
//            }
        }
        buildIndex(node.node, node.index);
     
        CopyOfMinheap minheap = new CopyOfMinheap();
        minheap.insert(node);
     
        while (minheap.nodes.size() > 0) {
            // get the heap top node to extend
            VCNode curnode = minheap.popmin();
//            if (INDEBUG) {
//                cout << "...current graph..." << endl;
//                printGraph(curnode.graph);
//            }
     
            // validate the current node
            if (curnode.e == 0) {
                int points = k - curnode.e;
                System.out.println(points);
                int count = 1;
                for (int i=1; i<=n; i++) {
                    if (curnode.state[i]==1) {
                        if(count == points){
                            System.out.println(i);;
                        }else{
                            System.out.println(i+" ");;
                        }
                        count++;
                    }
                }
                return;
            }
     
            // generate child nodes
            int label = nextNode(curnode.state, curnode.node);//the label of the node
            if (label != -1) {
                // node i is in index[k] position in array [node]
                // node i has number of edge[i] edges
                VCNode left = genLeft(curnode, label);
                VCNode right = genRight(curnode, label);
                if (verify(left.edge, left.p, left.e, left.node, left.state)) {
//                    cout << "insert " << label << " left" << endl;
                    minheap.insert(left);
                }
                if (verify(right.edge, right.p, right.e, right.node, right.state)) {
//                    cout << "insert " << label << " right" << endl;
                    minheap.insert(right);
                }
            }
     
        }
        // if not find, then return -1
        System.out.println(-1);
    }
    public static void main(String[] args) {
    	int t = 10;
        while(t-->0){
//          cin >> n >> m >> k; //n是指定点的个数，m是指需要覆盖的边，k是指上界可以选择的点数。
//          int graph[n+1][MAX_NODE];
          for (int i=0; i<= n; i++) {
              for (int j=0; j<= n; j++) {
                  graph[i][j]=0;
              }
          }
          int[] edges = new int[n+1];
          int[] nodes = new int[n+1];
          int[] state = new int[n+1];
//          int edges[n+1], nodes[n+1], state[n+1];
          for (int i=0; i<= n; i++) {
              edges[i]=0;
              state[i]=0;
              nodes[i]=i;
          }
          int temp = m;
          while(temp-->0){
//              cin >> a >> b;
        	  Scanner sc = new Scanner(System.in);
        	  int a = sc.nextInt();
        	  int b = sc.nextInt();
              graph[Math.min(a, b)][Math.max(a,b)]=1;
//            graph[a][b]=1;
//            graph[b][a]=1;//just save half a<=b
              edges[a]++;
              edges[b]++;
          }
          boolean flag = verify(edges, k, m, nodes, state);//k是可以选的点数，m是需要覆盖的边，edges是所有的边的集合
          													//nodes是顶点的个数，state是顶点的状态。
   
          if (!flag) {//must not be achieved!!!
              System.out.println(-1);
          }else{
              greedyFind(edges,nodes/*,graph*/);
          }
        }
  }
}
