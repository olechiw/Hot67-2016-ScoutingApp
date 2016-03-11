package org.hotteam67.scouter;

// A custom wrapper for RadioButton which uses
public class ToggleableRadioButton extends android.widget.RadioButton {

    public ToggleableRadioButton(android.content.Context context)
    {
        super(context);
    }

    @Override
    public void toggle() {
        if(isChecked()) {
            if(getParent() instanceof android.widget.RadioGroup) {
                ((android.widget.RadioGroup)getParent()).clearCheck();
            }
        } else {
            setChecked(true);
        }
    }
}