package harshshadows;

import harshshadows.view.View;

import javax.swing.*;

public class Main {
    public static final void main(String ... args){
        Model model = new Model();
        View view = new View(model);
        new Controller(model, view);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.getFrame().setVisible(true);
            }
        });
    }
}
