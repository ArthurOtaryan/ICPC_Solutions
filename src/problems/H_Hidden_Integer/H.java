package problems.H_Hidden_Integer;

import java.io.*;
import java.util.*;

public class H {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    static int ask(long x) throws IOException {
        out.println("? " + x);
        out.flush();
        String s = br.readLine();
        while (s != null && s.trim().isEmpty()) s = br.readLine();
        int r = Integer.parseInt(s.trim());
        if (r < 0) System.exit(0);
        return r;
    }

    public static void main(String[] args) throws IOException {
        int p = ask(2);
        long lo = p == 1 ? 1 : 2;
        long hi = p == 1 ? 999999999L : 1000000000L;
        while (lo < hi) {
            long k = (hi - lo) / 2 + 1;
            long h = (k + 1) / 2;
            long first = lo + 2 * h;
            long x = p == 1 ? first : first - 1;
            int r = ask(x);
            boolean ge = (r ^ p) == 1;
            if (ge) lo = first;
            else hi = first - 2;
        }
        out.println("! " + lo);
        out.flush();
    }
}
