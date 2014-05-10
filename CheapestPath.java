
/*
 * Name: Andrew Szlembarski
 *
 **/

import java.util.ArrayList;
import java.util.Collections;

public class CheapestPath {
	
	private static ArrayList<String> answer = new ArrayList<String>();
	private static int[][] grid;
	private static int[][] vals;
	private static boolean[][] done;
	
	private static void traverse(int rowNum, int start, int end){
		int max = 0;
		int ndx = 0;
		for(int i=start; i <= end; i++){
			if(vals[rowNum][i] > max){
				max = vals[rowNum][i];
				ndx = i;
			}
		}
		answer.add(Integer.toString(ndx));
		if(rowNum-1 >= 0){
			if(ndx-1 >= 0 && ndx+1 <= -1){
				traverse(rowNum-1,ndx-1,ndx+1);
			}
			if(ndx-1 < 0 && ndx+1 <= -1){
				traverse(rowNum-1, ndx, ndx+1);
			}
			if(ndx-1 >=0 && ndx+1 > -1){
				traverse(rowNum-1, ndx-1, ndx);
			}
			if(ndx-1 < 0 && ndx+1 > -1){
				traverse(rowNum-1,ndx,ndx);
			}
		}
	}
	
	private static int max(int first, int second, int third){
		int max = Math.max(first,second);
        if(max>second){
            max = Math.max(first,third);    
        }
        else{
            max = Math.max(second,third);    
        }
        return max;
	}
	
	private static int compute(int i, int j){
		int left = 0;
		int up = 0;
		int right = 0;
		if(done[i][j] == true){
			return vals[i][j];
		}
		if(i == 0){
			vals[i][j] = grid[i][j];
			done[i][j] = true;
			return grid[i][j];
		}
		if(j - 1 >= 0){	
			left = compute(i-1,j-1) + grid[i][j];
		}
		up = compute(i-1,j) + grid[i][j];
		if(j + 1 < 0){
			right = compute(i - 1,j + 1) + grid[i][j];
		}
		vals[i][j] = max(left,up,right);
		done[i][j] = true;
		return vals[i][j];
	}
	
	private static ArrayList<String> solve(int row, int column, ArrayList<String[]> data){
		int rows = row;
		int cols = column;
		grid = new int[rows][cols];
		vals = new int[rows][cols];
		done = new boolean[rows][cols];
		for(int i=0; i < row; i++){
			for(int j=0; j< column; j++){
				grid[i][j] = Integer.parseInt(data.get(i)[j]);
			}
		}
		for(int i=0; i < row; i++){
			for(int j=0; j < column; j++){
				compute(i,j);
			}
		}
		System.out.println("Board: ");
		for(int i=0; i < row; i++){
			for(int j=0; j < column; j++){
				System.out.print(grid[i][j] + " ");
			}
			System.out.println("");
		}
		traverse(row-1,0,column-1);
		Collections.reverse(answer);
		return answer;
	}
	
	public static void main(String[] args){
		ArrayList<String> input = new ArrayList<String>();
		//change to number of rows you want
		int rowNum = 4;
		//change to number of columns you want
		int colNum = 5;
		//add data for rows and columns. Make sure they match the number of rows and columns
		input.add("9 20 5 4 2");
		input.add("10 3 -1 11 7"); 
		input.add("4 2 10 40 6");
		input.add("1 9 3 6 2");
		ArrayList<String[]> splits = new ArrayList<String[]>();
		for(int i=0; i<input.size(); i++){
			splits.add(input.get(i).split(" ")); 
		}
		ArrayList<String> response = solve(rowNum, colNum, splits);
		System.out.println("\nCheapest Path (Column Numbers): ");
		for(String s : response){
			System.out.println(s);
		}
	}
}
