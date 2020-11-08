package JUnitTests;

import com.TweeterAnalytics.Terminal;
import com.TweeterAnalytics.User;
import com.TweeterAnalytics.graphOps.BetweennessCentrality_;
import com.TweeterAnalytics.graphOps.UserMetrics;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class userMetricsTests {

    @Test
    public void testUserMetrics1() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), 3.0 );
        distr1.put( users.get(3), 4.0 );
        distr1.put( users.get(4), 5.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 7.0 );

        distr2.put( users.get(0), 2.0 );
        distr2.put( users.get(1), 1.0 );
        distr2.put( users.get(2), 3.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 6.0 );
        distr2.put( users.get(5), 6.0 );
        distr2.put( users.get(6), 4.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.776, 0.001 );
    }

    @Test
    public void testUserMetrics2() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), 3.0 );
        distr1.put( users.get(3), 4.0 );
        distr1.put( users.get(4), 5.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 7.0 );

        distr2.put( users.get(0), 7.0 );
        distr2.put( users.get(1), 6.0 );
        distr2.put( users.get(2), 5.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 3.0 );
        distr2.put( users.get(5), 2.0 );
        distr2.put( users.get(6), 1.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, -1.00, 0.001 );
    }

    @Test
    public void testUserMetrics3() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), 3.0 );
        distr1.put( users.get(3), 4.0 );
        distr1.put( users.get(4), 5.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 7.0 );

        distr2.put( users.get(0), 7.0 );
        distr2.put( users.get(1), 8.0 );
        distr2.put( users.get(2), 9.0 );
        distr2.put( users.get(3), 10.0 );
        distr2.put( users.get(4), 11.0 );
        distr2.put( users.get(5), 12.0 );
        distr2.put( users.get(6), 13.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 1.0, 0.001 );
    }

    @Test
    public void testUserMetrics4() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), 3.0 );
        distr1.put( users.get(3), 4.0 );
        distr1.put( users.get(4), 5.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 7.0 );

        distr2.put( users.get(0), 1.0001 );
        distr2.put( users.get(1), 1.0 );
        distr2.put( users.get(2), 1.0 );
        distr2.put( users.get(3), 1.0 );
        distr2.put( users.get(4), 1.0 );
        distr2.put( users.get(5), 1.0 );
        distr2.put( users.get(6), 1.0001 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.000, 0.001 );
    }

    @Test
    public void testUserMetrics5() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), 3.0 );
        distr1.put( users.get(3), 4.0 );
        distr1.put( users.get(4), 5.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 7.0 );

        distr2.put( users.get(0), 3.0 );
        distr2.put( users.get(1), 6.0 );
        distr2.put( users.get(2), 4.0 );
        distr2.put( users.get(3), 0.0 );
        distr2.put( users.get(4), 7.0 );
        distr2.put( users.get(5), 10.0 );
        distr2.put( users.get(6), 1.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.110, 0.001 );
    }

    @Test
    public void testUserMetrics6() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), 3.0 );
        distr1.put( users.get(3), 4.0 );
        distr1.put( users.get(4), 5.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 7.0 );

        distr2.put( users.get(0), 1.0 );
        distr2.put( users.get(1), 3.0 );
        distr2.put( users.get(2), 2.0 );
        distr2.put( users.get(3), 5.0 );
        distr2.put( users.get(4), 4.0 );
        distr2.put( users.get(5), 6.0 );
        distr2.put( users.get(6), 5.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.858, 0.001 );
    }

    @Test
    public void testUserMetrics7() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 3.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), 5.0 );
        distr1.put( users.get(3), 1.5 );
        distr1.put( users.get(4), 7.0 );
        distr1.put( users.get(5), 0.0 );
        distr1.put( users.get(6), 1.0 );

        distr2.put( users.get(0), 2.0 );
        distr2.put( users.get(1), 1.0 );
        distr2.put( users.get(2), 3.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 6.0 );
        distr2.put( users.get(5), 6.0 );
        distr2.put( users.get(6), 4.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.093, 0.001 );
    }

    @Test
    public void testUserMetrics8() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 5.0 );
        distr1.put( users.get(2), -2.0 );
        distr1.put( users.get(3), 0.0 );
        distr1.put( users.get(4), 6.5 );
        distr1.put( users.get(5), 3.2 );
        distr1.put( users.get(6), 2.0 );

        distr2.put( users.get(0), 2.0 );
        distr2.put( users.get(1), 1.0 );
        distr2.put( users.get(2), 7.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 3.0 );
        distr2.put( users.get(5), -1.0 );
        distr2.put( users.get(6), 0.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, -0.563, 0.001 );
    }

    @Test
    public void testUserMetrics9() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 0.0 );
        distr1.put( users.get(1), 0.0 );
        distr1.put( users.get(2), 5.0 );
        distr1.put( users.get(3), 3.0 );
        distr1.put( users.get(4), 2.0 );
        distr1.put( users.get(5), 7.0 );
        distr1.put( users.get(6), 2.0 );

        distr2.put( users.get(0), 2.0 );
        distr2.put( users.get(1), 1.0 );
        distr2.put( users.get(2), 8.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 8.0 );
        distr2.put( users.get(5), 5.0 );
        distr2.put( users.get(6), 9.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.425, 0.001 );
    }

    @Test
    public void testUserMetrics10() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 7.0 );
        distr1.put( users.get(1), 0.0 );
        distr1.put( users.get(2), 6.0 );
        distr1.put( users.get(3), 2.0 );
        distr1.put( users.get(4), 3.0 );
        distr1.put( users.get(5), 9.0 );
        distr1.put( users.get(6), 5.0 );

        distr2.put( users.get(0), 2.0 );
        distr2.put( users.get(1), 1.0 );
        distr2.put( users.get(2), 3.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 6.0 );
        distr2.put( users.get(5), 8.0 );
        distr2.put( users.get(6), 4.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.497, 0.001 );
    }

    @Test
    public void testUserMetrics11() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 7.0 );
        distr1.put( users.get(1), 3.0 );
        distr1.put( users.get(2), 6.0 );
        distr1.put( users.get(3), 9.0 );
        distr1.put( users.get(4), 2.0 );
        distr1.put( users.get(5), 4.0 );
        distr1.put( users.get(6), 6.0 );

        distr2.put( users.get(0), 9.0 );
        distr2.put( users.get(1), 7.0 );
        distr2.put( users.get(2), 3.0 );
        distr2.put( users.get(3), 5.0 );
        distr2.put( users.get(4), 6.0 );
        distr2.put( users.get(5), 7.0 );
        distr2.put( users.get(6), 2.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, -0.200, 0.001 );
    }

    @Test
    public void testUserMetrics12() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 4.0 );
        distr1.put( users.get(1), 3.0 );
        distr1.put( users.get(2), 2.0 );
        distr1.put( users.get(3), 5.0 );
        distr1.put( users.get(4), 6.0 );
        distr1.put( users.get(5), 2.0 );
        distr1.put( users.get(6), 1.0 );

        distr2.put( users.get(0), 10.0 );
        distr2.put( users.get(1), 12.0 );
        distr2.put( users.get(2), 13.0 );
        distr2.put( users.get(3), 5.0 );
        distr2.put( users.get(4), 9.0 );
        distr2.put( users.get(5), 10.0 );
        distr2.put( users.get(6), 10.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, -0.572, 0.001 );
    }

    @Test
    public void testUserMetrics13() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 7.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), -6.0 );
        distr1.put( users.get(3), -10.0 );
        distr1.put( users.get(4), -20.0 );
        distr1.put( users.get(5), -25.0 );
        distr1.put( users.get(6), -26.0 );

        distr2.put( users.get(0), 100.0 );
        distr2.put( users.get(1), 90.0 );
        distr2.put( users.get(2), 80.0 );
        distr2.put( users.get(3), 70.0 );
        distr2.put( users.get(4), 60.0 );
        distr2.put( users.get(5), 50.0 );
        distr2.put( users.get(6), 40.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.988, 0.001 );
    }

    @Test
    public void testUserMetrics14() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 4.0 );
        distr1.put( users.get(1), 3.0 );
        distr1.put( users.get(2), 5.0 );
        distr1.put( users.get(3), 9.0 );
        distr1.put( users.get(4), -7.0 );
        distr1.put( users.get(5), -3.0 );
        distr1.put( users.get(6), 2.0 );

        distr2.put( users.get(0), 5.0 );
        distr2.put( users.get(1), -7.0 );
        distr2.put( users.get(2), 6.0 );
        distr2.put( users.get(3), 2.0 );
        distr2.put( users.get(4), 0.0 );
        distr2.put( users.get(5), -6.0 );
        distr2.put( users.get(6), 8.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.356, 0.001 );
    }

    @Test
    public void testUserMetrics15() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 5.0 );
        distr1.put( users.get(1), 6.0 );
        distr1.put( users.get(2), 7.0 );
        distr1.put( users.get(3), 6.0 );
        distr1.put( users.get(4), 1.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 0.0 );

        distr2.put( users.get(0), 5.0 );
        distr2.put( users.get(1), 0.0 );
        distr2.put( users.get(2), 3.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 8.0 );
        distr2.put( users.get(5), 6.0 );
        distr2.put( users.get(6), 4.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, -0.456, 0.001 );
    }

    @Test
    public void testUserMetrics16() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 5.0 );
        distr1.put( users.get(1), 3.0 );
        distr1.put( users.get(2), 1.0 );
        distr1.put( users.get(3), 5.0 );
        distr1.put( users.get(4), 8.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 7.0 );

        distr2.put( users.get(0), 7.0 );
        distr2.put( users.get(1), 0.0 );
        distr2.put( users.get(2), 0.0 );
        distr2.put( users.get(3), 6.0 );
        distr2.put( users.get(4), 4.0 );
        distr2.put( users.get(5), 9.0 );
        distr2.put( users.get(6), 2.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.498, 0.001 );
    }

    @Test
    public void testUserMetrics17() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 1.0 );
        distr1.put( users.get(1), 2.0 );
        distr1.put( users.get(2), -6.0 );
        distr1.put( users.get(3), 7.0 );
        distr1.put( users.get(4), -5.0 );
        distr1.put( users.get(5), 5.0 );
        distr1.put( users.get(6), -7.0 );

        distr2.put( users.get(0), 7.0 );
        distr2.put( users.get(1), 6.0 );
        distr2.put( users.get(2), 3.0 );
        distr2.put( users.get(3), 4.0 );
        distr2.put( users.get(4), 6.0 );
        distr2.put( users.get(5), 9.0 );
        distr2.put( users.get(6), 7.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.194, 0.001 );
    }

    @Test
    public void testUserMetrics18() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 8.0 );
        distr1.put( users.get(1), 6.0 );
        distr1.put( users.get(2), 4.0 );
        distr1.put( users.get(3), 6.0 );
        distr1.put( users.get(4), 8.0 );
        distr1.put( users.get(5), 7.0 );
        distr1.put( users.get(6), 3.0 );

        distr2.put( users.get(0), 4.0 );
        distr2.put( users.get(1), 1.0 );
        distr2.put( users.get(2), 8.0 );
        distr2.put( users.get(3), 3.0 );
        distr2.put( users.get(4), 8.0 );
        distr2.put( users.get(5), 2.0 );
        distr2.put( users.get(6), 6.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, -0.247, 0.001 );
    }

    @Test
    public void testUserMetrics19() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), -8.0 );
        distr1.put( users.get(1), -2.0 );
        distr1.put( users.get(2), -6.0 );
        distr1.put( users.get(3), -7.0 );
        distr1.put( users.get(4), 5.0 );
        distr1.put( users.get(5), 9.0 );
        distr1.put( users.get(6), 12.0 );

        distr2.put( users.get(0), 21.0 );
        distr2.put( users.get(1), -1.0 );
        distr2.put( users.get(2), -5.0 );
        distr2.put( users.get(3), 6.0 );
        distr2.put( users.get(4), 7.0 );
        distr2.put( users.get(5), -6.0 );
        distr2.put( users.get(6), 4.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, -0.355, 0.001 );
    }

    @Test
    public void testUserMetrics20() {
        Map<User, Double> distr1 = new HashMap<>();
        Map<User, Double> distr2= new HashMap<>();

        List<User> users = new ArrayList<>();

        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));
        users.add(new User(new String[10]));

        UserMetrics um = new UserMetrics(users);

        distr1.put( users.get(0), 9.0 );
        distr1.put( users.get(1), 3.0 );
        distr1.put( users.get(2), 5.0 );
        distr1.put( users.get(3), 9.0 );
        distr1.put( users.get(4), 2.0 );
        distr1.put( users.get(5), 6.0 );
        distr1.put( users.get(6), 0.0 );

        distr2.put( users.get(0), 7.0 );
        distr2.put( users.get(1), 12.0 );
        distr2.put( users.get(2), -4.0 );
        distr2.put( users.get(3), 9.0 );
        distr2.put( users.get(4), -7.0 );
        distr2.put( users.get(5), 6.0 );
        distr2.put( users.get(6), 7.0 );

        double correlation = um.getCorrelation(distr1, distr2);

        assertEquals( correlation, 0.251, 0.001 );
    }

}