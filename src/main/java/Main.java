import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

/**
 * Created by Sellm on 18.08.2016.
 */
public class Main {

    public static void main(String[] args){
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, 2));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 2));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false,1));
        network.getStructure().finalizeStructure();
        network.reset();



    }
}
