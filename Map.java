import java.io.*;
import java.util.*;

public class Map {
    private int[][] layout;
    private int[][] heightMap;
    private double playerStartXPos; //this is along width
    private double playerStartYPos; //this is along length
    private double playerStartZPos; //this is along height
    
    /*
        Original Map Size = 15x15
        '0' - air
        '1' - tile
        '2' - walltile
    */

    public Map(String path) {
        try {
            readMapData(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void readMapData(String pathName) throws IOException {
        String section = "";
        File file = new File("MapFiles/"+pathName+".txt");
        Scanner sc = new Scanner(file);
        int w = 0, l = 0;
        int index_r = 0, index_c = 0;
        boolean useHeightMap = true;
        while(sc.hasNextLine()) {
            String word = sc.next();
            String[] keyWords = {"width", "length", "playerStartXPos", "playerStartYPos", 
                "playerStartZPos", "layout", "useHeightMap", "overallMapHeight", "heightMap", "end"};
            for(int i = 0; i < keyWords.length; i++) { //updates section
                if(word.contains(keyWords[i]) && Math.abs(word.length() - keyWords[i].length()) < 3) { //avoids ':'
                    section = keyWords[i];
                    word = section;
                }
            }

            if(section != word) {
                switch(section) {
                    case "width":
                        w = Integer.parseInt(word);
                        break;
                    case "length":
                        l = Integer.parseInt(word);
                        break;
                    case "playerStartXPos":
                        layout = new int[l][w]; //width and length have just been retrieved
                        heightMap = new int[l][w];
                        playerStartXPos = Double.parseDouble(word);
                        break;
                    case "playerStartYPos":
                        playerStartYPos = Double.parseDouble(word);
                        break;
                    case "playerStartZPos":
                        playerStartZPos = Double.parseDouble(word);
                        break;
                    case "layout":
                        layout[index_r][index_c] = Integer.parseInt(word);
                        index_c++;
                        if(index_c == w) {
                            index_c = 0;
                            index_r++;
                        }
                        break;
                    case "useHeightMap":
                        index_r = 0;
                        index_c = 0;
                        if(word.equals("false"))
                            useHeightMap = false;
                        break;
                    case "overallMapHeight":
                        if(!useHeightMap)
                            for(int r = 0; r < heightMap.length; r++)
                                for(int c = 0; c < heightMap[r].length; c++)
                                    heightMap[r][c] = Integer.parseInt(word);
                        break;
                    case "heightMap":
                        if(useHeightMap) {
                            heightMap[index_r][index_c] = Integer.parseInt(word);
                            index_c++;
                            if(index_c == w) {
                                index_c = 0;
                                index_r++;
                            }
                        }
                        break;
                    case "end":
                        sc.close();
                        break;
                    default:
                        break;
                }
            }
        }
        
    }

    public int[][] getLayout() {
        return layout;
    }
    public int[][] getHeightMap() {
        return heightMap;
    }
    public int getWidth() {
        return layout[0].length;
    }
    public int getLength() {
        return layout.length;
    }
    public double getPlayerStartXPos() {
        return playerStartXPos;
    }
    public double getPlayerStartYPos() {
        return playerStartYPos;
    }
    public double getPlayerStartZPos() {
        return playerStartZPos;
    }
}