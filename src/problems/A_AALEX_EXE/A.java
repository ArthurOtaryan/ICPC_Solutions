package problems.A_AALEX_EXE;

import java.io.*;
import java.util.*;

public class A {
    static int cmp(String b1, String e1, String b2, String e2) {
        int c = b1.compareTo(b2);
        if (c != 0) return c;
        return e1.compareTo(e2);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String[] base = new String[n], ext = new String[n];
            int exe = -1;
            for (int i = 0; i < n; i++) {
                String s = br.readLine().trim();
                int d = s.indexOf('.');
                base[i] = s.substring(0, d);
                ext[i] = s.substring(d + 1);
                if (ext[i].equals("EXE")) exe = i;
            }
            String ans = null;
            for (int k = 0; k <= 8 && ans == null; k++) {
                String nb = "A".repeat(k) + base[exe];
                if (nb.length() > 8) nb = nb.substring(0, 8);
                boolean ok = true;
                for (int i = 0; i < n && ok; i++) {
                    if (i == exe) continue;
                    if (cmp(nb, "EXE", base[i], ext[i]) >= 0) ok = false;
                }
                if (ok) ans = nb + ".EXE";
            }
            sb.append(ans == null ? "IMPOSSIBLE" : ans).append('\n');
        }
        System.out.print(sb);
    }
}
