package com.cup.ycode.commons.dto.message;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractMessage implements Serializable {
    private String to;
    private String body;
}
