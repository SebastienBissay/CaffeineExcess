import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.Color;
import static parameters.Parameters.PALETTE;
import static processing.core.PApplet.constrain;

public class Quad {
    private static PApplet pApplet;

    private final PVector p1;
    private final PVector p2;
    private final PVector p3;
    private final PVector p4;
    private final int generation;

    public Quad(PVector p1, PVector p2, PVector p3, PVector p4, int generation) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.generation = generation;
    }

    public static void setPApplet(PApplet pApplet) {
        Quad.pApplet = pApplet;
    }

    public Quad[] divide() {
        float t = randomWeight();
        PVector m1 = PVector.add(PVector.mult(p1, t), PVector.mult(p2, 1 - t));
        t = randomWeight();
        PVector m2 = PVector.add(PVector.mult(p2, t), PVector.mult(p3, 1 - t));
        t = randomWeight();
        PVector m3 = PVector.add(PVector.mult(p3, t), PVector.mult(p4, 1 - t));
        t = randomWeight();
        PVector m4 = PVector.add(PVector.mult(p4, t), PVector.mult(p1, 1 - t));
        t = randomWeight();
        PVector m = PVector.add(PVector.mult(m1, t), PVector.mult(m3, 1 - t));

        Quad sub1 = new Quad(p1, m1, m, m4, generation + 1);
        Quad sub2 = new Quad(p2, m2, m, m1, generation + 1);
        Quad sub3 = new Quad(p3, m3, m, m2, generation + 1);
        Quad sub4 = new Quad(p4, m4, m, m3, generation + 1);

        return new Quad[]{sub1, sub2, sub3, sub4};
    }

    public void render() {
        Color color = PALETTE[generation % PALETTE.length];
        pApplet.fill(color.red(), color.green(), color.blue(), color.alpha());
        pApplet.stroke(color.red(), color.green(), color.blue(), color.alpha());
        pApplet.strokeWeight(1.5f);
        pApplet.quad(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);
    }

    private float randomWeight() {
        return constrain(.5f + 0.25f * pApplet.randomGaussian(), 0f, 1f);
    }
}
