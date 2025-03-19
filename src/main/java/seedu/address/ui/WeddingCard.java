package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wedding.Wedding;

/**
 * A UI component that displays information of a {@code Wedding}
 */
public class WeddingCard extends UiPart<Region> {
    private static final String FXML = "WeddingListCard.fxml";

    public final Wedding wedding;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label id;

    @FXML
    private boolean selected;

    /**
     * Creates a {@code WeddingCode} with the given {@code Wedding} and index to display.
     */
    public WeddingCard(Wedding wedding, int displayedIndex) {
        super(FXML);
        this.wedding = wedding;
        id.setText(displayedIndex + ". ");
        name.setText(wedding.getTitle().toString());
        date.setText(wedding.getDate().toString());
        selected = false;
    }

    /**
     * Sets a particular wedding as selected
     */
    public void select() {
        cardPane.setStyle("-fx-background-color: #000000");
    }

    public void deselect() {
        cardPane.setStyle("-fx-background-color: #110000");
    }
}
