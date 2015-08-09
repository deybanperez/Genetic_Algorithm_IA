package Proyecto_II;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Word
{
    private String value;
    private char type;
    private ArrayList<String> syllables;
    private char clasification;
    
    
    /**
     * Constructor de la clase
     */
    
    Word()
    {
        this.value = new String();
        this.syllables = new ArrayList<String>();
    }
    
    /**
     * Dada una palabra la procesa y asigna valores al objeto
     * @param word String con la palabra
     */
    
    public void setValues(String word)
    {
        this.value=word;
        this.syllables = splitSyllables(word);
        this.type = typeSyllables(this.syllables);
    }
    
    /**
     * Retorna el campo value del objeto
     * @return el campo value
     */
    
    public String getName()
    {
        return this.value;
    }
    
    /**
     *  Retorna el campo type del objeto con respecto a aguda, grave o esdrújula
     * @return el atributo type del objeto
     */
    
    public char getType()
    {
        return this.type;
    }
    
    /**
     * Retorna la clasificación con respecto a adjetivo, verbo, sustantivo
     * @return el atributo clasificación
     */
    
    public char getClasification()
    {
        return this.clasification;
    }
    
    /**
     * Retorna el arreglo de sílabas del objeto
     * @return el arreglo de silabas del objeto
     */
    
    public ArrayList<String> getSyllables()
    {
        return this.syllables;
    }
    
    /**
     * Toma una palabra y la pica en silabas
     * @param word es la palabra a separar en silabas
     * @return un arreglo con las silabas
     */
    
    public ArrayList<String> splitSyllables(String word)
    {
        ArrayList<String> returnValue = new ArrayList<String>();
        String temporalSyllables = new String();
        
        if(word.length() == 1)
        {
            returnValue.add(word);
            return returnValue;    
        }else
        {   
            for(int i = 0; i < word.length(); i++)
            {
                if(i == 0)//La primera letra siempre se agrega
                {
                     temporalSyllables += word.charAt(i); //Agrego la letra a la sílaba
                }else
                {
                    if(isVowel(word.charAt(i))) //Si la letra es una vocal, evaluar las posibles combinaciones evaluando hacia adelante y hacia atrás
                    {
                        if(!isStrong(word.charAt(i))) //Si es una vocal debil siempre se agrega la letra a la sílaba
                            temporalSyllables += word.charAt(i);
                        else                                    //Si es una vocal fuerte, deben evaluarse las posibilidades
                        {
                            if(!isStrong(previousChar(word,i)) || !isVowel(previousChar(word,i)))     //Si la letra anterior es debil o una consonante, agregar
                                temporalSyllables += word.charAt(i);
                            else                                //Si la vocal anterior es fuerte, entonces separamos la sílaba, la encolamos y agregamos a una silaba nueva
                            {
                                returnValue.add(temporalSyllables);
                                temporalSyllables = new String();
                                temporalSyllables+=word.charAt(i);
                            }
                        }                    
                    }else   //Si la letra es una consonante, evaluar las posibles combinaciones
                    {
                        
                        if(nextChar(word,i) != '?' ) //Si existe una siguiente letra
                        {
                            if( isVowel(previousChar(word,i)) && isVowel(nextChar(word,i))) //Si está rodeada por vocales, separar y agregar
                            {
                                returnValue.add(temporalSyllables);
                                temporalSyllables = new String();
                                temporalSyllables+=word.charAt(i);
                            
                            }else if(isVowel(previousChar(word,i)) && !isVowel(nextChar(word,i))) //Si a la izquierda tiene una vocal y a la derecha una consonante, ver si es r,c,l
                            {
                                if(word.charAt(i) == 'r' || word.charAt(i) == 'l' || word.charAt(i) == 'c') //Si son letras que se pueden combinar por pares
                                {
                                    if(word.charAt(i) == nextChar(word,i)) //Separar y agregar
                                    {
                                        returnValue.add(temporalSyllables);
                                        temporalSyllables = new String();
                                        temporalSyllables+=word.charAt(i);
                                    
                                    }else if ((word.charAt(i) == 'c' && nextChar(word,i) == 'h') || (word.charAt(i) == 'c' && nextChar(word,i) == 'r'))
                                    {
                                        returnValue.add(temporalSyllables);
                                        temporalSyllables = new String();
                                        temporalSyllables+=word.charAt(i);
                                    
                                    }else
                                        temporalSyllables+=word.charAt(i);
                              
                                }else if((word.charAt(i)== 'g' && nextChar(word,i) == 'r') || (word.charAt(i)== 'b' && nextChar(word,i) == 'r') || (word.charAt(i)== 'd' && nextChar(word,i) == 'r') || ((word.charAt(i)== 'p' && nextChar(word,i) == 'r')) || (word.charAt(i)== 'b' && nextChar(word,i) == 'l') || (word.charAt(i)== 'f' && nextChar(word,i) == 'r'))
                                {
                                    returnValue.add(temporalSyllables);
                                    temporalSyllables = new String();
                                    temporalSyllables+=word.charAt(i);
                                }else
                                    temporalSyllables+=word.charAt(i);
                            
                            }else if(!isVowel(previousChar(word,i)) && isVowel(nextChar(word,i))) //Si a la izquierda tiene consonante y a la derecha una vocal
                            {
                                if((word.charAt(i) == 'r' || word.charAt(i) == 'l' || word.charAt(i) == 'c'))
                                {
                                    if(previousChar(word,i) == word.charAt(i) || previousChar(word,i) == 't' || previousChar(word,i) == 'p' || previousChar(word,i) == 'c' || previousChar(word,i) == 'b' || previousChar(word,i) == 'g'  || previousChar(word,i) == 'f' || previousChar(word,i) == 'd') //Si la consonante actual es igual a la consonante anterior, agregar letra a la sílaba
                                        temporalSyllables+=word.charAt(i);
                                    else
                                    {
                                        returnValue.add(temporalSyllables);
                                        temporalSyllables = new String();
                                        temporalSyllables+=word.charAt(i);
                                    }
                                }
                                else    //Si la consonante anterior es diferente a la actual, separar y agregar
                                {
                                    if(previousChar(word,i) == 'c')
                                    {
                                        if(word.charAt(i) == 't')
                                        {
                                             returnValue.add(temporalSyllables);
                                            temporalSyllables = new String();
                                            temporalSyllables+=word.charAt(i);
                                        }else                                
                                            temporalSyllables+=word.charAt(i);
                                    
                                    }else
                                    {
                                        returnValue.add(temporalSyllables);
                                        temporalSyllables = new String();
                                        temporalSyllables+=word.charAt(i);
                                    }
                                }
                            }else //Si está rodeada de consonantes
                            {
                                if(nextChar(word,i) == 'r' || nextChar(word,i) == 'l')
                                {
                                    returnValue.add(temporalSyllables);
                                    temporalSyllables = new String();
                                    temporalSyllables+=word.charAt(i);
                                }
                            
                            }
                        }else //Si no existe siguiente letra, agregar siempre
                            temporalSyllables+=word.charAt(i);
                    }
                }
            }
            
            if(!temporalSyllables.isEmpty()) //Si el string de sílabas no está vacío, entonces lo agrego a la lista
            {
                returnValue.add(temporalSyllables);
                temporalSyllables=null;
            }
        }
        return returnValue;
    }
    
    /**
     * Retorna verdad si una letra es una vocal, falso en caso contrario
     * @param letter es la letra
     * @return retorna verdad o falso
     */
    
    public boolean isVowel(char letter) //Retorna true si la letra es una vocal, false en caso contrario
    {
        if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u' || letter == 'á' || letter == 'é' || letter == 'í' || letter == 'ó' || letter == 'ú')
            return true;
        
        return false;              
    }
    
    /**
     * Retorna verdad si una vocal es fuerte y false en caso contrario
     * @param letter letra
     * @return verdad o falso
     */
    
    public boolean isStrong(char letter) //Retorna true si la vocal es una vocal fuerte, false en caso contrario
    {
        if(letter == 'a' || letter == 'e' || letter == 'o' || letter == 'á' || letter == 'é' || letter == 'í' || letter == 'ó' || letter == 'ú')
            return true;
        
        return false;
    }
    
    /**
     * Retorna la siguiente letra de una palabra si existe
     * @param word palabra 
     * @param index indice actual 
     * @return la siguiente letra si existe o '?' si no existe
     */
    
    public char nextChar(String word, int index) //Retorna el siguiente caracter de existir, y retorna el caracter ´?´ si no existe caracter siguiente
    {
        if(index+1 < word.length())
            return word.charAt(index+1);
        
        return '?';
    }
    
    
    /**
     * Retorna la letra anterior a index en la palabra
     * @param word palabra
     * @param index indice
     * @return la letra anterior si existe o '?' si no existe
     */
    
    public char previousChar(String word, int index) //Retorna el caracter pervio, y retorna '?' si no existe caracter previo
    {
        if(index-1 >= 0)
            return word.charAt(index-1);
        
        return '?';
    }
    
    /**
     * Retorna true si la vocal tiene una tilde, falso en caso contrario
     * @param letter letra
     * @return true o false
     */
    
    public boolean isTilde(char letter) //Retorna True si al vocal tiene tilde, false en caso contrario
    {
        if(letter == 'á' || letter == 'é' || letter == 'í' || letter == 'ó' || letter == 'ú')
            return true;
        
        return false;
    }
    
    /**
     * Retorna true si la silaba tiene alguna letra con tilde
     * @param syllable silaba
     * @return true o false
     */
    
    public boolean hasTilde(String syllable) //Retorna true si la sílaba tiene alguna letra con tilde, false en caso congtr
    {
        for(int i = 0; i < syllable.length(); i++)
        {
            if(isTilde(syllable.charAt(i)))
                return true;
        }
        
        return false;
    }
    
    /**
     * Retorna la última letra de una silaba
     * @param syllable silaba
     * @return la ultima letra
     */
    
    public char lastChar(String syllable) //Retorna la última letra de una sílaba
    {
        return syllable.charAt(syllable.length()-1);
    }
    
    public char typeSyllables(ArrayList<String> syllables) //Retorna el tipo de palabra (agura: 'a', grave: 'g', esdrújula: 'e')
    {
        if(syllables.size() == 1)
            return 'a';
        
        else if(syllables.size() == 2)
        {
           if(hasTilde(syllables.get(syllables.size()-1)))
               return 'a';
           else if(hasTilde(syllables.get(syllables.size()-2)))
               return 'g';
           else 
           {
               if(lastChar(syllables.get(syllables.size()-1)) == 'n' || lastChar(syllables.get(syllables.size()-1)) == 's' || isVowel(lastChar(syllables.get(syllables.size()-1))))
                   return 'g';
               else
                   return 'a';   
           }
        }else
        {
            if(hasTilde(syllables.get(syllables.size()-1)))
               return 'a';
            else if(hasTilde(syllables.get(syllables.size()-2)))
               return 'g';
            else if(hasTilde(syllables.get(syllables.size()-3)))
                return 'e';
            else
            {
                if(lastChar(syllables.get(syllables.size()-1)) == 'n' || lastChar(syllables.get(syllables.size()-1)) == 's' || isVowel(lastChar(syllables.get(syllables.size()-1))))
                   return 'g';
               else
                   return 'a';
            }
        }  
    }
    
    /**
     * Toma una lista de palabras y las clasifica
     * @param words lista de palabras
     * @return arreglo de objetos de palabras
     */
    
    public ArrayList<Word> createWords(ArrayList<String> words)
    {
        ArrayList<Word> returnValue = new ArrayList<Word>();
        Word temporalWord = new Word();
        
        for(int i = 0; i < words.size(); i++)
        {
            temporalWord.setValues(words.get(i));
            returnValue.add(temporalWord);
            temporalWord = new Word();
        }
        return returnValue;
    }
    
    /**
     * Dado una ruta de archivo, el archivo se abre y se crea una lista con todas las palabras que hay en ela rchivo
     * @param path ruta al archivo
     * @return una arraylist con las palabras
     */
    
    public ArrayList<String> init(String path)
    {
        ArrayList<String> returnValue = new ArrayList<String>();
        File file = null;
        BufferedReader bufferedReader = null;
        String line;

        try 
        {
           file = new File (path);
           bufferedReader = new BufferedReader(
		   new InputStreamReader(
                      new FileInputStream(file), "ISO-8859-1"));
           // Lectura del fichero, toma los parametros n.m y limit
           
           while((line=bufferedReader.readLine()) != null)
               returnValue.add(line);
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{                    
              if( null != bufferedReader ){   
                 bufferedReader.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
        return returnValue;
   }
    
    /**
     * Retorna true si una palabra esta en una lists, false en caso contrario
     * @param words palabra a buscar
     * @return true o false
     */
    
    public boolean findWord(ArrayList<String> words)
    {
        for(int i = 0; i < words.size(); i++)
        {
            if(this.value.equals(words.get(i)))
                return true;
        }
    
        return false;
    }
    
    /**
     * Clasifica una palabra por sujeto, adjetivo o verbo
     * @param nouns lista de sujetos
     * @param adjectives lista de adjetivos
     * @param verbs lista de verbos
     */
    
    public void clasificateWord(ArrayList<String> nouns, ArrayList<String> adjectives, ArrayList<String> verbs)
    {
        if(findWord(nouns))
            this.clasification = 'n';
        
        else if(findWord(adjectives))
            this.clasification = 'a';
        else
            this.clasification = 'v'; 
    }
}
