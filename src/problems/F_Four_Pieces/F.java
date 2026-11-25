package problems.F_Four_Pieces;

import java.io.*;
import java.util.*;

public class F {
    static final long MOD = 998244353L;

    static long[] compute(int n, int[] cc, int[] bb, int L) {
        long[] A = new long[2 * L + 3];
        TreeMap<Integer, Integer> U = new TreeMap<>();
        for (int i = 0; i < n; i++) if (cc[i] == 0) U.merge(bb[i], 1, Integer::sum);
        Integer[] ord = new Integer[n];
        for (int i = 0; i < n; i++) ord[i] = i;
        Arrays.sort(ord, (p, q) -> Integer.compare(cc[p], cc[q]));
        long[] freq = new long[2 * L + 3];
        int[] distinct = new int[n];
        int dc = 0;
        int ptr = 0;
        for (Map.Entry<Integer, Integer> e : U.entrySet()) {
            int x = e.getKey();
            long ux = e.getValue();
            while (ptr < n && cc[ord[ptr]] <= x) {
                int b = bb[ord[ptr]] + L;
                if (freq[b] == 0) distinct[dc++] = b;
                freq[b]++;
                ptr++;
            }
            for (int j = 0; j < dc; j++) {
                int b = distinct[j] - L;
                int P = x + b;
                if (P < 0 || P >= A.length) continue;
                A[P] += ux * freq[distinct[j]];
            }
        }
        return A;
    }

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int t = nextInt(in);
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = nextInt(in);
            int[] c = new int[n], b = new int[n];
            int L = 0;
            for (int i = 0; i < n; i++) {
                int ch;
                do { ch = in.read(); } while (ch != '(' && ch != ')');
                int bal = 0, mn = 0, len = 0;
                while (ch == '(' || ch == ')') {
                    bal += ch == '(' ? 1 : -1;
                    mn = Math.min(mn, bal);
                    len++;
                    ch = in.read();
                }
                c[i] = -mn;
                b[i] = bal;
                L += len;
            }
            long[] A = compute(n, c, b, L);
            int[] c2 = new int[n], b2 = new int[n];
            for (int i = 0; i < n; i++) { c2[i] = c[i] + b[i]; b2[i] = -b[i]; }
            long[] Dd = compute(n, c2, b2, L);
            long ans = 0;
            for (int P = 0; P < A.length; P++) {
                if (A[P] == 0 || Dd[P] == 0) continue;
                ans = (ans + (A[P] % MOD) * (Dd[P] % MOD)) % MOD;
            }
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }

    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0, b;
        do { b = in.read(); } while (b != '-' && (b < '0' || b > '9'));
        boolean neg = false;
        if (b == '-') { neg = true; b = in.read(); }
        while (b >= '0' && b <= '9') { ret = ret * 10 + (b - '0'); b = in.read(); }
        return neg ? -ret : ret;
    }
}
