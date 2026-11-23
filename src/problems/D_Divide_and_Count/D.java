package problems.D_Divide_and_Count;

import java.io.*;
import java.util.*;

public class D {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long x1 = Long.parseLong(st.nextToken()), y1 = Long.parseLong(st.nextToken()), r1 = Long.parseLong(st.nextToken());
            long x2 = Long.parseLong(st.nextToken()), y2 = Long.parseLong(st.nextToken()), r2 = Long.parseLong(st.nextToken());
            long d2 = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
            int ans;
            if (d2 == 0 && r1 == r2) ans = 2;
            else if ((r1 - r2) * (r1 - r2) < d2 && d2 < (r1 + r2) * (r1 + r2)) ans = 4;
            else ans = 3;
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }
}
