package pl.rotkomichal.battleship.views.gameView.chooseGameType.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.rotkomichal.battleship.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ExpandedWidget extends LinearLayout {

    private List<Consumer<View>> listenerActions = new ArrayList<>();

    private boolean isExpanded = false;

    private TextView headerTextView;

    private View contentView;

    private ImageView imageView;

    private Drawable arrow_up;

    private Drawable arrow_down;

    private LinearLayout expandedWidgetLayout;

    public ExpandedWidget(Context context) {
        super(context);
        init();
    }

    public ExpandedWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandedWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.expanded_widget, this);

        arrow_up = getResources().getDrawable(android.R.drawable.arrow_up_float, null);
        arrow_down = getResources().getDrawable(android.R.drawable.arrow_down_float, null);

        imageView = findViewById(R.id.iv_expanded_widget_arrow);
        imageView.setImageDrawable(arrow_up);

        expandedWidgetLayout = findViewById(R.id.ll_expanded_widget);

        headerTextView = findViewById(R.id.tv_expanded_widget_header);

        addOnClickListenerAction(view -> {
            if(isExpanded) {
                collapse();
            } else {
                expand();
            }
        });

        setOnClickListener(view -> listenerActions.forEach(action -> action.accept(view)));
    }

    private void toggleArrow() {
        imageView.setImageDrawable(isExpanded ? arrow_down : arrow_up);
    }

    public void setHeader(String header) {
        headerTextView.setText(header);
    }

    public String getHeader() {
        return headerTextView.getText().toString();
    }

    public void expand() {
        isExpanded = true;
        contentView.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int targetHeight = contentView.getMeasuredHeight();
        contentView.getLayoutParams().height = 1;
        contentView.setVisibility(VISIBLE);
        Animation a = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                contentView.getLayoutParams().height = interpolatedTime == 1
                        ? LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                contentView.requestLayout();
            }
        };
        a.setDuration((int)(targetHeight / contentView.getContext().getResources().getDisplayMetrics().density));
        contentView.startAnimation(a);
        toggleArrow();
    }

    public void collapse() {
        isExpanded = false;

        int initialHeight = contentView.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1) {
                    contentView.setVisibility(View.GONE);
                } else {
                    contentView.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    contentView.requestLayout();
                }
            }
        };

        a.setDuration((int)(initialHeight / contentView.getContext().getResources().getDisplayMetrics().density));
        contentView.startAnimation(a);

        toggleArrow();
    }

    public void setContent(View content) {
        expandedWidgetLayout.removeView(contentView);
        contentView = content;
        contentView.setVisibility(View.GONE);
        expandedWidgetLayout.addView(contentView);
    }

    public void addOnClickListenerAction(Consumer<View> action) {
        listenerActions.add(action);
    }

    public void setOnClickListenerAction(Consumer<View> action) {
        listenerActions.clear();
        listenerActions.add(action);
    }

    public void clearOnClickListenerActions() {
        listenerActions.clear();
    }
}
