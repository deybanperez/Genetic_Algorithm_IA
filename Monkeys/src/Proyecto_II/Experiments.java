package Proyecto_II;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Experiments
{
    public ResultFirstExperiment firstExperiment(int iniPopu, int maxGen) throws IOException
    {
        Word auxiliar = new Word();
        ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
        ArrayList<Monkey> monkeysNextGeneration = new ArrayList<Monkey>();
        ArrayList<Monkey> numberCrossoverMonkeys = new ArrayList<Monkey>();
        ArrayList<Integer> arraylistSolution = new ArrayList<Integer>(); 
        Monkey temporalMonkey = new Monkey();
        ArrayList<String> arraylistWords = new ArrayList<String>();
        ArrayList<String> adjectives = new ArrayList<String>();
        ArrayList<String> sustantives = new ArrayList<String>();
        ArrayList<String> verbs = new ArrayList<String>();
        ArrayList<Word> words = new ArrayList<Word>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int initialPopulation;
        int numberGenerations;
        int indexGenerations = 0, population, monkeyTournament1, monkeyTournament2, indexTorunament;
        ArrayList<Double> medias = new ArrayList<Double>();
        Random rand = new Random();
        
        /*
            En este bloque se crean las listas de palabras, adjetivos, sustantivos y verbos
        */
      
        arraylistWords = auxiliar.init("lista_palabras.txt");
        words = auxiliar.createWords(arraylistWords);
        adjectives = auxiliar.init("lista_adjetivos.txt");
        sustantives = auxiliar.init("lista_sustantivos.txt");        
        verbs = auxiliar.init("lista_verbos.txt");
        
        /*
            Acá se clasifican las palabras en adjetivos, sustantivos o verbos
        */
        for(int i = 0; i < words.size(); i++)
            words.get(i).clasificateWord(sustantives, adjectives, verbs);
        
        
        initialPopulation = iniPopu; 
        numberGenerations = maxGen;
        
        /*
            Acá se genera la población aleatoria de monos
        */
        
        for(int i = 0; i < initialPopulation; i++)
        {
            temporalMonkey.generateRandomMonkey(1);
            monkeys.add(temporalMonkey);
            temporalMonkey = new Monkey();
        }
        
        
        /*
            Acá inicia el ciclo del algoritmo evolutivo
        */
        
        while(indexGenerations < numberGenerations) //Mientars no se llegue al numeor maximo de generaciones
        {
            
            if(indexGenerations == 0) //Calcular aptitud
            {
                for(int i = 0; i < monkeys.size(); i++) //Calcular aptitud
                    monkeys.get(i).calculateAptitudeExperiment1(words);
            }
            
            arraylistSolution = solutionExperiment(monkeys); //Se verifica si unmono en la población es solucion
            
            if(arraylistSolution.size() == 0) //Si el are   arreglo solucion está vacío
            {
                
                if(indexGenerations > 0)
                {
                
                    for(int i = 0; i < monkeys.size(); i++) //Calcular aptitud
                        monkeys.get(i).calculateAptitudeExperiment1(words);
                }

                //if(indexGenerations == 0)
                //{
                   double media = 0;

                    for(int i = 0; i < monkeys.size(); i ++)
                        media += monkeys.get(i).getAptitud();

                    media = media / monkeys.size();
                    medias.add(media);
                    System.out.println("Generación "+ Integer.toString(indexGenerations)+" : "+ Double.toString(media));
                //}

                /*
                Seleccionar los individuos por torneo determinístico siempre nos quedamos con el 60% de la población
                */
                monkeysNextGeneration = new ArrayList<Monkey>();
                indexTorunament = calculatePercentageSelection(monkeys.size());

                for(int i = 0; i <indexTorunament ; i++)
                {
                    //Se toman dos individuos al azar, el que tenga mayor aptitud de pasa a la siguiente generación

                    monkeyTournament1 = rand.nextInt(monkeys.size());
                    monkeyTournament2 = rand.nextInt(monkeys.size());

                    while(monkeyTournament2 == monkeyTournament1)
                        monkeyTournament2 = rand.nextInt(monkeys.size());

                    //Se compara la aptitud de ellos

                    if(monkeyFight(monkeys.get(monkeyTournament1),monkeys.get(monkeyTournament2)) == 1)
                    {
                        monkeysNextGeneration.add(monkeys.get(monkeyTournament1));
                        monkeys.remove(monkeyTournament1);
                    }else
                    {
                        monkeysNextGeneration.add(monkeys.get(monkeyTournament2));
                        monkeys.remove(monkeyTournament2);
                    }
                }

                //Se pasan los monos seleccionados a la nueva generacion
                monkeys = monkeysNextGeneration;

                //Se calcula la probabilidad de cruce
                for(int i = 0; i < monkeys.size(); i++)
                    monkeys.get(i).calculateCrossoverProbability();

                //Se obtienen los monos a cruzarse
                numberCrossoverMonkeys = selectCrossoverMonkeys(monkeys);

                //Se cruzan los monos
                numberCrossoverMonkeys =  crossoverMonkeysExperiment1(numberCrossoverMonkeys);

                //Se agregan los monos a la generación

                for(int i = 0; i < numberCrossoverMonkeys.size(); i++)
                    monkeys.add(numberCrossoverMonkeys.get(i));

                //Se calcula la probabilidad de mutación
                for(int i = 0; i < monkeys.size(); i++)
                    monkeys.get(i).calculateMutationProbability();

                //Se hace el proceso de mutacion
                mutationProcessExperiment1(monkeys);

                indexGenerations++;

            }else
            {
                
                System.out.println("Encontó solución");
                
                
                if(indexGenerations == 0)
                {
                    double media = 0;

                    for (Monkey monkey : monkeys) {
                        media += monkey.getAptitud();
                    }

                    media = media / monkeys.size();
                    
                    medias.add(media);
                    System.out.println("Generación "+ Integer.toString(indexGenerations)+" : "+ Double.toString(media));
                }
           
                indexGenerations = numberGenerations;
                    
                for (Integer arraylistSolution1 : arraylistSolution) {
                    monkeys.get(arraylistSolution1).printMonkeyLine(words);
                }
                if (arraylistSolution.size()>0){
                    ResultFirstExperiment rfe = new ResultFirstExperiment();
                    rfe.setSentence(monkeys.get(arraylistSolution.get(0)).getMonkeyLine(words));
                    rfe.setMedia(medias);
                    return rfe;
                }
            }
        }
        ResultFirstExperiment rfe = new ResultFirstExperiment();
        rfe.setSentence(null);
        rfe.setMedia(medias);
        return rfe;
    }
    
    public ResultSecondExperiment secondExperiment(int iniPupo, int maxGen) throws IOException
    {
        Word auxiliar = new Word();
        ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
        ArrayList<Monkey> monkeysNextGeneration = new ArrayList<Monkey>();
        ArrayList<Monkey> numberCrossoverMonkeys = new ArrayList<Monkey>();
        ArrayList<Integer> arraylistSolution = new ArrayList<Integer>(); 
        Monkey temporalMonkey = new Monkey();
        ArrayList<String> arraylistWords = new ArrayList<String>();
        ArrayList<String> adjectives = new ArrayList<String>();
        ArrayList<String> sustantives = new ArrayList<String>();
        ArrayList<String> verbs = new ArrayList<String>();
        ArrayList<Word> words = new ArrayList<Word>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int initialPopulation;
        int numberGenerations;
        int indexGenerations = 0, population, monkeyTournament1, monkeyTournament2, indexTorunament;
        ArrayList<Double> medias = new ArrayList<Double>();
        Random rand = new Random();
        
        /*
            En este bloque se crean las listas de palabras, adjetivos, sustantivos y verbos
        */
      
        arraylistWords = auxiliar.init("lista_palabras.txt");
        words = auxiliar.createWords(arraylistWords);
        adjectives = auxiliar.init("lista_adjetivos.txt");
        sustantives = auxiliar.init("lista_sustantivos.txt");        
        verbs = auxiliar.init("lista_verbos.txt");
        
        /*
            Acá se clasifican las palabras en adjetivos, sustantivos o verbos
        */
        for(int i = 0; i < words.size(); i++)
            words.get(i).clasificateWord(sustantives, adjectives, verbs);
        
        
        initialPopulation = iniPupo; 
        numberGenerations = maxGen;
        
        //Generar la población aleatoria de monos
    
        for(int i = 0; i < initialPopulation; i++)
        {
            temporalMonkey.generateRandomMonkey(2);
            monkeys.add(temporalMonkey);
            temporalMonkey = new Monkey();
        }
        
        for(int i = 0; i < monkeys.size();i++)
            monkeys.get(i).printMonkeyPoem(words);
        
        //Calcular la aptitud de los monos
        
        
        
        while(indexGenerations < numberGenerations)
        {
            if(indexGenerations == 0) //Calcular aptitud
            {
            
                for(int i = 0; i < monkeys.size(); i++) //Calcular aptitud
                        monkeys.get(i).calculateAptitudeExperiment2(words);
            }
            
            arraylistSolution = solutionExperiment(monkeys); //Se verifica si unmono en la población es solucion
            
            if(arraylistSolution.size() == 0) //Si el are   arreglo solucion está vacío
            {
                 if(indexGenerations > 0) //Sino no es la generación 0
                 {
                    for(int i = 0; i < monkeys.size(); i++) //Calcular aptitud
                        monkeys.get(i).calculateAptitudeExperiment2(words);
                 }
                 
                 double media = 0;
                 
                 for(int i = 0; i < monkeys.size(); i ++)
                     media += monkeys.get(i).getAptitud();

                 media = media / monkeys.size();
                 medias.add(media);
                 System.out.println("Generación "+ Integer.toString(indexGenerations)+" : "+ Double.toString(media));
                 
                monkeysNextGeneration = new ArrayList<Monkey>();
                indexTorunament = calculatePercentageSelection(monkeys.size());

                for(int i = 0; i <indexTorunament ; i++)
                {
                    //Se toman dos individuos al azar, el que tenga mayor aptitud de pasa a la siguiente generación

                    monkeyTournament1 = rand.nextInt(monkeys.size());
                    monkeyTournament2 = rand.nextInt(monkeys.size());

                    while(monkeyTournament2 == monkeyTournament1)
                        monkeyTournament2 = rand.nextInt(monkeys.size());

                    //Se compara la aptitud de ellos

                    if(monkeyFight(monkeys.get(monkeyTournament1),monkeys.get(monkeyTournament2)) == 1)
                    {
                        monkeysNextGeneration.add(monkeys.get(monkeyTournament1));
                        monkeys.remove(monkeyTournament1);
                    }else
                    {
                        monkeysNextGeneration.add(monkeys.get(monkeyTournament2));
                        monkeys.remove(monkeyTournament2);
                    }
                }

                //Se pasan los monos seleccionados a la nueva generacion
                monkeys = monkeysNextGeneration;
                
                //Se calcula la probabilidad de cruce
                for(int i = 0; i < monkeys.size(); i++)
                    monkeys.get(i).calculateCrossoverProbability();

                //Se obtienen los monos a cruzarse
                numberCrossoverMonkeys = selectCrossoverMonkeys(monkeys);
                
                //Se cruzan los monos
                numberCrossoverMonkeys =  crossoverMonkeysExperiment2(numberCrossoverMonkeys);

                //Se agregan los monos a la generación

                for(int i = 0; i < numberCrossoverMonkeys.size(); i++)
                    monkeys.add(numberCrossoverMonkeys.get(i));

                //Se calcula la probabilidad de mutación
                for(int i = 0; i < monkeys.size(); i++)
                    monkeys.get(i).calculateMutationProbability();
                
                //Se hace el proceso de mutacion
                mutationProcessExperiment2(monkeys);

                indexGenerations++;
            }else
            {
                System.out.println("Encontó solución");
                
                
                if(indexGenerations == 0)
                {
                    double media = 0;

                    for(int i = 0; i < monkeys.size(); i ++)
                        media += monkeys.get(i).getAptitud();

                    media = media / monkeys.size();
                    medias.add(media);
                    System.out.println("Generación "+ Integer.toString(indexGenerations)+" : "+ Double.toString(media));
                }
           
                indexGenerations = numberGenerations;
                    
                for (Integer arraylistSolution1 : arraylistSolution) {
                    monkeys.get(arraylistSolution1).printMonkeyPoem(words); 
                }
                if (arraylistSolution.size()>0){
                    ResultSecondExperiment rfe = new ResultSecondExperiment();
                    rfe.setPoem(monkeys.get(arraylistSolution.get(0)).getMonkeyPoem(words));
                    rfe.setMedia(medias);
                    return rfe;
                }
            }
        }
        ResultSecondExperiment rfe = new ResultSecondExperiment();
        rfe.setPoem(null);
        rfe.setMedia(medias);
        return rfe;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Esta función retorna true si existe un mono con aptitud 0
     * @param monkeys arreglo de monos
     * @return true o false
     * 
     */
    
    public ArrayList<Integer> solutionExperiment(ArrayList<Monkey> monkeys)
    {
        ArrayList<Integer> returnValue = new ArrayList<Integer>();
        
        for(int i = 0; i < monkeys.size(); i++)
        {
            if(monkeys.get(i).getAptitud() == 0)
                returnValue.add(i);
        }
        return returnValue;
    }
    
    
    /**
     * Calcula el 60% de la poblacion para la seleccion
     * @param population numero de individuos en la generacion
     * @return 60% del numero de individuos de la poblacion
     */
    
    public int calculatePercentageSelection(int population)
    {
        if(population <= 60000)
            return (population*60)/100;
        else
            return (population*40)/100;
    }
    
    /**
     * Pone a pelear a monkey1 con monkey2 el que tenga mayor aptitud gana
     * @param monkey1
     * @param monkey2
     * @return 0 si gana monkey1 o 1 si gana monkey2 
     */
    
    public int monkeyFight(Monkey monkey1, Monkey monkey2)
    {
        if(monkey1.getAptitud() >= monkey2.getAptitud())
            return 0;
        else
            return 1;
    }
    
    public ArrayList<Monkey> selectCrossoverMonkeys(ArrayList<Monkey> monkeys)
    {
        ArrayList<Monkey> returnValue = new ArrayList<Monkey>();
        
        for(int i =0; i < monkeys.size(); i++)
        {
            if(monkeys.get(i).getCrossoverProbability() < 0.7)
                returnValue.add(monkeys.get(i));
        }
    
        return returnValue;
    }
    
    public ArrayList<Monkey> crossoverMonkeysExperiment1(ArrayList<Monkey> monkeys)
    {
        ArrayList<Monkey> returnValue = new ArrayList();
        Monkey temporalMonkey1 = new Monkey();
        Monkey temporalMonkey2 = new Monkey();
        
        
        if(monkeys.size()%2 == 0)
        {
            for(int i = 0; i < monkeys.size(); i++)
            {
                temporalMonkey1.getLine().setAdjectiveSubject(monkeys.get(i).getLine().getAdjectiveSubject());
                temporalMonkey1.getLine().setNounSubject(monkeys.get(i+1).getLine().getNounSubject());
                temporalMonkey1.getLine().setVerbPredicate(monkeys.get(i).getLine().getVerbPredicate());
                temporalMonkey1.getLine().setAdjectivePredicate(monkeys.get(i+1).getLine().getAdjectivePredicate());
                temporalMonkey1.getLine().setNounPredicate(monkeys.get(i).getLine().getNounPredicate());
                returnValue.add(temporalMonkey1);
                
                temporalMonkey2.getLine().setAdjectiveSubject(monkeys.get(i+1).getLine().getAdjectiveSubject());
                temporalMonkey2.getLine().setNounSubject(monkeys.get(i).getLine().getNounSubject());
                temporalMonkey2.getLine().setVerbPredicate(monkeys.get(i+1).getLine().getVerbPredicate());
                temporalMonkey2.getLine().setAdjectivePredicate(monkeys.get(i).getLine().getAdjectivePredicate());
                temporalMonkey2.getLine().setNounPredicate(monkeys.get(i+1).getLine().getNounPredicate());
                returnValue.add(temporalMonkey2);
                
                i++;
            }
        }else
        {
            for(int i = 0; i < monkeys.size(); i++)
            {
                if(i < monkeys.size()-1)
                {
                
                    temporalMonkey1.getLine().setAdjectiveSubject(monkeys.get(i).getLine().getAdjectiveSubject());
                    temporalMonkey1.getLine().setNounSubject(monkeys.get(i+1).getLine().getNounSubject());
                    temporalMonkey1.getLine().setVerbPredicate(monkeys.get(i).getLine().getVerbPredicate());
                    temporalMonkey1.getLine().setAdjectivePredicate(monkeys.get(i+1).getLine().getAdjectivePredicate());
                    temporalMonkey1.getLine().setNounPredicate(monkeys.get(i).getLine().getNounPredicate());
                    returnValue.add(temporalMonkey1);

                    temporalMonkey2.getLine().setAdjectiveSubject(monkeys.get(i+1).getLine().getAdjectiveSubject());
                    temporalMonkey2.getLine().setNounSubject(monkeys.get(i).getLine().getNounSubject());
                    temporalMonkey2.getLine().setVerbPredicate(monkeys.get(i+1).getLine().getVerbPredicate());
                    temporalMonkey2.getLine().setAdjectivePredicate(monkeys.get(i).getLine().getAdjectivePredicate());
                    temporalMonkey2.getLine().setNounPredicate(monkeys.get(i+1).getLine().getNounPredicate());
                    returnValue.add(temporalMonkey2);

                    i++;
                }
            }
        }
        
        return returnValue;
    }
    
    public void mutationProcessExperiment1(ArrayList<Monkey> monkeys)
    {
        Random rand = new Random();
        
        
        for(int i = 0; i < monkeys.size(); i++)
        {
            if(monkeys.get(i).getMutationProbability() < 0.1)
            {
                switch(rand.nextInt(5))
                {
                    case 0:
                        
                        monkeys.get(i).getLine().setAdjectiveSubject(rand.nextInt(212));
                        break;
                        
                    case 1:
                        
                        monkeys.get(i).getLine().setNounSubject(rand.nextInt(212));
                        break;
                        
                    case 2:
                        
                        monkeys.get(i).getLine().setVerbPredicate(rand.nextInt(212));
                        break;
                        
                    case 3:
                        
                        monkeys.get(i).getLine().setAdjectivePredicate(rand.nextInt(212));
                        break;
                        
                    case 4:
                        
                        monkeys.get(i).getLine().setNounPredicate(rand.nextInt(212));
                        break;
                }
            }
        }
    }

    public ArrayList<Monkey> crossoverMonkeysExperiment2(ArrayList<Monkey> monkeys)
    {
        ArrayList<Monkey> returnValue = new ArrayList();
        Monkey temporalMonkey1 = new Monkey();
        Monkey temporalMonkey2 = new Monkey();
        Line line = new Line();
        
        
        if(monkeys.size()%2 == 0)
        {
            for(int i = 0; i < monkeys.size(); i++)
            {
                
                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(0).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(0).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(0).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(0).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(0).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                   
                line = new Line();
 
                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(1).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(1).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(1).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(1).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(1).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(2).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(2).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(2).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(2).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(2).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(3).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(3).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(3).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(3).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(3).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                
                
                returnValue.add(temporalMonkey1);
                temporalMonkey1 = new Monkey();
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(0).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(0).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(0).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(0).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(0).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                  
                line = new Line();
 
                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(1).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(1).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(1).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(1).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(1).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(2).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(2).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(2).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(2).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(2).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(3).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(3).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(3).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(3).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(3).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                
                
                returnValue.add(temporalMonkey2);
                temporalMonkey2 = new Monkey();
                line = new Line();
                
                i++;
            }
        }else
        {
            for(int i = 0; i < monkeys.size(); i++)
            {
                if(i < monkeys.size()-1)
                {
                
                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(0).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(0).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(0).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(0).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(0).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                   
                line = new Line();
 
                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(1).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(1).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(1).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(1).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(1).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(2).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(2).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(2).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(2).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(2).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i).getPoem().get(3).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i+1).getPoem().get(3).getNounSubject());
                line.setVerbPredicate(monkeys.get(i).getPoem().get(3).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i+1).getPoem().get(3).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i).getPoem().get(3).getNounPredicate());
                temporalMonkey1.getPoem().add(line);
                
                returnValue.add(temporalMonkey1);
                temporalMonkey1 = new Monkey();
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(0).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(0).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(0).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(0).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(0).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                  
                line = new Line();
 
                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(1).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(1).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(1).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(1).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(1).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(2).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(2).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(2).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(2).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(2).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                    
                line = new Line();

                line.setAdjectiveSubject(monkeys.get(i+1).getPoem().get(3).getAdjectiveSubject());
                line.setNounSubject(monkeys.get(i).getPoem().get(3).getNounSubject());
                line.setVerbPredicate(monkeys.get(i+1).getPoem().get(3).getVerbPredicate());
                line.setAdjectivePredicate(monkeys.get(i).getPoem().get(3).getAdjectivePredicate());
                line.setNounPredicate(monkeys.get(i+1).getPoem().get(3).getNounPredicate());
                temporalMonkey2.getPoem().add(line);
                
                returnValue.add(temporalMonkey2);
                temporalMonkey2 = new Monkey();
                    
                line = new Line();
                
                i++;
                }
            }
        }
        return returnValue;
    }
    
    public void mutationProcessExperiment2(ArrayList<Monkey> monkeys)
    {
        Random rand = new Random();
        
        for(int i = 0; i < monkeys.size(); i++)
        {
            if(monkeys.get(i).getMutationProbability() < 0.1)
            {
                switch(rand.nextInt(4))
                {
                    case 0:
                        
                        switch(rand.nextInt(5))
                        {
                            case 0:
                                
                                monkeys.get(i).getPoem().get(0).setAdjectiveSubject(rand.nextInt(212));
                                break;
                                
                            case 1:
                                monkeys.get(i).getPoem().get(0).setNounSubject(rand.nextInt(212));
                                break;
                                
                            case 2:
                                monkeys.get(i).getPoem().get(0).setVerbPredicate(rand.nextInt(212));
                                break;
                                
                            case 3:
                                monkeys.get(i).getPoem().get(0).setAdjectivePredicate(rand.nextInt(212));
                                break;
                                
                            case 4:
                                monkeys.get(i).getPoem().get(0).setNounPredicate(rand.nextInt(212));
                                break;
                        }
                    
                        break;
                        
                        
                    case 1:
                        
                        switch(rand.nextInt(5))
                        {
                            case 0:
                                
                                monkeys.get(i).getPoem().get(1).setAdjectiveSubject(rand.nextInt(212));
                                break;
                                
                            case 1:
                                monkeys.get(i).getPoem().get(1).setNounSubject(rand.nextInt(212));
                                break;
                                
                            case 2:
                                monkeys.get(i).getPoem().get(1).setVerbPredicate(rand.nextInt(212));
                                break;
                                
                            case 3:
                                monkeys.get(i).getPoem().get(1).setAdjectivePredicate(rand.nextInt(212));
                                break;
                                
                            case 4:
                                monkeys.get(i).getPoem().get(1).setNounPredicate(rand.nextInt(212));
                                break;
                        }
                    
                        break;
                        
                    case 2:
                        
                        switch(rand.nextInt(5))
                        {
                            case 0:
                                
                                monkeys.get(i).getPoem().get(2).setAdjectiveSubject(rand.nextInt(212));
                                break;
                                
                            case 1:
                                monkeys.get(i).getPoem().get(2).setNounSubject(rand.nextInt(212));
                                break;
                                
                            case 2:
                                monkeys.get(i).getPoem().get(2).setVerbPredicate(rand.nextInt(212));
                                break;
                                
                            case 3:
                                monkeys.get(i).getPoem().get(2).setAdjectivePredicate(rand.nextInt(212));
                                break;
                                
                            case 4:
                                monkeys.get(i).getPoem().get(2).setNounPredicate(rand.nextInt(212));
                                break;
                        }
                    
                        break;
                        
                    case 3:
                        
                        switch(rand.nextInt(5))
                        {
                            case 0:
                                
                                monkeys.get(i).getPoem().get(3).setAdjectiveSubject(rand.nextInt(212));
                                break;
                                
                            case 1:
                                monkeys.get(i).getPoem().get(3).setNounSubject(rand.nextInt(212));
                                break;
                                
                            case 2:
                                monkeys.get(i).getPoem().get(3).setVerbPredicate(rand.nextInt(212));
                                break;
                                
                            case 3:
                                monkeys.get(i).getPoem().get(3).setAdjectivePredicate(rand.nextInt(212));
                                break;
                                
                            case 4:
                                monkeys.get(i).getPoem().get(3).setNounPredicate(rand.nextInt(212));
                                break;
                        }
                    
                        break;
                }
            }
        }
    }
}
