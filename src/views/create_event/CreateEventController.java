package views.create_event;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {

    @FXML
    private JFXTextField eventNameTextField;

    @FXML
    private JFXButton closeDialogButton;

    @FXML
    private JFXButton createButton;

    @FXML
    private JFXTextField venueTextField;

    @FXML
    private JFXTextField ticketPriceTextField;

    @FXML
    private JFXTextField overHeadCostsTextField;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private Label dialogLabel;

    @FXML
    private JFXButton sellTicketButton;

    @FXML
    private AnchorPane soldTicketsPane;

    @FXML
    private Label soldTicketsLabel;

    private boolean editMode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpValidators();
    }

    private void setUpValidators() {
        RequiredFieldValidator eventNameRequired = new RequiredFieldValidator();
        eventNameRequired.setMessage("Event Name Required");
        eventNameTextField.setValidators(eventNameRequired);

        eventNameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            eventNameTextField.validate();
        });

        NumberValidator moneyValidator = new NumberValidator();
        moneyValidator.setNumberStringConverter(numberStringConverter);
        moneyValidator.setMessage("Invalid Money Value");

        ticketPriceTextField.setValidators(moneyValidator);
        overHeadCostsTextField.setValidators(moneyValidator);

        ticketPriceTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            ticketPriceTextField.validate();
        });
        overHeadCostsTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            overHeadCostsTextField.validate();
        });

    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        if (editMode) {
            createButton.setText("Save");
            dialogLabel.setText("Event Viewer");
            closeDialogButton.setText("Delete");
            soldTicketsPane.setVisible(true);
            sellTicketButton.setVisible(true);
            closeDialogButton.setStyle("-fx-border-color: crimson");
        } else {
            createButton.setText("Create");
            dialogLabel.setText("Create Event");
            closeDialogButton.setText("Cancel");
            sellTicketButton.setVisible(false);
            soldTicketsPane.setVisible(false);
            closeDialogButton.setStyle("-fx-border-color: #CDDC39");
        }
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    private NumberStringConverter numberStringConverter = new NumberStringConverter(){
        @Override
        public Number fromString(String string) {
            try {
                if (string == null) {
                    return 0;
                }
                string = string.trim();
                if (string.length() < 1) {
                    return 0;
                }
                NumberFormat parser = getNumberFormat();
                ParsePosition parsePosition = new ParsePosition(0);
                Number result = parser.parse(string, parsePosition);
                final int index = parsePosition.getIndex();
                if (index == 0 || index < string.length()) {
                    throw new ParseException("Unparseable number: \"" + string + "\"", parsePosition.getErrorIndex());
                }
                if (result.doubleValue() < 0) {
                    throw new ParseException("Negative Money Values do not make sense: \"" + string + "\"", 0);
                }
                return result;
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    };
}
