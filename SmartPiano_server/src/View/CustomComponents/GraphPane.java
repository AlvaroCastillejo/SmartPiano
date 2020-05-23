package View.CustomComponents;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPane extends JPanel {
    private int padding = 20;
    private int labelPadding = 15;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List<Double> graphPointValues;
    private boolean isReproductionsGraph;

    public GraphPane(boolean isReproductionsGraph, List<Double> graphValuesList) {
        this.isReproductionsGraph = isReproductionsGraph;
        this.graphPointValues = graphValuesList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (24 - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxPointValue() - getMinPointValue());

        java.util.List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < graphPointValues.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxPointValue() - graphPointValues.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (graphPointValues.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinPointValue() + (getMaxPointValue() - getMinPointValue()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < 24; i++) {
            if (graphPointValues.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (24 - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((24 / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    if (graphPointValues.size() == i+1) {
                        g2.setColor(Color.RED);
                    }                    
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel + ":00", x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.setColor(Color.BLACK);
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);
        g2.drawString("[DATETIME]", 700, 488);
        if (isReproductionsGraph) {
            g2.drawString("[nº Reproductions]", 5, 10);
        } else {
            g2.drawString("[nº minutes]", 5, 10);
        }
        
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        for (int i = 0; i < graphPoints.size(); i++) {
            g2.setColor(pointColor);
            if (graphPointValues.size() == i+1) {
                g2.setColor(Color.RED);
            }
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private double getMinPointValue() {
        double minPointValue = Double.MAX_VALUE;
        for (Double pointValue : graphPointValues) {
            minPointValue = Math.min(minPointValue, pointValue);
        }
        return minPointValue;
    }

    private double getMaxPointValue() {
        double maxPointValue = Double.MIN_VALUE;
        for (Double pointValue : graphPointValues) {
            maxPointValue = Math.max(maxPointValue, pointValue);
        }
        return maxPointValue;
    }

    public void setGraphPointValues(List<Double> graphPointValues) {
        this.graphPointValues = graphPointValues;
        invalidate();
        this.repaint();
    }

    public List<Double> getGraphPointValues() {
        return graphPointValues;
    }

}