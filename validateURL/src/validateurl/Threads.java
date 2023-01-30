/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validateurl;

/**
 *
 * @author asala
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Threads extends Thread {

    private String link;
    private int depth;
    private int maxDepth;
    
    public Threads(String link, int depth, int maxDepth) {
        this.link = link;
        this.depth = depth;
        this.maxDepth = maxDepth;
    }
    
    @Override
    public void run() {
        try {
            try {
                LinkValidation.validateURL(link, depth, maxDepth);
            } catch (InterruptedException ex) {
                Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
