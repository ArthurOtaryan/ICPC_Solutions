package problems.C_Cable_Network;

import java.io.*;
import java.util.*;

public class C {
    static long[] xs, ys;

    static int half(long dx, long dy) { return (dy > 0 || (dy == 0 && dx > 0)) ? 0 : 1; }

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int t = nextInt(in);
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = nextInt(in);
            xs = new long[n];
            ys = new long[n];
            for (int i = 0; i < n; i++) { xs[i] = nextInt(in); ys[i] = nextInt(in); }
            solve(n, sb);
        }
        System.out.print(sb);
    }

    static void solve(int n, StringBuilder sb) {
        boolean col = true;
        for (int i = 2; i < n && col; i++) {
            long cr = (xs[1] - xs[0]) * (ys[i] - ys[0]) - (ys[1] - ys[0]) * (xs[i] - xs[0]);
            if (cr != 0) col = false;
        }
        if (col) {
            if (n > 5) { sb.append("NO\n"); return; }
            Integer[] id = new Integer[n];
            for (int i = 0; i < n; i++) id[i] = i;
            Arrays.sort(id, (a, b) -> xs[a] != xs[b] ? Long.compare(xs[a], xs[b]) : Long.compare(ys[a], ys[b]));
            sb.append("YES\n");
            for (int i = 0; i + 1 < n; i++) sb.append(id[i] + 1).append(' ').append(id[i + 1] + 1).append('\n');
            return;
        }
        int c = 0;
        for (int i = 1; i < n; i++)
            if (ys[i] < ys[c] || (ys[i] == ys[c] && xs[i] < xs[c])) c = i;
        int X = -1;
        for (int i = 0; i < n; i++) {
            if (i == c) continue;
            if (X == -1) { X = i; continue; }
            long ax = xs[i] - xs[c], ay = ys[i] - ys[c], bx = xs[X] - xs[c], by = ys[X] - ys[c];
            long cr = bx * ay - by * ax;
            if (cr < 0 || (cr == 0 && Math.abs(ax) + Math.abs(ay) < Math.abs(bx) + Math.abs(by))) X = i;
        }
        final int cx = X;
        Integer[] id = new Integer[n - 1];
        int k = 0;
        for (int i = 0; i < n; i++) if (i != cx) id[k++] = i;
        Arrays.sort(id, (a, b) -> {
            long ax = xs[a] - xs[cx], ay = ys[a] - ys[cx], bx = xs[b] - xs[cx], by = ys[b] - ys[cx];
            int ha = half(ax, ay), hb = half(bx, by);
            if (ha != hb) return ha - hb;
            long cr = ax * by - ay * bx;
            if (cr != 0) return cr > 0 ? -1 : 1;
            return Long.compare(Math.abs(ax) + Math.abs(ay), Math.abs(bx) + Math.abs(by));
        });
        List<List<Integer>> rays = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            int p = id[i];
            if (i > 0) {
                int q = id[i - 1];
                long ax = xs[q] - xs[cx], ay = ys[q] - ys[cx], bx = xs[p] - xs[cx], by = ys[p] - ys[cx];
                if (ax * by - ay * bx == 0 && ax * bx + ay * by > 0) { rays.get(rays.size() - 1).add(p); continue; }
            }
            List<Integer> r = new ArrayList<>();
            r.add(p);
            rays.add(r);
        }
        int R = rays.size();
        int s = -1;
        for (int i = 0; i < R; i++) if (rays.get(i).get(0) == c) s = i;
        int bad = -1;
        for (int i = 0; i < R; i++) {
            int p = rays.get(i).get(0), q = rays.get((i + 1) % R).get(0);
            long ax = xs[p] - xs[cx], ay = ys[p] - ys[cx], bx = xs[q] - xs[cx], by = ys[q] - ys[cx];
            if (ax * by - ay * bx <= 0) bad = i;
        }
        int start = bad >= 0 ? (bad + 1) % R : s;
        int[] lin = new int[R];
        int ps = -1;
        for (int i = 0; i < R; i++) {
            lin[i] = (start + i) % R;
            if (lin[i] == s) ps = i;
        }
        sb.append("YES\n");
        for (int i = 0; i < R; i++) {
            List<Integer> ray = rays.get(lin[i]);
            sb.append(cx + 1).append(' ').append(ray.get(0) + 1).append('\n');
            if (ray.size() == 1) continue;
            int target = i < ps ? rays.get(lin[i + 1]).get(0) : rays.get(lin[i - 1]).get(0);
            for (int j = 1; j < ray.size(); j++) sb.append(ray.get(j) + 1).append(' ').append(target + 1).append('\n');
        }
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
