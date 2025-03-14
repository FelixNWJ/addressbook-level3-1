package calofit.ui;

import java.time.Clock;
import java.time.Duration;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import calofit.MainApp;
import calofit.commons.core.LogsCenter;
import calofit.commons.core.Timer;
import calofit.commons.util.StringUtil;
import calofit.logic.Logic;
import calofit.logic.NotificationHelper;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/calofit_logo.png";

    private Logic logic;
    private MainWindow mainWindow;
    private Timer timer;
    private NotificationWindow notificationWindow;
    private NotificationHelper notificationHelper;

    public UiManager(Logic logic, Timer timer) {
        super();
        this.logic = logic;
        this.timer = timer;
        this.notificationHelper = new NotificationHelper(Clock.systemDefaultZone());
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }

        notificationHelper.execute(logic.getModel()).ifPresent(s -> {
            notificationWindow = new NotificationWindow(s);
            notificationWindow.show();
        });

        timer.registerPeriodic(Duration.ofMinutes(10), () -> {
            Optional<String> notifMessage = notificationHelper.execute(logic.getModel());
            notifMessage.ifPresent(s -> {
                if (notificationWindow != null) {
                    notificationWindow.getRoot().hide();
                }
                notificationWindow = new NotificationWindow(s);
                notificationWindow.show();
            });
        });
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}
