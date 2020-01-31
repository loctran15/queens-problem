
/**
 * Write a description of queen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
enum Color
{
    BLACK,WHITE;
}
public class Queen {
    private Color color;
    public int positionX;
    public int positionY;
    public int facedEnemies;
    
    public Queen(Color color)
    {
        positionX = 0;
        positionY = 0;
        facedEnemies = 0;
        this.color = color;
    }
    
    public Queen()
    {
        positionX = 0;
        positionY = 0;
        facedEnemies = 0;
    }
    
    public boolean faces(Queen anotherQueen)
    {
        if (this.color != anotherQueen.getColor())
        {
            // code to check whether they are faces each other
            if(this.positionX == anotherQueen.positionX)
            {
                return true;
            }
            if(this.positionY == anotherQueen.positionY)
            {
                return true;
            }
            if(this.positionX - anotherQueen.positionX == this.positionY - anotherQueen.positionY)
            {
                return true;
            }
            
            if(anotherQueen.positionX - this.positionX == this.positionY - anotherQueen.positionY)
            {
                return true;
            }            

        }
        return false;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public String getColorName()
    {
        if (this.color == Color.BLACK)
        {
            return "black";
        }
        if (this.color == Color.WHITE)
        {
            return "white";
        }
        return "err";
    }
    
    public void equal(Queen anotherQueen)
    {
        this.positionX = anotherQueen.positionX;
        this.positionY = anotherQueen.positionY;
        this.color = anotherQueen.getColor();
    }
}
