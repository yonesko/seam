import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        notNull(picture);
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        checkBounds(x, y);
        if (isBorderX(x) || isBorderY(y)) {
            return 1000;
        }
        int[] colorXDiff = colorXDiff(x, y);
        int[] colorYDiff = colorYDiff(x, y);
        double xgrad = colorXDiff[0] * colorXDiff[0] + colorXDiff[1] * colorXDiff[1] + colorXDiff[2] * colorXDiff[2];
        double ygrad = colorYDiff[0] * colorYDiff[0] + colorYDiff[1] * colorYDiff[1] + colorYDiff[2] * colorYDiff[2];
        return Math.sqrt(xgrad * xgrad + ygrad * ygrad);
    }

    private int[] colorXDiff(int x, int y) {
        Color colorNext = new Color(picture.getRGB(x + 1, y));
        Color colorPrev = new Color(picture.getRGB(x - 1, y));
        return new int[]{
                colorNext.getRed() - colorPrev.getRed(),
                colorNext.getGreen() - colorPrev.getGreen(),
                colorNext.getBlue() - colorPrev.getBlue()
        };
    }

    private int[] colorYDiff(int x, int y) {
        Color colorNext = new Color(picture.getRGB(x, y + 1));
        Color colorPrev = new Color(picture.getRGB(x, y - 1));
        return new int[]{
                colorNext.getRed() - colorPrev.getRed(),
                colorNext.getGreen() - colorPrev.getGreen(),
                colorNext.getBlue() - colorPrev.getBlue()
        };
    }

    private boolean isBorderX(int x) {
        return x == 0 || x == picture.width() - 1;
    }

    private boolean isBorderY(int y) {
        return y == 0 || y == picture.height() - 1;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        throw new UnsupportedOperationException();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        throw new UnsupportedOperationException();
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        notNull(seam);
        if (height() <= 1) {
            throw new IllegalArgumentException();
        }
        checkHorizontalSeam(seam);
        throw new UnsupportedOperationException();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        notNull(seam);
        if (width() <= 1) {
            throw new IllegalArgumentException();
        }
        checkVerticalSeam(seam);
        throw new UnsupportedOperationException();
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

    private void checkVerticalSeam(int[] seam) {
        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            int x1 = seam[i];
            int x2 = seam[i + 1];
            checkX(x1);
            if (Math.abs(x1 - x2) > 1) {
                throw new IllegalArgumentException();
            }
        }
        checkX(seam[seam.length - 1]);
    }

    private void checkHorizontalSeam(int[] seam) {
        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            int y1 = seam[i];
            int y2 = seam[i + 1];
            checkY(y1);
            if (Math.abs(y1 - y2) > 1) {
                throw new IllegalArgumentException();
            }
        }
        checkY(seam[seam.length - 1]);
    }

    private void notNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkBounds(int x, int y) {
        checkX(x);
        checkY(y);
    }

    private void checkY(int y) {
        if (y < 0 || y >= height()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkX(int x) {
        if (x < 0 || x >= width()) {
            throw new IllegalArgumentException();
        }
    }

}