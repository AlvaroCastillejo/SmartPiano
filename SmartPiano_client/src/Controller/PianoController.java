package Controller;

import Controller.KeyControllers.Octave2.*;
import Controller.KeyControllers.Octave3.*;
import Controller.KeyControllers.Octave4.*;
import View.Piano;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PianoController implements ActionListener {
    private Piano v;

    private ActionListener[] actionListeners;

    public PianoController(){
        actionListeners = new ActionListener[36];
        actionListeners[0] = new C2();
        actionListeners[1] = new Csus2();
        actionListeners[2] = new D2();
        actionListeners[3] = new Dsus2();
        actionListeners[4] = new E2();
        actionListeners[5] = new F2();
        actionListeners[6] = new Fsus2();
        actionListeners[7] = new G2();
        actionListeners[8] = new Gsus2();
        actionListeners[9] = new A2();
        actionListeners[10] = new Asus2();
        actionListeners[11] = new B2();
        actionListeners[12] = new C3();
        actionListeners[13] = new Csus3();
        actionListeners[14] = new D3();
        actionListeners[15] = new Dsus3();
        actionListeners[16] = new E3();
        actionListeners[17] = new F3();
        actionListeners[18] = new Fsus3();
        actionListeners[19] = new G3();
        actionListeners[20] = new Gsus3();
        actionListeners[21] = new A3();
        actionListeners[22] = new Asus3();
        actionListeners[23] = new B3();
        actionListeners[24] = new C4();
        actionListeners[25] = new Csus4();
        actionListeners[26] = new D4();
        actionListeners[27] = new Dsus4();
        actionListeners[28] = new E4();
        actionListeners[29] = new F4();
        actionListeners[30] = new Fsus4();
        actionListeners[31] = new G4();
        actionListeners[32] = new Gsus4();
        actionListeners[33] = new A4();
        actionListeners[34] = new Asus4();
        actionListeners[35] = new B4();
    }

    public ActionListener getActionListener(int i) {
        return actionListeners[i];
    }

    public void setView(Piano v) {
        this.v = v;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        System.out.println(command);
    }
}
