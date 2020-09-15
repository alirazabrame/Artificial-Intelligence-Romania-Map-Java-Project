package project;

class Edge{
	int vertex,weight;
	public Edge(int v,int w){
		this.vertex=v; 
		this.weight=w; 
	}
	
	@Override
	public String toString(){
		
		return "("+Graph.cities[vertex]+","+weight+")";
	}
	
}