//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.knowm.xchart;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.internal.Utils;
import org.knowm.xchart.internal.chartpart.AxisPair;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.chartpart.Legend_Marker;
import org.knowm.xchart.internal.chartpart.Plot_Category;
import org.knowm.xchart.internal.series.Series.DataType;
import org.knowm.xchart.internal.style.SeriesColorMarkerLineStyle;
import org.knowm.xchart.internal.style.SeriesColorMarkerLineStyleCycler;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.theme.Theme;

public class CategoryChart extends Chart<CategoryStyler, CategorySeries> {
    public CategoryChart(int width, int height) {
        super(width, height, new CategoryStyler());
        this.axisPair = new AxisPair(this);
        this.plot = new Plot_Category(this);
        this.legend = new Legend_Marker(this);
    }

    public CategoryChart(int width, int height, Theme theme) {
        this(width, height);
        ((CategoryStyler)this.styler).setTheme(theme);
    }

    public CategoryChart(int width, int height, ChartTheme chartTheme) {
        this(width, height, chartTheme.newInstance(chartTheme));
    }

    public CategoryChart(CategoryChartBuilder chartBuilder) {
        this(chartBuilder.width, chartBuilder.height, chartBuilder.chartTheme);
        this.setTitle(chartBuilder.title);
        this.setXAxisTitle(chartBuilder.xAxisTitle);
        this.setYAxisTitle(chartBuilder.yAxisTitle);
    }

    public CategorySeries addSeries(String seriesName, double[] xData, double[] yData) {
        return this.addSeries(seriesName, (double[])xData, (double[])yData, (double[])null);
    }

    public CategorySeries addSeries(String seriesName, double[] xData, double[] yData, double[] errorBars) {
        return this.addSeries(seriesName, Utils.getNumberListFromDoubleArray(xData), Utils.getNumberListFromDoubleArray(yData), Utils.getNumberListFromDoubleArray(errorBars));
    }

    public CategorySeries addSeries(String seriesName, int[] xData, int[] yData) {
        return this.addSeries(seriesName, (int[])xData, (int[])yData, (int[])null);
    }

    public CategorySeries addSeries(String seriesName, int[] xData, int[] yData, int[] errorBars) {
        return this.addSeries(seriesName, Utils.getNumberListFromIntArray(xData), Utils.getNumberListFromIntArray(yData), Utils.getNumberListFromIntArray(errorBars));
    }

    public CategorySeries addSeries(String seriesName, List<?> xData, List<? extends Number> yData) {
        return this.addSeries(seriesName, (List)xData, (List)yData, (List)null);
    }

    public CategorySeries addSeries(String seriesName, List<?> xData, List<? extends Number> yData, List<? extends Number> errorBars) {
        this.sanityCheck(seriesName, xData, yData, errorBars);
        if (xData != null) {
            if (xData.size() != yData.size()) {
                throw new IllegalArgumentException("X and Y-Axis sizes are not the same!!!");
            }
        } else {
            xData = Utils.getGeneratedDataAsList(yData.size());
        }

        CategorySeries series = new CategorySeries(seriesName, xData, yData, errorBars, this.getDataType(xData));
        this.seriesMap.put(seriesName, series);
        return series;
    }

    private DataType getDataType(List<?> data) {
        Iterator<?> itr = data.iterator();
        Object dataPoint = itr.next();
        DataType axisType;
        if (dataPoint instanceof Number) {
            axisType = DataType.Number;
        } else if (dataPoint instanceof Date) {
            axisType = DataType.Date;
        } else {
            if (!(dataPoint instanceof String)) {
                throw new IllegalArgumentException("Series data must be either Number, Date or String type!!!");
            }

            axisType = DataType.String;
        }

        return axisType;
    }

    public CategorySeries updateCategorySeries(String seriesName, List<?> newXData, List<? extends Number> newYData, List<? extends Number> newErrorBarData) {
        Map<String, CategorySeries> seriesMap = this.getSeriesMap();
        CategorySeries series = (CategorySeries)seriesMap.get(seriesName);
        if (series == null) {
            throw new IllegalArgumentException("Series name >" + seriesName + "< not found!!!");
        } else {
            if (newXData == null) {
                List<Integer> generatedXData = new ArrayList();

                for(int i = 1; i <= newYData.size(); ++i) {
                    generatedXData.add(i);
                }

                series.replaceData(generatedXData, newYData, newErrorBarData);
            } else {
                series.replaceData(newXData, newYData, newErrorBarData);
            }

            return series;
        }
    }

    public CategorySeries updateCategorySeries(String seriesName, double[] newXData, double[] newYData, double[] newErrorBarData) {
        return this.updateCategorySeries(seriesName, Utils.getNumberListFromDoubleArray(newXData), Utils.getNumberListFromDoubleArray(newYData), Utils.getNumberListFromDoubleArray(newErrorBarData));
    }

    private void sanityCheck(String seriesName, List<?> xData, List<? extends Number> yData, List<? extends Number> errorBars) {
        if (this.seriesMap.keySet().contains(seriesName)) {
            throw new IllegalArgumentException("Series name >" + seriesName + "< has already been used. Use unique names for each series!!!");
        } else if (yData == null) {
            throw new IllegalArgumentException("Y-Axis data cannot be null!!!");
        } else if (yData.size() == 0) {
            throw new IllegalArgumentException("Y-Axis data cannot be empty!!!");
        } else if (xData != null && xData.size() == 0) {
            throw new IllegalArgumentException("X-Axis data cannot be empty!!!");
        } else if (errorBars != null && errorBars.size() != yData.size()) {
            throw new IllegalArgumentException("Error bars and Y-Axis sizes are not the same!!!");
        }
    }

    public void paint(Graphics2D g, int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        Iterator var4 = this.getSeriesMap().values().iterator();

        while(var4.hasNext()) {
            CategorySeries seriesCategory = (CategorySeries)var4.next();
            CategorySeriesRenderStyle seriesType = seriesCategory.getChartCategorySeriesRenderStyle();
            if (seriesType == null) {
                seriesCategory.setChartCategorySeriesRenderStyle(((CategoryStyler)this.getStyler()).getDefaultSeriesRenderStyle());
            }
        }

        this.setSeriesStyles();
        this.paintBackground(g);
        this.axisPair.paint(g);
        this.plot.paint(g);
        this.chartTitle.paint(g);
        this.legend.paint(g);
        this.annotations.forEach((x) -> {
            x.paint(g);
        });
    }

    private void setSeriesStyles() {
        SeriesColorMarkerLineStyleCycler seriesColorMarkerLineStyleCycler = new SeriesColorMarkerLineStyleCycler(((CategoryStyler)this.getStyler()).getSeriesColors(), ((CategoryStyler)this.getStyler()).getSeriesMarkers(), ((CategoryStyler)this.getStyler()).getSeriesLines());
        Iterator var2 = this.getSeriesMap().values().iterator();

        while(var2.hasNext()) {
            CategorySeries series = (CategorySeries)var2.next();
            SeriesColorMarkerLineStyle seriesColorMarkerLineStyle = seriesColorMarkerLineStyleCycler.getNextSeriesColorMarkerLineStyle();
            if (series.getLineStyle() == null) {
                series.setLineStyle(seriesColorMarkerLineStyle.getStroke());
            }

            if (series.getLineColor() == null) {
                series.setLineColor(seriesColorMarkerLineStyle.getColor());
            }

            if (series.getFillColor() == null) {
                series.setFillColor(seriesColorMarkerLineStyle.getColor());
            }

            if (series.getMarker() == null) {
                series.setMarker(seriesColorMarkerLineStyle.getMarker());
            }

            if (series.getMarkerColor() == null) {
                series.setMarkerColor(seriesColorMarkerLineStyle.getColor());
            }
        }

    }
}
