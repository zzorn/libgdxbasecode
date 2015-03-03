package org.gameflow.tools.picture.gradient;

import org.gameflow.utils.MathTools;

import java.util.*;

import static java.lang.Math.*;
import static org.gameflow.utils.ParameterChecker.*;

/**
 * A multi-value gradient.
 */
public final class Gradient {

    private final String[] channels;
    private Map<String, Integer> channelNamesToIndexes = new HashMap<String, Integer>();
    private List<GradientPoint> points = new ArrayList<GradientPoint>();

    /**
     * Creates a new gradient with the specified channels.
     * @param channels the names of the channels that this gradient should have.
     */
    public Gradient(String ... channels) {
        checkNotNull(channels, "channels");
        checkPositiveNonZeroInteger(channels.length, "size of channels");

        this.channels = channels;

        for (int i = 0; i < channels.length; i++) {
            checkNotNull(channels[i], "channels["+i+"]");
            channelNamesToIndexes.put(channels[i], i);
        }
    }

    /**
     * Adds a new gradient point to this gradient at the specified initial position, and returns it.
     */
    public GradientPoint addPoint(double initialPosition, double ... initialValues) {
        GradientPoint gradientPoint = new GradientPoint((float) initialPosition, initialValues);
        points.add(gradientPoint);
        sortPoints();
        return gradientPoint;
    }

    /**
     * Removes the specified gradient point from this gradient.
     */
    public void removePoint(GradientPoint point) {
        points.remove(point);
    }

    /**
     * @return gradient point closest to the specified position, or null if there are no gradient points in this gradient.
     */
    public GradientPoint getClosestPoint(float position) {
        if (points.isEmpty()) return null;
        else {
            // Binary search
            int start = 0;
            int end = points.size() - 1;
            int closestSmaller = start;
            int closestLarger = end;
            while (start <= end) {
                int mid = (start + end) / 2;

                GradientPoint midPoint = points.get(mid);
                if (position > midPoint.position) {
                    start = mid + 1;
                    closestSmaller = mid;
                }
                else if (position < midPoint.position) {
                    end = mid - 1;
                    closestLarger = mid;
                }
                else return midPoint; // Found point
            }

            // No exact match found, return closest point
            GradientPoint leftPoint  = points.get(closestSmaller);
            GradientPoint rightPoint = points.get(closestLarger);
            if (abs(position - leftPoint.position) <=
                abs(position - rightPoint.position) ) return leftPoint;
            else return rightPoint;
        }
    }

    /**
     * @return the names of the channels in the gradient, in the same order as the values are returned.
     */
    public String[] getChannelNames() {
        return channels;
    }

    /**
     * @return the index of the channel with the specified name, or -1 if no such channel exists in this gradient.
     */
    public int getChannelIndex(String channelName) {
        if (channelNamesToIndexes.containsKey(channelName)) return channelNamesToIndexes.get(channelName);
        else return -1;
    }

    /**
     * @return the value of the specified channel at the specified point, or 0 if the channel is unknonwn or there are no gradient points.
     */
    public float getChannelValue(String channel, float position) {
        if (points.size() > 0) {
            // Check if first
            GradientPoint firstPoint = points.get(0);
            if (position <= firstPoint.position) {
                return firstPoint.getValue(channel);
            }

            // Check if last
            GradientPoint lastPoint = points.get(points.size() - 1);
            if (position >= lastPoint.position) {
                return lastPoint.getValue(channel);
            }

            // Find closest
            int left = closestEqualOrSmallerPointIndex(position);
            GradientPoint leftPoint = points.get(left);
            if (leftPoint.position == position || left >= points.size() - 1) {
                // Exact match (or last point)
                return leftPoint.getValue(channel);
            }
            else {
                // Interpolate
                GradientPoint rightPoint = points.get(left + 1);
                float relativePos = (position - leftPoint.position) / (rightPoint.position - leftPoint.position);
                return leftPoint.getInterpolatedValue(relativePos, rightPoint, channel);
            }
        }
        else {
            // No points, zero values
            return 0;
        }
    }

    /**
     * @return the values at the specified point.  Creates a new array to return the values.
     */
    public float[] getChannelValues(float position) {
        return getChannelValues(position, null);
    }

    /**
     * @return the values at the specified point.  Reuses the specified array to return the values, or creates a new one if it is null.
     */
    public float[] getChannelValues(float position, float[] valuesOut) {
        if (valuesOut == null) valuesOut = new float[getChannelCount()];

        if (points.size() > 0) {
            // Check if first
            GradientPoint firstPoint = points.get(0);
            if (position <= firstPoint.position) {
                return firstPoint.getValues(valuesOut);
            }

            // Check if last
            GradientPoint lastPoint = points.get(points.size() - 1);
            if (position >= lastPoint.position) {
                return lastPoint.getValues(valuesOut);
            }

            // Find closest
            int left = closestEqualOrSmallerPointIndex(position);
            GradientPoint leftPoint = points.get(left);
            if (leftPoint.position == position || left >= points.size() - 1) {
                // Exact match (or last point)
                return leftPoint.getValues(valuesOut);
            }
            else {
                // Interpolate
                GradientPoint rightPoint = points.get(left + 1);
                float relativePos = (float) MathTools.relPos(position, leftPoint.position, rightPoint.position);
                return leftPoint.getInterpolatedValues(relativePos, rightPoint, valuesOut);
            }
        }
        else {
            // No points, zero values
            return valuesOut;
        }
    }

    /**
     * @return number of channels that this gradient has.
     */
    public int getChannelCount() {
        return channels.length;
    }

    private void sortPoints() {
        Collections.sort(points);
    }

    private int closestEqualOrSmallerPointIndex(float position) {
        // Binary search
        int start = 0;
        int end = points.size() - 1;
        int closestSmaller = start;
        while (start <= end) {
            int mid = (start + end) / 2;

            GradientPoint midPoint = points.get(mid);
            if (position > midPoint.position) {
                closestSmaller = mid;
                start = mid + 1;
            }
            else if (position < midPoint.position) {
                end = mid - 1;
            }
            else return mid;
        }

        // No exact match found, return smaller value
        return closestSmaller;
    }


    /**
     * Class used to specify the values of the channels for a specific point on the gradient.
     */
    public class GradientPoint implements Comparable<GradientPoint> {
        private float position;
        private float[] values;

        private GradientPoint(float position, double ... initialValues) {
            this.position = position;
            values = new float[getChannelCount()];

            for (int i = 0; i < initialValues.length && i < values.length; i++) {
                values[i] = (float) initialValues[i];
            }
        }

        /**
         * @return position of this gradient point in the gradient.
         */
        public float getPosition() {
            return position;
        }

        /**
         * Sets the position of this gradient point, and updates the gradient.
         */
        public void setPosition(float position) {
            if (this.position != position) {
                this.position = position;
                sortPoints();
            }
        }

        /**
         * @return the actual values of this gradient point.  Returns a reference to the array used for the gradient,
         * can be used to change the gradient values.
         */
        public float[] getValues() {
            return values;
        }

        /**
         * @return the value of the specified channel at this point, or 0 if the channel name is unknown.
         */
        public float getValue(String channelName) {
            int channelIndex = getChannelIndex(channelName);
            if (channelIndex < 0) return 0;
            else return values[channelIndex];
        }

        public float[] getValues(float[] valuesOut) {
            if (valuesOut == null) valuesOut = new float[values.length];
            System.arraycopy(values, 0, valuesOut, 0, values.length);
            return valuesOut;
        }

        public int compareTo(GradientPoint o) {
            if (position < o.position) return -1;
            else if (position > o.position) return 1;
            else return 0;
        }

        public float[] getInterpolatedValues(float relativePos, GradientPoint otherPoint, float[] valuesOut) {
            if (valuesOut == null) valuesOut = new float[values.length];

            for (int i = 0; i < values.length; i++) {
                valuesOut[i] = values[i] + relativePos * (otherPoint.values[i] - values[i]);
            }
            
            return valuesOut;
        }

        public float getInterpolatedValue(float relativePos, GradientPoint otherPoint, String channel) {
            float thisValue = getValue(channel);
            float otherValue = otherPoint.getValue(channel);
            return thisValue + relativePos * (otherValue - thisValue);
        }

        @Override
        public String toString() {
            return "GradientPoint{ pos = " + position + ", values = " + Arrays.toString(values) + "}";
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Gradient{ channels = ");
        s.append(Arrays.toString(channels));
        s.append(", points = ");
        boolean first = true;
        for (GradientPoint point : points) {
            if (first) first = false;
            else s.append(", ");
            s.append(point.toString());
        }
        s.append("}");
        return s.toString();
    }
}
