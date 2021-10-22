package service;

import java.util.HashMap;
import java.util.Map;

public class DescriptionService {
    public Map<Integer, String> types = new HashMap<Integer, String>();
    public Map<Integer, String> status = new HashMap<Integer, String>();

    public DescriptionService() {
        this.types.put(1,"Lodging");
        this.types.put(2,"Travel");
        this.types.put(3,"Food");
        this.types.put(4,"Other");
        this.status.put(1,"Pending");
        this.status.put(2,"Approved");
        this.status.put(3,"Denied");
    }
}
