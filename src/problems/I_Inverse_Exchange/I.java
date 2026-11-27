package problems.I_Inverse_Exchange;

import java.io.*;
import java.util.*;

public class I {
    static int n, m;

    static List<int[]> build(int[][] pts) {
        List<int[]> res = new ArrayList<>();
        res.add(new int[]{pts[0][0], pts[0][1]});
        for (int i = 1; i < pts.length; i++) {
            int r = res.get(res.size() - 1)[0], c = res.get(res.size() - 1)[1];
            while (r != pts[i][0] || c != pts[i][1]) {
                if (r != pts[i][0]) r += Integer.signum(pts[i][0] - r);
                else c += Integer.signum(pts[i][1] - c);
                res.add(new int[]{r, c});
            }
        }
        return res;
    }

    static List<int[]> transform(List<int[]> path, boolean flipRow, boolean flipCol) {
        List<int[]> res = new ArrayList<>();
        for (int[] p : path) res.add(new int[]{flipRow ? n - 1 - p[0] : p[0], flipCol ? m - 1 - p[1] : p[1]});
        if (res.get(0)[0] > res.get(res.size() - 1)[0]) Collections.reverse(res);
        return res;
    }

    static String moves(List<int[]> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < path.size(); i++) {
            int dr = path.get(i)[0] - path.get(i - 1)[0], dc = path.get(i)[1] - path.get(i - 1)[1];
            sb.append(dr == 1 ? 'D' : dc == 1 ? 'R' : 'L');
        }
        return sb.toString();
    }

    static String op(List<int[]> a, List<int[]> b) {
        if (a.get(0)[1] == 0) return moves(a) + " " + moves(b);
        return moves(b) + " " + moves(a);
    }

    static void gadget(int r, int c, List<String> ops) {
        boolean fr = false, fc = false;
        List<int[]> P1, P2, Q;
        if (c >= 1 && c <= m - 2) {
            if (r > n - 2) { fr = true; r = n - 1 - r; }
            P1 = build(new int[][]{{0, 0}, {r, 0}, {r, c - 1}, {r, c}, {r + 1, c}, {n - 1, c}, {n - 1, m - 1}});
            P2 = build(new int[][]{{0, 0}, {r, 0}, {r, c - 1}, {r + 1, c - 1}, {r + 1, c}, {n - 1, c}, {n - 1, m - 1}});
            Q = build(new int[][]{{0, m - 1}, {r + 1, m - 1}, {r + 1, 0}, {n - 1, 0}});
        } else {
            if (c == m - 1) { fc = true; c = 0; }
            P1 = build(new int[][]{{0, 0}, {r - 1, 0}, {r - 1, c}, {r, c}, {r, c + 1}, {n - 1, c + 1}, {n - 1, m - 1}});
            P2 = build(new int[][]{{0, 0}, {r - 1, 0}, {r - 1, c}, {r - 1, c + 1}, {r, c + 1}, {n - 1, c + 1}, {n - 1, m - 1}});
            Q = build(new int[][]{{0, m - 1}, {0, c + 1}, {n - 1, c + 1}, {n - 1, 0}});
        }
        List<int[]> q = transform(Q, fr, fc);
        ops.add(op(transform(P1, fr, fc), q));
        ops.add(op(transform(P2, fr, fc), q));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int t = Integer.parseInt(br.readLine().trim());
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16));
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            int[][] T = new int[n][m];
            for (int i = 0; i < n; i++) {
                String s = br.readLine().trim();
                for (int j = 0; j < m; j++) T[i][j] = s.charAt(j) - '0';
            }
            List<String> ops = new ArrayList<>();
            int w = T[0][0] + T[0][m - 1] + T[n - 1][0] + T[n - 1][m - 1];
            if (w >= 3) {
                ops.add("R".repeat(m - 1) + "D".repeat(n - 1) + " " + "L".repeat(m - 1) + "D".repeat(n - 1));
                for (int j = 0; j < m; j++) T[0][j] ^= 1;
                for (int i = 1; i < n; i++) { T[i][0] ^= 1; T[i][m - 1] ^= 1; }
            }
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++) {
                    boolean corner = (i == 0 || i == n - 1) && (j == 0 || j == m - 1);
                    if (!corner && T[i][j] == 1) gadget(i, j, ops);
                }
            out.println(ops.size());
            for (String s : ops) out.println(s);
        }
        out.flush();
    }
}
