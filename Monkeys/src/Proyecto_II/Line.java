package Proyecto_II;

import java.util.Random;

/**
 *Esta clase repreenta una inea
 */


public class Line
{
    private int adjectiveSubject;
    private int nounSubject;
    private int verbPredicate;
    private int adjectivePredicate;
    private int nounPredicate;
    
    /**
     * Esta constructor inicializa lso campos del objeto
     */
    Line()
    {
        this.adjectiveSubject = 0;
        this.nounSubject = 0;
        this.verbPredicate = 0;
        this.adjectivePredicate = 0;
        this.nounPredicate = 0;
    }

    
    /**
     * Toma el parametro y lo establece en el objeto
     * @param adjectiveSubject representa el numero de la palabra 
     */
    public void setAdjectiveSubject(int adjectiveSubject)
    {
        this.adjectiveSubject = adjectiveSubject;
    }
    
    /**
     * Toma el parametro y lo establece en el objeto
     * @param nounSubject representa el numero de la palabra 
     */
    
    public void setNounSubject(int nounSubject)
    {
        this.nounSubject = nounSubject;
    }
    
    /**
     * Toma el parametro y lo establece en el objeto
     * @param verbPredicate representa el numero de la palabra 
     */
    
    public void  setVerbPredicate(int verbPredicate)
    {
        this.verbPredicate = verbPredicate;
    }
    
    /**
     * Toma el parametro y lo establece en el objeto
     * @param adjectivePredicate representa el numero de la palabra 
     */
    
    public void setAdjectivePredicate(int adjectivePredicate)
    {
        this.adjectivePredicate = adjectivePredicate;
    }
    
    /**
     * Toma el parametro y lo establece en el objeto
     * @param nounPredicate representa el numero de la palabra 
     */
    
    public void setNounPredicate(int nounPredicate)
    {
        this.nounPredicate = nounPredicate;
    }
    
    /**
     * 
     * @return el campo adjectiveSubject del objeto
     */
    
    public int getAdjectiveSubject()
    {
        return this.adjectiveSubject;
    }
    
    /**
     * 
     * @return  el campo nounSubject del objeto
     */
    
    public int getNounSubject()
    {
        return this.nounSubject;
    }
    
    /**
     * 
     * @return el campo verbPredicate del objeto
     */
    
    public int getVerbPredicate()
    {
        return this.verbPredicate;
    }
    
    /**
     * 
     * @return el campo adjectivePredicate del objeto
     */
    
    public int getAdjectivePredicate()
    {
        return this.adjectivePredicate;
    }
    
    /**
     * 
     * @return el campo nounPredicate del objeto
     */
    
    public int getNounPredicate()
    {
        return this.nounPredicate;
    }
    
    
    /**
     * Genera una línea aleatoria
     * @return una linea aleatoria
     */
    public Line generateRandomLine()
    {
        Line returnValue = new Line();
        Random rand = new Random();
        
        returnValue.adjectiveSubject = rand.nextInt(212);
        returnValue.nounSubject = rand.nextInt(212);
        returnValue.verbPredicate = rand.nextInt(212);
        returnValue.adjectivePredicate = rand.nextInt(212);
        returnValue.nounPredicate = rand.nextInt(212);
    
        return returnValue;
    }
}
