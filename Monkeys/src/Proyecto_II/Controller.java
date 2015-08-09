/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto_II;

import UI.Graphic;
import UI.IExperiment1;
import UI.IExperiment1Result;
import UI.IExperiment2;
import UI.IExperiment2Result;
import UI.IMenu;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cesar_000
 */
public class Controller {
    Experiments experiments;
    IMenu iMenu;
    IExperiment1 iExperiment1;
    IExperiment1Result iExperiment1Result;
    IExperiment2Result iExperiment2Result;
    IExperiment2 iExperiment2;
    
    Controller(){
        experiments = new Experiments();
        iMenu = new IMenu(this);
        iExperiment1 = new IExperiment1(this);
        iExperiment2 = new IExperiment2(this);
        iExperiment1Result = new IExperiment1Result(this);
        iExperiment2Result = new IExperiment2Result(this);
    }
    public void init(){
        iMenu.setLocationRelativeTo(null);
        iMenu.setVisible(true);
    }
    public void initExperiment1(){
        iMenu.setVisible(false);
        iExperiment1.setLocationRelativeTo(null);
        iExperiment1.setVisible(true);
    }
    public void initExperiment2(){
        iMenu.setVisible(false);
        iExperiment2.setLocationRelativeTo(null);
        iExperiment2.setVisible(true);
    }
    public void startExperiment1(int iniPopu, int maxGen){
        iExperiment1.setVisible(false);
        iExperiment1Result.setLocationRelativeTo(null);
        iExperiment1Result.setVisible(true);
        
        ResultFirstExperiment experiment=null;
        
        try {
            experiment = experiments.firstExperiment(iniPopu, maxGen);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(experiment.getSentence()!=null){
            iExperiment1Result.setResult("Success");
            iExperiment1Result.setSentence(experiment.getSentence());
            iExperiment1Result.setExperiment(experiment);
            iExperiment1Result.setGraphButton(true);
        }else{
            iExperiment1Result.setResult("Failure");
            iExperiment1Result.setSentence("...");
            iExperiment1Result.setExperiment(experiment);
        }
    }
    public void startExperiment2(int iniPopu, int maxGen){
        iExperiment2.setVisible(false);
        iExperiment2Result.setLocationRelativeTo(null);
        iExperiment2Result.setVisible(true);
        
        ResultSecondExperiment experiment=null;
        
        try {
            experiment = experiments.secondExperiment(iniPopu, maxGen);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(experiment.getPoem()!=null){
            iExperiment2Result.setResult("Success");
            iExperiment2Result.setPoem(experiment.getPoem());
            iExperiment2Result.setExperiment(experiment);
            iExperiment2Result.setGraphButton(true);
        }else{
            iExperiment2Result.setResult("Failure");
            iExperiment2Result.setPoem();
            iExperiment2Result.setExperiment(experiment);
        }
    }
    public void backToExperiment1(){
        iExperiment1Result.setVisible(false);
        iExperiment1.setVisible(true);
    }
    public void backToExperiment2(){
        iExperiment2Result.setVisible(false);
        iExperiment2.setVisible(true);
    }
    public void backToMenu(int from){
        if(from==0) iExperiment1.setVisible(false);
        else iExperiment2.setVisible(false);
        iMenu.setVisible(true);
    }
    public void graphFirstResul(ResultFirstExperiment experiment){
        new Graphic(experiment).setVisible(true);
    }
    public void graphSecondResul(ResultSecondExperiment experiment){
        new Graphic(experiment).setVisible(true);
    }
}
