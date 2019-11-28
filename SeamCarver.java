import edu.princeton.cs.algs4.Picture;

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
        throw new UnsupportedOperationException();
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

    }

    private void notNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkBounds(int x, int y) {
        if (x < 0 || x >= width()) {
            throw new IllegalArgumentException();
        }
        if (y < 0 || y >= height()) {
            throw new IllegalArgumentException();
        }
    }

}