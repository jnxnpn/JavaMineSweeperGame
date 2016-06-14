import java.util.*;

public class Game  {
	
	public static int[][] answer ;  //9 = bomb, 1-8 = number of surrounding bombs, 0=safe.
	public static boolean[][] status ; //false = unrevealed
	
	public static boolean getConfig(){
		Scanner scan = new Scanner(System.in); 			
		
		System.out.println("input game size in x,y,bomb number");
		String in = scan.next();
		int numCommaP=0;
		int[] commaPosPs = new int[2];
		for (int n=0; n< in.length();n++ ){
			if (in.charAt(n)==',') {
				numCommaP++;
				if (numCommaP<=2)
				commaPosPs[numCommaP-1] = n;
			}
		}
		if(numCommaP==2) {
			 try {
				   		Jinxin.xSize = Integer.parseInt(in.substring(0,commaPosPs[0]));
				   		Jinxin.ySize = Integer.parseInt(in.substring(commaPosPs[0]+1,commaPosPs[1]));
				   		Jinxin.b = Integer.parseInt(in.substring(commaPosPs[1]+1,in.length()));
				   		if (Jinxin.xSize*Jinxin.ySize<=Jinxin.b) {
				   			System.out.println("error-too many bombs");
				   			return false;
				   		}
				  } catch (NumberFormatException exc ) {
					  System.out.println("error-comma but not number(size)\n");
					  return false;
				  }
			 System.out.println("X:"+Jinxin.xSize+"		Y:"+Jinxin.ySize+"		Bomb:"+Jinxin.b);
			 return true;
			 
		}else{
			System.out.println("wrong format(prompting size)\n");
			return false;
		}
	}
	
	
	public static void generateNew(){//generate map&initialize status,answer
		status = new boolean[Jinxin.xSize][Jinxin.ySize];
		
		/*for(int n=0; n<Jinxin.xSize;n++){
			for(int u=0; u<Jinxin.ySize;u++){
				status[n][u]=false;
			}
		}*/
		System.out.print(" ");//first line
		for(int n=0; n<Jinxin.xSize;n++){
			System.out.print(" "+(n+1)%10);
		}
		System.out.println();
		
		for(int n=0; n<Jinxin.ySize*2+1;n++){
			if (n==0) {//second line
				System.out.print(" ┌");
				for(int u=0; u<Jinxin.xSize-1;u++){
					System.out.print("─┬");
				}
				System.out.println("─┐");
				
			}else if (n%2==0 && n !=Jinxin.ySize*2){//middle lines
				System.out.print(" ├");
				for(int u=0; u<Jinxin.xSize-1;u++){
					System.out.print("─┼");
				}
				System.out.println("─┤");
			}
			
			if (n%2==1) { // numbered lines
				System.out.print((n/2+1)%10);
				for(int u=0; u<Jinxin.xSize;u++){
					System.out.print("|o");
				}
				System.out.println("|");
			}
			
			if (n == Jinxin.ySize*2) {
				System.out.print(" └");
				for(int u=0; u<Jinxin.xSize-1;u++){
					System.out.print("─┴");
				}
				System.out.println("─┘");
			}
		}
		generateAnswer();
	}//update: AND new answer as well.
	
	
	public static void generateAnswer(){//stored in static int[][] answer
		
		answer=new int[Jinxin.xSize][Jinxin.ySize];
		
		
		/*for(int n=0; n<Jinxin.xSize;n++){
			for(int u=0; u<Jinxin.ySize;u++){
				answer[n][u]=0;
			}
		} */// initialization
		int bombPlaced=0;
		
		while(bombPlaced<Jinxin.b){
			Random rand =new Random();
			int r= rand.nextInt(Jinxin.xSize*Jinxin.ySize);//BUG:Answers might repeat
			if(answer[r%(Jinxin.xSize)][r/(Jinxin.xSize)]!=9){
				answer[r%(Jinxin.xSize)][r/(Jinxin.xSize)]=9;
				bombPlaced++;
			
				for(int n=0;n<3;n++){
					for(int u=0;u<3;u++){
						if((n!=1 || u!=1)&&testInBound(new int[] {r%(Jinxin.xSize)-1+n,r/(Jinxin.xSize)-1+u})==true){
							if(answer[r%(Jinxin.xSize)-1+n][r/(Jinxin.xSize)-1+u]!=9) answer[r%(Jinxin.xSize)-1+n][r/(Jinxin.xSize)-1+u]++;
						}
					}
				}
			}
				
			}
		
	}
	
	
	
	public static boolean act(int[] cmd){//returns survival:true=>game continues
		if (answer[cmd[0]][cmd[1]]==9) {//if bomb clicked-set all and only bombs status to true.
			for(int n=0; n<Jinxin.xSize;n++){
				for(int u=0; u<Jinxin.ySize;u++){
					status[n][u]=true;
				}
			}
			return false;
		}else{
			openSur(cmd);
			return true;
		}
		
		
	}
	
	public static boolean statusCheck(int[] cord){
		return status[cord[0]][cord[1]];
	}

	public static void updateScreen(){//use current answer&status
		
		System.out.print(" ");//first line
		for(int n=0; n<Jinxin.xSize;n++){
			System.out.print(" "+(n+1)%10);
		}
		System.out.println();
		
		for(int n=0; n<Jinxin.ySize*2+1;n++){
			if (n==0) {//second line
				System.out.print(" ┌");
				for(int u=0; u<Jinxin.xSize-1;u++){
					System.out.print("─┬");
				}
				System.out.println("─┐");
				
			}else if (n%2==0 && n !=Jinxin.ySize*2){//middle lines
				System.out.print(" ├");
				for(int u=0; u<Jinxin.xSize-1;u++){
					System.out.print("─┼");
				}
				System.out.println("─┤");
			}
			
			if (n%2==1) { // numbered lines
				System.out.print((n/2+1)%10);
				for(int u=0; u<Jinxin.xSize;u++){
					System.out.print("|");
					if(status[u][n/2]==true){
						if(answer[u][(n/2)]!=9)System.out.print(answer[u][(n/2)]>0? answer[u][(n/2)]:" ");
						else System.out.print("x");
					}else System.out.print("o");
				}
				System.out.println("|");
			}
			
			if (n == Jinxin.ySize*2) {
				System.out.print(" └");
				for(int u=0; u<Jinxin.xSize-1;u++){
					System.out.print("─┴");
				}
				System.out.println("─┘");
			}
		}
	}
	
	public static void openSur(int[] cor){//only for non bomb action--bomb action is processed in act()
		if (answer [cor[0]][cor[1]]>0) status [cor[0]][cor[1]] = true;//numbered:stop spreading
		else{
			status [cor[0]][cor[1]] = true;
			for (int n=0; n<3; n++){
				for (int u=0; u<3; u++){
					if (testInBound(new int[] {cor[0]-1+n,cor[1]-1+u})==true ){
						if(status[cor[0]-1+n][cor[1]-1+u]==false) {
						openSur( new int[] {cor[0]-1+n,cor[1]-1+u});
						}
					}
				}
			}
		}
		return;
	}
	
	public static boolean testInBound (int[] cor){
		if((cor[0]>=0 && cor[0]< Jinxin.xSize) &&(cor[1]>=0 && cor[1]< Jinxin.ySize))
				return true;
		else return false; 
	}
	
	public static int numOfUnrevealed (){
		int numOfUnrevealed = 0; 
		for(int n=0; n<Jinxin.xSize;n++){
			for(int u=0; u<Jinxin.ySize;u++){
				if (status[n][u]==false) numOfUnrevealed++ ;
			}
		}
		return numOfUnrevealed; 
	}
	
}
