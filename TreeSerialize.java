import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class TreeSerialize {
    public static void main(String[] args) {
        String csvFile = "input.csv";

        BufferedReader br = null;
        String line = "";

        List<String[]> lines = new ArrayList<String[]>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // splitting into rows
                String ar[] = line.split(";");
                StringBuilder stringBuilder = new StringBuilder();
                for (int k = 0; k < ar.length; k++) {
                    stringBuilder.append(ar[k]);
                }
                // splitting into cols
                String f = stringBuilder.toString();
                lines.add(f.split(","));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}