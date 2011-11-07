package compiladores;

import constantes.Letras;
import enumeraciones.Alfabetos;
import excepciones.CaracterNoValidoEnExpresionRegularException;
import excepciones.ExpresionIncorrectaException;

public class Alfabeto {
	public static String SLetrasMin = "abcdefghijklmnopqrstuvwxyz";
	public static String SNumeros = "0123456789";
	public static String SLetrasMay = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String SEspeciales = " {}()|*?+\\";
    public static String SAgrupadores = "[]-";


	static Alfabeto especial = new Alfabeto(Alfabetos.ESPECIALES);
	
	public char[] letras;
        public Alfabetos alfabetos;

	public Alfabeto() {
		this.letras = "".toCharArray();
	}
        
        public Alfabeto(Alfabeto alf){
            this.letras = alf.letras;
            this.alfabetos = alf.alfabetos;
        }

	public Alfabeto(Alfabetos alfabeto) {
		alfabetos = alfabeto;
                //TODO ARREGLAR PARA QUE CIERTOS ALFABETOS NO TENGAN LOS SESPECIALES QUE SERAN USADOS EN LOS FA 
		if (alfabeto == Alfabetos.LETRAS_MINUSCULAS)
			letras = (SLetrasMin + constantes.Letras.empty).toCharArray();
		if (alfabeto == Alfabetos.LETRAS_MAYUSCULAS)
			letras = (SLetrasMay + constantes.Letras.empty ).toCharArray();
		if (alfabeto == Alfabetos.LETRAS_TODAS)
			letras = (SLetrasMin + SLetrasMay  + constantes.Letras.empty).toCharArray();
		if (alfabeto == Alfabetos.NUMEROS)
			letras = (SNumeros + constantes.Letras.empty ).toCharArray();
		if (alfabeto == Alfabetos.MAY_MIN_NUMEROS)
			letras = (SLetrasMin + SLetrasMay + SNumeros + constantes.Letras.empty)
					.toCharArray();
		if (alfabeto == Alfabetos.ESPECIALES)
			letras = ( SEspeciales +SAgrupadores+ constantes.Letras.empty).toCharArray();  
		if (alfabeto == Alfabetos.AYB)
			letras = ("ab" + constantes.Letras.empty).toCharArray();
		if (alfabeto == Alfabetos.CyU)
			letras = ("01" + constantes.Letras.empty).toCharArray();
	}
        public void agregarLetras(String aAgregar){
            String actuales = String.valueOf(this.letras);
            actuales = actuales + aAgregar;
            this.letras = actuales.toCharArray();
                    
        }
        public Alfabeto(char[] nAlfabeto)
        		throws CaracterNoValidoEnExpresionRegularException, ExpresionIncorrectaException {
        	Alfabeto alf = new Alfabeto(Alfabetos.ESPECIALES);
        	Alfabeto aRet= null;
        	boolean agrupador = false;
        	int agrupadorCount = 0;
        	this.letras = "".toCharArray();
        	String entreCorchetes ="";
                
        	for (int j = 0; j< nAlfabeto.length; j++) {
        		char letra = nAlfabeto[j];
                        
                       
                        if (letra == '\\'){
                            if (j+1 < nAlfabeto.length){
                                this.letras = (String.valueOf(this.letras) + nAlfabeto[j+1]).toCharArray();
                                j++;
        			continue;
                            }else{
                                throw new ExpresionIncorrectaException();
                            }
                        }
                    
                    
                        if (letra == '['){
        			agrupadorCount++;
        			agrupador = true;
        			continue;
        		}
        		if (alf.contiene(letra) && !agrupador && !Alfabeto.SAgrupadores.contains(String.valueOf(letra))){
                            if (!(j>0 && nAlfabeto[j-1] == '\\')){
                                throw new CaracterNoValidoEnExpresionRegularException();
                            }
                        }
        			

        		if (letra == ']'){
        			agrupadorCount--;
        			if (agrupadorCount!=0){
        				throw new ExpresionIncorrectaException();
        			}
        			agrupador = false;
        			String[] spliteado = entreCorchetes.split("-");

        			for (int i=0; i< spliteado.length-1 ; i++){
        				String inicioSecuencia = spliteado[i];
        				String finalSecuencia = spliteado[i+1];
        				if (inicioSecuencia.length()>1 ){
        					inicioSecuencia = String.valueOf(inicioSecuencia.charAt(1));
        				}else if (finalSecuencia.length()>1){
        					finalSecuencia = String.valueOf(finalSecuencia.charAt(0));
        				}
        				if (SLetrasMin.contains(inicioSecuencia)){ // letrasmin
        					if (SLetrasMin.contains(finalSecuencia)){ 
        						if (aRet==null){aRet = new Alfabeto(SLetrasMin.substring(SLetrasMin.indexOf(inicioSecuencia),SLetrasMin.indexOf(finalSecuencia)+1));}
        						else{aRet.agregarLetras(SLetrasMin.substring(SLetrasMin.indexOf(inicioSecuencia),SLetrasMin.indexOf(finalSecuencia)+1));}

        					}
        				}else if (SLetrasMay.contains(inicioSecuencia)){ // letrasmay
        					if (SLetrasMay.contains(finalSecuencia)){ 
        						if (aRet==null){aRet = new Alfabeto(SLetrasMay.substring(SLetrasMay.indexOf(inicioSecuencia),SLetrasMay.indexOf(finalSecuencia)+1));}
        						else{aRet.agregarLetras(SLetrasMay.substring(SLetrasMay.indexOf(inicioSecuencia),SLetrasMay.indexOf(finalSecuencia)+1));}
        					}
        				}else if (SNumeros.contains(inicioSecuencia)){ // numeros
        					if (SNumeros.contains(finalSecuencia)){ 
        						if (aRet==null){aRet = new Alfabeto(SNumeros.substring(SNumeros.indexOf(inicioSecuencia),SNumeros.indexOf(finalSecuencia)+1));}
        						else{aRet.agregarLetras(SNumeros.substring(SNumeros.indexOf(inicioSecuencia),SNumeros.indexOf(finalSecuencia)+1));}
        					}
        				}
        				else{
        					throw new ExpresionIncorrectaException();
        				}
        			}


        		} // end corchetes
        		else{
        			if (agrupador==true){
        				entreCorchetes += letra;
        				continue;
        			}else{
                                    String actual = String.valueOf(this.letras);
                                    String aMeter = String.valueOf(letra);
                                    if (!actual.contains(aMeter)){
                                        this.letras = (actual + letra).toCharArray();
        				continue;
                                    }else{
                                        throw new ExpresionIncorrectaException("Caracter repetido en expresión");
                                    }
        				
        			}
        		}
        		if (aRet!=null)
        			this.letras = (String.valueOf(this.letras) + String.valueOf(aRet.letras)).toCharArray() ;
        	}
                if (!String.valueOf(this.letras).contains(Letras.empty))
                    this.letras = (String.valueOf(this.letras) + constantes.Letras.empty).toCharArray() ;
        }

	public Alfabeto(String nAlfabeto)
			throws CaracterNoValidoEnExpresionRegularException, ExpresionIncorrectaException {
            this(nAlfabeto.toCharArray());
	}

	public boolean contiene(char otraLetra) {
		for (char letra : letras) {
			if (letra == otraLetra)
				return true;
		}

		return false;
	}
	
	public boolean pertenece(String cadena)
	{
		for (int i = 0; i < cadena.length(); i++)
		{
			if (!contiene(cadena.charAt(i))) return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String aRet = "{";
		for (int i = 0; i < letras.length; i++) {
			aRet += "'" + letras[i] + "'";
			if (i != letras.length - 1)
			{
				aRet += ",";
			}
		}
		return aRet + "}";
	}
        
       
	
	public void eliminarEspeciales()
	{
		String letrasS = "";
		for (char letra : letras)
		{
			letrasS += letra;
		}
		
		for (char letra : SEspeciales.toCharArray())
		{
			int i = -1;
			i = letrasS.indexOf(letra + "");
			while (i != -1)
			{
                            if (!letrasS.substring(0, i).endsWith("\\")){
				letrasS = letrasS.substring(0, i) + letrasS.substring(i + 1);
				i = letrasS.indexOf(letra + "");
                            }
			}
		}
		this.letras = letrasS.toCharArray();
	}

	
}
