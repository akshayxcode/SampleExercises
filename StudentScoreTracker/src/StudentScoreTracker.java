import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentScoreTracker {
    Map<String, List<Integer>> studentScores = new HashMap<>();

    void addScore(String studentName, int score){
        studentScores.putIfAbsent(studentName, new ArrayList<>());
        studentScores.get(studentName).add(score);
    }
    double getAverageScore(String studentName) {
        List<Integer> scores = studentScores.get(studentName);
        if(scores == null) {
            return -1;
        }
        return  scores.stream().mapToInt(Integer::intValue).average().orElse(-1);
    }
    String getTopStudent() {
        String topStudent = null;
        double highestAverage = -1;
        for(Map.Entry<String, List<Integer>> entry : studentScores.entrySet()) {
            List<Integer> scores = entry.getValue();
            if (!scores.isEmpty()) {
                double average = getAverageScore(entry.getKey());

                if (average > highestAverage) {
                    highestAverage = average;
                    topStudent = entry.getKey();
                }
            }
        }

        return (topStudent != null) ? topStudent : "No students available";

    }

    public void printRanking() {
        studentScores.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty()) // Ignore students with no scores
                .sorted((a, b) -> Double.compare(getAverageScore(b.getKey()), getAverageScore(a.getKey())))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + getAverageScore(entry.getKey())));
    }

    public void printAllScores() {
        studentScores.forEach((student, scores) -> System.out.println(student + ": " + scores));
    }
}
