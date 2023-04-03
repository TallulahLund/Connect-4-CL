import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connect4
{
	public static void main(String[] args)
	{
		// 
		forloopstest();
		////////

		char[][] board = new char[6][7];
		for (int i = 0; i < 6; i++)// i = row
		{
			for (int j = 0; j < 7; j++)// j = column
				board[i][j] = '.';
		}
		
		for (char[] c_a : board)// displayboard()
		{
			System.out.print("| ");
			for (char c : c_a)
				System.out.print(c+" | ");
			System.out.println("\n-----------------------------");
		}
		
		//////
		System.out.println("Q1: " + ValidBoardSquare('Y'));
		board[4][0] = 'R';// 'r' = true, 'y' = true, 'q' = true -> need to add ValidBoardSquare to funct -> fixed
		board[5][0] = 'Y';
		System.out.println("Q2: " + ValidBoard(board));
		System.out.println("Q3: " + ValidMove(board, 0)); // must handle out of bounds
		System.out.println("Q4: " + ValidMoves(board));
		System.out.println("Q5: " + WhoseMove(board));
		System.out.println();									// handles new char[][], null
		//////
		
		//
		ValidBoard(board);
		
		board[0][4] = 'Y';
		for (char[] c_a : board)
		{
			System.out.print("| ");
			for (char c : c_a)
				System.out.print(c+" | ");
			System.out.println("\n-----------------------------");
		}
		
		System.out.println(ValidMove(board, 2));
		
		System.out.println(ValidMoves(board));
		
		System.out.println(WhoseMove(board));
		
		
		
		// reset board
		for (int i = 0; i < 6; i++)// i = row
		{
			for (int j = 0; j < 7; j++)// j = column
				board[i][j] = '.';
		}
		displayboard(board);
		board[5][4] = 'Y';
		board[4][4] = 'Y';
		board[3][4] = 'Y';
		board[5][0] = 'R';
		board[5][2] = 'R';
		board[5][1] = 'R';
		board[5][3] = 'Y';
		board[4][1] = 'R';
		board[4][2] = 'Y';
		board[4][3] = 'Y';
		board[3][2] = 'R';
		board[3][3] = 'Y';
		board[5][6] = 'R';
		board[3][6] = 'R';
		
		board[4][6] = 'R';
		board[4][6] = 'Y';
		board[5][0] = 'R';
		board[4][0] = 'R';
		board[3][0] = 'Y';
		board[3][1] = 'R';
		
		//board[0][0] = 'R';
		board[2][0] = 'Y';
		board[1][0] = 'Y';
		
		board[5][5] = 'Y';
		board[4][5] = 'R';
		
		board[2][6] = 'R';
		board[1][6] = 'R';
		board[2][4] = 'R';
		
		displayboard(board);
		// must do whose move before hand
		System.out.println("\nWhoWon: " + WhoWon(board, /*'Y'*/WhoseMove(board), 0));// auto selects colour depending on whose go
	
	
		////////////////////////////////////////////////////////////////
		play();
	}
	
	
	///////
	public static void displayboard(char[][] board)
	{
		System.out.println("\n-----------------------------");
		for (char[] c_a : board)
		{
			System.out.print("| ");
			for (char c : c_a)
				System.out.print(c+" | ");
			System.out.println("\n-----------------------------");
		}
	}
	///////
	

	public static void Question1_1()
	{
		
	}
	public static boolean ValidBoardSquare(char input)
	{
		switch (input)
		{
			case 'R': case 'Y': case '.':// valid
				return true;
			default: // everything else = invalid
				return false;
		}
	}
	
	public static void Question1_2()
	{
		
	}
	public static boolean ValidBoard(char[][] input)// split individual tests into methods
	{
		// board 6 row, 7 column -> check for this?
		// make sure all squares are valid
		// no all red, no all yellow, no empties below colours
		// equal or 1 more red
		
		/**/// equal but yellow under red? -> as in yellow started, game play = no possible, random board could be possible if so invalid but comes up valid
		
		// null
//		if (input == null)
//			return false;
		if (isboardnull(input) || input.length != 6 || input[0].length != 7)// caters null, 6 rows, 7 columns
			return false;
		
		// check all square characters are valid
		for (char[] c_a : input)// loops through 2-D array board
		{
			for (char c : c_a)
			{
				if (ValidBoardSquare(c) == false)
					return false;
			}
		}
		
		
		// empty board
		//input[5][2] = 'R';
		boolean dots = true;// board full of dots
		for (char[] c_a : input)
		{
			for (char c : c_a)
			{
				if (c != '.')
					dots = false;
			}
		}
		//System.out.println(dots);
		
		// equalish red and yellow
		boolean equalish = false;
		int countR = 0, countY = 0;//// -> repeated - count function? -> questions are split on coderunner pointless to have count function
		for (char[] c_a : input) // counts number of reds and yellows
		{
			for (char c : c_a)
			{
				if (c == 'R')
					countR += 1;
				if (c == 'Y')
					countY += 1;
			}
		}////
		if (countR == countY || countR == countY + 1)// equal red and yellow, or 1 more red -> as yellow's go
			equalish = true; //System.out.println("Valid");
		
		// no empties under colour
		for (int i = 0; i < 6; i++)// i = row
		{
			for (int j = 0; j < 7; j++)// j = column
			{
				//System.out.print("_"+input[i][j]);
			}
			//System.out.println("+");
		}
		//System.out.println();
		// regex? .{6} | .{5}(R | Y) | .{4}(R | Y){2} | .{3}(R | Y){3} | .{2}(R | Y){4} | .(R | Y){5} | (R | Y){6}
		boolean valid = true, colour = false;
		for (int j = 0; j < 7; j++)// i = row
		{
			for (int i = 0; i < 6; i++)// j = column
			{
				//input[i][j] = '.';
				//System.out.print("_"+input[i][j]);
				if (!colour)// if colour false
				{
					//System.out.print("in");
					if (input[i][j] != '.')// if square not empty
						colour = true;// set colour to true
				}
				else
				{
					//System.out.print("else");
					if (input[i][j] == '.')
						valid = false;
				}
//				colour = false;
			}
			colour = false;// must be after inner for
			//System.out.println("="+ valid);
		}
		
		//System.out.println(dots+" "+equalish+" "+valid);
		if (dots == true || (equalish == true && valid == true))
		{
			//System.out.println("true");
			return true;
		}
		else
			return false;
	}
	
	public static boolean isboardnull(char[][] input)// pointless -> save space?
	{
		if (input == null)
			return true;
		return false;
	}
	
	public static void Question1_3()
	{
		
	}
	public static boolean ValidMove(char[][] board, int input)//0-6 -> board global?
	{
		// null board
		if (isboardnull(board))
			return false;
		
		// columns 0 - 6
		if (input < 0 || input > 6) // -> try catch to condense?
			return false; // out of bounds
		
		
		// can't put piece in full column
//		for (int j = 0; j < 7; j++)// i = row
//		{
//			for (int i = 0; i < 6; i++)// j = column
//			{
//				//board[i][j] = '.';
//				
//			}
//		}
//		for (int i = 0; i < 6; i++)// i = row
//		{
//			if (board[i][input] == '.')
//				return true;
//		
		if (board[0][input] == '.')// 0 = 1st row - top of board
			return true;
		else
			return false;
	}
	
	public static void Question1_4()
	{
		
	}
	public static List<Integer> ValidMoves(char[][] board)
	{// is meant to call q3 method?
		List<Integer> possible = new ArrayList<Integer>();
		
		if (isboardnull(board))
			return possible;
		
		// return all valid column numbers
		//List<Integer> possible = new ArrayList<Integer>();
		for (int j = 0; j < 7; j++)// j = columns
		{
			if (board[0][j] == '.')// 1st row - top of board // if(ValidMove(board, j) == true)
				possible.add(j);
		}
		
		return possible;//new ArrayList<Integer>();
	}
	
	public static void Question1_5()
	{
		
	}
	public static char WhoseMove(char[][] input)
	{
		// R if red, Y if yellow, '.' if invalid board
		
		if (ValidBoard(input))
		{
			int countR = 0, countY = 0;
			for (char[] c_a : input)// counts number of reds and yellows
			{
				for (char c : c_a)
				{
					if (c == 'R')
						countR += 1;
					if (c == 'Y')
						countY += 1;
				}
			}
			
			if ((countR + countY) == 42)// all squares -> board full
			    return '.';
			
			if (countR == countY)// if equal -> reds go
				return 'R';
			else if (countR == countY + 1)// if 1 more red -> yellows go
				return 'Y';
		}
		// invalid board -> call methods?
		return '.';
	}
	
	
	
	///////////////BONUS////////////////////////
	
	public static void Bonus_Question()
	{
		
	}
	public static char WhoWon(char[][] board, char player, int column)// called after every player's move + ValidMove	// split into diff methods?
	{//what do if only allowed board input? -> figure something out
		// returns 'R' if red won, 'Y' if yellow won
		// returns 'D' if draw -> no more moves can be made -> full board -> simple !.contains('.') -> last moves not won
		
//		// for draw
//		if (ValidMoves(board).size() == 0)
//			; // draw if not won -> end? -> will still have to check if won or not
		
		
		
		// stack / grid search  -> upon latest move
		int numinrow = 1;
		
		if (ValidBoard(board))
		{
			for (int i = 0; i <= 4; i++)// need better conditions for rows
			{
				
				if (i == 0 && board[i][column] != '.')					// check valid moves before hand
				{
					return 'X';// invalid move		// use ValidMove() instead?
				}
				else if (board[i+1][column] != '.')// || (board[i][column] == '.' && i == 5))// i-1 is where goes -> shouldn't be 0 - 1 if ValidMove
				{
					board[i][column] = player;// setting square
					// now check if won
					
//					if (board[i-2][column+1] == player)
//					{
//						numinrow += 1;
//						
//					}
					displayboard(board);
					boolean outcome = false;		// initial square search funct?
//					if (i-1 >= 0 && column+1 <= 6)// make initial checks for 1st square
//						outcome = searchupright(board, i-1, column+1, player, numinrow, 1);//add conditions for top row
//					else if (i+1 <= 5 && column-1 >= 0)
//						outcome = searchdownleft(board, i+1, column-1, player, numinrow, 1);
//					else if (i-1 >= 0 && column-1 >= 0)// 2 is diff between downleft square and upleft square (in row)
//						outcome = searchupleft(board, i-1, column-1, player, 1, 1);
//					else if (i+1 <= 5 && column+1 <= 6)
//						outcome = searchdownright(board, i+1, column+1, player, 1, 1);
//					else if (column+1 <= 6)
//						outcome = searchright(board, i, column+1, player, 1, 1);
//					else if (column-1 >= 0)
//						outcome = searchleft(board, i, column-1, player, 1, 1);
//					else if (i+1 <= 5)
//						outcome = searchdown(board, i+1, column, player, 1, 1);
					
					outcome = search(board, i, column, player, numinrow, 1);
					System.out.println(outcome);
					if (outcome)//searchupright(board, i-2, column, player, numinrow))
					{
						displayboard(board);
						return player;
					}
					// else not won
					else
					{
						// for draw
						if (ValidMoves(board).size() == 0)
							return 'D';
//						else
//							return '.';// for no win + draw -> keep playing
					}
					
					//
					break;
				}
				else if (i == 4)// for bottom row -> would still need to search -> put out of for?
				{// will go here if bottom of column is empty
					System.out.println("WW for else if");
					board[i+1][column] = player;
					displayboard(board);
					boolean outcome = false;
//					if (column+1 <= 6)// if right exists
//						outcome = searchupright(board, i+1, column+1, player, numinrow, 1);//add conditions for column
					outcome = search(board, i+1, column, player, numinrow, 1);
					System.out.println(outcome);
					if (outcome)//searchupright(board, i-2, column, player, numinrow))
					{
						displayboard(board);
						return player;
					}
					else
					{
						// for draw
						if (ValidMoves(board).size() == 0)
							return 'D';
//						else
//							return '.';// for no win + draw -> keep playing
					}
				}
			}
		}
		else
			return 'I';// invalid board
		
		return '.';// continue playing				// make . = invalid, C = continue?
	}
	// must get head around conditions and exceptions -> lay it out simply -> whiteboard?
	
	// diff = num of squares including original for search, numinrow = num of same colour in a row
	// initial
	public static boolean search(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
		if (row-1 >= 0 && column+1 <= 6)// make initial checks for 1st square
			return searchupright(board, row-1, column+1, player, numinrow, 1);//add conditions for top row
		else if (row+1 <= 5 && column-1 >= 0)
			return searchdownleft(board, row+1, column-1, player, numinrow, 1);
		else if (row-1 >= 0 && column-1 >= 0)// 2 is diff between downleft square and upleft square (in row)
			return searchupleft(board, row-1, column-1, player, 1, 1);
		else if (row+1 <= 5 && column+1 <= 6)
			return searchdownright(board, row+1, column+1, player, 1, 1);
		else if (column+1 <= 6)
			return searchright(board, row, column+1, player, 1, 1);
		else if (column-1 >= 0)
			return searchleft(board, row, column-1, player, 1, 1);
		else if (row+1 <= 5)
			return searchdown(board, row+1, column, player, 1, 1);
		
		return false;
	}
	
	public static boolean searchupright(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
//		if (board[row][column] == player)
//		{
//			numinrow += 1;
//			if (numinrow == 4)
//				return true;
//			else
//			{			// only conditions for up right?
//				if (row-1 >= 0 && column+1 <= 6)// if (board[row-1][column-1]) exists
//					return searchupright(board, row-1, column+1, player, numinrow);
//				else// check if down exists?
//					return searchdownleft(board, row + numinrow, column - numinrow, player, numinrow);
//			}
//		}
//		else// check if down exists?
//		{
//			System.out.println("\nin sur else");
//			System.out.println("r: " + row + " c: " + column + " n: " + numinrow);
//			if (row+1 < 5 && column-1 > 0)
//				return searchdownleft(board, row+1 + numinrow, column-1 - numinrow, player, numinrow);//false;
//			else if (column-1 > 0)
//				return searchupleft(board, row, column-1 - numinrow, player, 1);
//			else
//				return searchdownright(board, 1, 1, player, 1);
//		}
		
		
		//System.out.println("\nsur -> r: " + row + " c: " + column + " n: " + numinrow);
		//int diff = numinrow + 1;
		diff += 1;
		if (board[row][column] == player)
		{
			numinrow+=1;	
			if (numinrow == 4)
				return true;
			else
			{
				if (row-1 >= 0 && column+1 <= 6)
					return searchupright(board, row-1, column+1, player, numinrow, diff);
			}
		}// make sure using correct numinrow offset
		if (row+diff <= 5 && column-diff >= 0)
			return searchdownleft(board, row+diff, column-diff, player, numinrow, 1);
		else if (row+diff - 2 >= 0 && column-diff >= 0)// 2 is diff between downleft square and upleft square (in row)
			return searchupleft(board, row+diff - 2, column-diff, player, 1, 1);
		else if (row+diff <= 5 && column-diff + 2 <= 6)
			return searchdownright(board, row+diff, column-diff + 2, player, 1, 1);
		else if (row+diff - 1 >= 0 && column-diff + 2 <= 6)
			return searchright(board, row+diff - 1, column-diff + 2, player, 1, 1);
		else if (row+diff - 1 >= 0 && column-diff >= 0)
			return searchleft(board, row+diff - 1, column-diff, player, 1, 1);
		else if (row+diff <= 5)
			return searchdown(board, row+diff, column-diff + 1, player, 1, 1);
		
		
		// final else
		return false;
	}
	
	public static boolean searchdownleft(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
//		if (board[row][column] == player)
//		{
//			System.out.println("\nin sdl if");
//			System.out.println("r: " + row + " c: " + column);
//			numinrow += 1;
//			if (numinrow == 4)
//				return true;
//			else
//			{
//				if (row+1 <= 5 && column-1 >= 0)
//					return searchdownleft(board, row+1, column-1, player, numinrow);
//				else
//				{
//					//numinrow = 1;// reset
//					return searchupleft(board, row - numinrow, column-2 + numinrow, player, 1/*numinrow*/);
//				}
//			}
//		}
//		else
//		{
//			System.out.println("\nin sdl else");
//			//numinrow = 1;// reset
//			return searchupleft(board, row - numinrow, column-2 + numinrow, player, 1/*numinrow*/);
//		}//false;
		
		
		//System.out.println("\nsdl -> r: " + row + " c: " + column + " n: " + numinrow);
		//int diff = numinrow + 1;
		diff += 1;
		if (board[row][column] == player)
		{
			numinrow+=1;	
			if (numinrow == 4)
				return true;
			else
			{
//				if (row-1 >= 0 && column+1 <= 6)
//					return searchupright(board, row-1, column+1, player, numinrow);
				
				if (row+1 <= 5 && column-1 >= 0)
					return searchdownleft(board, row+1, column-1, player, numinrow, diff);
			}
		}
//		if (row+numinrow <= 5 && column-numinrow >= 0)
//			return searchdownleft(board, row+numinrow, column-numinrow, player, numinrow);
		//else 
		if (row-diff >= 0 && column+diff - 2 >= 0)// 2 is diff between downleft square and upleft square (in row)
			return searchupleft(board, row-diff, column+diff - 2, player, 1, 1);
		else if (row-diff + 2 <= 5 && column+diff <= 6)
			return searchdownright(board, row-diff + 2, column+diff, player, 1, 1);
		else if (row-diff + 1 >= 0 && column+diff <= 6)
			return searchright(board, row-diff + 1, column+diff, player, 1, 1);
		else if (row-diff + 1 >= 0 && column+diff - 2 >= 0)
			return searchleft(board, row-diff + 1, column+diff - 2, player, 1, 1);
		else if (row-diff + 2 <= 5)
			return searchdown(board, row-diff + 2, column+diff - 1, player, 1, 1);
		
		// final else
		return false;
	}
	
	public static boolean searchupleft(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
		//numinrow = 1;// reset -> would reset every time
//		if (board[row][column] == player)
//		{
//			numinrow += 1;
//			if (numinrow == 4)
//				return true;
//			else
//			{
//				if (row-1 >= 0 && column-1 >= 0)
//					return searchupleft(board, row-1, column-1, player, numinrow);
//				else
//					return searchdownright(board, row + numinrow, column + numinrow, player, numinrow);
//			}
//		}
//		else
//			return searchdownright(board, row + numinrow, column + numinrow, player, numinrow);//false;
	
		
		//System.out.println("\nsul -> r: " + row + " c: " + column + " n: " + numinrow);
		//int diff = numinrow + 1;
		diff += 1;
		if (board[row][column] == player)
		{
			//System.out.println("\nsul if -> r: " + row + " c: " + column + " n: " + numinrow);
			numinrow+=1;	
			if (numinrow == 4)
				return true;
			else
			{
//				if (row-1 >= 0 && column+1 <= 6)
//					return searchupright(board, row-1, column+1, player, numinrow);
				if (row-1 >= 0 && column-1 >= 0)
					return searchupleft(board, row-1, column-1, player, numinrow, diff);
			}
		}
//		if (row+numinrow <= 5 && column-numinrow >= 0)
//			return searchdownleft(board, row+numinrow, column-numinrow, player, numinrow);
//		else if (row+numinrow - 2 >= 0 && column-numinrow >= 0)// 2 is diff between downleft square and upleft square (in row)
//			return searchupleft(board, row+numinrow - 2, column-numinrow, player, 1);
//		else 
		if (row+diff <= 5 && column+diff <= 6)
			return searchdownright(board, row+diff, column+diff, player, numinrow, 1);
		else if (row+diff - 1 >= 0 && column+diff <= 6)
			return searchright(board, row+diff - 1, column+diff, player, 1, 1);
		else if (row+diff - 1 >= 0 && column+diff - 2 >= 0)
			return searchleft(board, row+diff - 1, column+diff - 2, player, 1, 1);
		else if (row+diff <= 5)
			return searchdown(board, row+diff, column+diff - 1, player, 1, 1);
		
		
		// final else
		return false;
	}
	
	public static boolean searchdownright(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
//		System.out.println("r: " + row + " c: " + column);
//		if (board[row][column] == player)
//		{
//			numinrow += 1;
//			if (numinrow == 4)
//				return true;
//			else
//			{
//				if (row+1 <= 5 && column+1 <= 6)
//					return searchdownright(board, row+1, column+1, player, numinrow);
//				else
//				{
//					//numinrow = 1;// reset
//					return searchright(board, row+1 - numinrow, column+2 - numinrow, player, 1/*numinrow*/);
//				}
//			}
//		}
//		else
//		{
//			//numinrow = 1;// reset
//			return searchright(board, row+1 - numinrow, column+2 - numinrow, player, 1/*numinrow*/);
//		}//false;
		
		
		//System.out.println("\nsdr -> r: " + row + " c: " + column + " n: " + numinrow + " d: " + diff);
		//int diff = numinrow + 1;								// diff is wrong as numinrow for oppo not reset -> need work around -> send through 2 values?
		diff += 1;
		if (board[row][column] == player)
		{
			numinrow+=1;	
			if (numinrow == 4)
				return true;
			else
			{
//				if (row-1 >= 0 && column+1 <= 6)
//					return searchupright(board, row-1, column+1, player, numinrow);
				if (row+1 <= 5 && column+1 <= 6)
					return searchdownright(board, row+1, column+1, player, numinrow, diff);
			}
		}
//		if (row+numinrow <= 5 && column-numinrow >= 0)
//			return searchdownleft(board, row+numinrow, column-numinrow, player, numinrow);
//		else if (row+numinrow - 2 >= 0 && column-numinrow >= 0)// 2 is diff between downleft square and upleft square (in row)
//			return searchupleft(board, row+numinrow - 2, column-numinrow, player, 1);
//		else if (row+numinrow <= 5 && column-numinrow + 2 <= 6)
//			return searchdownright(board, row+numinrow, column-numinrow + 2, player, 1);
//		else 
		if (row-diff + 1 >= 0 && column-diff + 2 <= 6)
			return searchright(board, row-diff + 1, column-diff + 2, player, 1, 1);
		else if (row-diff + 1 >= 0 && column-diff >= 0)
			return searchleft(board, row-diff + 1, column-diff, player, 1, 1);
		else if (row-diff + 2 <= 5)
			return searchdown(board, row-diff + 2, column-diff + 1, player, 1, 1);
		
		
		// final else
		return false;
	}
	
	public static boolean searchright(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
		//numinrow = 1;// reset -> would reset every time
//		System.out.println("r: " + row + " c: " + column);
//		if (board[row][column] == player)
//		{
//			numinrow += 1;
//			if (numinrow == 4)
//				return true;
//			else
//			{
//				if (column+1 <= 6)
//					return searchright(board, row, column+1, player, numinrow);
//				else
//					return searchleft(board, row, column - numinrow, player, numinrow);
//			}
//		}
//		else
//			return searchleft(board, row, column - numinrow, player, numinrow);//false;
		
		
		//System.out.println("\nsr -> r: " + row + " c: " + column + " n: " + numinrow + " d: " + diff);
		//int diff = numinrow + 1;
		diff += 1;
		if (board[row][column] == player)
		{
			numinrow+=1;	
			if (numinrow == 4)
				return true;
			else
			{
//				if (row-1 >= 0 && column+1 <= 6)
//					return searchupright(board, row-1, column+1, player, numinrow);
				if (column+1 <= 6)
					return searchright(board, row, column+1, player, numinrow, diff);
			}
		}
//		if (row+numinrow <= 5 && column-numinrow >= 0)
//			return searchdownleft(board, row+numinrow, column-numinrow, player, numinrow);
//		else if (row+numinrow - 2 >= 0 && column-numinrow >= 0)// 2 is diff between downleft square and upleft square (in row)
//			return searchupleft(board, row+numinrow - 2, column-numinrow, player, 1);
//		else if (row+numinrow <= 5 && column-numinrow + 2 <= 6)
//			return searchdownright(board, row+numinrow, column-numinrow + 2, player, 1);
//		else if (row+numinrow - 1 >= 0 && column-numinrow + 2 <= 6)
//			return searchright(board, row+numinrow - 1, column-numinrow + 2, player, 1);
//		else 
		if (/*row+numinrow - 1 >= 0 &&*/ column-diff >= 0)
			return searchleft(board, row, column-diff, player, numinrow, 1);
		else if (row+1 <= 5)
			return searchdown(board, row+1, column-diff + 1, player, 1, 1);
		
		
		// final else
		return false;
	}
	
	public static boolean searchleft(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
//		System.out.println("\nr: " + row + " c: " + column);
//		if (board[row][column] == player)
//		{
//			numinrow += 1;
//			if (numinrow == 4)
//				return true;
//			else
//			{
//				if (column-1 >= 0)
//					return searchleft(board, row, column-1, player, numinrow);
//				else
//				{
//					//numinrow = 1;// reset
//					return searchdown(board, row+1, column-1 + numinrow, player, 1/*numinrow*/);
//				}
//			}
//		}
//		else
//		{
//			//numinrow = 1;// reset
//			return searchdown(board, row+1, column-1 + numinrow, player, 1/*numinrow*/);
//		}//false;
		
		
		//System.out.println("\nsl -> r: " + row + " c: " + column + " n: " + numinrow);
		//int diff = numinrow + 1;
		diff += 1;
		if (board[row][column] == player)
		{
			//System.out.println("\nsl if");
			numinrow+=1;	
			if (numinrow == 4)
				return true;
			else
			{
//				if (row-1 >= 0 && column+1 <= 6)
//					return searchupright(board, row-1, column+1, player, numinrow);
				if (column-1 >= 0)
					return searchleft(board, row, column-1, player, numinrow, diff);
			}
		}
//		if (row+numinrow <= 5 && column-numinrow >= 0)
//			return searchdownleft(board, row+numinrow, column-numinrow, player, numinrow);
//		else if (row+numinrow - 2 >= 0 && column-numinrow >= 0)// 2 is diff between downleft square and upleft square (in row)
//			return searchupleft(board, row+numinrow - 2, column-numinrow, player, 1);
//		else if (row+numinrow <= 5 && column-numinrow + 2 <= 6)
//			return searchdownright(board, row+numinrow, column-numinrow + 2, player, 1);
//		else if (row+numinrow - 1 >= 0 && column-numinrow + 2 <= 6)
//			return searchright(board, row+numinrow - 1, column-numinrow + 2, player, 1);
//		else if (row+numinrow - 1 >= 0 && column-numinrow >= 0)
//			return searchleft(board, row+numinrow - 1, column-numinrow, player, 1);
//		else 
		if (row+1 <= 5)
			return searchdown(board, row+1, column+diff - 1, player, 1, 1);
		
		
		// final else
		return false;
	}
	
	public static boolean searchdown(char[][] board, int row, int column, char player, int numinrow, int diff)
	{
		//System.out.println("\nd: " + row + " c: " + column);
		//System.out.println("\nsd -> r: " + row + " c: " + column + " n: " + numinrow);
		//numinrow = 1;// reset -> would reset every time
//		int diff = numinrow + 1;// unnecessary 
		diff += 1;
		if (board[row][column] == player)
		{
			numinrow += 1;
			if (numinrow == 4)
				return true;
			else
			{
				if (row+1 <= 5)
					return searchdown(board, row+1, column, player, numinrow, diff);
				else
					return false;// not won
			}
		}
		else
			return false;// not won
	}

	//////////////////////////////////////////////////////////////////
	
	public static Character WhoHasWon(char board[][])
	{
		board[5][0] = 'R';
		board[5][1] = 'Y';
		board[4][0] = 'R';
		board[5][2] = 'Y';
		board[3][0] = 'R';
		board[5][3] = 'Y';
//		board[2][0] = 'R';
//		board[5][4] = 'Y';
		board[5][4] = 'R';
		board[5][6] = 'Y';
		board[4][4] = 'R';
		board[5][5] = 'Y';
		board[3][4] = 'R';
		board[4][5] = 'Y';
		board[2][4] = 'R';
		displayboard(board);
		// stack / grid search  -> upon latest move
		int numinrow = 1;
		///////////////
		
		Character player = ' ';
		boolean hasPlayer = false;
		int row = 9;// ....................................
		
		if (ValidBoard(board))
		{
			for (int j = 0; j < 7; j++)// j = column
			{
				for (int i = 0; i < 6; i++)// i = row
				{
					row = i;
					if (!hasPlayer)
					{
						if (board[i][j] != '.')
						{
							player = board[i][j];
							System.out.println("player- " + player);
							hasPlayer = true;
							break;
						}
					}
//					else// has player -> should only do 1 per row
//					{
//						// search if won
//						boolean outcome = false;		// initial square search funct?
//						outcome = search(board, i, j, player, numinrow, 1);
//					}
				}
				// empty column?
				if (hasPlayer)// player not null
				{
					// search if won
					boolean outcome = false;		// initial square search funct?
					outcome = search(board, row, j, player, numinrow, 1);
					System.out.println("Who has won: " + player + " , " + outcome);
					if (outcome)
					{
						displayboard(board);
						return player;
					}
//					else	// would skip other columns
//						return '.';
					else
						hasPlayer = false;
				}
				continue;// doesn't have player -> empty column
			}
		}
		else
			return null;
		
		
		///////////////		
//		if (ValidBoard(board))
//		{
//			for (int i = 0; i <= 4; i++)// need better conditions for rows
//			{	
//				if (i == 0 && board[i][column] != '.')					// check valid moves before hand
//				{
//					return 'X';// invalid move		// use ValidMove() instead?
//				}
//				else if (board[i+1][column] != '.')// || (board[i][column] == '.' && i == 5))// i-1 is where goes -> shouldn't be 0 - 1 if ValidMove
//				{
//					board[i][column] = player;// setting square
//					// now check if won
//							
//					displayboard(board);
//					boolean outcome = false;		// initial square search funct?
//					outcome = search(board, i, column, player, numinrow, 1);
//					System.out.println(outcome);
//					if (outcome)//searchupright(board, i-2, column, player, numinrow))
//					{
//						displayboard(board);
//						return 'P';//player;
//					}
//					// else not won
//					else
//					{
//						// for draw
//						if (ValidMoves(board).size() == 0)
//							return 'D';
////						else
////							return '.';// for no win + draw -> keep playing
//					}
//					//
//					break;
//				}
//				else if (i == 4)// for bottom row -> would still need to search -> put out of for?
//				{// will go here if bottom of column is empty
//					System.out.println("WW for else if");
//					board[i+1][column] = player;
//					displayboard(board);
//					boolean outcome = false;
//					outcome = search(board, i+1, column, player, numinrow, 1);
//					System.out.println(outcome);
//					if (outcome)//searchupright(board, i-2, column, player, numinrow))
//					{
//						displayboard(board);
//						return player;
//					}
//					else
//					{
//						// for draw
//						if (ValidMoves(board).size() == 0)
//							return 'D';
////						else
////							return '.';// for no win + draw -> keep playing
//					}
//				}
//			}
//		}
//		else
//			return 'I';// invalid board
		
		return '.';// 'T';
	}
	
	
	
	
	

	//////////////////////////////////////////////////////////////////
	public static void play()
	{
		System.out.println("Play");
		char[][] board = new char[6][7];
		for (int i = 0; i < 6; i++)// i = row
		{
			for (int j = 0; j < 7; j++)// j = column
				board[i][j] = '.';
		}
		displayboard(board);
		
		boolean endofgame = false;
		
		Scanner scan;// = new Scanner(System.in);
		int column;
		char result, move;
		do
		{
			scan = new Scanner(System.in);
			move = WhoseMove(board);
			if (move == '.')
			{
				endofgame = true;
				break;
			}
			System.out.println("moves available: "+ ValidMoves(board));
			System.out.print(move + "'s Move: ");
			try
			{column = scan.nextInt();}
			catch(Exception e)
			{System.out.println("invalid: enter number"); column = -1;}
			if (column < 0 || column > 6)
				continue;
			
//			result = WhoWon(board, WhoseMove(board), column);
			result = WhoHasWon(board);
			System.out.println("Who Won: " + result);//WhoWon(board, WhoseMove(board), column));
			
			if (result != '.')
				endofgame = true;
			else if (result == 'X')// X = invalid move -> do again
				endofgame = false;
			
			//scan.close(); -> causes infinite loop when ran
		}
		while (!endofgame);
		
		System.out.println("GAME OVER");
		System.out.println("WhoseMove: " + WhoseMove(board));
	}
	
	
	public static void forloopstest()
	{
		for (int j = 0; j < 4; j++)// j = column
		{
			for (int i = 0; i < 3; i++)// i = row
			{
				System.out.print("test ");
				if (i == 1 && j == 1)
					break;
			}
			System.out.println();
		}
	}


}
