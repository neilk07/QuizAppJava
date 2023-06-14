package FinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Main class
public class QuizUI extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton nextButton;

    public QuizUI(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;

        setTitle("Quiz Application");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());


        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        ButtonGroup optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        add(nextButton, BorderLayout.SOUTH);

        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextButtonClick();
            }
        });

        displayQuestion(currentQuestionIndex);
    }

    private void displayQuestion(int questionIndex) {
        Question question = questions.get(questionIndex);

        questionLabel.setText(question.getQuestion());

        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            optionButtons[i].setText(options.get(i));
            optionButtons[i].setSelected(false);
            optionButtons[i].setEnabled(true);
            optionButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nextButton.setEnabled(true);
                }
            });
        }

        nextButton.setEnabled(false);
    }



    private void handleNextButtonClick() {
        int selectedOption = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedOption = i + 1;
                break;
            }
        }

        if (selectedOption != -1) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            if (selectedOption == currentQuestion.getCorrectOption()) {
                score++;
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                showResult();
            }
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz completed. Your score: " + score + "/" + questions.size(),
                "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public static void main(String[] args) {
        // Create quiz questions //
        Question question1 = new Question("Which OOP concept refers to the ability of an object " +
                "to have multiple forms of implementation?",
                List.of("Inheritance" ,"Encapsulation" , " Polymorphism" , "Abstraction"), 3);
        Question question2 = new Question("Which of the following keywords is used in Java to implement " +
                "inheritance between classes?",
                List.of("this", "extends", "new", "super"), 2);
        Question question3 = new Question("Which access modifier allows a member to be accessed" +
                " only within its own class?",
                List.of("public", "private", "default public", "protected"), 2);
        Question question4 = new Question("What is the term used to describe a blueprint for " +
                "creating objects?",
                List.of("Variable", "Method", "Class", "Object"), 3);
        Question question5 = new Question("Which keyword in Java is used to invoke a " +
                "superclass constructor within a subclass?",
                List.of("this", "extends", "new", "super"), 4);

        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);

        // Create and show the quiz UI
        QuizUI quizUI = new QuizUI(questions);
        quizUI.setVisible(true);
    }
}
