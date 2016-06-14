import java.util.Scanner;

public class Command {
	Scanner scan = new Scanner(System.in);
	public int[] command = new int[2];
	private int x;
	private int y;
	
	
	
	public int[] getCommand (){		
		boolean cmdExist = false; 
		while (cmdExist == false){
		System.out.println("input action: x,y");
		int commaPosG=0;
		
		
		String in = scan.next();
		int numComma = 0;
		for (int n=0; n< in.length();n++ ){
			if (in.charAt(n)==',') {
				numComma++;
				commaPosG = n;
			}
		}
		
		if(numComma==1) {
				 try {
					   		this.x = Integer.parseInt(in.substring(0,commaPosG))-1;
					   		this.y = Integer.parseInt(in.substring(commaPosG+1,in.length()))-1;	
					   		cmdExist = true; 
					  } catch (NumberFormatException hehehe ) {
						  System.out.println("error-comma but not number(action)\n");
					  }
				
				System.out.println("("+x+","+y+")");
		}
		else System.out.println("wrong format(prompting action)\n");
		}
		int[] cmd = {this.x,this.y};		
		return cmd;
	}
}

/*
 
  1 2 3 4 5 6 7 8 
 ┌─┬─┬─┬─┬─┬─┬─┬─┐
1|x│1|o│o|x│x|x│x|
 ├─┼─┼─┼─┼─┼─┼─┼─┤
2|1│1|o│o|x│x|x│x|
 ├─┼─┼─┼─┼─┼─┼─┼─┤
3|x│x|x│x|x│x|x│x|
 ├─┼─┼─┼─┼─┼─┼─┼─┤
4|x│x|x│x|x│x|o│o|
 └─┴─┴─┴─┴─┴─┴─┴─┘
	 1	 2	 3	
	┌───┬───┬───┬───┐
1	│ x	│ o	│ o	│ o	│
	├───┼───┼───┼───┤
*/