package jwenger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static class BFS {
        boolean[] boolArr;
        final Map<Integer, Integer> map;
        Map<Integer, Integer> parent;
        ArrayDeque<Integer> queue;
        public BFS (Map<Integer, Integer> map) {
            this.map=map;
            this.parent=new HashMap<>();
            this.queue=new ArrayDeque<>();
            this.boolArr=new boolean[101];
        }
        public List<Integer> run() {
            repeat();
            List<Integer> list = new ArrayList<>();
            int current = 100;
            list.add(current);
            while (current!=0) {
                current=this.parent.get(current);
                list.add(0, current);
            }
            return list;
        }
        private void repeat() {
            queue.add(0);
            this.boolArr[0]=true;
            while (!queue.isEmpty()) {
                int num = queue.remove();
                if (num==100) {
                    return;
                }
                List<Integer> adjacentNums = Arrays.asList(num+1, num+2, num+3, num+4, num+5, num+6);
                for (int i : adjacentNums) {
                    if (i<=100&&!boolArr[i]) {
                        boolArr[i]=true;
                        if (map.get(i)!=null) {
                            int j =map.get(i);
                            if (!boolArr[j]) {
                                this.parent.put(j, i);
                                queue.add(j);
                                boolArr[j]=true;
                            }
                        } else {
                            queue.add(i);
                        }
                        this.parent.put(i, num);
                    }
                }
             }
        }
    } 
    public static void main (String[] args) {
        boolean[] hasChuteOrLadder = new boolean[101];
        Map<Integer, Integer> chuteLadder = new HashMap<>();
        System.out.println("Welcome to Chutes and Ladders! Now is the time for you to create your own board.\nThe board goes from 1-100.");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Would you like to add another chute or a ladder? Y for yes, N for no:");
            String s = scanner.nextLine();
            if (!s.equals("n")&&!s.equals("N")&&!s.equals("Y")&&!s.equals("y")) {
                System.out.println("You did not type Y or N.");
                continue;
            }
            else if (s.equals("Y")||s.equals("y")) {
                int start = -1;
                while (start<1||start>99) {
                    System.out.print("What is the start index for the chute/ladder?");
                    start = scanner.nextInt();
                    if (start<1||start>99) {
                        System.out.println("Invalid start index.");
                    }
                }
                int end = 102;
                while (end<1||end>100) {
                    System.out.print("What is the end index for the chute/ladder?");
                    end = scanner.nextInt();
                    if (end<1||end>100) {
                        System.out.println("Invalid end index.");
                    }
                }
                if (start==end) {
                    System.out.println("You can't use the same start and end indices for a chute or ladder.");
                    continue;
                }
                else if (hasChuteOrLadder[start]) {
                    System.out.println("You already have a chute or ladder at the start index.");
                    continue;
                }
                else if (hasChuteOrLadder[end]) {
                    System.out.println("You already have a chute or ladder at the end index.");
                    continue;
                }
                chuteLadder.put(start, end);
                hasChuteOrLadder[start]=true;
                hasChuteOrLadder[end]=true;
            }
            else if (s.equals("n")||s.equals("N")) {
                break;
            }
        }
        BFS bfs = new BFS(chuteLadder);
        List<Integer> solution = bfs.run();
        int totalMoves=0;
        for (int i=0; i<solution.size()-1; i++) {
            if (chuteLadder.get(solution.get(i))!=null) {
                if (solution.get(i)<chuteLadder.get(solution.get(i))) {
                    System.out.println("Climb from " + solution.get(i) + " to " + chuteLadder.get(solution.get(i)));
                } else {
                    System.out.println("Slide from " + solution.get(i) + " to " + chuteLadder.get(solution.get(i)));
                }
            } else {
                totalMoves++;
                System.out.println("Go " + (solution.get(i+1)-solution.get(i)) + " steps from " + solution.get(i) + " to " + solution.get(i+1));
            }
        }
        System.out.println("Requires a total of " + totalMoves + " moves");
    }
}
