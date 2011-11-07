package compiladores;

import automatas.NFA;
import enumeraciones.Thompson;
import excepciones.CaracterNoValidoEnExpresionRegularException;
import excepciones.ExpresionIncorrectaException;
import excepciones.IdentificadorNoEncontradoException;
import excepciones.IdentificadorNoValidoException;
import excepciones.LetraNoPerteneceAlfabeto;
import excepciones.ParentesisDesvalanceadosException;

public class ResolucionRegular {
	
	public static ExprRegular resoverCorchetes(ExprRegular reg) throws ExpresionIncorrectaException{
		
		String cadena = reg.cadena;
		String entreCorchetes ="";
		boolean agrupador = false;
    	int agrupadorCount = 0;
    	int regLength = cadena.length();
    	String aceptado = "";
    	String conOs= "";
		for (int k = 0; k < regLength; k++) {
			char letra = cadena.charAt(k);
			if (letra == '['){
    			agrupadorCount++;
    			agrupador = true;
    			continue;
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
    				if (Alfabeto.SLetrasMin.contains(inicioSecuencia)){ // letrasmin
    					if (Alfabeto.SLetrasMin.contains(finalSecuencia)){ 
    						aceptado = Alfabeto.SLetrasMin.substring(Alfabeto.SLetrasMin.indexOf(inicioSecuencia),Alfabeto.SLetrasMin.indexOf(finalSecuencia)+1);
    						
    						for (int j = 0;  j<aceptado.toCharArray().length -1; j++) {
								String op = String.valueOf(aceptado.charAt(j));
								String opARem = "";
								opARem = op + "|";
								if(conOs.equals(""))conOs = aceptado.replaceAll(op, opARem);
								else conOs = conOs.replace(op, opARem);
								
							}
    					}
    				}else if (Alfabeto.SLetrasMay.contains(inicioSecuencia)){ // letrasmay
    					if (Alfabeto.SLetrasMay.contains(finalSecuencia)){ 
    						aceptado = Alfabeto.SLetrasMin.substring(Alfabeto.SLetrasMin.indexOf(inicioSecuencia),Alfabeto.SLetrasMin.indexOf(finalSecuencia)+1);
    						for (int j = 0;  j<aceptado.toCharArray().length-1; j++) {
    							String op = String.valueOf(aceptado.charAt(j));
								String opARem = "";
								opARem = op + "|";
								if(conOs.equals(""))conOs = aceptado.replaceAll(op, opARem);
								else conOs = conOs.replace(op, opARem);
								
							}
    					}
    				}else if (Alfabeto.SNumeros.contains(inicioSecuencia)){ // numeros
    					if (Alfabeto.SNumeros.contains(finalSecuencia)){ 
    						aceptado = Alfabeto.SLetrasMin.substring(Alfabeto.SLetrasMin.indexOf(inicioSecuencia),Alfabeto.SLetrasMin.indexOf(finalSecuencia)+1);
    						for (int j = 0;  j<aceptado.toCharArray().length-1; j++) {
    							String op = String.valueOf(aceptado.charAt(j));
								String opARem = "";
								opARem = op + "|";
								if(conOs.equals(""))conOs = aceptado.replaceAll(op, opARem);
								else conOs = conOs.replace(op, opARem);
								
							}
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
    			}
    		
    		}
    		if (!entreCorchetes.equalsIgnoreCase("")){
    			reg.cadena = reg.cadena.replace("[" + entreCorchetes + "]", "("+conOs+")");
    			entreCorchetes = "";
    		}
    	} // end for
		
		return reg;
	}
	
	public static NFA generarNfa(ExprRegular reg)throws IdentificadorNoEncontradoException,
	LetraNoPerteneceAlfabeto, ParentesisDesvalanceadosException, CaracterNoValidoEnExpresionRegularException,
	IdentificadorNoValidoException {

		if (reg == null) // eof
			return null;
		boolean openParethesis = false;
                boolean especial = false;
//		ExprRegular exp = null;
		String cadena = reg.cadena;
		int parentesis = 0;
		int regLength = cadena.length();
		int openParentesisAt =0;
		NFA nfa = null;
		NFA nfaUnario = null;
//                Alfabeto inicial = new Alfabeto(reg.alfabeto);
               
                
		//PilaExp pilaCaracteres = new PilaExp(regLength);
		// logica
		for (int i = 0; i < regLength; i++) {
			char letra = cadena.charAt(i);
                        
                        if (letra == '\\'){
                            especial = true;
                            continue;
                        }
		
			
			
			
			
			
			if (letra == '*' && !openParethesis && !especial) {
				if(nfaUnario==null){
					nfa =  NFA.ConstruirNFA(Thompson.Kleene, nfa);
					continue;
				}
				if((i+1)<regLength){
					char siguiente = cadena.charAt(i+1);
					if (siguiente == '|'){
						
						if (nfa!=null) {
							nfaUnario = NFA.ConstruirNFA(Thompson.Y, nfa, NFA.ConstruirNFA(Thompson.Kleene, nfaUnario)) ;
							nfa = null;
						}
						else{
							nfaUnario = NFA.ConstruirNFA(Thompson.Kleene, nfaUnario);
						}
						continue;
					}
				}
				if (nfa!=null) nfa = NFA.ConstruirNFA(Thompson.Y, nfa, NFA.ConstruirNFA(Thompson.Kleene, nfaUnario)) ;
				else{
					nfa = NFA.ConstruirNFA(Thompson.Kleene, nfaUnario);
				}
				
				
				
				
				continue;
			}
		
			if (letra == '+' && !openParethesis && !especial) {
				if(nfaUnario==null){
					nfa =  NFA.ConstruirNFA(Thompson.KleenePlus, nfa);
					continue;
				}
				if((i+1)<regLength){
					char siguiente = cadena.charAt(i+1);
					if (siguiente == '|'){
						
						if (nfa!=null) {
							nfaUnario = NFA.ConstruirNFA(Thompson.Y, nfa, NFA.ConstruirNFA(Thompson.KleenePlus, nfaUnario)) ;
							nfa = null;
						}
						else{
							nfaUnario = NFA.ConstruirNFA(Thompson.KleenePlus, nfaUnario);
						}
						
						continue;
					}
					
				}
				if (nfa!=null) nfa = NFA.ConstruirNFA(Thompson.Y, nfa, NFA.ConstruirNFA(Thompson.KleenePlus, nfaUnario)) ;
				else{
					nfa = NFA.ConstruirNFA(Thompson.KleenePlus, nfaUnario);
				}
				
				continue;
			}
		
			if (letra == '?' && !openParethesis && !especial) {
				if(nfaUnario==null){
					nfa =  NFA.ConstruirNFA(Thompson.Opcional, nfa);
					continue;
				}
				if((i+1)<regLength){
					char siguiente = cadena.charAt(i+1);
					if (siguiente == '|'){
						
						if (nfa!=null) {
							nfaUnario = NFA.ConstruirNFA(Thompson.Y, nfa, NFA.ConstruirNFA(Thompson.Opcional, nfaUnario)) ;
							nfa = null;
						}
						else{
							nfaUnario = NFA.ConstruirNFA(Thompson.Opcional, nfaUnario);
						}
						continue;
					}
					
				}
				if (nfa!=null) nfa = NFA.ConstruirNFA(Thompson.Y, nfa, NFA.ConstruirNFA(Thompson.Opcional, nfaUnario)) ;
				else{
					nfa = NFA.ConstruirNFA(Thompson.Opcional, nfaUnario);
				}
			
				continue;
			}
		
			if (letra == '(' && !especial) {
				openParethesis=true;
				if (parentesis == 0)
					openParentesisAt = i;
				parentesis++;
				
				continue;
			}
			
		
			if (letra == ')' && !especial) {
				parentesis--;
				if (parentesis!=0){
					openParethesis = true;
					continue;
				}
				openParethesis =false;
				String dentroParentesis = cadena.substring(openParentesisAt+1,i);
				
					if ((i+1)<regLength){
						char siguiente = cadena.charAt(i+1);
						boolean huboOp = false;
						boolean huboOr =false;
						switch (siguiente){
							case '*':
								if (nfa!=null) nfa=  NFA.ConstruirNFA(Thompson.Y, nfa,  NFA.ConstruirNFA(Thompson.Kleene, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto))));
								else nfa = NFA.ConstruirNFA(Thompson.Kleene, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto)));
								huboOp = true;
							break;
							case '+':
								if (nfa!=null) nfa=  NFA.ConstruirNFA(Thompson.Y, nfa,  NFA.ConstruirNFA(Thompson.KleenePlus, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto))));
								else nfa = NFA.ConstruirNFA(Thompson.KleenePlus, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto)));
								huboOp = true;
								
								break;
							case '?':
								if (nfa!=null) nfa=  NFA.ConstruirNFA(Thompson.Y, nfa,  NFA.ConstruirNFA(Thompson.Opcional, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto))));
								else nfa = NFA.ConstruirNFA(Thompson.Opcional, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto)));
								huboOp = true;
								
								break;
							case '|':
								
								huboOp = true;
								huboOr = true;
								if ((i+2)<regLength){
									if (nfa == null) nfa = NFA.ConstruirNFA(Thompson.O, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto,!openParethesis )), generarNfa(new ExprRegular(cadena.substring(i+2), reg.alfabeto, !openParethesis)));
									else nfa = NFA.ConstruirNFA(Thompson.Y, nfa,NFA.ConstruirNFA(Thompson.O, generarNfa(new ExprRegular(dentroParentesis, reg.alfabeto,!openParethesis )), generarNfa(new ExprRegular(cadena.substring(i+2), reg.alfabeto, !openParethesis))));
								}else{
									throw new CaracterNoValidoEnExpresionRegularException("Expresion mal formada");
									
								}
								break;
							default:
								if (nfa!=null)
									nfa = NFA.ConstruirNFA(Thompson.Y, nfa,generarNfa(new ExprRegular(cadena.substring(openParentesisAt+1, (i)), reg.alfabeto, !openParethesis)));
								else
									nfa = generarNfa(new ExprRegular(cadena.substring(openParentesisAt+1, (i)), reg.alfabeto, !openParethesis));
								
								
						}
						
						if (huboOp && (i+1)<regLength){
							if (huboOr)
								cadena = cadena.substring(i+3);
							else
								cadena = cadena.substring(i+2);
							regLength = cadena.length();
							i=-1;
						}else{
							if ((i+1)<regLength){
								cadena = cadena.substring(i+1);
								regLength = cadena.length();
								i=-1;
								
								continue;
							}
						}
						
		
					
				}else{
					if (nfa!=null)
						nfa = NFA.ConstruirNFA(Thompson.Y, nfa,generarNfa(new ExprRegular(cadena.substring(openParentesisAt+1, (i)), reg.alfabeto, !openParethesis)));
					else
						nfa = generarNfa(new ExprRegular(cadena.substring(openParentesisAt+1, (i)), reg.alfabeto, !openParethesis));
					
				}
				continue;
				
			}
		
			if (letra == '|' && !openParethesis && !especial) {
				if ((i+1)<regLength){
					char siguiente = cadena.charAt(i+1);
					if (Alfabeto.SEspeciales.contains("" + siguiente) )throw new CaracterNoValidoEnExpresionRegularException("Expresion Mal formada: " + cadena);
//					String hola = cadena.substring(i+1);
					if (nfa!= null)
						nfa = NFA.ConstruirNFA(Thompson.Y,nfa, NFA.ConstruirNFA(Thompson.O, nfaUnario,generarNfa(new ExprRegular(cadena.substring(i + 1),reg.alfabeto,!openParethesis))));
					else
						nfa =  NFA.ConstruirNFA(Thompson.O, nfaUnario,generarNfa(new ExprRegular(cadena.substring(i + 1),reg.alfabeto,!openParethesis)));
					break;
				}else{
					throw new CaracterNoValidoEnExpresionRegularException("Expresion mal formada");
				}
			}
		
			if (!openParethesis){
                                 if (especial)
                                    especial = false;
				nfaUnario = null;
				if ((i+1)<regLength ){
					char siguiente = cadena.charAt(i+1);
					switch (siguiente){
					case '*':
					case '?':
					case '+':
						nfaUnario =  NFA.ConstruirNFA(letra + "", reg.alfabeto);
						break;
					case '|':
						if (nfa==null)nfaUnario = NFA.ConstruirNFA(letra + "", reg.alfabeto);
						else nfaUnario = NFA.ConstruirNFA(Thompson.Y, nfa , NFA.ConstruirNFA(letra +"", reg.alfabeto));
						nfa = null;
						break;
						
//						if (nfa==null)nfa = NFA.ConstruirNFA(letra + "");
//						else nfaUnario = NFA.ConstruirNFA(letra +"");
//						
//						break;
					default:
						break;
					}
					
				}
                               
				if (nfa == null && nfaUnario == null)
					nfa = NFA.ConstruirNFA(letra + "", reg.alfabeto);
				
				else if (nfaUnario==null)
					nfa = NFA.ConstruirNFA(Thompson.Y, nfa,	NFA.ConstruirNFA(letra + "", reg.alfabeto));
			}
		}
		
		return nfa;
		
	}
	
//	public static void main(String[] args) {
//		//ResolucionRegular res = new ResolucionRegular(")", new Alfabeto(Alfabetos.MAY_MIN_NUMEROS));
//		try {
//			NFA nfa = generarNfa(new ExprRegular("bb+|c*",new Alfabeto(Alfabetos.LETRAS_TODAS) ));
//		} catch (IdentificadorNoEncontradoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (LetraNoPerteneceAlfabeto e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParentesisDesvalanceadosException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IdentificadorNoValidoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CaracterNoValidoEnExpresionRegularException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
