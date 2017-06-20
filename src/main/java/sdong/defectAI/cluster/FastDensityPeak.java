package sdong.defectAI.cluster;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.DistanceMeasure;

public class FastDensityPeak implements Clusterer {
    /**
     * The number of clusters.
     */
    private int numberOfClusters = -1;
    
    private DistanceMeasure dm;
	
	@Override
	public Dataset[] cluster(Dataset data) {

		return null;
	}
	
	public double[] Gaussiankernel(){
		
	}
	
	

    
}
