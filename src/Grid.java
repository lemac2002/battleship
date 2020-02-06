public class Grid {
	private final int N = 10;
    private Battleship carrier;
    private Battleship battleBattleship;
    private Battleship cruiser;
    private Battleship submarine;
    private Battleship destroyer;
    private int cells[][];   //empty is 0, Battleship is 1, hitted is 2
    
    Grid(){
       carrier = new Battleship(5, 1);
       destroyer = new Battleship(4, 2);
       battleBattleship = new Battleship(3, 3);
       submarine = new Battleship(3, 4);
       cruiser = new Battleship(2, 5);
       cells = new int[N][];
       for(int i = 0; i < N; i++){
    	   cells[i] = new int[N];
       }
    }
    
    public boolean isEmptyCell(int x, int y){
    	if(cells[x][y] != 0){
    		return false;
    	}
    	return true;
    }
    
    
    public void randomSetupBattleship(){
        if(!carrier.getAlive()){
    	  carrier.generateValidBattleship(cells);
    	  updateGrid(carrier);
        }
    	
        if(!destroyer.getAlive()){
        	destroyer.generateValidBattleship(cells);
        	updateGrid(destroyer);
        	
        }
    	
        if(!battleBattleship.getAlive()){
        	battleBattleship.generateValidBattleship(cells);
        	updateGrid(battleBattleship);	
        }
    	
        if(!submarine.getAlive()){
    	  submarine.generateValidBattleship(cells);
    	  updateGrid(submarine);
        }
    	
        if(!cruiser.getAlive()){
    	  cruiser.generateValidBattleship(cells);
    	  updateGrid(cruiser);
        }
        //System.out.println("************ran one***************************");
   	    //carrier.printShip();
        //destroyer.printShip();
       // battleBattleship.printShip();
        //submarine.printShip();
        //cruiser.printShip();
        //System.out.println("************ran one one***************************");
    }
    //left is 1,  right is 2, down is 3, up is 4
    public void updateGrid(Battleship Battleship){
    	int x = Battleship.getX();
    	int y = Battleship.getY();
    	int len = Battleship.getLen();
    	int direction = Battleship.getDirection();
    	
    	switch(direction){
    	  case 1:
    		for(int i = 0; i < len; i++){
    			cells[x][y-i] = 1;
    		}
    		break;
    	  case 2:
    		for(int i = 0; i < len; i++){
      		   cells[x][y+i] = 1;
      		}
    		break;
    	  case 3:
    		for(int i = 0; i < len; i++){
         	   cells[x+i][y] = 1;
         	}
    		break;
    	  case 4:
    		  for(int i = 0; i < len; i++){
            	cells[x-i][y] = 1;
            }
    		break;
    	  default:
    		break;  
    	}
    }
    
    public int getCellInfo(int x, int y){
    	return cells[x][y];
    }
    
    public int hittedCells(int x, int y){
    	if(cells[x][y] != 1){
    		return -1;
    	}
    	
    	cells[x][y] = 2;
    	if(carrier.markHited(x, y) > 0){
    		return 1;
    	}
    	if(destroyer.markHited(x, y) > 0){
    		return 2;
    	}
    	if(battleBattleship.markHited(x, y) > 0){
    		return 3;
    	}
    	if(submarine.markHited(x, y) > 0){
    		return 4;
    	}
    	if(cruiser.markHited(x, y) > 0){
    		return 5;
    	}
    	return -1;
    	
    }
    
    public boolean isWin(){
    	return !carrier.getAlive() && !destroyer.getAlive() && !battleBattleship.getAlive() && !submarine.getAlive() && !cruiser.getAlive();
    }
    public boolean isAllAlive(){
    	return carrier.getAlive() && destroyer.getAlive() && battleBattleship.getAlive() && submarine.getAlive() && cruiser.getAlive();
    }
    public int[][] getGrid(){
    	return cells;
    }
    
    //0 is carrier, 1 is destroyer, 2 is battleBattleship, 3 is submarine, 4 is cruise 
    public Battleship getBattleship(int nameId){
    	switch(nameId){
    	  case 0:
    		return carrier;
    	  case 1:
      		return destroyer;
    	  case 2:
      		return battleBattleship;
    	  case 3:
      		return submarine;
    	  case 4:
      		return cruiser;
      	  default:
      		return null;  
    	}
    }
    
    public void printGrid(){
    	for(int i = 0; i < N; i++){
    		for(int j = 0; j < N; j++){
    			//System.out.print(cells[i][j] + " ");
    		}
    		//System.out.println();
    	}
    	//System.out.println("************grid***************************");
    }
    public void resetGrid() {
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			cells[i][j] = 0;
    		}
    	}
    	
    	carrier.resetBattleShip();
    	destroyer.resetBattleShip();
    	battleBattleship.resetBattleShip();
    	submarine.resetBattleShip();
    	cruiser.resetBattleShip();
    }
  //left is 1,  right is 2, down is 3, up is 4
    void resetShipCells(int x, int y, int dire, int len){
    	int ex = x;
    	int ey = y;
    	int sx = x; 
    	int sy = y;
    	switch(dire){
    	  case 1:
    		  sy = y - len + 1;
    	     break;
    	  case 2:
    		 ey = y +len -1;
    		 break;
    	  case 3:
    		 ex = x + len -1;
    		 break;
    	  case 4:
    		 sx = x - len + 1;
    		 break;
    	  default:
    		  return;
    	}
    	for(int i = sx; i <= ex; i++){
    		for(int j = sy; j <= ey; j++){
    			cells[i][j] = 0;
    		}
    	}
    }
    public boolean setOneShip(String ship, String direction, int row, int col){
    	int len = 0;
    	int id = 0;
    	boolean isAlive = false;
    	int x = -1;
    	int y = -1;
    	int olddire = 0;
    	if(ship == "carrier"){
    		len = 5;
    		id = 1;
    		isAlive = carrier.getAlive();
    		x = carrier.getX();
    		y = carrier.getY();
    		olddire = carrier.getDirection();
    	}
    	else if(ship == "destroyer"){
    		len = 4;
    		id = 2;
    		isAlive = destroyer.getAlive();
    		x = destroyer.getX();
    		y = destroyer.getY();
    		olddire = destroyer.getDirection();
    	}
    	else if(ship == "battleship") {
    		len = 3;
    		id = 3;
    		isAlive = battleBattleship.getAlive();
    		x = battleBattleship.getX();
    		y = battleBattleship.getY();
    		olddire = battleBattleship.getDirection();
    	}
    	else if(ship == "submarine"){
    		len = 3;
    		id = 4;
    		isAlive = submarine.getAlive();
    		x = submarine.getX();
    		y = submarine.getY();
    		olddire = submarine.getDirection();
    	}
    	else if(ship == "crusier"){
    		len = 2;
    		id = 5;
    		isAlive = cruiser.getAlive();
    		x = cruiser.getX();
    		y = cruiser.getY();
    		olddire = cruiser.getDirection();
    	}
    	else{
    		return false;
    	}
    	int endX = -1;
    	int endY = -1;
    	int dire = 0;
    	if(direction == "up"){
    		dire = 4;
    		endX = row - len + 1;
    		endY = col;
    	}
    	else if(direction == "down"){
    		dire = 3;
    		endX = row + len -1;
    		endY = col;
    	}
    	else if(direction =="left"){
    		dire = 1;
    		endY = col -len + 1;
    		endX = row;
    		
    	}
    	else if(direction =="right"){
    		dire = 2;
    		endY = col + len -1;
    		endX = row;
    	}
    	else{
    		return false;
    	}
    	if(endX < 0 || endX >= N || endY < 0 || endY >= N){
    		return false;
    	}
    	if(endX < row){
    		int t = endX;
    		endX = row;
    		row = t;
    	}
    	if(endY < col){
    		int t = endY;
    		endY = col;
    		col = t;
    	}
    	if(isAlive){
    		resetShipCells(x, y, olddire, len);
    	}
    	for(int i = row; i <= endX; i++){
    		for(int j = col; j<= endY; j++){
    			if(cells[i][j] != 0){
    				return false;
    			}
    		}
    	}
    	for(int i = row; i <= endX; i++){
    		for(int j = col; j<= endY; j++){
    			cells[i][j] = 1;
    		}
    	}
    	switch(id){
    	  case 1:
    		  carrier.setBattleship(row, col, endX, endY, dire);
    		  break;
    	  case 2:
    		  destroyer.setBattleship(row, col, endX, endY, dire);
    		  break;
    	  case 3:
    		  battleBattleship.setBattleship(row, col, endX, endY, dire);
    		  
    		  break;
    	  case 4:
    		  submarine.setBattleship(row, col, endX, endY, dire);
    		  break;
    	  case 5:
    		  cruiser.setBattleship(row, col, endX, endY, dire);
    		  break;
    	  default:
    		  return false;
    		
    	}
         //System.out.println("************one***************************");
    	 //carrier.printShip();
         //destroyer.printShip();
         //battleBattleship.printShip();
         //submarine.printShip();
        // cruiser.printShip();
        // System.out.println("************one one***************************");
       
    	return true;
    }
    public boolean getOneShipSink(int id){
    	switch (id){
    	  case 1:
    		  return carrier.getAlive();
    	  case 2:
    		  return destroyer.getAlive();
    	  case 3:
    		  return battleBattleship.getAlive();
    	  case 4:
    		  return submarine.getAlive();
    	  case 5:
    		  return cruiser.getAlive();
    	  default:
    		  return false;
    	}
    }
}