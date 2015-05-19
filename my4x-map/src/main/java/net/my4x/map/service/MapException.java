package net.my4x.map.service;

public class MapException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MapException(String message, Throwable t) {
        super(message, t);
    }

    public MapException(String message) {
        super(message);
    }

    public MapException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }
    
    

}
