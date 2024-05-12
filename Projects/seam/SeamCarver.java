/* *****************************************************************************
 *  Name:           rina
 *  Date:           2024/5/11
 *  Description:    Implement SeamCarver class
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

import java.util.function.BiFunction;

public class SeamCarver {
    private static final int BORDER_PIXEL_ENERGY = 1000;
    private Picture picture;

    /**
     * create a seam carver object based on the given picture
     *
     * @param picture the given picture; should not be mutated by this class.
     * @throws IllegalArgumentException when PICTURE is NULL
     */
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Constructor: the input picture is null");
        }
        this.picture = new Picture(picture);
    }

    /** @return the current picture */
    public Picture picture() {
        return new Picture(picture);
    }

    /** @return the width of current picture */
    public int width() {
        return picture.width();
    }

    /** @return height of current picture */
    public int height() {
        return picture.height();
    }

    /**
     * Calculate the energy of pixels
     *
     * @param x the column index of pixel; assume 0 <= X < width
     * @param y the row index of pixel; assume 0 <= Y < height
     * @return the energy of pixel at column X and row Y; if the pixel is
     * boundary, then return BORDER_PIXEL_ENERGY(1000), else calculate its
     * energy and return it.
     * @throws IllegalArgumentException if either X or Y out of its range
     */
    public double energy(int x, int y) {
        validateColIndex(x);
        validateRowIndex(y);
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return BORDER_PIXEL_ENERGY;
        }
        int above = picture.getRGB(x - 1, y);
        int bottom = picture.getRGB(x + 1, y);
        int left = picture.getRGB(x, y - 1);
        int right = picture.getRGB(x, y + 1);
        return Math.sqrt(deltaValue(above, bottom) + deltaValue(left, right));
    }

    /* calculate the delta value between two point */
    private double deltaValue(int rgbA, int rgbB) {
        return Math.pow(((rgbA >> 16) & 0xff) - ((rgbB >> 16) & 0xff), 2)
                + Math.pow(((rgbA >> 8) & 0xff) - ((rgbB >> 8) & 0xff), 2)
                + Math.pow(((rgbA >> 0) & 0xff) - ((rgbB >> 0) & 0xff), 2);
    }

    private void validateColIndex(int x) {
        if (x < 0 || x >= width()) {
            throw new IllegalArgumentException(
                    "col index must be between 0 and " + (width() - 1) + ": " + x);
        }
    }

    private void validateRowIndex(int y) {
        if (y < 0 || y >= height()) {
            throw new IllegalArgumentException(
                    "row index must be between 0 and " + (height() - 1) + ": " + y);
        }
    }

    /** @return sequence of indices for horizontal seam */
    public int[] findHorizontalSeam() {
        return findSeam(height(), width(), (x, y) -> energy(y, x));
    }

    /** @return sequence of indices for vertical seam */
    public int[] findVerticalSeam() {
        return findSeam(width(), height(), this::energy);

    }

    private int[] findSeam(int width, int height, BiFunction<Integer, Integer, Double> energy) {
        /* dp */
        double[][] minimalEnergy = new double[height][width];
        for (int i = 0; i < width; i += 1) {
            minimalEnergy[height - 1][i] = energy.apply(i, height - 1);
        }
        for (int row = height - 2; row >= 0; row -= 1) {
            for (int col = 0; col < width; col += 1) {
                double possibleMin = minimalEnergy[row + 1][col];
                if (col - 1 >= 0) {
                    possibleMin = Math.min(possibleMin, minimalEnergy[row + 1][col - 1]);
                }
                if (col + 1 < width) {
                    possibleMin = Math.min(possibleMin, minimalEnergy[row + 1][col + 1]);
                }
                minimalEnergy[row][col] = possibleMin + energy.apply(col, row);
            }
        }
        /* generate path from minimalEnergy */
        int[] path = new int[height];
        int minIdx = 0;
        double minVal = minimalEnergy[0][0];
        for (int i = 1; i < width; i += 1) {
            if (minimalEnergy[0][i] < minVal) {
                minVal = minimalEnergy[0][i];
                minIdx = i;
            }
        }
        path[0] = minIdx;
        for (int i = 1; i < height; i += 1) {
            minVal = minimalEnergy[i][path[i - 1]];
            if (path[i - 1] - 1 >= 0 && minimalEnergy[i][path[i - 1] - 1] < minVal) {
                minIdx = path[i - 1] - 1;
                minVal = minimalEnergy[i][minIdx];
            }
            if (path[i - 1] + 1 < width && minimalEnergy[i][path[i - 1] + 1] < minVal) {
                minIdx = path[i - 1] + 1;
            }
            path[i] = minIdx;
        }
        return path;
    }

    /**
     * remove horizontal seam from current picture
     *
     * @param seam the horizontal Seam need to be removed; assume
     *             seam.length == height and
     *             0 <= seam[i] < width and
     *             Math.abs(seam[i+1] - seam[i]) <= 1.
     * @throws IllegalArgumentException when
     *                                  SEAM is NULL or
     *                                  SEAM is not a valid seam or
     *                                  the width of picture <= 1.
     */
    public void removeHorizontalSeam(int[] seam) {
        validateHorizontalSeam(seam);
        Picture newPicture = new Picture(width(), height() - 1);
        for (int col = 0; col < width(); col += 1) {
            for (int row = 0; row < height(); row += 1) {
                if (row < seam[col]) {
                    newPicture.setRGB(col, row, picture.getRGB(col, row));
                }
                else if (row > seam[col]) {
                    newPicture.setRGB(col, row - 1, picture.getRGB(col, row));
                }
            }
        }
        picture = newPicture;
    }

    private void validateHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam should not be null");
        }
        if (seam.length != width()) {
            throw new IllegalArgumentException(
                    "seam.length must equal to " + width() + ": " + seam.length);
        }
        if (height() <= 1) {
            throw new IllegalArgumentException(
                    "the height of picture should greater than 1 : " + height());
        }
        /* Assume seam.length >= 1, because the picture.width >= 1 */
        assert seam.length >= 1 : "seam.length should >= 1";
        validateRowIndex(seam[0]);
        for (int i = 1; i < seam.length; i += 1) {
            validateRowIndex(seam[i]);
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException(
                        "the array is not a validate seam, seam[" + (i - 1) + "]: " + seam[i - 1]
                                + ", seam[" + i + "]: " + seam[i]);
            }
        }
    }

    /**
     * remove vertical seam from current picture
     *
     * @param seam the vertical Seam need to be removed; assume
     *             seam.length == width and
     *             0 <= seam[i] < height and
     *             Math.abs(seam[i+1] - seam[i]) <= 1.
     * @throws IllegalArgumentException when
     *                                  SEAM is NULL or
     *                                  SEAM is not a valid seam or
     *                                  the height of picture <= 1.
     */
    public void removeVerticalSeam(int[] seam) {
        validateVerticalSeam(seam);
        Picture newPicture = new Picture(width() - 1, height());
        for (int row = 0; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                if (col < seam[row]) {
                    newPicture.setRGB(col, row, picture.getRGB(col, row));
                }
                else if (col > seam[row]) {
                    newPicture.setRGB(col - 1, row, picture.getRGB(col, row));
                }
            }
        }
        picture = newPicture;
    }

    private void validateVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam should not be null");
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException(
                    "seam.length must equal to " + height() + ": " + seam.length);
        }
        if (width() <= 1) {
            throw new IllegalArgumentException(
                    "the width of picture should greater than 1 : " + width());
        }
        /* Assume seam.length >= 1, because the picture.height >= 1 */
        assert seam.length >= 1 : "seam.length should >= 1";
        validateColIndex(seam[0]);
        for (int i = 1; i < seam.length; i += 1) {
            validateColIndex(seam[i]);
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException(
                        "the array is not a validate seam, seam[" + (i - 1) + "]: " + seam[i - 1]
                                + ", seam[" + i + "]: " + seam[i]);
            }
        }
    }
}
