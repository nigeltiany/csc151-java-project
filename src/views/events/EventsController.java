package views.events;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import models.Event;
import views.create_event.CreateEventController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventsController implements Initializable {

    private JFXDialog createEventDialog = new JFXDialog();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTreeTableView<Event> table;

    private ObservableList<Event> events;

    private FXMLLoader dialogLoader;
    private Node dialogView;
    private CreateEventController createEventController;
    private Event selectedEvent;

    public EventsController() {
        events = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createColumns();
        dialogView = initDialog();
        assembleListeners();
    }

    private void createColumns() {
        JFXTreeTableColumn<Event, String> nameColumn = new JFXTreeTableColumn<>("Event");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getName());

        JFXTreeTableColumn<Event, String> venueColumn = new JFXTreeTableColumn<>("Venue");
        venueColumn.setPrefWidth(150);
        venueColumn.setCellValueFactory(param -> param.getValue().getValue().getVenue());

        JFXTreeTableColumn<Event, String> dateColumn = new JFXTreeTableColumn<>("Date");
        dateColumn.setPrefWidth(150);
        dateColumn.setCellValueFactory(param -> param.getValue().getValue().getDate());

        JFXTreeTableColumn<Event, Double> ticketPriceColumn = new JFXTreeTableColumn<>("Ticket Price");
        ticketPriceColumn.setPrefWidth(150);
        ticketPriceColumn.setCellValueFactory(param -> param.getValue().getValue().getTicketPrice().asObject());

        JFXTreeTableColumn<Event, Integer> soldTicketsColumn = new JFXTreeTableColumn<>("Tickets Sold");
        soldTicketsColumn.setPrefWidth(150);
        soldTicketsColumn.setCellValueFactory(param -> param.getValue().getValue().getSoldTickets().asObject());

        JFXTreeTableColumn<Event, Double> overHeadCosts = new JFXTreeTableColumn<>("OverHead Costs");
        overHeadCosts.setPrefWidth(150);
        overHeadCosts.setCellValueFactory(param -> param.getValue().getValue().getOverheadCosts().asObject());

        events = FXCollections.observableArrayList();

        final TreeItem<Event> root = new RecursiveTreeItem<>(events, RecursiveTreeObject::getChildren);
        table.getColumns().setAll(nameColumn, venueColumn, dateColumn, ticketPriceColumn, soldTicketsColumn, overHeadCosts);

        table.setRoot(root);
        table.setShowRoot(false);
        listenToSelectionEvents(table);
    }

    private void listenToSelectionEvents(JFXTreeTableView<Event> tbl) {
        tbl.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem<Event>>) c -> {
            if (tbl.getSelectionModel().getSelectedItems().size() == 0) {
                clearFields();
                return;
            }
            selectedEvent = tbl.getSelectionModel().getSelectedItem().getValue();
            showDialog(true);
        });
    }

    private Node initDialog() {
        Node dialogView = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/create_event/create_event.fxml"));

        try {
            dialogView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialogLoader = loader;
        createEventController = loader.getController();
        stackPane.setVisible(false);

        createEventDialog.getChildren().add(dialogView);
        createEventDialog.setDialogContainer(stackPane);
        createEventDialog.setOverlayClose(false);
        createEventDialog.setAlignment(Pos.CENTER);
        createEventDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

        JFXDepthManager.setDepth(createEventDialog, 2);

        return dialogView;
    }

    private void evaluateFields() {
        if (selectedEvent != null) {
            ((JFXTextField)dialogView.lookup("#eventNameTextField")).setText(selectedEvent.getName().getValue());
            ((JFXTextField)dialogView.lookup("#venueTextField")).setText(selectedEvent.getVenue().getValue());
            ((JFXTextField)dialogView.lookup("#ticketPriceTextField")).setText(Double.toString(selectedEvent.getTicketPrice().getValue()));
            ((JFXTextField)dialogView.lookup("#overHeadCostsTextField")).setText(Double.toString(selectedEvent.getOverheadCosts().getValue()));
            ((Label)dialogView.lookup("#soldTicketsLabel")).setText(Integer.toString(selectedEvent.getSoldTickets().getValue()));
            ((JFXDatePicker)dialogView.lookup("#datePicker")).setValue(LocalDate.parse(selectedEvent.getDate().getValue()));
        }
    }

    private void clearFields() {
        ((JFXTextField)dialogView.lookup("#eventNameTextField")).setText("");
        ((JFXTextField)dialogView.lookup("#venueTextField")).setText("");
        ((JFXTextField)dialogView.lookup("#ticketPriceTextField")).setText("");
        ((JFXTextField)dialogView.lookup("#overHeadCostsTextField")).setText("");
        ((JFXDatePicker)dialogView.lookup("#datePicker")).setValue(null);
    }

    @FXML
    void showCreatePopUp(ActionEvent e) {
        showDialog(false);
    }

    private void showDialog(boolean editMode) {
        evaluateFields();
        stackPane.setVisible(true);
        createEventController.setEditMode(editMode);
        createEventDialog.show();
    }

    public int eventCount() {
        return events.size();
    }

    private void assembleListeners() {
        dialogCloseListener();
        cancelButtonListener();
        createButtonListener();
        sellTicketListener();
    }

    private void dialogCloseListener() {
        createEventDialog.setOnDialogClosed(event -> {
            stackPane.setVisible(false);
            table.getSelectionModel().clearSelection();
            selectedEvent = null;
            clearFields();
        });
    }

    private void sellTicketListener() {
        dialogView.lookup("#sellTicketButton").setOnMouseClicked(event -> {
            events.get(table.getSelectionModel().getSelectedIndex()).sellTicket();
            ((Label)dialogView.lookup("#soldTicketsLabel")).setText(Integer.toString(selectedEvent.getSoldTickets().getValue()));
        });
    }

    private void cancelButtonListener () {
        dialogView.lookup("#closeDialogButton").setOnMouseClicked(event -> {
            if (createEventController.isEditMode()) {
                events.remove(table.getSelectionModel().getSelectedItem().getValue());
            }
            createEventDialog.close();
        });
    }

    private void createButtonListener () {
        dialogView.lookup("#createButton").setOnMouseClicked(event -> {
            JFXTextField name = (JFXTextField) dialogView.lookup("#eventNameTextField");
            JFXTextField venue = (JFXTextField) dialogView.lookup("#venueTextField");
            JFXTextField price = (JFXTextField) dialogView.lookup("#ticketPriceTextField");
            JFXTextField overHead = (JFXTextField) dialogView.lookup("#overHeadCostsTextField");
            JFXDatePicker date = (JFXDatePicker) dialogView.lookup("#datePicker");

            Event newOrEditedEvent;
            if (selectedEvent == null) {
                newOrEditedEvent = new Event();
                if (name.getText().isEmpty() || name.getText() == null) {
                    name.validate();
                    return;
                }
            } else {
                newOrEditedEvent = selectedEvent;
            }

            newOrEditedEvent.setName(name.getText());
            newOrEditedEvent.setVenue(venue.getText());
            newOrEditedEvent.setTicketPrice(price.getText() != null && !price.getText().isEmpty() ? Double.parseDouble(price.getText()) : 0.0);
            newOrEditedEvent.setOverheadCosts(overHead.getText() != null && !overHead.getText().isEmpty() ? Double.parseDouble(overHead.getText()) : 0.0);
            newOrEditedEvent.setDate(date.getValue() != null ? date.getValue().toString() : LocalDate.now().toString());

            if (selectedEvent == null) {
                events.add(newOrEditedEvent);
            } else {
                events.iterator().forEachRemaining(editedEvent -> {
                    if (editedEvent.getEventID() == selectedEvent.getEventID()) {
                        editedEvent = newOrEditedEvent;
                    }
                });
            }

            createEventDialog.close();
        });
    }

    public int soldTicketsCount() {
        final int[] tickets = {0};
        events.forEach(event -> {
            tickets[0] += event.getSoldTickets().getValue();
        });
        return tickets[0];
    }

    public double revenue() {
        final double[] revenue = {0};
        events.forEach(event -> {
            revenue[0] += event.getSoldTickets().getValue() * event.getTicketPrice().getValue();
        });
        return revenue[0];
    }

    public double expenses() {
        final double[] expense = {0};
        events.forEach(event -> {
            expense[0] += event.getOverheadCosts().getValue();
        });
        return expense[0];
    }
}
