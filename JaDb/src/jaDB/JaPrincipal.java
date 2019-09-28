package jaDB;
public class JaPrincipal {
	public static void main(String args[]) {
		System.out.print(JaSelect.selectInto().campo("campo", "alias")
							 .campo("campo1", "alias2")
							 .where("condicion = 1")	
							 .crear());
	}
}