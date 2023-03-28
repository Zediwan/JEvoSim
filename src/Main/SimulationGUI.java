package Main;

import Main.Organisms.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SimulationGUI extends JFrame {
    private JPanel simPanel;
    private JScrollPane scrollPane;
    private JPanel graphPanel;

    private JSplitPane controlPane;

    private JTabbedPane settingsPane;
    private JPanel animalSettingsPanel;
    private JPanel plantSettingPanel;
    private JPanel organismSettingPanel;
    private JPanel worldSettingPanel;

    private JTabbedPane statPanel;
    private final JScrollPane animalStatPanel;
    private final JScrollPane plantStatPanel;
    private final JScrollPane organismStatPanel;
    private final JPanel worldStatPanel;

    private JCheckBox showHealthCheckBox;
    private JCheckBox showEnergyCheckBox;
    private JCheckBox showAnimalQTCheckBox;
    private JCheckBox showPlantQTCheckBox;

    private JPanel maxPlantsPanel;
    private JSlider maxPlantsSlider;
    private JLabel maxPlantsLabel;
    private JPanel minPlantsPanel;
    private JSlider minPlantsSlider;
    private JLabel minPlantsLabel;

    private JPanel maxAnimalsPanel;
    private JSlider maxAnimalsSlider;
    private JLabel maxAnimalsLabel;
    private JPanel minAnimalsPanel;
    private JSlider minAnimalsSlider;
    private JLabel minAnimalsLabel;

    private JPanel simulationSpeedPanel;
    private JSlider simulationSpeedSlider;
    private JLabel simulationSpeedLabel;

    private JLabel fpsLabel;

    private Object[][] animalData;

    public static boolean showHealth = false;
    public static boolean showEnergy = false;
    public static boolean showAnimalQT = false;
    public static boolean showPlantQT = false;
    public static int simulationSpeed = 10;

    public SimulationGUI() {
        // Set up main frame
        setTitle("Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up simulation panel
        World w =new World(2000,2000, 20, 20);
        Simulation s = new Simulation(50000,2000,80000,
                8000,5000,100,
                1000,100,
                w);
        this.simPanel = s;
        this.simPanel.setPreferredSize(w.getWorldDimension()); // Set initial size
        this.scrollPane = new JScrollPane(this.simPanel);
        /*
        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                System.out.println("Test");
                int notches = e.getWheelRotation();
                Dimension size = simPanel.getSize();
                double scale = 1.0 + (0.1 * notches); // Adjust scale based on mouse wheel
                size.width = (int) (size.width * scale);
                size.height = (int) (size.height * scale);
                simPanel.setPreferredSize(size);
                scrollPane.revalidate(); // Redraw scroll pane
            }
        });
         */
        this.scrollPane.setPreferredSize(new Dimension(800, 600)); // Set initial viewport size
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);

        // Set up bottom panel
        this.graphPanel = new JPanel();
        this.graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        this.graphPanel.add(new JLabel("Simulation Statistics"));
        this.add(this.graphPanel, BorderLayout.SOUTH);

        // Set up right panel
        this.controlPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        this.settingsPane = new JTabbedPane();
        this.animalSettingsPanel = new JPanel(new GridLayout(0, 2));
        this.plantSettingPanel = new JPanel(new GridLayout(0, 2));
        this.organismSettingPanel = new JPanel(new GridLayout(0, 2));
        this.worldSettingPanel = new JPanel(new GridLayout(0, 2));
        this.settingsPane.addTab("Animals", this.animalSettingsPanel);
        this.settingsPane.addTab("Plants", this.plantSettingPanel);
        this.settingsPane.addTab("Organisms", this.organismSettingPanel);
        this.settingsPane.addTab("World", this.worldSettingPanel);

        this.statPanel =  new JTabbedPane();
        this.plantStatPanel = new JScrollPane();
        this.organismStatPanel = new JScrollPane();
        this.worldStatPanel = new JPanel();
        this.statPanel.addTab("Plants", this.plantStatPanel);
        this.statPanel.addTab("Organisms", this.organismStatPanel);
        this.statPanel.addTab("World", this.worldStatPanel);

        //Show health checkbox
        this.showHealthCheckBox = new JCheckBox("Show Health");
        this.showHealthCheckBox.setToolTipText("Toggle the visualization of the current Health");
        this.showHealthCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHealth = showHealthCheckBox.isSelected();
            }
        });

        //Show energy checkbox
        this.showEnergyCheckBox = new JCheckBox("Show Energy");
        this.showEnergyCheckBox.setToolTipText("Toggle the visualization of the current Energy");
        this.showEnergyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEnergy = showEnergyCheckBox.isSelected();
            }
        });

        //Show Animal QT checkbox
        this.showAnimalQTCheckBox = new JCheckBox("Show Animal Quad Tree");
        this.showAnimalQTCheckBox.setToolTipText("Toggle the visualization of the Animal Quad Tree");
        this.showAnimalQTCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAnimalQT = showAnimalQTCheckBox.isSelected();
            }
        });

        //Show Plant QT checkbox
        this.showPlantQTCheckBox = new JCheckBox("Show Plant Quad Tree");
        this.showPlantQTCheckBox.setToolTipText("Toggle the visualization of the Plant Quad Tree");
        this.showPlantQTCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlantQT = showPlantQTCheckBox.isSelected();            }
        });

        JTextField animalQTCapacity = new JTextField();
        animalQTCapacity.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        int capacity = Integer.parseInt(animalQTCapacity.getText());
                        if(capacity > 0){
                            s.getWorld().getAnimalQuadTree().setCapacity(capacity);
                        }
                        // Set the capacity of your quad tree here
                    }
                    catch (NumberFormatException ex) {
                        // Handle the case where the user enters an invalid value
                    }
                }
            }
        });

        JTextField plantQTCapacity = new JTextField();
        plantQTCapacity.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        int capacity = Integer.parseInt(plantQTCapacity.getText());
                        if(capacity > 0){
                            s.getWorld().getPlantQuadTree().setCapacity(capacity);
                        }
                        // Set the capacity of your quad tree here
                    }
                    catch (NumberFormatException ex) {
                        // Handle the case where the user enters an invalid value
                    }
                }
            }
        });

        //TODO create rangeSliders
        //Slider for maxPlants
        this.maxPlantsPanel = new JPanel(new BorderLayout());
        int startingValue = 50000;
        this.maxPlantsSlider = new JSlider(JSlider.HORIZONTAL, 0, 100000, startingValue);
        this.maxPlantsSlider.setMajorTickSpacing(25000);
        this.maxPlantsSlider.setMinorTickSpacing(12500);
        this.maxPlantsSlider.setPaintTicks(true);
        this.maxPlantsSlider.setPaintTrack(true);
        this.maxPlantsLabel = new JLabel("Max Plants: " + startingValue, JLabel.CENTER);
        s.setMaxPlants(startingValue);
        this.maxPlantsSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            int maxPlants = source.getValue();
            int minPlants = s.getMinNumPlants();
            if(minPlants > maxPlants){
                this.minPlantsSlider.setValue(maxPlants);
            }
            this.maxPlantsLabel.setText("Max Plants: " + maxPlants);
            s.setMaxPlants(maxPlants);

        });
        this.maxPlantsPanel.add(this.maxPlantsLabel, BorderLayout.NORTH);
        this.maxPlantsPanel.add(this.maxPlantsSlider, BorderLayout.CENTER);

        //Slider for minPlants
        this.minPlantsPanel = new JPanel(new BorderLayout());
        startingValue = 20000;
        this.minPlantsSlider = new JSlider(JSlider.HORIZONTAL, 0, 100000, startingValue);
        this.minPlantsSlider.setMajorTickSpacing(25000);
        this.minPlantsSlider.setMinorTickSpacing(12500);
        this.minPlantsSlider.setPaintTicks(true);
        this.minPlantsSlider.setPaintTrack(true);
        this.minPlantsLabel = new JLabel("Min Plants: " + startingValue, JLabel.CENTER);
        s.setMinNumPlants(startingValue);
        this.minPlantsSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            int minPlants = source.getValue();
            int maxPlants = this.maxPlantsSlider.getValue();
            if(minPlants > maxPlants){
                this.maxPlantsSlider.setValue(minPlants);
            }
            this.minPlantsLabel.setText("Min Plants: " + minPlants);
            s.setMinNumPlants(minPlants);

        });
        this.minPlantsPanel.add(this.minPlantsLabel, BorderLayout.NORTH);
        this.minPlantsPanel.add(this.minPlantsSlider, BorderLayout.CENTER);

        //Slider for maxAnimals
        this.maxAnimalsPanel = new JPanel(new BorderLayout());
        startingValue = 20000;
        this.maxAnimalsSlider = new JSlider(JSlider.HORIZONTAL, 0, 100000, startingValue);
        this.maxAnimalsSlider.setMajorTickSpacing(25000);
        this.maxAnimalsSlider.setMinorTickSpacing(12500);
        this.maxAnimalsSlider.setPaintTicks(true);
        this.maxAnimalsSlider.setPaintTrack(true);
        this.maxAnimalsLabel = new JLabel("Max Animals: " + startingValue, JLabel.CENTER);
        s.setMaxAnimals(startingValue);
        this.maxAnimalsSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            int maxAnimals = source.getValue();
            int minAnimals = s.getMinNumAnimals();
            if(minAnimals > maxAnimals){
                this.minAnimalsSlider.setValue(maxAnimals);
            }
            maxAnimalsLabel.setText("Max Animals: " + maxAnimals);
            s.setMaxAnimals(maxAnimals);

        });
        this.maxAnimalsPanel.add(this.maxAnimalsLabel, BorderLayout.NORTH);
        this.maxAnimalsPanel.add(this.maxAnimalsSlider, BorderLayout.CENTER);

        //Slider for minAnimals
        this.minAnimalsPanel = new JPanel(new BorderLayout());
        startingValue = 1000;
        this.minAnimalsSlider = new JSlider(JSlider.HORIZONTAL, 0, 100000, startingValue);
        this.minAnimalsSlider.setMajorTickSpacing(25000);
        this.minAnimalsSlider.setMinorTickSpacing(12500);
        this.minAnimalsSlider.setPaintTicks(true);
        this.minAnimalsSlider.setPaintTrack(true);
        this.minAnimalsLabel = new JLabel("Min Animals: " + startingValue, JLabel.CENTER);
        s.setMinNumAnimals(startingValue);
        this.minAnimalsSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            int minAnimals = source.getValue();
            int maxAnimals = this.maxAnimalsSlider.getValue();
            if(minAnimals > maxAnimals){
                this.maxAnimalsSlider.setValue(minAnimals);
            }
            this.minAnimalsLabel.setText("Min Animals: " + minAnimals);
            s.setMinNumAnimals(minAnimals);

        });
        this.minAnimalsPanel.add(this.minAnimalsLabel, BorderLayout.NORTH);
        this.minAnimalsPanel.add(this.minAnimalsSlider, BorderLayout.CENTER);

        //Simulation Speed Slider
        this.simulationSpeedPanel = new JPanel(new BorderLayout());
        startingValue = 100;
        this.simulationSpeedSlider = new JSlider(JSlider.HORIZONTAL,1,100,startingValue);
        this.simulationSpeedSlider.setMajorTickSpacing(10);
        this.simulationSpeedSlider.setPaintTrack(true);
        this.simulationSpeedLabel = new JLabel("Frames per Sec: " + startingValue, JLabel.CENTER);
        s.setTimerDelay(1000/startingValue);
        this.simulationSpeedSlider.addChangeListener(e ->{
            JSlider source = (JSlider)e.getSource();
            int simulationSpeed = source.getValue();
            this.simulationSpeedLabel.setText("Frames per Sec: " + simulationSpeed);
            s.setTimerDelay(1000/simulationSpeed);
        });
        this.simulationSpeedPanel.add(this.simulationSpeedLabel, BorderLayout.NORTH);
        this.simulationSpeedPanel.add(this.simulationSpeedSlider, BorderLayout.CENTER);

        //FPS Label
        this.fpsLabel = new JLabel();
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fpsLabel.setText("FPS: " + s.getFps());
            }
        });
        timer.start();

        JLabel timeLabel = new JLabel();
        Timer runtimeTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText("Runtime: " + (System.currentTimeMillis()-s.startTime)/1000 + " sec");
            }
        });
        runtimeTimer.start();

        // Create a 2D array to hold your statistics data
        this.animalData = new Object[][]{
                { "Amount of Animals: " , s.getAnimals().size() },
                { "All-time of Animals: " , Animal.aniCount },
                { "Average Age: ", s.getAVGAgeAnimals() },
                { "Average Animals Killed: ", s.getAVGAnimalsKilled()},
                { "Average Plants Killed: ", s.getAVGPlantsKilled()},
                { "Average Offspring Birthed: ", s.getAVGOffspringBirthed()},

                { "Average Max Health: ", s.getAVGMaxHealthAnimals() },
                { "Average Health: ", s.getAVGHealthAnimals() },
                { "Average Health Ratio: ", s.getAVGHealthRatioAnimals() },

                { "Average Max Energy: ", s.getAVGMaxEnergyAnimals() },
                { "Average Energy: ", s.getAVGEnergyAnimals() },
                { "Average Energy Ratio: ", s.getAVGEnergyRatioAnimals() },

                { "All-time Animals born: ", Animal.aniBornCount}
                // Add more rows as needed
        };

        // Create an array of column names
        String[] columnNames = { "Statistic Name", "Value" };

        // Create the JTable with the data and column names
        JTable table = new JTable(this.animalData, columnNames);

        // Add the JTable to a JScrollPane
        //JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to your statPanel
        this.animalStatPanel = new JScrollPane(table);
        this.statPanel.addTab("Animals", this.animalStatPanel);
        //this.animalStatPanel.add(scrollPane);

        Timer aniStatTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the table data here
                animalData[0][1] = s.getAnimals().size();
                animalData[1][1] = Animal.aniCount;
                animalData[2][1] = String.format("%.2f", s.getAVGAgeAnimals());
                animalData[3][1] = Math.round(s.getAVGAnimalsKilled());
                animalData[4][1] = Math.round(s.getAVGPlantsKilled());
                animalData[5][1] = Math.round(s.getAVGOffspringBirthed());

                animalData[6][1] = String.format("%.2f", s.getAVGMaxHealthAnimals());
                animalData[7][1] = String.format("%.2f", s.getAVGHealthAnimals());
                animalData[8][1] = String.format("%.2f", s.getAVGHealthRatioAnimals());

                animalData[9][1] = String.format("%.2f", s.getAVGMaxEnergyAnimals());
                animalData[10][1] = String.format("%.2f", s.getAVGEnergyAnimals());
                animalData[11][1] = String.format("%.2f", s.getAVGEnergyRatioAnimals());

                animalData[12][1] = Animal.aniBornCount;

                // Repaint the table
                statPanel.repaint();
            }
        });
        aniStatTimer.start();
        
        //Add the buttons and sliders to the setting panel
        this.animalSettingsPanel.add(this.showHealthCheckBox);
        this.animalSettingsPanel.add(this.showEnergyCheckBox);
        this.animalSettingsPanel.add(this.showAnimalQTCheckBox);
        this.animalSettingsPanel.add(animalQTCapacity);
        this.plantSettingPanel.add(this.showPlantQTCheckBox);
        this.plantSettingPanel.add(plantQTCapacity);
        this.worldSettingPanel.add(this.minPlantsPanel);
        this.worldSettingPanel.add(this.maxPlantsPanel);
        this.worldSettingPanel.add(this.minAnimalsPanel);
        this.worldSettingPanel.add(this.maxAnimalsPanel);
        this.worldSettingPanel.add(this.simulationSpeedPanel);

        //Add info in the stat panel
        this.worldStatPanel.add(this.fpsLabel);
        this.worldStatPanel.add(timeLabel);

        this.controlPane.setResizeWeight(.5);
        this.controlPane.setTopComponent(settingsPane);
        this.controlPane.setBottomComponent(statPanel);

        this.add(controlPane, BorderLayout.EAST);
    }
}
