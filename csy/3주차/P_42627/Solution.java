import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/** 프로그래머스 ID: 42627, 디스크 컨트롤러 */
public class Solution {
    private int current = 0;

    class Job implements Comparable<Job>{
        private int request;
        private int execution;

        public Job(int[] job){
            this.request = job[0];
            this.execution = job[1];
        }

        public int getRequest() {
            return request;
        }

        public int getExecution() {
            return execution;
        }

        @Override
        public int compareTo(Job o) {
            return Integer.compare(this.execution, o.execution);
        }
    }

    public int getMinAverage(List<Job> jobs) {
        int idx = 0;
        int sum = 0;
        PriorityQueue<Job> queue = new PriorityQueue<>();
        while (idx < jobs.size() || queue.size() > 0) {
            if ((idx < jobs.size() && jobs.get(idx).getRequest() <= current) || queue.size() == 0) {
                queue.add(jobs.get(idx++));
                continue;
            }
            Job job = queue.poll();
            current = Math.max(current, job.getRequest()) + job.getExecution();
            sum += (current - job.getRequest());
        }
        return sum / jobs.size();
    }

    public int solution(int[][] jobs) {
        return getMinAverage(Arrays.stream(jobs)
                .sorted(Comparator.comparingInt((int[] x) -> x[0])
                        .thenComparingInt(x -> x[1]))
                .map(Job::new).collect(Collectors.toList()));
    }
}
