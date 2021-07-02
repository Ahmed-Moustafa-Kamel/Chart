import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphPassengers
{
    public static void graphPassengerAges(List<Passenger> data)
    {
        List<Float> pAges= data.stream().map(Passenger::getAge).limit(8).collect(Collectors.toList());
        List<String> pNames= data.stream().map(Passenger::getName).limit(8).collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title ("Age Histogram").xAxisTitle("Names").yAxisTitle("Age").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setStacked(true);
        chart.addSeries("Passenger's Ages", pNames, pAges);
        new SwingWrapper(chart).displayChart();
    }

    public static void graphPassengerClass(List<Passenger> data) {
        Map<String, Long> map = data.stream()
                .collect(Collectors.groupingBy(Passenger::getPclass, Collectors.counting()));

        PieChart chart = new PieChartBuilder().width(800).height(600).build();
        Color[] sliceColors = new Color[]{new Color(180, 68, 50), new Color(130, 105, 120), new Color(80, 143, 160)};
        chart.getStyler().setSeriesColors(sliceColors);
        // Series
        chart.addSeries("First Class", map.get("1"));
        chart.addSeries("Second Class", map.get("2"));
        chart.addSeries("Third Class", map.get("3"));
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    public static void graphPassengerSurvived(List<Passenger> passengerList)
    {
        Map<String, Long> map =passengerList.stream()
                .collect(Collectors.groupingBy(Passenger::getSurvived, Collectors.counting()));

        PieChart chart = new PieChartBuilder().width (800).height (600).build ();
        Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
        chart.getStyler().setSeriesColors(sliceColors);
        chart.addSeries("Survived", map.get("0"));
        chart.addSeries("Not-Survived", map.get("1"));
        new SwingWrapper(chart).displayChart();
    }

    public static void graphPassengerNotSurvivedSex(List<Passenger> passengerList)
    {
        Map<String, Long> map =passengerList.stream()
                .filter(p -> p.getSurvived().equals("1"))
                .collect(Collectors.groupingBy(Passenger::getSex, Collectors.counting()));

        PieChart chart = new PieChartBuilder().width (800).height (600).build();
        Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
        chart.getStyler().setSeriesColors(sliceColors);
        chart.addSeries("Dead-Males", map.get("male"));
        chart.addSeries("Dead-Females", map.get("female"));
        new SwingWrapper(chart).displayChart();
    }
}