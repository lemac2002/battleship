import java.util.ArrayList;

public class Player {
	private boolean isComputer;
	private Grid myGrid;
	private ArrayList<Cell> nextSteps;
	
	Player(){
		myGrid = new Grid();
		isComputer = false;
		nextSteps = new  ArrayList<Cell>();
	}
	
	public void autoSetUp(){
	    myGrid.randomSetupBattleship();
	}
	
	public void setAsComputer(){
		isComputer = true;
	}
	
	public int setOneCell(int x, int y){
		int id = myGrid.hittedCells(x, y);
		if(id > 0){
			if(x+1 < 10){
				if(myGrid.getCellInfo(x+1, y) == 1){
				  Cell oneCell1 = new Cell(x+1, y);
				  nextSteps.add(oneCell1);
				}
			}
	        if(x - 1 >= 0){
	        	if(myGrid.getCellInfo(x-1, y) == 1){
					  Cell oneCell1 = new Cell(x-1, y);
					  nextSteps.add(oneCell1);
				}
	        }
	        if(y + 1 < 10){
	        	if(myGrid.getCellInfo(x, y+1) == 1){
					  Cell oneCell1 = new Cell(x, y+1);
					  nextSteps.add(oneCell1);
				}
	        }
	        if(y - 1 >= 0){
	        	if(myGrid.getCellInfo(x, y-1) == 1){
					  Cell oneCell1 = new Cell(x, y-1);
					  nextSteps.add(oneCell1);
				}
	        }
		}
		return id;
	}
	
	public int getShipCellType(int x, int y){
		return myGrid.getCellInfo(x, y);
	}
	public boolean isAllShipsAlive(){
		return myGrid.isAllAlive();
	}
	public boolean isAllShipsSink(){
		return myGrid.isWin();
	}
	
	public Grid getGrid(){
		return myGrid;
	}
	
	public void setGrid(Grid aGrid){
		myGrid = aGrid;
	}
	
	
	public boolean getIsComputer(){
		return isComputer;
	}
	
	public void printgrid(){
		myGrid.printGrid();
	}
	public void resetPlayer() {
		myGrid.resetGrid();
		nextSteps.clear();
		
	}
	
	public Cell getNextStep(){
		if(!nextSteps.isEmpty()){
			Cell oneCell = nextSteps.get(0);
			nextSteps.remove(0);
			return oneCell;
		}
		return null;
		
	}
	public boolean setOneShip(String ship, String direction, int row, int col){
		return myGrid.setOneShip(ship, direction, row, col);
	}
	
	public boolean getOneShipSink(int id){
		return myGrid.getOneShipSink(id);
	}
}