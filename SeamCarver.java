import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Point2D;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SeamCarver {

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        notNull(picture);
        this.picture = new Picture(picture);
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
        return Math.sqrt(xgrad + ygrad);
    }

    private int[] colorXDiff(int x, int y) {
        int rgbNext = picture.getRGB(x + 1, y);
        int rgbPrev = picture.getRGB(x - 1, y);
        return new int[]{
                getRed(rgbNext) - getRed(rgbPrev),
                getGreen(rgbNext) - getGreen(rgbPrev),
                getBlue(rgbNext) - getBlue(rgbPrev)
        };
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    private int[] colorYDiff(int x, int y) {
        int rgbNext = picture.getRGB(x, y + 1);
        int rgbPrev = picture.getRGB(x, y - 1);
        return new int[]{
                getRed(rgbNext) - getRed(rgbPrev),
                getGreen(rgbNext) - getGreen(rgbPrev),
                getBlue(rgbNext) - getBlue(rgbPrev)
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
        return new SeamCarver(transpose(picture)).findVerticalSeam();
    }

    private Picture transpose(Picture picture) {
        Picture ans = new Picture(picture.height(), picture.width());

        for (int col = 0; col < this.width(); ++col) {
            for (int row = 0; row < this.height(); ++row) {
                ans.setRGB(row, col, picture.getRGB(col, row));
            }
        }
        return ans;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] distTo = new double[width()][height()];
        double[][] energyField = energyField();
        Point2D[][] path = new Point2D[width()][height()];
        for (double[] arr : distTo) {
            Arrays.fill(arr, Double.MAX_VALUE);
        }
        for (int x = 0; x < width(); x++) {
            distTo[x][0] = energyField[x][0];
        }

        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width(); x++) {
                for (int xAdj : findVerticalAdj(x, y)) {
                    int yAdj = y + 1;
                    if (energyField[xAdj][yAdj] + distTo[x][y] < distTo[xAdj][yAdj]) {
                        distTo[xAdj][yAdj] = energyField[xAdj][yAdj] + distTo[x][y];
                        path[xAdj][yAdj] = new Point2D(x, y);
                    }
                }
            }
        }

        double minSum = Double.MAX_VALUE;
        int minSumIndex = -1;
        for (int x = 0; x < width(); x++) {
            if (distTo[x][height() - 1] < minSum) {
                minSum = distTo[x][height() - 1];
                minSumIndex = x;
            }
        }

        if (minSumIndex == -1) {
            return new int[]{};
        }

        int[] ans = new int[height()];

        for (Point2D current = new Point2D(minSumIndex, height() - 1); current.y() > 0; current = path[(int) current.x()][(int) current.y()]) {
            ans[(int) current.y()] = (int) current.x();
        }
        ans[0] = ans[1];

        return ans;
    }

    private void print(Point2D[][] path) {
        System.out.println("path");
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                System.out.print(path[x][y] + " ");
            }
            System.out.println();
        }
    }

    private void print(double[][] arr) {
        System.out.println("distTo");
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                System.out.printf("%7.2f ", arr[x][y] == Double.MAX_VALUE ? 777 : arr[x][y]);
            }
            System.out.println();
        }
    }

    private List<Integer> findVerticalAdj(int x, int y) {
        List<Integer> ans = new LinkedList<>();

        if (y >= height() - 1) {
            return Collections.emptyList();
        }

        if (x - 1 >= 0) {
            ans.add(x - 1);
        }
        ans.add(x);
        if (x + 1 <= width() - 1) {
            ans.add(x + 1);
        }

        return ans;
    }

    private double[][] energyField() {
        double[][] energyField = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                energyField[x][y] = energy(x, y);
            }
        }
        return energyField;
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
        int[] verticalSeam = findVerticalSeam();
        Picture newPicture = new Picture(width() - 1, height());

        for (int x = 0; x < newPicture.width(); ++x) {
            for (int y = 0; y < newPicture.height(); ++y) {
                int newX = x >= verticalSeam[y] ? x + 1 : x;
                newPicture.setRGB(x, y, picture.getRGB(newX, y));
            }
        }

        picture = newPicture;
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

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture picture = new Picture("6x5.png");
        SeamCarver seamCarver = new SeamCarver(picture);
        if (seamCarver.energy(3, 4) != seamCarver.energyField()[3][4]) {
            throw new RuntimeException("energyField");
        }

        if (!seamCarver.findVerticalAdj(3, 0).equals(Arrays.asList(2, 3, 4))) {
            throw new RuntimeException("findVerticalAdj" + seamCarver.findVerticalAdj(3, 0));
        }
        if (!seamCarver.findVerticalAdj(0, 0).equals(Arrays.asList(0, 1))) {
            throw new RuntimeException("findVerticalAdj" + seamCarver.findVerticalAdj(0, 0));
        }
    }
}