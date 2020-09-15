package project;

import java.io.*; 
import java.util.*; 
class Graph 
{ 
	
    private int V;   // No. of vertices 
    private LinkedList<Edge> adj[]; //Adjacency Lists
    static String[] cities;
  
    Graph(int v,String[] cities) 
    { 
        this.V = v; 
        adj = new LinkedList[v]; 
        for (int i=0; i<v; ++i) 
            adj[i] = new LinkedList(); 
        this.cities=cities;
    }
   
    void addEdge(int fromVertex,int toVertex,int weight) 
    { 
        Edge e=new Edge(toVertex,weight);
        adj[fromVertex].add(e);
        //System.out.println("( "+v+" , "+dest+" , "+weight+" )");
    } 
  @Override
	public String toString(){
		String result="";
               
		for(int i=0;i<adj.length;i++)
                       
			result+=cities[i]+"=>"+adj[i]+"\n";
		return result;
	}
    void BFS(int s,int goal) 
    { 
    	int[] cityCost=new int[V];
    	String[] cityPath = new String[V];
        //list of visited vertices or cities
    	//the indexes are based on the value of the vertices
        boolean visited[] = new boolean[V]; 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        
        visited[s]=true; 
        queue.add(s);
        cityPath[s] = cities[s]; //starting city
        cityCost[s] = 0; //initial cost

        while (queue.size() != 0) 
        { 
            System.out.print(cities[s]+": ");
        	printQueue(queue);
            s = queue.poll();
            
            if(s==goal)break;
           
            Iterator<Edge> i = adj[s].listIterator(); 
            int n;
            
            while (i.hasNext()) 
            { 
                Edge e = i.next();
                n=e.vertex;
                
                if (!visited[n]) 
                {   
                	cityPath[n]=cityPath[s]+"-->"+cities[n];
                	cityCost[n]=cityCost[s]+e.weight;
                    visited[n] = true; 
                    queue.add(n);
                }//if
            }//inner-while 
        }//outer-while 
        System.out.print(cities[s]+": ");
        printQueue(queue);
        
        System.out.println("\nPath is: "+cityPath[s]);
        System.out.println("Cost is: "+cityCost[s]);
    }
   
    void DFS(int s,int goal) 
        { 
	    	int[] cityCost=new int[V];
	    	String[] cityPath = new String[V];
	    	
	    	Stack<Integer> stack = new Stack<>(); 
	    	/*we use vector because it appends the comming value 
	    	  at the end. it is same as stack. Vector use first in
	    	  last out mechanism (FILO)
	    	*/
            Vector<Boolean> visited = new Vector<Boolean>(V);
            for (int i = 0; i < V; i++)
                visited.add(false);
            
            stack.push(s);
            cityPath[s] = cities[s]; //starting city
            cityCost[s] = 0; //initial cost
            int cost=0,c; 
            while(!stack.empty()) 
            { 
                System.out.print(cities[s]+": ");
            	printStack(stack);
                s=stack.pop();
                
                if(!visited.get(s)) 
                    visited.set(s, true);
                
                if(s==goal)break; 
                
                Iterator<Edge> iterator = adj[s].iterator(); 
                  
                while (iterator.hasNext())  
                {   
                    Edge e = iterator.next();
                    int n=e.vertex;
                    if(!visited.get(n)){
                    	cityPath[n]=cityPath[s]+"-->"+cities[n];
                    	cityCost[n]=cityCost[s]+e.weight;
                        stack.push(n); 
                    } 
                }//inner-while    
            }//outer-while
            System.out.print(cities[s]+": ");
            printStack(stack);
            
            System.out.println("\nPath is: "+cityPath[s]);
            System.out.println("Cost is: "+cityCost[s]);
        } 
   
    void AST(int s,int goal) {
    	//index of huerists is corresponding to name to the city
    	//Cities are entered according to the index
    	int[] hueristic = {366,329,253,374,244,193,178,380,241,160
    						,98,0,242,77,80,151,161,199,226,234};
    	//printHueristic(hueristic);
    	
    	String[] hueristicPlusCost=new String[V];
    	int[] hpc=new int[V];
    	int[] weight= new int[V]; 
    	Comparator<Integer> hueristicComparator= new Comparator<Integer>() {
			
			@Override
			public int compare(Integer o1, Integer o2) {
				
				return hpc[o1]-hpc[o2];
			}
		};
    	
    	int[] cityCost=new int[V];
    	String[] cityPath = new String[V];
        //list of visited vertices or cities
    	//the indexes are based on the value of the vertices
        boolean visited[] = new boolean[V]; 
       // LinkedList<Integer> queue = new LinkedList<Integer>();
        PriorityQueue<Integer> queue = new PriorityQueue<>(hueristicComparator);
        
        hpc[s]=weight[s]+hueristic[s];
        hueristicPlusCost[s]="Weight:"+weight[s]+", Hueristic:"+hueristic[s]
        		+"Total= "+(weight[s]+hueristic[s]);
        
        visited[s]=true; 
        queue.add(s);
        weight[s]=0;
        cityPath[s] = cities[s]; //starting city
        cityCost[s] = 0; //initial cost
        
        boolean stopNow=false;
        while (queue.size() != 0) 
        {
            System.out.print(cities[s]+": "); 
        	printPriorityQueue(queue);
            s = queue.poll();
            
            if(s==goal)break;
           
            Iterator<Edge> i = adj[s].listIterator(); 
            int n;
            
            while (i.hasNext()) 
            { 
                Edge e = i.next();
                n=e.vertex;
                
                if (!visited[n]) 
                {   
                	cityPath[n]=cityPath[s]+"-->"+cities[n];
                	cityCost[n]=cityCost[s]+e.weight;
                	weight[n]=cityCost[n];
                	
                	hpc[n]=weight[n]+hueristic[n];
                	hueristicPlusCost[n]="Weight:"+weight[n]+", Hueristic:"+hueristic[n]
                    		+"Total= "+(weight[n]+hueristic[n]);
                	
                    visited[n] = true; 
                    queue.add(n);
                }//if
            }//inner-while 
        }//outer-while 
        System.out.print(cities[s]+": ");
        printPriorityQueue(queue);
        
        System.out.println("\nPath is: "+cityPath[s]);
        System.out.println("Cost is: "+cityCost[s]);
//        System.out.println("\nArray Weight: ");
//        printIngeterArray(weight);
//        System.out.println("\nArray CityCost: ");
//        printIngeterArray(cityCost);
//        System.out.println("\nArray CityPath: ");
//        printStringArray(cityPath);
        //System.out.println("\nPriority Queue: ");
        //printPriorityQueue(queue);
        
        //printHueristicPlusCost(hueristicPlusCost);
    	
    }//AST
    
    static void printHueristic(int[] hueristic) 
    {	
    	System.out.println("Hueristics are:");
    	for (int i = 0; i < hueristic.length; i++) {
			System.out.println(cities[i]+":"+hueristic[i]);
		}
    	
    }
    
    static void printStringArray(String[] path) 
    {
    	for (int i = 0; i < path.length; i++) {
			if(path[i]!=null)
				System.out.println(cities[i]+":"+path[i]);
		}
    	
    }
    
    static void printHueristicPlusCost(String[] hc) {
    	System.out.println("=====Hueristic + Cost=======");
    	for (int i = 0; i < hc.length; i++) {
			if (hc[i]!=null) {
				System.out.println(cities[i]+": "+hc[i]);
			}
		}
    	System.out.println("=====Hueristic + Cost=======");
    }
    
    static void printIngeterArray(int[] weight) 
    {
    	String s="";
    	for (int i = 0; i < weight.length; i++) {
			if (weight[i]!=0) {
				s+=cities[i]+":"+weight[i]+" ";
			}
		}
    	System.out.println(s);
    }
    
    static void printPriorityQueue(PriorityQueue<Integer> queue) 
    {
    	String s="";
    	for (Integer integer : queue) {
			s+=cities[integer]+"-->";
		}
    	System.out.println(s);
    }
    
    static void printStack(Stack<Integer> stack) {
    	String s="";
    	for (Integer vertex : stack) {
    		s+=cities[vertex]+"-->";
		}
    	System.out.println(s);
    }
   
    static void printQueue(LinkedList<Integer> queue) {
    	String s="";
    	for (Integer value : queue) {
			s+=cities[value]+"-->";
		}
    	System.out.println(s);
    }
    //Driver Main
    // Driver method to 
    public static void main(String args[]) 
    { 
    	String[] cities= {"Arad","Timisioara","Sibiu","Zerind","Lugoj",
    			"Rimnicu Vilcea","Fagarus","Oradea","Mehadia","Craiova"
    			,"Pitesti","Bucharest","Drobeta","Giurgiu","Urziceni"
    			,"Hirsova","Eforie","Vaslui","Iasi","Neamt"};
        Graph RomaniaMap = new Graph(20,cities); 
        
        /*
        0= Arad, 1=Timisioara, 2=Sibiu, 3=Zerind, 4=Lugoj
        5=Rimnicu Vilcea, 6=Fagarus, 7=Oradea, 8=Mehadia
        9=Craiova, 10=Pitesti, 11=Bucharest, 12=Drobeta
        13=Giurgiu, 14=Urziceni, 15=Hirsova, 16=Eforie
        17=Vaslui, 18=Iasi, 19=Neamt
         */
        //Arad-->Timisoara				Arad-->Sibiu
        RomaniaMap.addEdge(0, 1, 118); RomaniaMap.addEdge(0, 2, 140);
      //Arad-->Zerind
        RomaniaMap.addEdge(0, 3, 75); 
        
        //Timisoara-->Arad				Timisoara-->Lugoj
        RomaniaMap.addEdge(1, 0, 118); RomaniaMap.addEdge(1, 4, 111);
        
        //Sibiu-->Arad					Sibiu-->Rimnicu Vilcea
        RomaniaMap.addEdge(2, 0, 140); RomaniaMap.addEdge(2, 5, 80);
        //Sibiu-->Fagaras				Sibiu-->Oradea
        RomaniaMap.addEdge(2, 6, 99);  RomaniaMap.addEdge(2, 7, 151);
        
        //Zerind-->Arrad				Zerind-->Oradea
        RomaniaMap.addEdge(3, 0, 75); RomaniaMap.addEdge(3, 7, 71);
        
        //Lugoj-->Timisoara				Lugoj-->Mehadia
        RomaniaMap.addEdge(4, 1, 111); RomaniaMap.addEdge(4, 8, 70);
        
        //Rimnicu Vilcea-->Sibiu		Rimnicu Vilcea-->Craiova
        RomaniaMap.addEdge(5, 2, 80); RomaniaMap.addEdge(5, 9, 146);
        //Rimnicu Vilcea-->Pitesti
        RomaniaMap.addEdge(5, 10, 97);
        
        //Fagaras-->Sibiu				Fagaras-->Bucharest
        RomaniaMap.addEdge(6, 2, 99); RomaniaMap.addEdge(6, 11, 211);
        
        //Oradea-->Zerind				Oradea-->Sibiu
        RomaniaMap.addEdge(7, 3, 71); RomaniaMap.addEdge(7, 2, 151);
        
        //Mehadia-->Lugoj				Mehadia-->Drobeta
        RomaniaMap.addEdge(8, 4, 70); RomaniaMap.addEdge(8, 12, 75);
        
        //Craiova-->Drobeta				Craiova-->Pitesti
        RomaniaMap.addEdge(9, 12, 120); RomaniaMap.addEdge(9, 10, 138);
        //Craiova-->Rimnicu Vilcea
        RomaniaMap.addEdge(9, 5, 146);
        
        //Pitesti-->Craiova				Pitesti-->Bucharest
        RomaniaMap.addEdge(10, 9, 138); RomaniaMap.addEdge(10, 11, 101);
        //Pitesti-->Rimnicu Vilcea
        RomaniaMap.addEdge(10, 5, 97);
        
        //Bucharest-->Pitesti			Bucharest-->Fagaras
        RomaniaMap.addEdge(11, 10, 101); RomaniaMap.addEdge(11, 6, 211);
        //Bucharest-->Giurgiu			Bucharest-->Urzeceni
        RomaniaMap.addEdge(11, 13, 90); RomaniaMap.addEdge(11, 14, 85);
     
        //Drobeta-->Mehadia				Drobeta-->Craiova
        RomaniaMap.addEdge(12, 8, 75); RomaniaMap.addEdge(12, 9, 120);
        
        //Giurgiu-->Bucharest
        RomaniaMap.addEdge(13, 11, 90);
        
        //Urzeceni-->Bucharest			Urzeceni-->Hirsova
        RomaniaMap.addEdge(14, 11, 85); RomaniaMap.addEdge(14, 15, 98);
        //Urzeceni-->Vaslui
        RomaniaMap.addEdge(14, 17, 142);
        
        //Hirsova-->Urzeceni			Hirsova-->Eforie
        RomaniaMap.addEdge(15, 14, 98); RomaniaMap.addEdge(15, 16, 86);
        
        //Eforie-->Hirsova
        RomaniaMap.addEdge(16, 15, 86);
        
        //Vaslui-->Urzeceni				Vaslui-->Iasi
        RomaniaMap.addEdge(17, 14, 142); RomaniaMap.addEdge(17, 18, 92);
        
        //Iasi-->Vaslui					Iasi-->Neamt
        RomaniaMap.addEdge(18, 17, 92); RomaniaMap.addEdge(18, 19, 87);
        
        //Neamt-->Iasi
        RomaniaMap.addEdge(19, 18, 87);
       
        //System.out.println("ROMANIA MAP ADJACENCY LIST:\n"+RomaniaMap);
       
        int from=0, to=11;
        System.out.println("Following is Breadth First Traversal "+ 
                           "\n(starting from vertex "+from+" ("+cities[from]+")"
                           		+ " to reach vertex "+to+" ("+cities[to]+")"); 
  
        RomaniaMap.BFS(from,to); 
        System.out.println("");
        //======================================
        System.out.println("Following is Depth First Traversal "+ 
        		"\n(starting from vertex "+from+" ("+cities[from]+")"
           		+ " to reach vertex "+to+" ("+cities[to]+")");  
  
        RomaniaMap.DFS(from,to);
        System.out.println();
        //=======================================
        System.out.println("Following is A Star Traversal "+ 
        		"\n(starting from vertex "+from+" ("+cities[from]+")"
           		+ " to reach vertex "+to+" ("+cities[to]+")");  
  
        RomaniaMap.AST(from,to);
        
    }//main

    
}//class

