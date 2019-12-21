import javax.swing.plaf.DimensionUIResource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Executor {
    private double counter(double k, int iteration, boolean isLast) {
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        double sum4 = 0;
        int[] timeArr = {1, 2, 3, 4, 5, 6, 7};
        int[] nArr = {3, 5, 2, 1, 1, 1, 0};
        File file = new File("table.txt");
        File file_1 = new File("k.txt");
        try (FileWriter writer = new FileWriter(file); FileWriter writer_1 = new FileWriter(file_1, true)) {
            for (int i = 0; i < 7; i++) {
                writer.write(Integer.toString(timeArr[i]));
                writer.write("\t");
                writer.write(Integer.toString(nArr[i]));
                writer.write("\t");
                writer.write(Integer.toString(timeArr[i] * nArr[i]));
                writer.write("\t");
                writer.write(Double.toString(Math.round(k * timeArr[i]*100.0)/100.0));
                writer.write("\t");
                writer.write(Double.toString(Math.round(Math.exp(-k * timeArr[i])*100.0)/100.0));
                writer.write("\t");
                writer.write(Double.toString(Math.round(nArr[i] * Math.exp(-k * timeArr[i])*100.0)/100.0));
                writer.write("\t");
                writer.write(Double.toString(Math.round(timeArr[i] * Math.exp(-2 * k * timeArr[i])*100.0)/100.0));
                writer.write("\t");
                writer.write(Double.toString(Math.round(Math.exp(-2 * k * timeArr[i])*100.0)/100.0));
                writer.write("\t");
                writer.write(Double.toString(Math.round(nArr[i] * timeArr[i] * Math.exp((-k) * timeArr[i])*100.0)/100.0));
                writer.write("\n");
                sum1 += nArr[i] * Math.exp(-k * timeArr[i]);
                sum2 += nArr[i] * timeArr[i] * Math.exp((-k) * timeArr[i]);
                sum3 += timeArr[i] * Math.exp(-2 * k * timeArr[i]);
                sum4 += Math.exp(-2 * k * timeArr[i]);
            }
            writer_1.write(Integer.toString(iteration));
            writer_1.write("\t");
            writer_1.write(Double.toString(k));
            writer_1.write("\t");
            writer_1.write(Double.toString(Math.round((sum1)*100000000)/100000000));
            writer_1.write("\t");
            writer_1.write(Double.toString(Math.round(((sum2 / sum3) * sum4)*100000000)/100000000));
            writer_1.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (sum1 - (sum2 / sum3) * sum4);
    }

    double coeffSearchEngine(double coeff_1, double coeff_2) {
        double eps = 0.00001;
        double left = coeff_1, right = coeff_2, k, f;
        int iterator = 0;
        System.out.println("k0 = " + coeff_1);
        System.out.println("k1 = " + coeff_2);
        do {
            k = (left + right) / 2;
            System.out.println("k = " + k);
            f = counter(k, iterator + 1, false);
            if (f < 0) right = k;
            else left = k;
            iterator++;
        } while (Math.abs(f) > eps);
        System.out.println("Number of done steps is: " + iterator);
        Double n;
        double sum2 = 0;
        double sum3 = 0;
        int[] timeArr = {1, 2, 3, 4, 5, 6, 7};
        int[] nArr = {3, 5, 2, 1, 1, 1, 0};
        File file = new File("ras.txt");
        double n0;
        for (int i = 0; i < 7; i++) {
            sum2 += nArr[i] * timeArr[i] * Math.exp((-k) * timeArr[i]);
            sum3 += timeArr[i] * Math.exp(-2 * k * timeArr[i]);
        }
        n0 = sum2 / (k * sum3);
        System.out.println("N0 is: " + n0);
        System.out.println("Raschetnoe: " + sum2 / (sum3));
        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < 7; i++) {
                writer.write(Integer.toString(timeArr[i]));
                writer.write("\t");
                writer.write(Integer.toString(nArr[i]));
                writer.write("\t");
                double ras = n0*Math.exp(-k*timeArr[i])*k;
                writer.write(Double.toString(ras));
                writer.write("\t");
                writer.write(Double.toString(ras-nArr[i]));
                writer.write("\t");
                writer.write(Double.toString(Math.pow(ras-nArr[i], 2)));
                writer.write("\n");
                sum2 += nArr[i] * timeArr[i] * Math.exp((-k) * timeArr[i]);
                sum3 += timeArr[i] * Math.exp(-2 * k * timeArr[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return k;
    }
}
