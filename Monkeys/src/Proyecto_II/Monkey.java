package Proyecto_II;

import java.util.ArrayList;
import java.util.Random;

public class Monkey 
{
    private Line line; //Acá iría la línea del experimento 1
    private ArrayList<Line> poem; // Acá iría el poema del experimento 2
    private int aptitude;
    private double crossoverProbability;
    private double mutationProbability;
    

    public Monkey()
    {
        this.line = new Line();
        this.poem = new ArrayList<Line>();
        this.aptitude = -1;
        this.crossoverProbability = 0;
        this.mutationProbability = 0;
    }
    
    /**
     * 
     * @return 
     */
    
    public Line getLine()
    {
        return this.line;
    }
    
    public ArrayList<Line> getPoem()
    {
        return this.poem;
    }
    
    public int getAptitud()
    {
        return this.aptitude;
    }
    
    public double getCrossoverProbability()
    {
        return this.crossoverProbability;
    }
    
    public double getMutationProbability()
    {
        return this.mutationProbability;
    }
    
    /**
     * Genera un mono aleatorio
     * @param exp representa el tipo de experimento
     */
    
    public void generateRandomMonkey(int exp)
    {
        if(exp == 1)
            this.line = line.generateRandomLine();
        else
            this.poem = generateRandomPoem();
    }
    
    /**
     * Genera un poema aleatorio
     * @return retorna el poema
     */
    
    public ArrayList<Line> generateRandomPoem()
    {
        ArrayList<Line> poem = new ArrayList<Line>();
        Line line = new Line();
        
        for(int i =0; i < 4; i++)
            poem.add(line.generateRandomLine());
        
        return poem;
    }
    
    /**
     * Imprime el atributo line del objeto con formato
     * @param words representa la lista de palabras
     */
        
    public void printMonkeyLine(ArrayList<Word> words)
    {
        if(this.line.getAdjectiveSubject() != 211)
            System.out.print(words.get(this.line.getAdjectiveSubject()).getName() + " ");
        
        if(this.line.getNounSubject() != 211)
            System.out.print(words.get(this.line.getNounSubject()).getName() + " ");
        
        if(this.line.getVerbPredicate() != 211)
            System.out.print(words.get(this.line.getVerbPredicate()).getName() + " ");
        
        if(this.line.getAdjectivePredicate() != 211)
            System.out.print(words.get(this.line.getAdjectivePredicate()).getName() + " ");
        
        if(this.line.getNounPredicate() != 211)
            System.out.print(words.get(this.line.getNounPredicate()).getName() + "\n");
        
    }
    
    public void printMonkeyPoem(ArrayList<Word> words)
    {
        for(int i = 0; i < 4; i++)
        {
            if(this.poem.get(i).getAdjectiveSubject() != 211)
            System.out.print(words.get(this.poem.get(i).getAdjectiveSubject()).getName() + " ");
        
            if(this.poem.get(i).getNounSubject() != 211)
                System.out.print(words.get(this.poem.get(i).getNounSubject()).getName() + " ");

            if(this.poem.get(i).getVerbPredicate() != 211)
                System.out.print(words.get(this.poem.get(i).getVerbPredicate()).getName() + " ");

            if(this.poem.get(i).getAdjectivePredicate() != 211)
                System.out.print(words.get(this.poem.get(i).getAdjectivePredicate()).getName() + " ");

            if(this.poem.get(i).getNounPredicate() != 211)
                System.out.print(words.get(this.poem.get(i).getNounPredicate()).getName() + "\n");
        }
        
        System.out.print("\n");
    }
    
    
    /**
     * Dada una lista de palabras, calcula la aptitud de un mono
     * @param words representa la lista de palabras
     */
    
    public void calculateAptitudeExperiment1(ArrayList<Word> words)
    {
        this.aptitude = 0;
        
        if(this.line.getAdjectiveSubject() != 211)
        {
            if(words.get(this.line.getAdjectiveSubject()).getClasification() != 'a')
                this.aptitude++;
        }
        
        if(this.line.getNounSubject() == 211)
            this.aptitude++;
        else
        {
            if(words.get(this.line.getNounSubject()).getClasification() != 'n')
                this.aptitude++;
        }
        
        if(this.line.getVerbPredicate()== 211)
            this.aptitude++;
        else
        {
            if(words.get(this.line.getVerbPredicate()).getClasification() != 'v')
                this.aptitude++;
        }
        
        
        if(this.line.getAdjectivePredicate() != 211)
        {
            if(words.get(this.line.getAdjectivePredicate()).getClasification() != 'a')
                this.aptitude++;
        }
        
        if(this.line.getNounPredicate() == 211)
            this.aptitude++;
        else
        {
            if(words.get(this.line.getNounPredicate()).getClasification() != 'n')
                this.aptitude++;
        }
    }
    
       public void calculateAptitudeExperiment2(ArrayList<Word> words)
       {
           this.aptitude = 0; 
           ArrayList<Integer> aux1 = new ArrayList<Integer>();
           ArrayList<Integer> aux2 = new ArrayList<Integer>();
           boolean linesRhyme = false;
           
           // Primero debo calcular que cada oración esté bien formada
           
           for(int i = 0; i < 4; i++)
           {
               if(this.poem.get(i).getAdjectiveSubject() != 211)
                {
                    if(words.get(this.poem.get(i).getAdjectiveSubject()).getClasification() != 'a')
                        this.aptitude++;
                }

                if(this.poem.get(i).getNounSubject() == 211)
                    this.aptitude++;
                else
                {
                    if(words.get(this.poem.get(i).getNounSubject()).getClasification() != 'n')
                        this.aptitude++;
                }

                if(this.poem.get(i).getVerbPredicate()== 211)
                    this.aptitude++;
                else
                {
                    if(words.get(this.poem.get(i).getVerbPredicate()).getClasification() != 'v')
                        this.aptitude++;
                }


                if(this.poem.get(i).getAdjectivePredicate() != 211)
                {
                    if(words.get(this.poem.get(i).getAdjectivePredicate()).getClasification() != 'a')
                        this.aptitude++;
                }

                if(this.poem.get(i).getNounPredicate() == 211)
                    this.aptitude++;
                else
                {
                    if(words.get(this.poem.get(i).getNounPredicate()).getClasification() != 'n')
                        this.aptitude++;
                }
           }
           
           
           
           //Debo calcular que la la linea 1 rime con la 3, y la linea 2 con la 4
           
           if(this.poem.get(0).getNounPredicate() == 211 || this.poem.get(2).getNounPredicate() == 211)
               this.aptitude+=2;
           else
           {
               aux1 = rhyme(this.getPoem().get(0),this.getPoem().get(2),words);
               
               if(aux1.isEmpty())
                   this.aptitude+=2;
               
           }
               
           if(this.poem.get(1).getNounPredicate() == 211 || this.poem.get(3).getNounPredicate() == 211)
               this.aptitude+=2;
           else
           {
               aux2 = rhyme(this.getPoem().get(1),this.getPoem().get(3),words);
               
               if(aux2.isEmpty())
                   this.aptitude+=2;
           }
           
           if(!aux1.isEmpty() && !aux2.isEmpty())
           {
               for(int i = 0; i < aux1.size(); i++)
               {
                   for(int j = 0; j < aux2.size(); j++)
                   {
                       if(aux1.get(i) == aux2.get(j))
                           linesRhyme = true;
                   }
               }
               
               if(!linesRhyme)
                   this.aptitude+= 4;
           }else
               this.aptitude+=4;
           
           
           //aux1 = rhyme(this.getPoem()[0],this.getPoem()[2],words);
           //aux2 = rhyme(this.getPoem()[1],this.getPoem()[3],words);
           
           
           
           
           
           //Debo calcular la diferencia de silabas entre cada linea
           
           this.aptitude += getSyllablesDistance(this.poem,words);
       
       
       
       
       }
    
    /**
     * Calcula la probabilidad de cruce de un mono
     */
    
    public void calculateCrossoverProbability()
    {
        Random rand = new Random();
        
        this.crossoverProbability = rand.nextDouble();
    }
    
    public void calculateMutationProbability()
    {
        Random rand = new Random();
        
        this.mutationProbability = rand.nextDouble();
    }
    
    public ArrayList<Integer> rhyme(Line line1, Line line2, ArrayList<Word> words)
    {
        ArrayList<Integer> returnValue = new ArrayList<Integer>();
        String auxiliar1 = new String();
        String auxiliar2 = new String();
        int indexRhyme1, indexRhyme2;
        
        indexRhyme1 = getIndexRhyme(line1, words);
        indexRhyme2 = getIndexRhyme(line2, words);
        
        if(indexRhyme1 != -1 && indexRhyme2 != -1)  //Rima asonante
        {
            auxiliar1 = getVowels(line1, indexRhyme1,words);
            auxiliar2 = getVowels(line2, indexRhyme2,words);
            
        
            if(sameVowels(auxiliar1,auxiliar2))
                returnValue.add(1);
        
        
            //Rima Consonante
            auxiliar1 = getConsonants(line1, getIndexRhyme(line1, words),words);
            auxiliar2 = getConsonants(line2, getIndexRhyme(line2,words),words);

            if(auxiliar1.equals(auxiliar2))
                returnValue.add(2);
            
        }
        
        return returnValue;
    }
    
    public int getIndexRhyme(Line line, ArrayList<Word> words)
    {
        switch(words.get(line.getNounPredicate()).getType())
        {
            case 'a':
                
                if(line.getNounPredicate() != 211)
                    return words.get(line.getNounPredicate()).getSyllables().size()-1;
                else
                    return -1;
                
            case 'g':
                if(line.getNounPredicate() != 211)
                    return words.get(line.getNounPredicate()).getSyllables().size()-2;
                else
                    return -1;
                
            case 'e':
                if(line.getNounPredicate() != 211)
                    return words.get(line.getNounPredicate()).getSyllables().size()-3;
                else
                    return -1;
        }
        return -1;
    }
    
    public String getVowels(Line line, int index, ArrayList<Word> words)
    {
        String returnValue = new String();
        char auxiliar;
    
        if((auxiliar = lastVowel(line, index, words)) != '?')
            returnValue += auxiliar;
        
        for(int i = index + 1; i < words.get(line.getNounPredicate()).getSyllables().size(); i++)
        {
            for(int j = 0; j < words.get(line.getNounPredicate()).getSyllables().get(i).length(); j++)
            {
                if(isVowel(words.get(line.getNounPredicate()).getSyllables().get(i).charAt(j)))
                    returnValue += words.get(line.getNounPredicate()).getSyllables().get(i).charAt(j);
            }
        }
   
        return returnValue;
    }
    
    public char lastVowel(Line line, int index, ArrayList<Word> words)
    {
        
        for(int i = words.get(line.getNounPredicate()).getSyllables().get(index).length()-1; i >= 0; i--)
        {
            if(isVowel(words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i)))
            {
                if(i-1 >= 0)
                {
                    
                    if(isVowel(words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i-1)))
                    {
                        if(isStrong(words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i-1)))
                            return words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i-1);   
                    }
                }
                
            }else
                return words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i);   
        }
        
        return '?';
    }
    
    public boolean isVowel(char letter) //Retorna true si la letra es una vocal, false en caso contrario
    {
        if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u' || letter == 'á' || letter == 'é' || letter == 'í' || letter == 'ó' || letter == 'ú')
            return true;
        
        return false;              
    }
    
    public boolean sameVowels(String word1, String word2)
    {
        int auxiliar = 0;
        
        if(word1.length() == word2.length())
        {
            for(int i  = 0; i < word1.length(); i++)
            {
                if( (word1.charAt(i) == 'a' || word1.charAt(i) == 'á') && (word2.charAt(i) == 'a' || word2.charAt(i) == 'á'))
                    auxiliar++;
                
                if( (word1.charAt(i) == 'e' || word1.charAt(i) == 'é') && (word2.charAt(i) == 'e' || word2.charAt(i) == 'é'))
                    auxiliar++;
                
                if( (word1.charAt(i) == 'i' || word1.charAt(i) == 'í') && (word2.charAt(i) == 'i' || word2.charAt(i) == 'í'))
                    auxiliar++;
                
                if( (word1.charAt(i) == 'o' || word1.charAt(i) == 'ó') && (word2.charAt(i) == 'o' || word2.charAt(i) == 'ó'))
                    auxiliar++;
                
                if( (word1.charAt(i) == 'u' || word1.charAt(i) == 'ú') && (word2.charAt(i) == 'u' || word2.charAt(i) == 'ú'))
                    auxiliar++;
            }
        }
        
        if(auxiliar == word1.length())
            return true;
        else
            return false;
    }
    
    public String getConsonants(Line line, int index, ArrayList<Word> words)
    {
        String returnValue = new String();
        
        returnValue = getLatestConsonants(line,index,words);
    
        for(int i = index + 1; i < words.get(line.getNounPredicate()).getSyllables().size(); i++)
        {
            for(int j = 0; j < words.get(line.getNounPredicate()).getSyllables().get(i).length(); j++)
            {
                if(!isVowel(words.get(line.getNounPredicate()).getSyllables().get(i).charAt(j)))
                    returnValue += words.get(line.getNounPredicate()).getSyllables().get(i).charAt(j);
            }
        }
        
        return returnValue;
    }
    
    public String getLatestConsonants(Line line, int index, ArrayList<Word> words)
    {
        String returnValue = new String();
        int auxiliar = 0;
        
        for(int i = words.get(line.getNounPredicate()).getSyllables().get(index).length()-1; i >= 0; i--)
        {
            if(isVowel(words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i)))
                auxiliar = i+1;   
        }
        
        for(int i = auxiliar; i < words.get(line.getNounPredicate()).getSyllables().get(index).length(); i++ )
        {
            if(!isVowel(words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i)))
                returnValue += words.get(line.getNounPredicate()).getSyllables().get(index).charAt(i);
        }
        
        return returnValue;
    }
    
    public int getSyllablesDistance(ArrayList<Line> poem,ArrayList<Word> words)
    {
        int returnValue = 0, aux1, aux2;
        
        for(int i = 0; i < 4; i++)
        {
            switch(i)
            {
                case 0:
                    
                    aux1 = getNumSyllables(poem.get(0), words);
                    
                    for(int j = 1; j < 4; j++)
                    {
                        aux2 = getNumSyllables(poem.get(j), words);
                        
                        if(aux1 != aux2)
                            returnValue += Math.abs(aux1 - aux2);
                    }
                    break;
                    
                case 1:
                    
                    aux1 = getNumSyllables(poem.get(1), words);
                    
                    for(int j = 2; j < 4; j++)
                    {
                        aux2 = getNumSyllables(poem.get(j), words);
                        
                        if(aux1 != aux2)
                            returnValue += Math.abs(aux1 - aux2);
                    }
                    break;
                    
                case 2:
                    
                    aux1 = getNumSyllables(poem.get(2), words);
                    
                    for(int j = 3; j < 4; j++)
                    {
                        aux2 = getNumSyllables(poem.get(j), words);
                        
                        if(aux1 != aux2)
                            returnValue += Math.abs(aux1 - aux2);
                    }
                    break;
            }
        }
        return returnValue;
    }
    
    
    
    public int getNumSyllables(Line line, ArrayList<Word> words)
    {
        int returnValue = 0;
        
        if(line.getAdjectiveSubject() != 211)
            returnValue += words.get(line.getAdjectiveSubject()).getSyllables().size();
        
        if(line.getNounSubject() != 211)
            returnValue += words.get(line.getNounSubject()).getSyllables().size();
        
        if(line.getVerbPredicate() != 211)
            returnValue += words.get(line.getVerbPredicate()).getSyllables().size();
        
        if(line.getAdjectivePredicate() != 211)
            returnValue += words.get(line.getAdjectivePredicate()).getSyllables().size();
        
        if(line.getNounPredicate() != 211)
        {
            returnValue += words.get(line.getNounPredicate()).getSyllables().size();
            
            if(words.get(line.getNounPredicate()).getType() == 'a')
                returnValue++;
            
            if(words.get(line.getNounPredicate()).getType() == 'e')
                returnValue--;
        }
    
        return returnValue;
    }
    
    public boolean isStrong(char letter) //Retorna true si la vocal es una vocal fuerte, false en caso contrario
    {
        if(letter == 'a' || letter == 'e' || letter == 'o' || letter == 'á' || letter == 'é' || letter == 'í' || letter == 'ó' || letter == 'ú')
            return true;
        
        return false;
    }
    
     public String getMonkeyLine(ArrayList<Word> words)
    {
        String line = "";
        if(this.line.getAdjectiveSubject() != 211)
            line += (words.get(this.line.getAdjectiveSubject()).getName() + " ");
        
        if(this.line.getNounSubject() != 211)
            line += (words.get(this.line.getNounSubject()).getName() + " ");
        
        if(this.line.getVerbPredicate() != 211)
            line += (words.get(this.line.getVerbPredicate()).getName() + " ");
        
        if(this.line.getAdjectivePredicate() != 211)
            line += (words.get(this.line.getAdjectivePredicate()).getName() + " ");
        
        if(this.line.getNounPredicate() != 211)
           line += (words.get(this.line.getNounPredicate()).getName() + "\n");
        
        return line;
    }
     
     public ArrayList<String> getMonkeyPoem(ArrayList<Word> words)
    {
        ArrayList<String> poem = new ArrayList<String>();
        for(int i = 0; i < 4; i++)
        {
            String sentence = "";
            if(this.poem.get(i).getAdjectiveSubject() != 211)
            sentence += words.get(this.poem.get(i).getAdjectiveSubject()).getName() + " ";
        
            if(this.poem.get(i).getNounSubject() != 211)
                sentence += words.get(this.poem.get(i).getNounSubject()).getName() + " ";

            if(this.poem.get(i).getVerbPredicate() != 211)
                sentence += words.get(this.poem.get(i).getVerbPredicate()).getName() + " ";

            if(this.poem.get(i).getAdjectivePredicate() != 211)
                sentence += words.get(this.poem.get(i).getAdjectivePredicate()).getName() + " ";

            if(this.poem.get(i).getNounPredicate() != 211)
                sentence += words.get(this.poem.get(i).getNounPredicate()).getName() + "\n";
            poem.add(sentence);
        }
        return poem;
    }
}
