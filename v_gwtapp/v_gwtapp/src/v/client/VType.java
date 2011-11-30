package v.client;

/**
 * Define expresiones regulares utilizadas en la validación de formularios.
 **/
public enum VType {
	ALPHABET("^[a-zA-Z_.ñÑáÁéÉíÍóÓúÚ ]+$", "Alphabet"), 
	ALPHANUMERIC("^[a-zA-Z0-9_.ñÑáÁéÉíÍóÓúÚ ]+$", "Alphanumeric"), 
	NUMERIC("^[+0-9]+$", "Numeric"),
	PHONE_NUMBER("^(\\d{3,4}[-]?){1,2}(\\d{3})$", "PhoneNumber"),
	EMAIL("^(\\w+)([-+.][\\w]+)*@(\\w[-\\w]*\\.){1,5}([A-Za-z]){2,4}$", "Email");
	String regex;
	String name;

	VType(String regex, String name) {
		this.regex = regex;
		this.name = name;
	}
}
