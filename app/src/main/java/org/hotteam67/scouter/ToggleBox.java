package org.hotteam67.scouter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;

import org.xml.sax.helpers.AttributesImpl;

public class ToggleBox extends RadioButton {

    public ToggleBox(Context context)
    {
        super(context);
        SetListener();
    }

    public ToggleBox(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        SetListener();
    }

    public ToggleBox(Context context, AttributeSet attributeSet, int defStyleAttr)
    {
        super(context, attributeSet, defStyleAttr);
        SetListener();
    }

    public void SetListener()
    {
        View.OnClickListener radioButtonOnTouchListener = new View.OnClickListener() {
            boolean checked = false;
            @Override
            public void onClick(View v) {
                if (checked)
                {
                    RadioButton view = (RadioButton)v;
                    view.setChecked(false);
                    checked = false;
                }
                else if (((RadioButton) v).isChecked()) {
                    checked = true;
                    RadioButton view = (RadioButton)v;
                }
            }
        };
        this.setOnClickListener(radioButtonOnTouchListener);
    }
}
