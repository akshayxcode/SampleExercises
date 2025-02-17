import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class StudentScoreTrackerTest {
    StudentScoreTracker scoreTracker;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        scoreTracker = new StudentScoreTracker();
        System.setOut(new PrintStream(outputStream));
    }
   @Test
    public void testAddScore() {
       scoreTracker.addScore("Akshay",15);
       scoreTracker.addScore("Akshay",13);
       scoreTracker.addScore("Adam",13);
       assertEquals(2,scoreTracker.studentScores.size());
   }

    @Test
    void testGetAverageScore_ValidStudent() {
        scoreTracker.addScore("Alice", 80);
        scoreTracker.addScore("Alice", 90);
        scoreTracker.addScore("Alice", 70);

        assertEquals(80.0, scoreTracker.getAverageScore("Alice"));
    }

    @Test
    void testGetAverageScore_SingleScore() {
        scoreTracker.addScore("Bob", 95);
        assertEquals(95.0, scoreTracker.getAverageScore("Bob"));
    }

    @Test
    void testGetAverageScore_NoScores() {
        scoreTracker.studentScores.put("Charlie", new ArrayList<>());
        assertEquals(-1, scoreTracker.getAverageScore("Charlie"));
    }
    @Test
    void testGetTopStudent_SingleStudent() {
        scoreTracker.addScore("Alice", 90);
        scoreTracker.addScore("Alice", 95);
        assertEquals("Alice", scoreTracker.getTopStudent());
    }

    @Test
    void testGetTopStudent_MultipleStudents() {
        scoreTracker.addScore("Alice", 90);
        scoreTracker.addScore("Alice", 95);
        scoreTracker.addScore("Bob", 85);
        scoreTracker.addScore("Bob", 92);
        scoreTracker.addScore("Charlie", 98);

        assertEquals("Charlie", scoreTracker.getTopStudent());
    }
    @Test
    void testPrintRanking_SingleScore() {
        scoreTracker.addScore("Suresh",13);
        scoreTracker.addScore("Suresh",19);
        scoreTracker.addScore("Ramesh",18);
        scoreTracker.printRanking();
        String expectedOutput = """
                Ramesh: 18.0
                Suresh: 16.0
                """;
        assertEquals(expectedOutput, outputStream.toString());
    }





}