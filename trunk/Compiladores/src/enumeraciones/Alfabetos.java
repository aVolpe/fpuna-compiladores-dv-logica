package enumeraciones;

public enum Alfabetos {
		LETRAS_MAYUSCULAS,
		LETRAS_MINUSCULAS,
		LETRAS_TODAS,
		NUMEROS,
		MAY_MIN_NUMEROS,
		ESPECIALES,
		AYB,
		CyU;
                
     public static Alfabetos fromString(String s) {
        if (s.equalsIgnoreCase("Letras Mayusculas")) {
            return Alfabetos.LETRAS_MAYUSCULAS;
        }
        if (s.equalsIgnoreCase("Letras Minusculas")) {
            return Alfabetos.LETRAS_MINUSCULAS;
        }
        if (s.equalsIgnoreCase("Todas las Letras")) {
            return Alfabetos.LETRAS_TODAS;
        }
        if (s.equalsIgnoreCase("Numeros")) {
            return Alfabetos.NUMEROS;
        }
        if (s.equalsIgnoreCase("MAY_MIN_NUMEROS")) {
            return Alfabetos.MAY_MIN_NUMEROS;
        }
        if (s.equalsIgnoreCase("ESPECIALES")) {
            return Alfabetos.ESPECIALES;
        }
        if (s.equalsIgnoreCase("a y b")) {
            return Alfabetos.AYB;
        }
        if (s.equalsIgnoreCase("0 y 1")) {
            return Alfabetos.CyU;
        }
        return null;
    }
     
      public String toString(Alfabetos s) {
        if (s.equals(Alfabetos.LETRAS_MAYUSCULAS)) {
            return  "Letras Mayusculas";
        }
        if (s.equals(Alfabetos.LETRAS_MINUSCULAS)) {
            return "Letras Minusculas";
        }
        if (s.equals( Alfabetos.LETRAS_TODAS)) {
            return "Todas las Letras";
        }
        if (s.equals(Alfabetos.NUMEROS)) {
            return "Numeros";
        }
        if (s.equals(Alfabetos.MAY_MIN_NUMEROS)) {
            return "MAY_MIN_NUMEROS";
        }
        if (s.equals(Alfabetos.ESPECIALES)) {
            return "ESPECIALES";
        }
        if (s.equals(Alfabetos.AYB)) {
            return "a y b";
        }
        if (s.equals(Alfabetos.CyU)) {
            return "0 y 1";
        }
        return null;
    }
	
}
