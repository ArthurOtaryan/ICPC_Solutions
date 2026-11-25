package problems.G_Goin_Round;

import java.io.*;
import java.util.*;

public class G {
    static int c, g;
    static int[] Gp;

    static boolean safe(int j) {
        int a = Gp[((j - 2) % c + c) % c], b = Gp[((j - 1) % c + c) % c];
        return Gp[j] >= 1 && a + b <= g - 2;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());
            String s = br.readLine().trim();
            int[] pos = new int[n];
            c = 0;
            for (int i = 0; i < n; i++) if (s.charAt(i) == '>') pos[c++] = i;
            if (c == 2) { sb.append(n - 2 >= g ? "Anya" : "Draw").append('\n'); continue; }
            Gp = new int[c];
            for (int k = 0; k < c; k++) Gp[k] = ((pos[(k + 1) % c] - pos[k] - 1) % n + n) % n;
            boolean immediate = false;
            for (int i = 0; i < c; i++) if (Gp[i] + Gp[(i + 1) % c] >= g) immediate = true;
            if (immediate) { sb.append("Anya\n"); continue; }
            int cnt = 0;
            for (int i = 0; i < c; i++) if (safe(i)) cnt++;
            if (cnt == 0) { sb.append("Borya\n"); continue; }
            boolean win = false;
            int[] js = new int[4];
            for (int i = 0; i < c && !win; i++) {
                int im1 = (i - 1 + c) % c, im2 = (i - 2 + c) % c;
                int m = 0;
                for (int d = -1; d <= 2; d++) {
                    int j = ((i + d) % c + c) % c;
                    boolean dup = false;
                    for (int q = 0; q < m; q++) if (js[q] == j) dup = true;
                    if (!dup) js[m++] = j;
                }
                int before = 0;
                for (int q = 0; q < m; q++) if (safe(js[q])) before++;
                int orig = Gp[i];
                for (int k = 1; k <= orig && !win; k++) {
                    if (Gp[im2] + Gp[im1] + k >= g) break;
                    Gp[i] = orig - k;
                    Gp[im1] += k;
                    int after = 0;
                    for (int q = 0; q < m; q++) if (safe(js[q])) after++;
                    if (cnt - before + after == 0) win = true;
                    Gp[i] = orig;
                    Gp[im1] -= k;
                }
            }
            sb.append(win ? "Anya" : "Draw").append('\n');
        }
        System.out.print(sb);
    }
}
