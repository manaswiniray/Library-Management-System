import java.util.*;
public class TicTacToeGame {
	 
	static ArrayList<Integer> playerpos=new ArrayList<>();
	static ArrayList<Integer> computerpos=new ArrayList<>(); 
	
	public static void printBoard(char[][] a) {
		for(char[] i: a) {
			for(char j:i) {
				System.out.print(j);
			}
			System.out.println();
		}
	}
	
	public static void placing(char[][] board, int p, String choice) {
		char symbol=' ';
		if(choice.equals("player")) {
			playerpos.add(p);
			symbol='X';
		}
		else if(choice.equals("computer")){
			computerpos.add(p);
			symbol='O';
		}
		
	switch(p) {
		case 1:
			board[0][0]=symbol;
			break;
		case 2:
			board[0][2]=symbol;
			break;
		case 3:
			board[0][4]=symbol;
			break;
		case 4:
			board[2][0]=symbol;
			break;
		case 5:
			board[2][2]=symbol;
			break;
		case 6:
			board[2][4]=symbol;
			break;
		case 7:
			board[4][0]=symbol;
			break;
		case 8:
			board[4][2]=symbol;
			break;
		case 9:
			board[4][4]=symbol;
			break;
		default:
			break;
	}
		
	//printBoard(board);
	}
	
	public static String checkWinner() {
		
		List row1=Arrays.asList(1,2,3);
		List row2=Arrays.asList(4,5,6);
		List row3=Arrays.asList(7,8,9);
		List column1=Arrays.asList(1,4,7);
		List column2=Arrays.asList(2,5,8);
		List column3=Arrays.asList(3,6,9);
		List diagonal1=Arrays.asList(1,5,9);
		List diagonal2=Arrays.asList(3,5,7);
		
		List<List> win=new ArrayList<>();
		win.add(row1);
		win.add(row2);
		win.add(row3);
		win.add(column1);
		win.add(column2);
		win.add(column3);
		win.add(diagonal1);
		win.add(diagonal2);
		
		for(List l: win) {
			if(playerpos.containsAll(l)) {
				return "Player Won";
			}
			else if(computerpos.containsAll(l)) {
				return "Computer Won";
			}
			else if(computerpos.size()+playerpos.size()==9){
				return "It's a tie";
			}
		}
		return "";
		
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		char[][] board= {{' ','|',' ','|',' '},{'-','-','-','-','-'},{' ','|',' ','|',' '},{'-','-','-','-','-'},{' ','|',' ','|',' '}};
		
		printBoard(board);
		
		while(true) {
			Scanner sc=new Scanner(System.in);
			
			System.out.println("Enter the place: ");
			int ppos=sc.nextInt();
			while(playerpos.contains(ppos)||computerpos.contains(ppos)) {
				System.out.println("Already taken! Give another position: ");
				ppos=sc.nextInt();
			}
			
			
		
			placing(board,ppos,"player");
			
			String res=checkWinner();
			if(res.length()>0) {
				System.out.println(res);
				break;
			}

						
			Random r=new Random();
			int cpos=r.nextInt(9)+1;
			while(playerpos.contains(cpos)||computerpos.contains(cpos)) {
				cpos=r.nextInt(9)+1;
			}
			
			placing(board,cpos,"computer");
			
			printBoard(board);
			res=checkWinner();
			if(res.length()>0) {
				System.out.println(res);
				break;
			}

			
		}
		
		
	}

}
