public class Main {
    public static void main(String[] args) {
        StudentScoreTracker scoreTracker = new StudentScoreTracker();
        scoreTracker.addScore("Akshay",15);
        scoreTracker.addScore("Akshay",13);
        scoreTracker.addScore("Akshay",15);
        scoreTracker.addScore("Suresh",13);
        scoreTracker.addScore("Suresh",13);
        scoreTracker.addScore("Suresh",19);
        scoreTracker.addScore("Ramesh",18);

        scoreTracker.printAllScores();
        double avgScore = scoreTracker.getAverageScore("Akshay");
        System.out.println("Average Score: " + avgScore);

        scoreTracker.printRanking();

        String topStudent = scoreTracker.getTopStudent();
        System.out.println("Top student is : " + topStudent);

    }
}