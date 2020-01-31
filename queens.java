import java.util.Random;
import java.io.*; 
import java.util.Scanner;
import java.lang.Object;
import java.util.ArrayList;
import java.lang.Math;
/**
 * Write a description of queens here.
 * 
 * @author (Loc Tran) 
 */
public class queens {
    
    public static void main(String[] args) throws IOException
    {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int w = Integer.parseInt(args[2]);
        int b = Integer.parseInt(args[3]);
        double timeMax = Double.parseDouble(args[4]) * 1000;
        String fileName = args[5];
        String method = args[6];
        double initialTime = System.currentTimeMillis();
        Queen[][] globalMatrix = null;
        if (method.equals("hill_climbing"))
        {
            while (System.currentTimeMillis() - initialTime < timeMax)
            {
                Queen[] whiteQueens = new Queen[w];
                Queen[] blackQueens = new Queen[b];
                Queen[][] matrix;
                matrix = initialize(w, b, n, m);
                if(globalMatrix == null)
                {
                     globalMatrix = copy(matrix);
                }
                else
                {
                    if (val(matrix) < val(globalMatrix)) 
                    {
                    globalMatrix = copy(matrix);
                    }
                }
                while (val(matrix) != 0 && System.currentTimeMillis() - initialTime < timeMax)
                {
                    Queen[][] neighborMatrix = bestNeighbor(matrix, true);
                    if (val(neighborMatrix) <= val(matrix))
                    {
                        matrix = copy(neighborMatrix);
                        if (val(matrix) < val(globalMatrix))
                        {
                            globalMatrix = copy(matrix);
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                if(val(globalMatrix) == 0)
                {
                    break;
                }
            }
            printResult(globalMatrix, fileName);
            
        }
        else if (method.equals("simulated_annealing"))
        {
            double T = 10000;
            while (System.currentTimeMillis() - initialTime < timeMax)
            {
                T = T/1.2;
                Queen[] whiteQueens = new Queen[w];
                Queen[] blackQueens = new Queen[b];
                Queen[][] matrix = null;
                Queen[][] neighborMatrix;
                if(globalMatrix == null)
                {
                     matrix = initialize(w, b, n, m);
                     globalMatrix = copy(matrix);
                }
                else
                {
                    neighborMatrix = bestNeighbor(globalMatrix, false);
                    int deltaVal = val(neighborMatrix) - val(globalMatrix);
                    if (deltaVal < 0)
                    {
                        globalMatrix = copy(neighborMatrix);
                    }
                    else if (p(deltaVal,T) > Math.random())
                    {
                        globalMatrix = copy(neighborMatrix);
                    }
                }
                if (val(globalMatrix) == 0)
                {
                    break;
                }
            }
            printResult(globalMatrix, fileName);
        }
        else
        {
            System.out.println ("the method is either hill_climbing or simulated_annealing");
        }
    }
    
    public static Queen[][] initialize(int w, int b, int n, int m)
    {
        Queen[] whiteQueens = new Queen[w];
        Queen[] blackQueens = new Queen[b];
        Queen[][] matrix = new Queen[n][m];
        for (int i = 0; i < whiteQueens.length; i++)
        {
            whiteQueens[i] = new Queen(Color.WHITE);
            do
            {
                Random rand = new Random();
                int x = rand.nextInt(matrix.length);
                int y = rand.nextInt(matrix[0].length);
                if (matrix[x][y] == null)
                {
                    matrix[x][y] = whiteQueens[i];
                    whiteQueens[i].positionX = x;
                    whiteQueens[i].positionY = y;
                    break;
                }
            }while(true);
        }
        for (int i = 0; i < blackQueens.length; i++)
        {
            blackQueens[i] = new Queen(Color.BLACK);
            do
            {
                Random rand = new Random();
                int x = rand.nextInt(matrix.length);
                int y = rand.nextInt(matrix[0].length);
                if (matrix[x][y] == null)
                {
                    matrix[x][y] = blackQueens[i];
                    blackQueens[i].positionX = x;
                    blackQueens[i].positionY = y;
                    break;
                }
            }while(true);            
        }
        return matrix;
    }
    
    public static int val(Queen[][] matrix)
    {
        int score = 0;
        ArrayList<Queen> whiteQueens = new ArrayList<Queen>();
        ArrayList<Queen> blackQueens = new ArrayList<Queen>();
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                if (matrix[i][j] != null)
                {
                    if (matrix[i][j].getColor() == Color.WHITE)
                    {
                        whiteQueens.add(matrix[i][j]);
                    }
                    else if (matrix[i][j].getColor() == Color.BLACK)
                    {
                        blackQueens.add(matrix[i][j]);
                    }
                }
            }
        }
        for (int i = 0; i < whiteQueens.size(); i++)
        {
            for (int j = 0; j < blackQueens.size(); j++)
            {
                if (whiteQueens.get(i).faces(blackQueens.get(j)))
                {
                    score += 1;
                }
            }
        }
        return score;
    }
    
    
    public static int getScore(Queen queen, ArrayList<Queen> whiteQueens, ArrayList<Queen> blackQueens)
    {
        int score = 0;
        if (queen.getColor() == Color.WHITE)
        {
            for (int i = 0; i < blackQueens.size(); i++)
            {
                if (queen.faces(blackQueens.get(i)))
                {
                    score += 1;
                }
            }
        }
        
        else if (queen.getColor() == Color.BLACK)
        {
            for (int i = 0; i < whiteQueens.size(); i++)
            {
                if (queen.faces(whiteQueens.get(i)))
                {
                    score += 1;
                }
            }
        }
        return score;
    }
    
    public static Queen[][] bestNeighbor(Queen[][] matrix, boolean includeOriginalPosition)
    {
        Queen[][] bestNeighbour;
        Queen bestQueen;
        int score;
        bestNeighbour = copy(matrix);
        ArrayList<Queen> whiteQueens = new ArrayList<Queen>();
        ArrayList<Queen> blackQueens = new ArrayList<Queen>();
        for (int i = 0; i < bestNeighbour.length; i++)
        {
            for (int j = 0; j < bestNeighbour[0].length; j++)
            {
                if (bestNeighbour[i][j] != null)
                {
                    if (bestNeighbour[i][j].getColor() == Color.WHITE)
                    {
                        whiteQueens.add(bestNeighbour[i][j]);
                    }
                    else if (bestNeighbour[i][j].getColor() == Color.BLACK)
                    {
                        blackQueens.add(bestNeighbour[i][j]);
                    }
                }
            }
        }
        for (int i = 0; i < bestNeighbour.length; i++)
        {
            for (int j = 0; j < bestNeighbour[0].length; j++)
            {
                if (bestNeighbour[i][j] != null)
                {
                    Queen queen = bestNeighbour[i][j];
                    if (includeOriginalPosition)
                    {
                        score = getScore(queen, whiteQueens, blackQueens);
                    }
                    else
                    {
                        score = whiteQueens.size() + blackQueens.size();
                    }
                    if (queen.getColor() == Color.WHITE)
                    {
                        bestQueen = new Queen(Color.WHITE);
                    }
                    else
                    {
                        bestQueen = new Queen(Color.BLACK);
                    }
                    bestNeighbour[queen.positionX][queen.positionY] = null;
                    for (int k = 0; k < bestNeighbour.length; k++)
                    {
                        for (int h = 0; h < bestNeighbour[0].length; h++)
                        {
                           if (includeOriginalPosition)
                           {
                               if (bestNeighbour[k][h] == null)
                               {
                                   bestQueen.positionX = k;
                                   bestQueen.positionY = h;
                                   if (score >= getScore(bestQueen, whiteQueens, blackQueens))
                                   {
                                       score = getScore(bestQueen, whiteQueens, blackQueens);
                                       queen.positionX = bestQueen.positionX;
                                       queen.positionY = bestQueen.positionY;
                                   }
                               }
                           }
                           else
                           {
                               if (bestNeighbour[k][h] == null && k != i && h != j)
                               {
                                   bestQueen.positionX = k;
                                   bestQueen.positionY = h;
                                   if (score >= getScore(bestQueen, whiteQueens, blackQueens))
                                   {
                                       score = getScore(bestQueen, whiteQueens, blackQueens);
                                       queen.positionX = bestQueen.positionX;
                                       queen.positionY = bestQueen.positionY;
                                   }
                               }                               
                           }
                        }
                    }
                    bestNeighbour[queen.positionX][queen.positionY] = queen;
                    if (val(bestNeighbour) == 0)
                    {
                        return bestNeighbour;
                    }
                }
            }
        }
        return bestNeighbour;
    }
    
    
    public static void printResult(Queen[][] matrix, String fileName) throws IOException
    {
        PrintWriter outputFile = new PrintWriter(fileName);
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                if (matrix[i][j] == null)
                {
                    outputFile.print("| ");
                }
                else if (matrix[i][j].getColor() == Color.WHITE)
                {
                    outputFile.print("|w");
                }
                else if (matrix[i][j].getColor() == Color.BLACK)
                {
                    outputFile.print("|b");
                }
            }
            outputFile.println("|");
        }
        outputFile.print("\n");
        if (val(matrix) == 0)
        {
            outputFile.print("YES");
        }
        if (val(matrix) != 0)
        {
            outputFile.print("NO");
        }        
        outputFile.close();
    }
    
    public static Queen[][] copy(Queen[][] matrix)
    {
        Queen[][] result = new Queen[matrix.length][matrix[0].length];
        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < result[0].length; j++)
            {
                if (matrix[i][j] != null)
                {
                    result[i][j] = new Queen();
                    result[i][j].equal(matrix[i][j]);
                }
            }
        }
        return result;
    }

    
    public static double p(double deltaVal, double T)
    {
        return Math.pow(2.718281,(-deltaVal)/T);
    }
}
