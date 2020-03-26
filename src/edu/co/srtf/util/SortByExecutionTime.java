package edu.co.srtf.util;

import java.util.Comparator;
import edu.co.srtf.models.Process;
/**
 * 
 * @author juancsr
 * @author davidssantoss
 */

public class SortByExecutionTime implements Comparator<Process> {

    // Used for sorting in ascending order of 
    // roll number 
    @Override
    public int compare(Process b, Process a) {
        if (b.isListo()) {
            return b.getTiempoRafaga() - a.getTiempoRafaga();
        }
        
        if (b.getTiempoRafaga() - a.getTiempoRafaga() == 0) {
            return b.getTiempoLlegada() - a.getTiempoLlegada();
        }
        return b.getTiempoRafaga() - a.getTiempoRafaga();
        
    }
}

