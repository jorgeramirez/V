package util;

/**
 * Define un elemento de filtro utilizado para realizar los filtrados
 * por atributos en la base de datos.
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 * 
 **/
public class SimpleFilter {
	/**
	 * Representa a la columna en la base de datos.
	 **/
	private String field;
	
	/**
	 * El valor para dicha columna
	 **/
	private Object value;
	
	/**
	 * El comparador utilizado. 
	 * 		En el caso de cadenas de texto se utiliza 'like'
	 * 		En el caso de valores numéricos se utilizan  '=', '>', '<'
	 **/
	private String comparison;
	
	public SimpleFilter(String field, Object value, String comparison){
		this.field = field;
		this.value = value;
		if(comparison.equals("lt")){
			this.comparison = "<";
		}else if(comparison.equals("gt")){
			this.comparison = ">";
		}else if(comparison.equals("eq")){
			this.comparison = "=";
		}else{
			this.comparison = comparison;
		}
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getComparison() {
		return comparison;
	}
	
	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public String toString(){
		return field + " " + comparison + " '" + value.toString() + "'";	
	}

}
