package simulator.tools;

import java.io.File;
import java.io.FileReader;

public abstract class EasierIO {
    /**
     * Read all from extract file, return contents as String.
     * @param path
     * @return
     */
    public static String fileRead(String path){
        String      contents = "";
        int         temp     = 0;
        try {
            File        f        = new File(path);
            FileReader  r        = new FileReader(f);
            while((temp = r.read()) != -1){
                contents += String.valueOf((char)(temp));
            }
            r.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
}