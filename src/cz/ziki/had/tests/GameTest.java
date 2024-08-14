package cz.ziki.had.tests;

import cz.ziki.had.ColisionChecker;
import cz.ziki.had.Rect;
import org.junit.jupiter.api.Test;


import java.awt.geom.Point2D;

public class GameTest {

    @Test
    public void colisionCheckerTest() {
        Point2D pointRight = new Point2D.Double(100,100);
        Point2D pointWrong = new Point2D.Double(5,5);
        Point2D pointWrong1 = new Point2D.Double(20,60);
        Point2D pointWrong2 = new Point2D.Double(60,20);
        Point2D pointWrong3 = new Point2D.Double(60,130);
        Point2D pointWrong4 = new Point2D.Double(130,60);

        Rect rect = new Rect(40,40,80,80);
        assert ColisionChecker.intersect(pointRight,rect);
        assert !ColisionChecker.intersect(pointWrong,rect);
        assert !ColisionChecker.intersect(pointWrong1,rect);
        assert !ColisionChecker.intersect(pointWrong2,rect);
        assert !ColisionChecker.intersect(pointWrong3,rect);
        assert !ColisionChecker.intersect(pointWrong4,rect);

    }
}
