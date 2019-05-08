package views.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Stats;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private Stats stats;
    public DashboardController(){
        stats = new Stats();
    }

    @FXML
    private Label eventsCounterLabel;

    @FXML
    private Label soldTicketsCounterLabel;

    @FXML
    private Label revenueLabel;

    @FXML
    private Label expensesLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStats(Stats stats) {
         this.stats = stats;

         eventsCounterLabel.setText(Integer.toString(stats.getNumberOfEvents()));
         soldTicketsCounterLabel.setText(Integer.toString(stats.getNumberOfSoldTickets()));
         revenueLabel.setText(Double.toString(stats.getRevenue()));
         expensesLabel.setText(Double.toString(stats.getExpenses()));
    }
}
