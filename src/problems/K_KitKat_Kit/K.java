package problems.K_KitKat_Kit;

import java.io.*;
import java.util.*;

public class K {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int t = nextInt(in);
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = nextInt(in), m = nextInt(in);
            long K = 1;
            for (int i = 0; i < m; i++) {
                int a = nextInt(in), b = nextInt(in), c = nextInt(in);
                K = Math.max(K, (long) b - a + c - n + 1);
            }
            for (int i = 1; i <= n; i++) {
                sb.append(n - i + K);
                sb.append(i == n ? '\n' : ' ');
            }
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
