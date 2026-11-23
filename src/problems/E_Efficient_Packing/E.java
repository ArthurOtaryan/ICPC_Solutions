package problems.E_Efficient_Packing;

import java.io.*;
import java.util.*;

public class E {
    static int n, np;
    static int[] pa, pb;
    static long[][] dims;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] C = {Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())};
        n = Integer.parseInt(br.readLine().trim());
        dims = new long[n][3];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) dims[i][j] = Long.parseLong(st.nextToken());
        }
        np = n * (n - 1) / 2;
        pa = new int[np];
        pb = new int[np];
        int q = 0;
        for (int i = 0; i < n; i++) for (int j = i + 1; j < n; j++) { pa[q] = i; pb[q] = j; q++; }

        List<int[]> perms = new ArrayList<>();
        permute(new int[n], new boolean[n], 0, perms);

        int choices = 1;
        for (int i = 0; i < n; i++) choices *= 3;
        int masks = 1 << np;
        long[][] T = new long[choices][masks];
        int[][] bestPerm = new int[choices][masks];
        long[] sz = new long[n];
        long[] pos = new long[n];
        for (int ch = 0; ch < choices; ch++) {
            int tmp = ch;
            for (int i = 0; i < n; i++) { sz[i] = dims[i][tmp % 3]; tmp /= 3; }
            for (int mask = 0; mask < masks; mask++) {
                long best = Long.MAX_VALUE;
                int bp = 0;
                for (int pi = 0; pi < perms.size(); pi++) {
                    long len = longest(perms.get(pi), mask, sz, pos);
                    if (len < best) { best = len; bp = pi; }
                }
                T[ch][mask] = best;
                bestPerm[ch][mask] = bp;
            }
        }

        int[][] orient = {{0, 1, 2}, {0, 2, 1}, {1, 0, 2}, {1, 2, 0}, {2, 0, 1}, {2, 1, 0}};
        int totalOr = 1;
        for (int i = 0; i < n; i++) totalOr *= 6;
        int totalAs = 1;
        for (int i = 0; i < np; i++) totalAs *= 3;
        int[] chAx = new int[3];
        int[] ori = new int[n];
        for (int o = 0; o < totalOr; o++) {
            int tmp = o;
            for (int i = 0; i < n; i++) { ori[i] = tmp % 6; tmp /= 6; }
            for (int ax = 0; ax < 3; ax++) {
                int ch = 0;
                for (int i = n - 1; i >= 0; i--) ch = ch * 3 + orient[ori[i]][ax];
                chAx[ax] = ch;
            }
            for (int as = 0; as < totalAs; as++) {
                int[] mk = new int[3];
                int a2 = as;
                for (int p = 0; p < np; p++) { mk[a2 % 3] |= 1 << p; a2 /= 3; }
                if (T[chAx[0]][mk[0]] <= C[0] && T[chAx[1]][mk[1]] <= C[1] && T[chAx[2]][mk[2]] <= C[2]) {
                    long[][] lo = new long[n][3], hi = new long[n][3];
                    for (int ax = 0; ax < 3; ax++) {
                        int tmp2 = chAx[ax];
                        for (int i = 0; i < n; i++) { sz[i] = dims[i][tmp2 % 3]; tmp2 /= 3; }
                        longest(perms.get(bestPerm[chAx[ax]][mk[ax]]), mk[ax], sz, pos);
                        for (int i = 0; i < n; i++) { lo[i][ax] = pos[i]; hi[i][ax] = pos[i] + sz[i]; }
                    }
                    StringBuilder sb = new StringBuilder("YES\n");
                    for (int i = 0; i < n; i++)
                        sb.append(lo[i][0]).append(' ').append(lo[i][1]).append(' ').append(lo[i][2]).append(' ')
                          .append(hi[i][0]).append(' ').append(hi[i][1]).append(' ').append(hi[i][2]).append('\n');
                    System.out.print(sb);
                    return;
                }
            }
        }
        System.out.println("NO");
    }

    static long longest(int[] perm, int mask, long[] sz, long[] pos) {
        long mx = 0;
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) rank[perm[i]] = i;
        for (int i = 0; i < n; i++) {
            int v = perm[i];
            long p = 0;
            for (int e = 0; e < np; e++) {
                if ((mask >> e & 1) == 0) continue;
                int u;
                if (pa[e] == v) u = pb[e];
                else if (pb[e] == v) u = pa[e];
                else continue;
                if (rank[u] < i) p = Math.max(p, pos[u] + sz[u]);
            }
            pos[v] = p;
            mx = Math.max(mx, p + sz[v]);
        }
        return mx;
    }

    static void permute(int[] cur, boolean[] used, int k, List<int[]> out) {
        if (k == cur.length) { out.add(cur.clone()); return; }
        for (int i = 0; i < cur.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            cur[k] = i;
            permute(cur, used, k + 1, out);
            used[i] = false;
        }
    }
}
