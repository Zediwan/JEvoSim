package Main.NeuralNetwork;
import java.awt.*;
import java.util.ArrayList;

public class Network {
    ArrayList<Neuron> neurons;

    public Network(double x, double y){
        this.neurons = new ArrayList();
    }

    public void addNeuron(Neuron neuron){
        neurons.add(neuron);
    }

    public void connect(Neuron a, Neuron b) {
        Connection c = new Connection(a,b,Math.random());
        a.addConnection(c);
    }

    public void connect(Neuron a, Neuron b, double weight) {
        Connection c = new Connection(a,b,weight);
        a.addConnection(c);
    }

    public void generateCentralizedNodes(double rowPos, int amountOfNodes){
        //TODO: adjust order
        //is it an even amount of Nodes
        if(amountOfNodes % 2 == 0){
            for(int i = 0; i < amountOfNodes/2 ; i++){
                addNeuron(new Neuron(rowPos, 25 + 50 * i));
                addNeuron(new Neuron(rowPos, -25 - 50 * i));
            }
        }else{
            addNeuron(new Neuron(rowPos, 0));
            for(int i = 1; i <= amountOfNodes/2; i++){
                addNeuron(new Neuron(rowPos, 50 * i));
                addNeuron(new Neuron(rowPos, -50 * i));
            }
        }
    }

    public void feedForward(double input) {
        Neuron start = neurons.get(0);
        start.feedForward(input);
    }

    public void update() {
        for(Neuron n : neurons) n.update();
    }

    public void paint(Graphics2D g, double x, double y) {
        g.translate(x, y);
        for(Neuron n : neurons) n.paint(g);
    }
}
