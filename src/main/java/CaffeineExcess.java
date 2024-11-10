import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class CaffeineExcess extends PApplet {
    public static void main(String[] args) {
        PApplet.main(CaffeineExcess.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        noLoop();

        Quad.setPApplet(this);
    }

    @Override
    public void draw() {
        Quad[] quads = new Quad[1];
        quads[0] = new Quad(new PVector(MARGIN, MARGIN),
                new PVector(MARGIN, height - MARGIN),
                new PVector(width - MARGIN, height - MARGIN),
                new PVector(width - MARGIN, MARGIN),
                0);

        for (int k = 0; k < NUMBER_OF_RECURSIONS; k++) {
            if (random(1) < DIVISION_PROBABILITY) {
                // Divide Quad and append it
                quads = (Quad[]) concat(subset(quads, 1), quads[0].divide());
            } else {
                // Append Quad without dividing it
                quads = (Quad[]) append(subset(quads, 1), quads[0]);
            }
        }

        for (Quad q : quads) {
            q.render();
        }

        // Apply some gaussian noise
        loadPixels();
        for (int i = 0; i < pixels.length; i++) {
            int c = pixels[i];
            float r = red(c) + GAUSSIAN_NOISE_AMOUNT * randomGaussian();
            float g = green(c) + GAUSSIAN_NOISE_AMOUNT * randomGaussian();
            float b = blue(c) + GAUSSIAN_NOISE_AMOUNT * randomGaussian();
            pixels[i] = color(r, g, b);
        }
        updatePixels();


        saveSketch(this);
    }
}
