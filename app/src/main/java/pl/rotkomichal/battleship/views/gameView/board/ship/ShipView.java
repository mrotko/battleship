package pl.rotkomichal.battleship.views.gameView.board.ship;


import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.VelocityTracker;
import android.widget.LinearLayout;

import pl.rotkomichal.battleship.R;

import pl.rotkomichal.battleship.views.gameView.board.BoardFieldView;

public class ShipView extends LinearLayout {

    private VelocityTracker velocityTracker = null;

    protected LinearLayout shipLL;

    protected Ship ship;

    public ShipView(Context context, Ship ship) {
        super(context);
        this.ship = ship;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_ship, this);
        shipLL = findViewById(R.id.llShip);
        createLongClickListener();
        createClickListener();
    }

    private void createClickListener() {
        this.setOnClickListener(view -> toggleDirection());
    }

    public int getSize() {
        return ship.getFields().size();
    }

    public void toggleDirection() {
        if(ship.getDirection() == Direction.HORIZONTAL) {
            setDirection(Direction.VERTICAL);
        } else {
            setDirection(Direction.HORIZONTAL);
        }
    }

    public void setDirection(Direction direction) {
        if(direction == Direction.HORIZONTAL) {
            shipLL.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            shipLL.setOrientation(LinearLayout.VERTICAL);
        }
        ship.setDirection(direction);
        redraw();
    }

    public void redraw() {
        shipLL.removeAllViews();
        ship.getFields().forEach(field -> {
            BoardFieldView boardFieldView = new BoardFieldView(getContext(), field);
            boardFieldView.setClickable(false);
            boardFieldView.redraw();
            shipLL.addView(boardFieldView);
        });
    }
    private void createLongClickListener() {
        this.setOnLongClickListener((view) -> {
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
            ClipData dragData = new ClipData((CharSequence) view.getTag(), new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
            DragShadowBuilder shadowBuilder = new DragShadowBuilder(this);
            this.startDragAndDrop(dragData, shadowBuilder, ship, 0);
            return true;
        });
    }

    public Ship getShip() {
        return ship;
    }
}
