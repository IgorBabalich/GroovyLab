package org.fxmisc.richtext;

import java.util.Collection;
import java.util.Collections;

import javafx.beans.NamedArg;
import org.fxmisc.richtext.model.Codec;
import org.fxmisc.richtext.model.EditableStyledDocument;
import org.fxmisc.richtext.model.SimpleEditableStyledDocument;
import org.fxmisc.richtext.model.StyledText;

/**
 * Text area that uses style classes to define style of text segments and paragraph segments.
 */
public class StyleClassedTextArea extends StyledTextArea<Collection<String>, Collection<String>> {

    public StyleClassedTextArea(@NamedArg("document") EditableStyledDocument<Collection<String>, StyledText<Collection<String>>, Collection<String>> document,
                                @NamedArg("preserveStyle") boolean preserveStyle) {
        super(Collections.<String>emptyList(),
                (paragraph, styleClasses) -> paragraph.getStyleClass().addAll(styleClasses),
                Collections.<String>emptyList(),
                (text, styleClasses) -> text.getStyleClass().addAll(styleClasses),
                document, preserveStyle
        );

        setStyleCodecs(
                Codec.collectionCodec(Codec.STRING_CODEC),
                StyledText.codec(Codec.collectionCodec(Codec.STRING_CODEC))
        );
    }
    public StyleClassedTextArea(@NamedArg("preserveStyle") boolean preserveStyle) {
        this(
                new SimpleEditableStyledDocument<>(
                    Collections.<String>emptyList(), Collections.<String>emptyList()
                ), preserveStyle);
    }

    /**
     * Creates a text area with empty text content.
     */
    public StyleClassedTextArea() {
        this(true);
    }

    /**
     * Convenient method to assign a single style class.
     */
    public void setStyleClass(int from, int to, String styleClass) {
        setStyle(from, to, Collections.singletonList(styleClass));
    }

    @Override
    public void scrollXBy(double deltaX) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void scrollYBy(double deltaY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void scrollXToPixel(double pixel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void scrollYToPixel(double pixel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
