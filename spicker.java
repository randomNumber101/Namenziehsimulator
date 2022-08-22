import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.File;

class spicker {

    public static void main(String[] args) {

        try {

        File f = new File("C:\\Users\\MKhal\\Desktop\\Freizeit\\PV\\Namenziehsimulator\\permut saver\\permutation_20-11-03_16-15.txt");
        BufferedReader fReader = new BufferedReader(new FileReader(f));

        BufferedReader cReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String name = cReader.readLine();
            if (name.equals("quit")) {
                fReader.close();
                break;
            }


            String info;
            while ((info = fReader.readLine()) != null &! info.startsWith(name));

            if (info!= null && info.startsWith(name))
                System.out.println(info);
            else
                System.out.println("Not found.");
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
