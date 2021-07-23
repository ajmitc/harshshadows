package harshshadows.view;

import harshshadows.Model;

import javax.swing.*;

public class View {
    private Model model;
    private JFrame frame;

    public View(Model model){
        this.model = model;

        frame = new JFrame();
    }

    public JFrame getFrame() {
        return frame;
    }
}
