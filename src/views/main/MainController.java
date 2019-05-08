package views.main;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Stats;
import views.dashboard.DashboardController;
import views.events.EventsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Node dashboardView;
    private Node eventsView;
    private FXMLLoader dashboardLoader;
    private FXMLLoader eventsLoader;

    public MainController() {
    }

    @FXML
    private JFXListView<Label> menu;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardView = initDashboardView();
        eventsView = initEventsView();
    }

    @FXML
    void loadDashboardView(MouseEvent event) {
        DashboardController controller = dashboardLoader.getController();
        EventsController eventsController = eventsLoader.getController();

        Stats stats = new Stats();
        stats.setExpenses(eventsController.expenses());
        stats.setNumberOfEvents(eventsController.eventCount());
        stats.setNumberOfSoldTickets(eventsController.soldTicketsCount());
        stats.setRevenue(eventsController.revenue());

        controller.setStats(stats);
        switchView(dashboardView);
    }

    private Node initDashboardView() {
        Node dashboardView = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/dashboard/dashboard.fxml"));

        try {
            dashboardView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert dashboardView != null;
        dashboardLoader = loader;

        return dashboardView;
    }

    private Node initEventsView() {
        Node eventsView = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/events/events.fxml"));

        try {
            eventsView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert eventsView != null;

        eventsLoader = loader;
        return eventsView;
    }

    public void loadMainView() {
        switchView(eventsView);
    }

    @FXML
    void loadEventsView(MouseEvent event) {
        switchView(eventsView);
    }

    void switchView(Node node) {
        anchorPane.getChildren().clear();
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        anchorPane.getChildren().add(node);
    }

}
