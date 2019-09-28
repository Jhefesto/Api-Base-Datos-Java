package jaDB;

import java.util.ArrayList;

public class JaSelect {
	private final String _SELECT = "SELECT";
	private final String _FROM = "FROM";
	private final String _WHERE = "WHERE";
	private final String _AND = "AND";
	private final String _OR = "OR";
	private final String _ON = "ON";
	private final String _ORDER_BY = "ORDER BY";
	private final String _GROUP_BY = "GROUP BY";
	private final String _INNER_JOIN ="INNER JOIN";
	private final String _NOT = "NOT";
	private ArrayList<Atributo> _CAMPO;
	private ArrayList<String> _TABLA;
	private String _CONDICION;
	private String _UNIR;
	private String _ORDENAR;
	private String _AGRUPAR;
	private String _WHERE_NOT;
	public JaSelect() {
		this._CAMPO = new ArrayList<Atributo>();
		this._TABLA = new ArrayList<String>(); 
		this._CONDICION = "";
		this._UNIR = "";
		this._ORDENAR ="";
		this._AGRUPAR = "";
		this._WHERE_NOT = "";
	}
	public static Select selectInto() {
		return new JaSelect.Select();
	}
	public static class Select{
		private final JaSelect instance =  new JaSelect();
		private Select() {}
	    public Select campo(String descripcion, String alias) {
	    	instance._CAMPO.add(new Atributo(descripcion, alias));
	    	return this;
	    }
	    public Select where(String condicion) {
	    	instance._CONDICION+="("+condicion+")";
	    	return this;
	    }
	    public Select gWhereAnd(String ...condicion) {
	    	joinWhere(instance._AND, condicion);
	    	return this;
	    }
	    public Select gWhereOr(String ...condicion) {
	    	joinWhere(instance._OR, condicion);
	    	return this;
	    }
	    public Select or() {
	    	instance._CONDICION+=" OR "; 
	    	return this;
	    }
	    public Select and() {
	    	instance._CONDICION+=" AND ";
	    	return this;
	    }
	    public Select whereNot(String condicion) {
	    	instance._WHERE_NOT+= " " +instance._NOT + " " + condicion;
	    	return this;
	    }
	    public Select orderBy(String ...ordenar) {
	    	if(instance._ORDENAR.trim().compareTo("")==0) {
		    	instance._ORDENAR+=" "+instance._ORDER_BY+" ";
		    	int c = 1;
		    	for (String value : ordenar) {
		    		instance._ORDENAR+= " "+value+" " +((ordenar.length!=c)?" , ":" ");
		    		c++;
		    	}
	    	}
	    	return this;
	    }
	    public Select groupBy(String ...agrupar) {
	    	if(instance._AGRUPAR.trim().compareTo("")==0) {
		    	instance._AGRUPAR+=" "+instance._GROUP_BY+" ";
		    	int c = 1;
		    	for (String value : agrupar) {
		    		instance._AGRUPAR+= " "+value+" " +((agrupar.length!=c)?" , ":" ");
		    		c++;
		    	}
	    	}
	    	return this;
	    }
	    public Select tabla(String ...tabla) {
	    	for (String value : tabla) {
	    		instance._TABLA.add(value);
			}
	    	return this;
	    }
	    public Select innerJoin(String tabla, String on) {
	    	instance._UNIR += " "+ instance._INNER_JOIN+ " " +tabla+" "+ instance._ON+" "+on + " " ; 
	    	return this;
	    }
	    private void joinWhere(String n, String ...q) {
	    	instance._CONDICION+= " ( ";
	    	int c=1;
	    	for (String value : q) {
					instance._CONDICION+= ""+ value + ((c!=q.length && q.length!=1)?" "+n+" ":" ");
					c++;
	    	}
	    	instance._CONDICION+= " ) ";
	    }
	    
	    public String crear() {
	    	String sentencia = instance._SELECT;
		    int c = 1;
		    if(instance._CAMPO.size()>0) {
				for (Atributo value : instance._CAMPO) {
					sentencia+= " "+ value.descripcion + ((value.alias.compareTo("")==0)?"":" AS "+value.alias) + ((c!=instance._CAMPO.size())?",":" ");
					c++;
				}
		    }else {
		    	sentencia +=" * ";
		    }
			if(instance._TABLA.size()>0) {
				sentencia+=" "+instance._FROM+" ";
				c=1;
				for (String value : instance._TABLA) {
					sentencia+= " "+ value + ((c!=instance._TABLA.size())?",":" ");
					c++;
				}
			}
			if(instance._INNER_JOIN.trim().compareTo("")!=0)
				sentencia += " "+instance._UNIR+" ";
			if(instance._CONDICION.trim().compareTo("")!=0) 
				sentencia +=instance._WHERE+ " "+ instance._CONDICION;
			else
				if(instance._WHERE_NOT.trim().compareTo("")!=0)
					sentencia+=" "+instance._WHERE+" "+ instance._WHERE_NOT;
			if(instance._AGRUPAR.trim().compareTo("")!=0)
				sentencia+= instance._AGRUPAR;
			if(instance._ORDENAR.trim().compareTo("")!=0)
				sentencia+=instance._ORDENAR;
		return sentencia;
	    }
	}
	public static class Atributo {
		private String descripcion;
		private String alias;
		public Atributo(String descripcion, String alias) {
			this.descripcion = descripcion;
			this.alias = alias;
		}
	}
}
