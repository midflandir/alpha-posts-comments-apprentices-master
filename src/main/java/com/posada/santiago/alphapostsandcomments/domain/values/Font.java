package com.posada.santiago.alphapostsandcomments.domain.values;

import co.com.sofka.domain.generic.ValueObject;

public class Font implements ValueObject<String> {

private final String font;

public Font(String font) {
        this.font = font;
        }

@Override
public String value() {
        return font;
        }
        }
