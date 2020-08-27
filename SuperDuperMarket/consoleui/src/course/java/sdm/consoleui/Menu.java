package course.java.sdm.consoleui;

import java.util.List;

public interface Menu {
    void showMe();

    boolean readUserChoiceAndValidate();

    void activateChoice();
}
