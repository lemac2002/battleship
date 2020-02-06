
public class Cell {
   private int x;
   private int y;
   Cell(){
	   x = -1;
	   y = -1;
   }
   Cell(int i, int j){
	   x = i;
	   y = j;
   }
   
   public void setX(int i){
	   x = i;
   }
   
   public void setY(int i){
	   y = i;
   }
   
   public int getX(){
	   return x;
   }
   
   public int getY(){
	   return y;
   }
  
}