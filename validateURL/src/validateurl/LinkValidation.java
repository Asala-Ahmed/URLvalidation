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
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class LinkValidation {

    static int count = 0;
    static int counter = 0;
    static ExecutorService es;

    public LinkValidation(int threadsno) {
        System.out.println("Number Of Threads : " + threadsno);
        es = Executors.newFixedThreadPool(threadsno);
    }
    public LinkValidation() {
     
    }

    public static void validateURL(String link, int currentDepth, int totalDepth) throws IOException, InterruptedException {

        Threads t1 = null;
        if (validateSingleURL(link)) {
          
            //System.out.println("Valid Link : " + link );
             count++;
            if (currentDepth == totalDepth) {
                return;
            }
            Document doc = Jsoup.connect(link).get();
            Elements e = doc.select("a[href]"); // extract only links
            URL u = new URL(link);
            System.out.println("Protocol: " + u.getProtocol());
            System.out.println("Host: " + u.getHost());
            String baseLINK = u.getProtocol() + "://" + u.getHost();
            System.out.println("BASE URL : " + baseLINK);
            for (int i = 0; i < e.size(); i++) {
                String x = e.get(i).attr("href");
                e.get(i).text();
                System.out.println("Text: " + e.get(i).text());
                if (!x.startsWith("http")) {
                    x = baseLINK + x;
                }

                t1 = new Threads(x, currentDepth + 1, totalDepth);
                es.execute(t1);
            }
        } else {
            //System.err.println("Invalid Link : " + link );
            counter++;
        }
    }

    public static boolean validateSingleURL(String link) throws IOException {
        boolean valid = false;
        try {
            Document doc = (Document) Jsoup.connect(link).get();
            valid = true;
        } catch (HttpStatusException ex) {
            valid = false;
            System.err.println("PAGE NOT FOUND");
        } catch (IOException ex) {
            valid = false;
        } catch (Exception ex) {

        }
        return valid;
    }

}
