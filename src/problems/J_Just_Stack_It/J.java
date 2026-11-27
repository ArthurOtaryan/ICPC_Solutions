package problems.J_Just_Stack_It;

import java.io.*;
import java.util.*;

public class J {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken());
            int[] a = new int[n];
            st = new StringTokenizer(br.readLine());
            int[] cnt = new int[n + 1];
            int mx = 0;
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
                mx = Math.max(mx, ++cnt[a[i]]);
            }
            Arrays.sort(a);
            int r = Math.max(mx, (n + k - 1) / k);
            List<List<Integer>> rods = new ArrayList<>();
            for (int i = 0; i < r; i++) rods.add(new ArrayList<>());
            for (int i = 0; i < n; i++) rods.get(i % r).add(a[i]);
            sb.append(r).append('\n');
            for (List<Integer> rod : rods) {
                sb.append(rod.size());
                for (int x : rod) sb.append(' ').append(x);
                sb.append('\n');
            }
        }
        System.out.print(sb);
    }
}
