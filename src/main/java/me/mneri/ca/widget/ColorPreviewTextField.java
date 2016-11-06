package me.mneri.ca.widget;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.event.DocumentEvent;

import me.mneri.ca.event.DocumentListenerAdapter;
import me.mneri.ca.util.Colors;

public class ColorPreviewTextField extends IconTextField {
    public interface ColorListener {
        void onColor(Color color);
    }

    private Color mColor = Color.BLACK;
    private ArrayList<ColorListener> mListener = new ArrayList<>();

    public ColorPreviewTextField() {
        super(7);

        init();
    }

    public ColorPreviewTextField(Color color) {
        super(7);

        init();
        setColor(color);
    }

    public void addColorListener(ColorListener listener) {
        mListener.add(listener);
    }

    public Color getColor() {
        return mColor;
    }

    private void init() {
        getDocument().addDocumentListener(new DocumentListenerAdapter() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChange();
            }

            private void onTextChange() {
                try {
                    mColor = Color.decode(getText());
                    setIcon(new ColorPreviewIcon(mColor));
                    notifyColorListeners(mColor);
                } catch (NumberFormatException ignored) { }
            }
        });
    }

    private void notifyColorListeners(Color color) {
        for (ColorListener listener : mListener)
            listener.onColor(color);
    }

    public void removeColorListener(ColorListener listener) {
        mListener.remove(listener);
    }

    public void setColor(Color color) {
        mColor = color;
        setIcon(new ColorPreviewIcon(color));
        setText(Colors.toHexString(color));
        notifyColorListeners(color);
    }
}
