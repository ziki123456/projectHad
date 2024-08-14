package cz.ziki.had;

/**
 * Represents a single segment of the snake's body.
 */
public class BodyPiece {

    public final Rect rect;

    /**
     * Constructs a new BodyPiece with the specified rectangular area.
     *
     * @param rect the rectangular area defining the position and size of the body segment
     */
    public BodyPiece(Rect rect) {

        this.rect = rect;

    }
}