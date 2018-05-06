package il.ac.hit.model;

public class LoggerMsg {

	public void write (String tag,  String msg) {
		System.out.println ( tag  + "massage :" +msg);
	}

	public void warning(String message){
		write("WARNING",message);
	}
	
	
	public void error(String message){
		write("ERROR",message);
	}
	
	public void info(String message){
		write("INFO",message);
	}
	
	public void debug(String message){
		write("DEBUG",message);
	}
	
}
