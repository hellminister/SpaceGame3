package spacegame3.userinterface.startscreen;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

import java.util.Optional;
import java.util.Set;

/**
 * Creates a dialog box which asks given questions to the player
 */
public class QuestionBox {

    /**
     * The dialog box
     */
    private final TextInputDialog userAnswer;

    /**
     * A choice list (reusable)
     */
    private final ChoiceDialog<String> userAnswerChoice;

    /**
     * Initialize the dialog box
     */
    public QuestionBox() {
        userAnswer = new TextInputDialog();
        userAnswer.setHeaderText(null);
        userAnswer.initStyle(StageStyle.UNDECORATED);

        userAnswerChoice = new ChoiceDialog<>();
        userAnswerChoice.setHeaderText(null);
        userAnswerChoice.initStyle(StageStyle.UNDECORATED);
    }

    /**
     * Asks a question necessitating a Text answer
     * @param question the question to ask
     * @return the question
     */
    public String getAnswer(String question) {
        userAnswer.setContentText(question);
        userAnswer.getEditor().clear();
        Optional<String> result = userAnswer.showAndWait();
        return result.orElse("");
    }

    /**
     * Asks a multiple choices question
     * @param question the question to ask
     * @param choices  the possible choices
     * @return The answer
     */
    public String getAnswer(String question, Set<String> choices) {
        String[] choicesA = choices.toArray(new String[0]);
        userAnswerChoice.setContentText(question);
        userAnswerChoice.getItems().clear();
        userAnswerChoice.getItems().addAll(choicesA);
        userAnswerChoice.setSelectedItem(choicesA[0]);

        Optional<String> result = userAnswerChoice.showAndWait();
        return result.orElse("");
    }
}
