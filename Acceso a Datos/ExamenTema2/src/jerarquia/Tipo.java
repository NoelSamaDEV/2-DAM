package jerarquia;

public enum Tipo {
	
	PLANTA, 
	FUEGO,
	AGUA,
	BICHO,
	NORMAL,
	VENENO,
	ELÉCTRICO,
	TIERRA,
	HADA,
	LUCHA,
	PSÍQUICO,
	ROCA,
	FANTASMA,
	HIELO,
	DRAGÓN;
	
	@Override
	public String toString() {
		String salida = ""+this.name().charAt(0);
		salida += this.name().toLowerCase().substring(1);
		return salida;
	}
	
}
