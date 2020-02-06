public class Battleship {
   private int x;  //Battleship start x axis, initialize as  -1
   private int y;  //Battleship start y axis, initialize as  -1
   private int length;  //Battleship length
   private int direction; //left is 1,  right is 2, down is 3, up is 4
   private int[] block; //this keep row or column index
   private boolean hitted[];
   private boolean isAlive; 
   private int shipid; //1 is carrier, 2 is destroyer, 3 is battleship, 4 is submarine, 5 is cruiser
   
   Battleship(int len, int id ){
	   x = -1;
	   y = -1;
	   this.length = len;
	   direction = 0;
	   hitted = new boolean[len];
	   block = new int[len];
	   isAlive = false;
	   shipid = id;
   }
   //Min + (int)(Math.random() * ((Max - Min) + 1))
   public void randomMakeBattleship(){
	   x = (int) (Math.random() * 10);
	   y = (int) (Math.random() * 10);
	   direction = 1 + (int)(Math.random() * 4);
   }
   //left is 1,  right is 2, down is 3, up is 4
   public void setBattleship(int startX, int startY, int endX, int endY, int dire){
	   direction = dire;
	   isAlive = true;
	   if(startX == endX){
		   x = startX;
		   if(dire == 1){
			   y = endY;
		   }
		   else{
			   y = startY;
		   }
		   for(int i = startY; i <= endY; i++){
			   block[i-startY] = i;
		   }
	   }
	   else {
		 y = startY;
		 if(dire == 3){
			 x = startX;
		 }
		 else{
			 x = endX;
		 }
		 for(int i = startX; i <= endX; i++){
			   block[i-startX] = i;
		 }
	   }
   }
   
   public boolean isValidBattleship(int[][] grid){
	   int n = 0;
	   switch(direction){
	     case 1:
	    	 if(y + 1 < length){
	    		 return false;
	    	 }
	    	 n = y;
	    	 while(n >= 0){
	    		 if(grid[x][n] != 0){
	    			 return false;
	    		 }
	    		 n--;
	    	 }
	    	 return true;
	     case 2:
	    	 if(10 - y < length){
	    		 return false;
	    	 }
	    	 n = y;
	    	 while(n < 10){
	    		 if(grid[x][n] != 0){
	    			 return false;
	    		 }
	    		 n++;
	    	 }
	    	 return true;
	     case 3:
	    	 if(10-x < length){
	    		 return false;
	    	 }
	    	 n = x;
	    	 while(n < 10){
	    		 if(grid[n][y] != 0){
	    			 return false;
	    		 }
	    		 n++;
	    	 }
	    	 return true;
	     case 4:
	    	 if(x + 1 < length){
	    		 return false;
	    	 }
	    	 n = x;
	    	 while(n >= 0){
	    		 if(grid[n][y] != 0){
	    			 return false;
	    		 }
	    		 n--;
	    	 }
	    	 return true;
	     default:
	    	 return false;
	   }
   }
   
   public void generateValidBattleship(int[][] grid){
	   randomMakeBattleship();
	   while(!isValidBattleship(grid)){
		   randomMakeBattleship();
	   }
	 //left is 1,  right is 2, down is 3, up is 4
	   int yx = 0;
	   switch(direction){
	     case 1:
	    	 yx = y;
	    	 for(int i = 0; i < length; i++){
	    	    block[i] = yx;
	    	    yx--;
	    	 }
	    	 break;
	     case 2:
	    	 yx = y;
	    	 for(int i = 0; i < length; i++){
	    	    block[i] = yx;
	    	    yx++;
	    	 }
	    	 break;
	     case 3:
	    	 yx = x;
	    	 for(int i = 0; i < length; i++){
		       block[i] = yx;
		       yx++;
		     }
	    	 break;
	     case 4:
	    	 yx = x;
	    	 for(int i = 0; i < length; i++){
		    	block[i] = yx;
		    	yx--;
		     }
	    	 break;
	     	
	   }
	   isAlive = true;
   }
   
   public int getX(){
	   return x;
   }
   
   public int getY(){
	   return y;
   }
   
   public int getLen(){
	   return length;
   }
   public int getDirection(){
	   return direction;
   }
   
   public boolean getAlive(){
	   return isAlive;
   }
  
   int searchAndGetCellIndex(int i, int j){
	   int index = -1;
	   if((direction == 1 || direction == 2) && x == i) {
		   for(int m = 0; m < length; m++){
			   if(j == block[m]){
				   return m;
			   }
		   }
	   }
	   else{
		   if(y == j){
		     for(int m = 0; m < length; m++){
			     if(i == block[m]){
				     return m;
			     }
		     } 
		   }
	   }
	   return index;
   }
   //left is 1,  right is 2, down is 3, up is 4
   public int markHited(int i, int j){
	   if(direction == 1 || direction == 2){
		   if(x != i){
			   return -1;
		   }
	   }
	   if(direction == 3 || direction == 4){
		   if(y != j){
			   return -1;
		   }
	   }
	
	   int index = searchAndGetCellIndex(i, j);
	   if(index >= 0){
		   hitted[index] = true;
		   updateAlive();
		   return shipid;
	   }
	   return -1;
   }
   
   public void updateAlive(){
	   for(int m = 0; m < length; m++){
		   if(!hitted[m]){
			   return;
		   }
	   }
	   isAlive = false;
   }
   
   public void resetBattleShip() {
	   x = -1;
	   y = -1;
	   direction = 0;
	   isAlive = false;
	   
	   for(int i = 0; i < length; i++) {
		   hitted[i] = false;
		   block[i] = -1;
	   }
	   
   }
   
   public void printShip(){
	   System.out.println("x:" + x + "  y:"+ y+"   dir:"+direction);
   }
}
