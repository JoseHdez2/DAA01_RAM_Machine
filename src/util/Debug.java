package util;

public class Debug {
    boolean debug = false;
    
    public Debug(boolean b){
        this.debug = b;
    }
    
    public void out(String string){
        if(debug) System.out.println(string);
    }
    
    public void out(String format, Object... objects){
        if(debug) Sys.out(format,objects);
    }
}
