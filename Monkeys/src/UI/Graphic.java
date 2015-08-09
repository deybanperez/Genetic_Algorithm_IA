/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import Proyecto_II.ResultFirstExperiment;
import Proyecto_II.ResultSecondExperiment;

/**
 *
 * @author cesar_000
 */
public class Graphic extends JFrame{
    JPanel panel;
    ResultFirstExperiment experiment1;
    ResultSecondExperiment experiment2;
    public Graphic(ResultFirstExperiment experiment){
        this.experiment1 = experiment;
        setTitle("Graphic");
        setSize(700,470);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }
    
    public Graphic(ResultSecondExperiment experiment){
        this.experiment2 = experiment;
        setTitle("Graphic");
        setSize(700,470);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        init2();
    }
    
    private void init() {
        panel = new JPanel();
        getContentPane().add(panel);
        // Fuente de Datos
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        for(int i=0; i<experiment1.getMedia().size(); i++){
            line_chart_dataset.addValue(experiment1.getMedia().get(i), "aptitud", String.valueOf(i));
        }
 
        // Creando el Grafico
        JFreeChart chart=ChartFactory.createLineChart("Apitud de las generaciones",
                "Generacion","Aptitud",line_chart_dataset,PlotOrientation.VERTICAL,
                true,true,false);  
        
        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel);
    }
    
    private void init2() {
        panel = new JPanel();
        getContentPane().add(panel);
        // Fuente de Datos
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        for(int i=0; i<experiment2.getMedia().size(); i++){
            line_chart_dataset.addValue(experiment2.getMedia().get(i), "aptitud", String.valueOf(i));
        }
 
        // Creando el Grafico
        JFreeChart chart=ChartFactory.createLineChart("Apitud de las generaciones",
                "Generacion","Aptitud",line_chart_dataset,PlotOrientation.VERTICAL,
                true,true,false);  
        
        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel);
    }
}