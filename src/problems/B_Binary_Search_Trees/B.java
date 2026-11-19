package problems.B_Binary_Search_Trees;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class B {
    static final long MOD = 998244353L;

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int t = nextInt(in);
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = nextInt(in);
            int[] deg = new int[n + 1];
            for (int i = 0; i < n - 1; i++) {
                deg[nextInt(in)]++;
                deg[nextInt(in)]++;
            }
            int leaves = 0;
            for (int i = 1; i <= n; i++) if (deg[i] == 1) leaves++;
            long[] pw = new long[n + 2];
            pw[0] = 1;
            for (int i = 1; i <= n + 1; i++) pw[i] = pw[i - 1] * 2 % MOD;
            long ans = 0;
            for (int r = 1; r <= n; r++) {
                if (deg[r] > 2) continue;
                int internal = n - leaves + (deg[r] == 1 ? 1 : 0);
                ans = (ans + pw[internal]) % MOD;
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