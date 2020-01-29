package spacegame3.userinterface.startscreen;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

import java.util.Optional;
import java.util.Set;

public class QuestionBox {

    private final TextInputDialog userAnswer;
    private final ChoiceDialog<String> userAnswerChoice;

    public QuestionBox() {
        userAnswer = new TextInputDialog();
        userAnswer.setHeaderText(null);
        userAnswer.initStyle(StageStyle.UNDECORATED);

        userAnswerChoice = new ChoiceDialog<>();
        userAnswerChoice.setHeaderText(null);
        userAnswerChoice.initStyle(StageStyle.UNDECORATED);
    }

    public String getAnswer(String question) {
        userAnswer.setContentText(question);
        userAnswer.getEditor().clear();
        Optional<String> result = userAnswer.showAndWait();
        return result.orElse("");
    }

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
