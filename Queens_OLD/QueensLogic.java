/**
 * This class implements the logic behind the BDD for the n-queens problem
 * You should implement all the missing methods
 * 
 * @author Stavros Amanatidis
 *
 */
import java.util.*;

import net.sf.javabdd.*;

public class QueensLogic {
    private int size = 0;
    private int[][] board;
    BDDFactory fact = JFactory.init(2000000,100000);
    BDD T = fact.one();
	BDD F = fact.zero();
	BDD queensBDD[][] = null;
	BDD mainBDD = null;


    public QueensLogic() {
       //constructor
    }

    public void initializeGame(int size) {
        this.size = size;
        this.board = new int[size][size];
		this.fact.setVarNum(size*size);
		this.queensBDD = new BDD[size][size];
		
		
		mainBDD = T;
		System.out.println(mainBDD.isOne());
		
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				queensBDD[x][y] = fact.ithVar(x+size*y);
		
//		Iterates through all board variables
		for(int y = 0; y < size ; y++){
			for(int x = 0; x < size ; x++){
//		Row		
				for(int compX = x+1; compX<size; compX++){
					mainBDD.andWith(queensBDD[x][y].id().imp(queensBDD[compX][y].not().id()));
				}
				
//		Columns
				for(int compY = y+1; compY<size; compY++){
					mainBDD.andWith(queensBDD[x][y].id().imp(queensBDD[x][compY].not().id()));
				}
		
			
//		Left diagonals
//				for(int compLd = 1; compLd+x<size-y; compLd++){
//					mainBDD.andWith(queensBDD[x][y].xor(queensBDD[compLd+x][compLd+y]));					
//				}
//		
////		Right diagonals
//				for(int compRd = 1; compRd > x-y; compRd++){
//					mainBDD.andWith(queensBDD[x][y].xor(queensBDD[compRd-x][compRd+y]));
//				}
			}
		}
    }

   
    public int[][] getGameBoard() {
        return board;
    }

    public boolean insertQueen(int column, int row) {

    	
    	// You cannot put a queen if the space is occupied
        if (board[column][row] == -1 || board[column][row] == 1) {
            return false;
        }
       
        
    //    mainBDD.restrictWith(fact.ithVar(Utils.CalcXY(row, column, 5)));
    	mainBDD.restrictWith(queensBDD[column][row].id());
    	
        //System.out.println(fact.ithVar(Utils.CalcXY(row, column, 5)));
    	
    	System.out.println(queensBDD[column][row].id());
        
        for (int x = 0; x < this.size; x++) {
        	for (int y = 0; y < this.size; y++) {
        		if (mainBDD.restrict(this.queensBDD[x][y].id()).isZero())
        			board[x][y] = -1;
        	}
        }
//        
        board[column][row] = 1;
        

        
        //System.out.println(mainBDD.isOne());
        
        //Iterator BDDitt = mainBDD.iterator(F);
        
        
        
//        while (BDDitt.hasNext()) {
//        	System.out.println("hej");
//        	BDD temp = (BDD)BDDitt.next();
//        }
//        
        
     
        return true;
    }
}

