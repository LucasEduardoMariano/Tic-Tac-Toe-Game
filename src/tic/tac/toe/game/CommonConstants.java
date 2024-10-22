package tic.tac.toe.game;
import java.awt.*;

public class CommonConstants {
    
    public static final Color BACKGROUND_COLOR = Color.decode("#011627");
    public static final Color X_COLOR = Color.decode("#E71036");
    public static final Color O_COLOR = Color.decode("#2EC486");
    public static final Color BAR_COLOR = Color.decode("#FF9F1C");
    public static final Color BOARD_COLOR = Color.decode("#FDFFFC");
    
    public static final Dimension FRAME_SIZE = new Dimension(540, 760);
    public static final Dimension BOARD_SIZE = new Dimension((int)(FRAME_SIZE.width * 0.96),(int)(FRAME_SIZE.height * 0.60));
    public static final Dimension BUTTON_SIZE = new Dimension(100,100);
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width/3),(int)(FRAME_SIZE.height/6));

    public static final String X_LABEL = "x";
    public static final String O_LABEL = "o";
    public static final String SCORE_LABEL = "X:0 | O:0";
}
