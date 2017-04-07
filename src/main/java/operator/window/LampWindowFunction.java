package operator.window;

import model.Lamp;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.Collector;

/**
 * Created by maurizio on 28/03/17.
 *
 * Prende in ingresso una Tuple2 proveniente dalla fold function SumFoldFunction avente come Lamp un oggetto
 * con consumption uguale alla somma di tutti i consumption dei lamp arrivati all'interno della finestra
 * e come Long il numero di Lamp arrivati di cui si è sommato il consumption;
 * tramite la funzione apply restituisce il Lamp avente come consumption il valore della somma di tutti i consumption
 * diviso il numero di Lamp arrivati (la media)
 */

//WindowFunction<input, output, key, window>
public class LampWindowFunction implements WindowFunction<Tuple2<Object, Long>, Lamp, Long, TimeWindow> {

    @Override
    public void apply (Long key, TimeWindow timeWindow, Iterable<Tuple2<Object, Long>> input, Collector<Lamp> out) throws Exception {

        Tuple2<Object, Long> totConsLamp = input.iterator().next();

        //System.out.println("LampWindowFunction result " + (totConsLamp.f0.getConsumption()/totConsLamp.f1) + " Timestamp " + totConsLamp.f0.getTimestamp());
        out.collect(new Lamp(key, ((Lamp)totConsLamp.f0).getConsumption() / totConsLamp.f1, ((Lamp)totConsLamp.f0).getAddress(), ((Lamp)totConsLamp.f0).getTimestamp()));
    }
}
